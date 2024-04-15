import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static User loggedInUser;

    public static UserController userController;
    public static HotelController hotelController;
    public static RoomTypeController roomTypeController;
    public static BookingController bookingController;
    public static RoomController roomController;

    public static void main(String[] args) {

        Connection connection = null;
        try {
            connection = DatabaseConnector.connectDatabase();
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
            return;
        }
        
        UserDB userDB = new UserDB(connection);
        userController = new UserController(userDB);

        HotelDB hotelDB = new HotelDB(connection);
        hotelController = new HotelController(hotelDB);

        RoomTypeDB roomTypeDB = new RoomTypeDB(connection);
        roomTypeController = new RoomTypeController(roomTypeDB);

        BookingDB bookingDB = new BookingDB(connection);
        bookingController = new BookingController(bookingDB);

        RoomDB roomDB = new RoomDB(connection);
        roomController = new RoomController(roomDB);

        new LoginScreen();
    }


    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void clearLoggedInUser() {
        loggedInUser = null;
    }
}