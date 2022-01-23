package main;

import base.AccountService;
import base.DBService;
import dbService.DBServiceImpl;
import frontend.AccountServiceController;
import frontend.AccountServiceControllerMBean;
import frontend.AccountServiceImpl;
import frontend.servlet.*;
import frontend.servlet.tool.ScriptSelector;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;


/**
 * @author a.akbashev
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */

// https://www.youtube.com/watch?v=3Tu5R1OfrRo

public class Main {
    private static final Logger logger = Log4jCfg.configure(Main.class);

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            logger.error("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);

        logger.info("Starting at http://127.0.0.1:" + portString);

        DBService dbService = new DBServiceImpl();
        dbService.createUsersTable();
        dbService.check();

        AccountService accountService = new AccountServiceImpl(dbService, 10);
        ScriptSelector scriptSelector = new ScriptSelector();

        AccountServiceControllerMBean serverStatistics = new AccountServiceController(accountService);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ServiceManager:type=AccountServiceController");
        mBeanServer.registerMBean(serverStatistics, name);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet( new ServletHolder( new ServletUser(accountService, scriptSelector) ),
                ServletUser.WS_PAGE_URL + "/*");
        context.addServlet( new ServletHolder(
                new ServletSession(accountService) ),
                ServletSession.WS_PAGE_URL + "/*");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        logger.info("Server started");
        server.join();
    }

}
