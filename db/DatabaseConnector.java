import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection connectDatabase() throws SQLException {
        String url = "jdbc:postgresql://dpg-cob6vu779t8c73bp6jqg-a.oregon-postgres.render.com:5432/team6hoteldb";
        String username = "team6hoteldb_user";
        String password = "UJSR4zhXmtodSMDid1DocNo5hdNIydKn";
        return DriverManager.getConnection(url, username, password);
    }
}
