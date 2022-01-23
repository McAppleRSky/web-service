package frontend.servlet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ServletableTest implements Servletable{

    @Test
    void InitTest() {
        assertEquals("/api/latest", WS_URL);
    }

}
