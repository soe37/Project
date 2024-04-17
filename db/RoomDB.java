import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDB {
    private Connection connection;
    
    public RoomDB(Connection connection) {
        this.connection = connection;
    }

    public Room getRoomById(int id) {
        String query = "SELECT * FROM rooms WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int roomId = resultSet.getInt("id");
                    int hotelId = resultSet.getInt("hotelid");
                    int roomTypeId = resultSet.getInt("roomtypeid");
                    
                    return new Room(roomId, roomTypeId, hotelId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Room> getRoomsByHotelId(int hotelId) {
        List<Room> roomsByHotelId = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE hotelid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int roomId = resultSet.getInt("id");
                    int roomTypeId = resultSet.getInt("roomtypeid");
                    
                    roomsByHotelId.add(new Room(roomId, roomTypeId, hotelId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomsByHotelId;
    }

    public List<Room> getRoomsByRoomTypeId(int roomTypeId) {
        List<Room> roomsByRoomTypeId = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE roomtypeid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int roomId = resultSet.getInt("id");
                    int hotelId = resultSet.getInt("hotelid");
                    
                    roomsByRoomTypeId.add(new Room(roomId, roomTypeId, hotelId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomsByRoomTypeId;
    }

    public List<Room> getAvailableRoomsByHotelId(LocalDate checkInDate, LocalDate checkOutDate, int hotelId) {
        List<Room> availableRooms = new ArrayList<>();
        List<Room> unavailableRooms = new ArrayList<>();

        String query = "SELECT roomid FROM bookings WHERE hotelid = ? AND checkoutdate > ? AND checkindate < ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            statement.setDate(2, java.sql.Date.valueOf(checkInDate));
            statement.setDate(3, java.sql.Date.valueOf(checkOutDate));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int roomId = resultSet.getInt("roomid");
                    unavailableRooms.add(getRoomById(roomId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Room> roomsByHotelId = getRoomsByHotelId(hotelId);


        for (Room room : roomsByHotelId) {
            if (!unavailableRooms.contains(room)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }


    public void saveRooms(int roomTypeId, int hotelId, int numberOfRooms) {
        String query = "INSERT INTO rooms (roomtypeid, hotelid) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < numberOfRooms; i++) {
                statement.setInt(1, roomTypeId);
                statement.setInt(2, hotelId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}