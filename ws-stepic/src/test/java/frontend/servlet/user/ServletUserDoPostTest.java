package frontend.servlet.user;

import base.AccountService;
import frontend.servlet.ServletUser;
import frontend.servlet.tool.ScriptSelector;
import frontend.servlet.tool.ScriptStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;

class ServletUserDoPostTest {

    private AccountService accountServiceMock;
    private ServletUser servletUserTest;
    private HttpServletRequest requestTest;
    private HttpServletResponse responseTest;
    private ScriptSelector scriptSelectorMock;

    @BeforeEach
    void init(){
        accountServiceMock = mock(AccountService.class);
        scriptSelectorMock = mock(ScriptSelector.class);
        this.servletUserTest = new ServletUser(accountServiceMock, scriptSelectorMock);
        requestTest = mock(HttpServletRequest.class);
        responseTest = mock(HttpServletResponse.class);
    }

    @Test
    void doPostPositive() {
        String   tstStrLoginParam = "login"
                ,tstStrLogin = randomAlphabetic(4)
                ,tstStrPasswordParam = "password"
                ,tstStrPassword = randomAlphabetic(8)
                ,tstContentType = "text/html;charset=utf-8"
                ;
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        when(testRequest.getParameter(tstStrLoginParam))
                .thenReturn(tstStrLogin);
        when(testRequest.getParameter(tstStrPasswordParam))
                .thenReturn(tstStrPassword);
        when( scriptSelectorMock.servletUserDoPost( any(String.class), any(String.class), any(String[].class) ) )
                .thenReturn(ScriptStatus.SCRIPT_POSITIVE);
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(HttpServletResponse.SC_OK, arg0);
            return null;
        }).when(testResponse).setStatus(anyInt());
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(tstContentType, arg0);
            return null;
        }).when(testResponse).setContentType(anyString());
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            assertEquals(tstStrLogin, arg0);
            assertEquals(tstStrPassword, arg1);
            return null;
        }).when(accountServiceMock).createUser(anyString(), anyString());

        try {
            servletUserTest.doPost(testRequest, testResponse);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void doPostEmptyLogin() {
        String   tstStrLoginParam = "login"
                ,tstStrLogin = ""
                ,tstStrPasswordParam = "password"
                ,tstStrPassword = randomAlphabetic(8)
                ,tstContentType = "text/html;charset=utf-8"
                ;
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        when(testRequest.getParameter(tstStrLoginParam))
                .thenReturn(tstStrLogin);
        when(testRequest.getParameter(tstStrPasswordParam))
                .thenReturn(tstStrPassword);
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(HttpServletResponse.SC_NO_CONTENT, arg0);
            return null;
        }).when(testResponse).setStatus(anyInt());
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(tstContentType, arg0);
            return null;
        }).when(testResponse).setContentType(anyString());
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            assertEquals(tstStrLogin, arg0);
            assertEquals(tstStrPassword, arg1);
            return null;
        }).when(accountServiceMock).createUser(anyString(), anyString());
        try {
            servletUserTest.doPost(testRequest, testResponse);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void doPostEmptyPassword() {
        String   tstStrLoginParam = "login"
                ,tstStrLogin = randomAlphabetic(6)
                ,tstStrPasswordParam = "password"
                ,tstStrPassword = ""
                ,tstContentType = "text/html;charset=utf-8"
                ;
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        when(testRequest.getParameter(tstStrLoginParam))
                .thenReturn(tstStrLogin);
        when(testRequest.getParameter(tstStrPasswordParam))
                .thenReturn(tstStrPassword);
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(HttpServletResponse.SC_NO_CONTENT, arg0);
            return null;
        }).when(testResponse).setStatus(anyInt());
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(tstContentType, arg0);
            return null;
        }).when(testResponse).setContentType(anyString());
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            assertEquals(tstStrLogin, arg0);
            assertEquals(tstStrPassword, arg1);
            return null;
        }).when(accountServiceMock).createUser(anyString(), anyString());
        try {
            servletUserTest.doPost(testRequest, testResponse);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
