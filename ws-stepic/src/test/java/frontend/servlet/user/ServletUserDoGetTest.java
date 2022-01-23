package frontend.servlet.user;

import base.AccountService;
import base.UserProfile;
import frontend.servlet.ServletUser;
import frontend.servlet.tool.ScriptSelector;
import frontend.servlet.tool.UrlResolveResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ServletUserDoGetTest {

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
    void doGetTest() {
        String tst_host = "tst://loc:80"
                ,tstStrLogin = randomAlphabetic(4)
                ,tstStrPassword = randomAlphabetic(8)
                ,tstPathname = "tmp/http/response/print.txt"
                ;
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        when(mockRequest.getRequestURL())
                .thenReturn(new StringBuffer(tst_host + ServletUser.WS_PAGE_URL));
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(HttpServletResponse.SC_OK, arg0);
            return null;
        }).when(mockResponse).setStatus(anyInt());
        when(accountServiceMock.getUserAll())
                .thenReturn(Collections.singletonList(
                        new UserProfile(tstStrLogin, tstStrPassword)
                ));
        File f = new File(tstPathname);
        f.getParentFile().mkdirs();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("tmp/http/response/print.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            when(mockResponse.getWriter())
                    .thenReturn(new PrintWriter(fos, true));
            try {
                servletUserTest.doGet(mockRequest, mockResponse);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}