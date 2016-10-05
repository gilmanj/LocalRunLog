package db;

import domain.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Created by jared on 10/3/16.
 */
public class ActivityDAO {

    Logger log = LoggerFactory.getLogger(ActivityDAO.class);

    public ActivityDAO() {
        initializeActivityTable();
    }

    public Activity getActivityById(long id) {
        Connection connection = ConnectionManager.getConnection();

        String sql = "select * from activity where id = ?";
        Activity activity = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // read the result set
                activity = new Activity();
                activity.setId(rs.getInt("id"));
                activity.setName(rs.getString("name"));
                activity.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            log.error("read failed:" + e.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return activity;
    }

    public boolean createActivity(Activity activity) {
        String sql = "INSERT INTO activity \n" +
                "(id, name, description, moving_time, distance, type, run_type, start_date_time, average_speed, stravaId, total_elevation_gain, import_datetime) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        boolean succeeded = true;
        Connection connection = ConnectionManager.getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, null); //SQLite will insert autoincrement an ID when given null
            pstmt.setString(2, activity.getName());
            pstmt.setString(3, activity.getDescription());
            pstmt.setLong(4, activity.getMovingTime());
            pstmt.setDouble(5, activity.getDistance().doubleValue());
            pstmt.setString(6, activity.getType());
            pstmt.setString(7, activity.getRunType());
            pstmt.setString(8, activity.getStartDateLocal());
            pstmt.setDouble(9, activity.getAverageSpeed().doubleValue());
            pstmt.setLong(10, activity.getStravaId());
            pstmt.setDouble(11, activity.getTotalElevation().doubleValue());
            pstmt.setString(12, LocalDateTime.now().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            succeeded = false;
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return succeeded;
    }

    private void initializeActivityTable() {

        String sql = "CREATE TABLE IF NOT EXISTS activity (\n"
                +"	id integer PRIMARY KEY,\n"
                +"	name text,\n"
                +"	description text,\n"
                +"	moving_time integer,\n"
                +"	distance numeric,\n"
                +"	type text,\n"
                +"	run_type text,\n"
                +"	start_date_time text,\n"
                +"	average_speed real,\n"
                +"	stravaId integer,\n"
                +"	total_elevation_gain integer,\n"
                +"  import_datetime text \n"
                +");";


        log.info("Creating activity table (if it doesn't already exist)...");
        Connection connection = ConnectionManager.getConnection();

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionManager.closeConnection(connection);
        }


    }
}
