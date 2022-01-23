package frontend.servlet;

import base.AccountService;
import base.UserProfile;
//import com.google.gson.Gson;
import com.google.gson.Gson;
import frontend.servlet.tool.FormBuilder;
import frontend.servlet.tool.ServletHelper;
import frontend.servlet.tool.UrlResolveResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
//import jakarta.servlet.ServletException;
import javax.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author v.chibrikov
 */
public class ServletSession extends HttpServlet implements Servletable {

    public static final String PAGE_URL = "/session";
    public static final String WS_PAGE_URL = WS_URL + PAGE_URL;
    private static final Logger logger = LogManager.getLogger(ServletSession.class.getName());
    private final AccountService accountService;

    public ServletSession(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || password == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile profile = accountService.getUserByLogin(login);
        if ( profile == null || !profile.getPassword().equals(password) ) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (accountService.getUsersLimit()-accountService.getUsersCount() > 0) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.getWriter().println("User were rejected");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        accountService.addSession(request.getSession().getId(), login);
//        Gson gson = new Gson();
//        String json = gson.toJson(profile);
        ServletHelper servletHelper = new ServletHelper();
        response.setContentType(COMMON_CONTENT_TYPE);
        response.getWriter().println("Authorized: " + //profile.getLogin()
//                json
                servletHelper.toMyStrToJson(
                        Arrays.asList(
                                profile
                        )
                )
        );
        response.getWriter().println(CALL_BACK_HTEXPR);
        response.setStatus(HttpServletResponse.SC_OK);

        /*boolean loggedIn = accountService.addSession(sessionId, login);
        response.setContentType("text/html;charset=utf-8");
        if (loggedIn) {
            response.getWriter().println("Authorized: " + login);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);}*/
    }

    //sign out
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        String login = accountService.getUserBySessionId(sessionId);
        if (login == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            response.setContentType(COMMON_CONTENT_TYPE);
            response.getWriter().println("Goodbye!");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    //get logged user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        ServletHelper servletHelper = new ServletHelper();
        servletHelper.setSplitter(PAGE_URL);
        StringBuffer requestURL = request.getRequestURL();
        UrlResolveResult urlResolveResult = servletHelper.resolve(requestURL);

        switch (urlResolveResult) {
            case NEW:
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print(
                        new FormBuilder(WS_PAGE_URL)
                                .setPostMethod()
                                .setSubmitButtonValue("login")
                                .toString()
                );
                logger.info("session create form send");
                break;
            default:
                HttpSession session = request.getSession();
                String sessionId = session.getId();
                //UserProfile profile
                String login
                        = accountService.getUserBySessionId(sessionId);
                response.setContentType(COMMON_CONTENT_TYPE);
                if (login == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    accountService.deleteSession(sessionId);
                    response.getWriter().println("Goodbye!");
                    response.getWriter().println(CALL_BACK_HTEXPR);
                }
                break;
        }


    }

}
