package db;

import integration.api.strava.StravaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jared on 10/3/16.
 */
public class ConnectionManager {

    private static final Logger log = LoggerFactory.getLogger(StravaService.class);

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:activity.db";
            Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("Connected to the database");
                DatabaseMetaData dm = conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
            return conn;

        } catch (ClassNotFoundException cnfe) {
            log.error("Connection failed. class not found");
        } catch (SQLException sqlException) {
            log.error("Unable to connect to sqlite db");
        }
        return null;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException sqle) {
            log.error("Unable to close connection");
        }
    }

}
