package ru.bookfind.parsers.labirint;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LabirintParserTest {
    @Mock
    private Connection connection;
    @Mock
    Connection.Response response;
    @Mock
    Document document;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void onlyDigitsLettersUnderlinesTest() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Mockito.when(connection.execute()).thenReturn(response);
        Mockito.when(response.parse()).thenReturn(document);
        LabirintParser labirintParser = new LabirintParser(connection);
        Method onlyDigitsLetters = labirintParser.getClass().getDeclaredMethod("onlyDigitsLetters", String.class);
        onlyDigitsLetters.setAccessible(true);
        Assertions.assertEquals("1Sд", onlyDigitsLetters.invoke(labirintParser, "1Sд_}"));
    }
}
