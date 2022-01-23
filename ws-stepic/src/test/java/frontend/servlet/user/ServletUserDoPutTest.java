package frontend.servlet.user;

import base.AccountService;
import frontend.servlet.ServletUser;
import frontend.servlet.tool.ScriptSelector;
import frontend.servlet.tool.ServletHelper;
import frontend.servlet.tool.UrlResolveResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ServletUserDoPutTest {

    private AccountService accountServiceMock;
    private ServletUser servletUserTest;
    private HttpServletRequest testRequest;
    private HttpServletResponse testResponse;

    @BeforeEach
    void init(){
        accountServiceMock = mock(AccountService.class);
        ScriptSelector scriptSelectorMock = mock(ScriptSelector.class);
        this.servletUserTest = new ServletUser(accountServiceMock, scriptSelectorMock);
        testRequest = mock(HttpServletRequest.class);
        testResponse = mock(HttpServletResponse.class);
    }

    @Test
    void doPutPositiveTest() {
        String   tstStrLoginParam = "login"
                ,tstStrLogin = randomAlphabetic(4)
                ,tstStrPasswordParam = "password"
                ,tstStrPassword = randomAlphabetic(8)
                ,tst_host = "tst://loc:80"
                ,tstUsr = randomAlphabetic(4)
                ,tstSuffix = "/" + tstUsr
                ;
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        when(testRequest.getParameter(tstStrLoginParam))
                .thenReturn(tstStrLogin);
        when(testRequest.getParameter(tstStrPasswordParam))
                .thenReturn(tstStrPassword);
        when(testRequest.getRequestURL())
                .thenReturn(new StringBuffer(tst_host + ServletUser.PAGE_URL + tstSuffix));
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            Object arg2 = invocation.getArgument(2);
            assertEquals(tstUsr, arg0);
            assertEquals(tstStrLogin, arg1);
            assertEquals(tstStrPassword, arg2);
            return null;
        }).when(accountServiceMock).updateUser(anyString(), anyString(), anyString());
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(HttpServletResponse.SC_OK, arg0);
            return null;
        }).when(testResponse).setStatus(anyInt());
        servletUserTest.doPut(testRequest, testResponse);
    }

}
