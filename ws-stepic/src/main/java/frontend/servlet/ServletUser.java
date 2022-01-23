package frontend.servlet;

import base.AccountService;
import frontend.servlet.tool.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author v.chibrikov
 */
public class ServletUser extends HttpServlet implements Servletable {

    private static final Logger logger = LogManager.getLogger(ServletUser.class.getName());

    public static final String PAGE_URL = "/user";
    public static final String WS_PAGE_URL = WS_URL + PAGE_URL;
    protected final String[] DO_POST_CREATE_EXCLUSIONS = {"new"};

    private final AccountService accountService;
    private final ScriptSelector scriptSelector;

    public ServletUser(AccountService accountService, ScriptSelector scriptSelector) {
        this.accountService = accountService;
        this.scriptSelector = scriptSelector;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        logger.info("received user credentials (login: " + login + ", password: " + password + ")");
        ScriptStatus scriptStatus = scriptSelector.servletUserDoPost(login, password, DO_POST_CREATE_EXCLUSIONS);
        switch (scriptStatus) {
            case SCRIPT_POSITIVE:
                accountService.createUser(login, password);
                response.setStatus(HttpServletResponse.SC_OK);
                break;
            case SCRIPT_0:
                logger.error("credentials not contain text");
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                break;
            case SCRIPT_1:
                logger.error("credentials is pool");
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                break;
            case SCRIPT_2:
                logger.error("Create \"new\" excluding");
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                break;
        }
        response.setContentType("text/html;charset=utf-8");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletHelper servletHelper = new ServletHelper();
        servletHelper.setSplitter(PAGE_URL);
        StringBuffer requestURL = request.getRequestURL();
        UrlResolveResult urlResolveResult = servletHelper.resolve(requestURL);
        switch (urlResolveResult) {
            case NEW:
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("text/html");
                response.getWriter().print(
                        new FormBuilder()
                                .setPostMethod()
                                .setPath(WS_PAGE_URL)
                                .setSubmitButtonValue("create")
                                .toString());
                logger.info("user create form send");
                break;
            case EMPTY:
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(
                        servletHelper.toMyStrToJson(
                                accountService.getUserAll()));
                logger.info("retrieve all user");
                break;
            case OVER:
                String urlSuffix = servletHelper.getUrlSuffix();
//                UserProfile curUsrProf = accountService.getUser(urlSuffix);
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(
                        servletHelper.toMyStrToJson(
                                Arrays.asList(
                                        accountService.getUser(
                                                servletHelper.getUrlSuffix()
                )       )       )       );
                logger.info("retrieve any user");
                break;
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.error("Deleting user");
        String login = request.getParameter("login");
        accountService.deleteUser(login);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        StringBuffer requestURL = request.getRequestURL();
        ServletHelper servletHelper = new ServletHelper();
        servletHelper.setSplitter(PAGE_URL);
        UrlResolveResult urlResolveResult = servletHelper.resolve(requestURL);
        switch (urlResolveResult) {
            case OVER:
                accountService.updateUser(servletHelper.getUrlSuffix(), login, password);
                response.setStatus(HttpServletResponse.SC_OK);
                break;
            case NEW:
                logger.error("Update \"new\" excluding");
            case EMPTY:
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                break;
        }
    }
}
