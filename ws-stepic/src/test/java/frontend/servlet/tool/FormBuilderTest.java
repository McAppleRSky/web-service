package frontend.servlet.tool;

import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;

class FormBuilderTest {

    @Test
    void toStringNegativeTest() {
        String expected =
                String.format(
                        "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                                + "</head><body><div style=\"visibility: visible; overflow: visible; position: absolute; left: 100px; top: 100px;\">"
                                + "<form action=\"" + "%s"
                                + "\" method=\"" + "%s"
                                + "\"><label for=\"login\">user name:</label>"
                                + "<input type=\"text\" name=\"login\">"
                                + "<label for=\"password\">user pass:</label>"
                                + "<input type=\"text\" name=\"password\">"
                                + "<input type=\"submit\" value=\"" + "%s"
                                + "\"></form></div></body></html>",
                        null, HttpMethod.GET, null);
        assertEquals(expected, new FormBuilder().toString());
    }

    @Test
    void toStringPositiveTest() {
        String path = "/" + randomAlphabetic(3),
                value = randomAlphabetic(8),
                expected =
                String.format(
                        "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                                + "</head><body><div style=\"visibility: visible; overflow: visible; position: absolute; left: 100px; top: 100px;\">"
                                + "<form action=\"" + "%s"
                                + "\" method=\"" + "%s"
                                + "\"><label for=\"login\">user name:</label>"
                                + "<input type=\"text\" name=\"login\">"
                                + "<label for=\"password\">user pass:</label>"
                                + "<input type=\"text\" name=\"password\">"
                                + "<input type=\"submit\" value=\"" + "%s"
                                + "\"></form></div></body></html>",
                        path, HttpMethod.GET, value),
                actual = new FormBuilder(path)
                        .setSubmitButtonValue(value)
                        .toString();
        assertEquals(expected, actual);
    }

    @Test
    void setPath() {
    }

    @Test
    void setPostMethod() {
        String path = "/" + randomAlphabetic(3),
                value = randomAlphabetic(8),
                expected =
                        String.format(
                                "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                                        + "</head><body><div style=\"visibility: visible; overflow: visible; position: absolute; left: 100px; top: 100px;\">"
                                        + "<form action=\"" + "%s"
                                        + "\" method=\"" + "%s"
                                        + "\"><label for=\"login\">user name:</label>"
                                        + "<input type=\"text\" name=\"login\">"
                                        + "<label for=\"password\">user pass:</label>"
                                        + "<input type=\"text\" name=\"password\">"
                                        + "<input type=\"submit\" value=\"" + "%s"
                                        + "\"></form></div></body></html>",
                                path, HttpMethod.POST, value),
                actual = new FormBuilder(path)
                        .setPostMethod()
                        .setSubmitButtonValue(value)
                        .toString();
        assertEquals(expected, actual);
        actual = new FormBuilder(path)
                .setMethod(HttpMethod.POST)
                .setSubmitButtonValue(value)
                .toString();
        assertEquals(expected, actual);
    }

    @Test
    void setSubmitButtonValue() {
    }

    @Test
    void testToString() {
    }
}