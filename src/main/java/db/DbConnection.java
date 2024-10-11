package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance;

    private Connection connection;

    private DbConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothifystore","root","456741");
    }
    public Connection getConnection(){
        return connection;
    }

    public static DbConnection getInstance() throws SQLException {
        return null == instance?instance=new DbConnection():instance;
    }
}
