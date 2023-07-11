package server.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import server.entities.Book;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class BookDeserializer implements Deserializer<Book> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void configure(Map<String, ?> configs, boolean isKey) {
//        String propertyName = isKey ? "key.deserializer.encoding" : "value.deserializer.encoding";
//        Object encodingValue = configs.get(propertyName);
//        if (encodingValue == null) {
//            encodingValue = configs.get("deserializer.encoding");
//        }
//
//        if (encodingValue instanceof String) {
//            this.encoding = (String)encodingValue;
//        }
    }

    public Book deserialize(String topic, byte[] data) {
        try {
            return data == null ? null : objectMapper.readValue(new String(data, StandardCharsets.UTF_8), Book.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }
}
