package frontend.servlet.user;

import base.AccountService;
import frontend.servlet.ServletUser;
import frontend.servlet.tool.ScriptSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ServletUserDoDeleteTest {

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
    void doDeleteTest() {
        String   tstStrLoginParam = "login"
                ,tstStrLogin = randomAlphabetic(4)
                ;
        when(testRequest.getParameter(tstStrLoginParam))
                .thenReturn(tstStrLogin);
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(HttpServletResponse.SC_OK, arg0);
            return null;
        }).when(testResponse).setStatus(anyInt());
        try {
            servletUserTest.doDelete(testRequest, testResponse);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}