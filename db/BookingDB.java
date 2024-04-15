import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDB {
    private Connection connection;

    public BookingDB(Connection connection) {
        this.connection = connection;
    }

    public List<Booking> getBookingsByHotelId(int hotelId) {
        List<Booking> bookingsByHotel = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE hotelid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int userId = resultSet.getInt("userid");
                    int roomId = resultSet.getInt("roomid");
                    LocalDate checkInDate = resultSet.getDate("checkindate").toLocalDate();
                    LocalDate checkOutDate = resultSet.getDate("checkoutdate").toLocalDate();
                    int totalPrice = resultSet.getInt("totalprice");
                    bookingsByHotel.add(new Booking(id, userId, roomId, hotelId, checkInDate, checkOutDate, totalPrice));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingsByHotel;
    }

    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookingsByUser = new ArrayList<>();
        String query = "SELECT * FROM bookings WHERE userid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int hotelId = resultSet.getInt("hotelid");
                    int roomId = resultSet.getInt("roomid");
                    LocalDate checkInDate = resultSet.getDate("checkindate").toLocalDate();
                    LocalDate checkOutDate = resultSet.getDate("checkoutdate").toLocalDate();
                    int totalPrice = resultSet.getInt("totalprice");
                    bookingsByUser.add(new Booking(id, userId, roomId, hotelId, checkInDate, checkOutDate, totalPrice));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingsByUser;
    }

    public void saveBooking(int userId, int roomId, int hotelId, LocalDate checkInDate, LocalDate checkOutDate, int totalPrice) {
        String query = "INSERT INTO bookings (userid, roomid, hotelid, checkindate, checkoutdate, totalprice) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, roomId);
            statement.setInt(3, hotelId);
            statement.setDate(4, java.sql.Date.valueOf(checkInDate));
            statement.setDate(5, java.sql.Date.valueOf(checkOutDate));
            statement.setInt(6, totalPrice);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}