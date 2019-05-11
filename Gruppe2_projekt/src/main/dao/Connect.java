package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    String user = "s180557";
    String pw = "SnM6HsTt8iPhYpasthnCW";
    String path = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/"+user+"?";

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection(path + "user=" + user + "&password=" + pw + "&useSSL=FALSE");
    }

}
