package ru.bookfind.parsers.labirint.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import server.entities.Book;

import java.util.Map;

public class BookSerializer implements Serializer<Book> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    //private String encoding = StandardCharsets.UTF_8.name();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
//        String propertyName = isKey ? "key.serializer.encoding" : "value.serializer.encoding";
//        Object encodingValue = configs.get(propertyName);
//        if (encodingValue == null)
//            encodingValue = configs.get("serializer.encoding");
//        if (encodingValue instanceof String)
//            encoding = (String) encodingValue;
    }

    @Override
    public byte[] serialize(String topic, Book data) {
        try {
            if (data == null)
                return null;
            else
                return objectMapper.writeValueAsBytes(data);
                //return data.getBytes(encoding);
        }
//        catch (UnsupportedEncodingException e) {
//            throw new SerializationException("Error when serializing string to byte[] due to unsupported encoding " + encoding);
//        }
        catch (Exception e) {
            throw new SerializationException("Error when serializing Book to byte[]");
        }
    }
}
