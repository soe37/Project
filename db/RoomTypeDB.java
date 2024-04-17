import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDB {
    private Connection connection;

    public RoomTypeDB(Connection connection) {
        this.connection = connection;
    }

    public RoomType getRoomTypeById(int id) {
        String query = "SELECT * FROM roomtypes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int capacity = resultSet.getInt("capacity");
                    int price = resultSet.getInt("pricepernight");
                    int hotelId = resultSet.getInt("hotelid");
                    return new RoomType(id, name, capacity, price, hotelId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RoomType> getRoomTypesByHotelId(int hotelId) {
        List<RoomType> roomTypesByHotel = new ArrayList<>();
        String query = "SELECT * FROM roomtypes WHERE hotelid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int capacity = resultSet.getInt("capacity");
                    int price = resultSet.getInt("pricepernight");
                    roomTypesByHotel.add(new RoomType(id, name, capacity, price, hotelId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomTypesByHotel;
    }

    public List<RoomType> getRoomTypesByMinCapacity(int capacity, int hotelId) {
        List<RoomType> filteredRoomTypes = new ArrayList<>();
        String query = "SELECT * FROM roomtypes WHERE hotelid = ? AND capacity >= ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            statement.setInt(2, capacity);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int roomCapacity = resultSet.getInt("capacity");
                    int price = resultSet.getInt("pricepernight");
                    filteredRoomTypes.add(new RoomType(id, name, roomCapacity, price, hotelId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredRoomTypes;
    }

    public List<RoomType> getRoomTypesByMaxPrice(int price, int hotelId) {
        List<RoomType> filteredRoomTypes = new ArrayList<>();
        String query = "SELECT * FROM roomtypes WHERE hotelid = ? AND pricepernight <= ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            statement.setInt(2, price);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int capacity = resultSet.getInt("capacity");
                    int roomPrice = resultSet.getInt("pricepernight");
                    filteredRoomTypes.add(new RoomType(id, name, capacity, roomPrice, hotelId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredRoomTypes;
    }


    public void saveRoomType(String name, int capacity, int pricePerNight, int hotelId) {
        String query = "INSERT INTO roomtypes (name, capacity, pricepernight, hotelid) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setInt(2, capacity);
            statement.setInt(3, pricePerNight);
            statement.setInt(4, hotelId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}