package ru.bookfind.parsers.labirint.configs;

import feign.Client;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;

import java.net.InetSocketAddress;
import java.net.Proxy;

//Note:- Do not annotate this class with @Configuration annotation, otherwise this configuration will become global
// i.e. all Feign Clients will inherit this config in that case.
public class FeignClientConfig {
    //@Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("header_1", "value_1");
            requestTemplate.header("header_2", "value_2");
            requestTemplate.header("header_3", "value_3");
        };
    }

    /**
     * Enable this bean if you want to add basic Authorization header
     * for e.g. Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=
     */
    //@Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("username", "password");
    }

//    /**
//     * Enable this bean if you want to setup HTTP proxy for Default Feign Client
//     */
//    @Bean
//    public Client feignClient() {
//        return new Client.Proxied(null, null,
//                new Proxy(Proxy.Type.HTTP,
//                        new InetSocketAddress(proxyHost, proxyPort)));
//    }
//
//    /**
//     * Enable this bean if you want to setup HTTP proxy for ApacheHttpClient Feign Client
//     */
//    @Bean
//    public CloseableHttpClient feignClient() {
//        return HttpClientBuilder.create().setProxy(
//                new HttpHost(proxyHost, proxyPort)).build();
//    }
//
//    /**
//     * Enable this bean if you want to setup HTTP proxy for OkHttpClient Feign Client
//     */
//    @Bean
//    public OkHttpClient okHttpClient() {
//        return new OkHttpClient.Builder()
//                .proxy(new Proxy(Proxy.Type.HTTP,
//                        new InetSocketAddress(proxyHost, proxyPort)))
//                .build();
//    }
}