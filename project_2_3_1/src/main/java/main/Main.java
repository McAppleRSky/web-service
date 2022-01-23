package main;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

// https://stepik.org/lesson/12404/step/15?unit=2834
// https://github.com/vitaly-chibrikov/stepic_java_webserver
public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBService();
        dbService.printConnectInfo();
        try{
            long userId = dbService.addUser("tully");
            System.out.println("" + userId);
            UsersDataSet dataSet = dbService.getUser(userId);
            System.out.println("" + dataSet);
            dbService.cleanUp();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}