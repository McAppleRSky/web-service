package frontend.servlet.tool;

import frontend.servlet.tool.ServletHelper;
import frontend.servlet.tool.UrlResolveResult;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServletHelperUrlResolverResultTest {

    @Test
    void servletUserDoPostEmpty_getUsrProf_test() {
        String
                tst_PAGE_URL = "/usr"
                ,tst_host = "tst://loc:80"
                ,tst_WS_URL = "/api"
                ,tstUrlStr = tst_host + tst_WS_URL + tst_PAGE_URL
                ;
        ServletHelper servletHelper = new ServletHelper();
        servletHelper.setSplitter(tst_PAGE_URL);
        StringBuffer tstUrl = new StringBuffer(tstUrlStr);
        UrlResolveResult expected = UrlResolveResult.EMPTY,
                actual = servletHelper.resolve(tstUrl);
        assertEquals(expected, actual);
    }

    @Test
    void servletUserDoPostEmpty_slash_test() {
        String
                tst_PAGE_URL = "/usr"
                ,tst_host = "tst://loc:80"
                ,tst_WS_URL = "/api"
                ,tstUrlStr = tst_host + tst_WS_URL + tst_PAGE_URL + "/"
                ;
        ServletHelper servletHelper = new ServletHelper();
        servletHelper.setSplitter(tst_PAGE_URL);
        StringBuffer tstUrl = new StringBuffer(tstUrlStr);
        UrlResolveResult expected = UrlResolveResult.EMPTY,
                actual = servletHelper.resolve(tstUrl);
        assertEquals(expected, actual);
    }

    @Test
    void servletUserDoPostNewTest() {
        String
                tst_PAGE_URL = "/usr"
                ,tst_host = "tst://loc:80"
                ,tst_WS_URL = "/api"
                ,tstUrlStr = tst_host + tst_WS_URL + tst_PAGE_URL + "/new"
                ;
        ServletHelper servletHelper = new ServletHelper();
        servletHelper.setSplitter(tst_PAGE_URL);
        StringBuffer tstUrl = new StringBuffer(tstUrlStr);
        UrlResolveResult expected = UrlResolveResult.NEW,
                actual = servletHelper.resolve(tstUrl);
        assertEquals(expected, actual);
    }

    @Test
    void servletUserDoPostOverTest() {
        String
                tst_PAGE_URL = "/usr"
                ,tst_host = "tst://loc:80"
                ,tst_WS_URL = "/api"
                ,tstStrLogin = randomAlphabetic(4)
                ,tstUrlStr = tst_host + tst_WS_URL + tst_PAGE_URL + "/" + tstStrLogin
                ;
        ServletHelper servletHelper = new ServletHelper();
        servletHelper.setSplitter(tst_PAGE_URL);
        StringBuffer tstUrl = new StringBuffer(tstUrlStr);
        UrlResolveResult expected = UrlResolveResult.OVER,
                actual = servletHelper.resolve(tstUrl);
        assertEquals(expected, actual);
    }

}
