import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelDB {
    private Connection connection;

    public HotelDB(Connection connection) {
        this.connection = connection;
    }

    public List<Hotel> getHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM hotels";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                int rating = resultSet.getInt("rating");
                int ownerId = resultSet.getInt("hotelownerid");
                hotels.add(new Hotel(id, name, location, rating, ownerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public List<String> getDistinctHotelLocations() {
        Set<String> distinctLocations = new HashSet<>();
        String query = "SELECT DISTINCT location FROM hotels";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                distinctLocations.add(resultSet.getString("location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(distinctLocations);
    }

    public List<Hotel> getHotelsByLocation(String[] locations) {
        List<Hotel> filteredHotels = new ArrayList<>();
        String query = "SELECT * FROM hotels WHERE location IN (";
        for (int i = 0; i < locations.length; i++) {
            if (i > 0) {
                query += ",";
            }
            query += "?";
        }
        query += ")";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < locations.length; i++) {
                statement.setString(i + 1, locations[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");
                    int rating = resultSet.getInt("rating");
                    int ownerId = resultSet.getInt("hotelownerid");
                    filteredHotels.add(new Hotel(id, name, location, rating, ownerId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredHotels;
    }

    public List<Hotel> getHotelsByRating(int minRating, int maxRating) {
        List<Hotel> filteredHotels = new ArrayList<>();
        String query = "SELECT * FROM hotels WHERE rating BETWEEN ? AND ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, minRating);
            statement.setInt(2, maxRating);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");
                    int rating = resultSet.getInt("rating");
                    int ownerId = resultSet.getInt("hotelownerid");
                    filteredHotels.add(new Hotel(id, name, location, rating, ownerId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredHotels;
    }

    public List<Hotel> getHotelsByUserId(int id) {
        List<Hotel> filteredHotels = new ArrayList<>();
        String query = "SELECT * FROM hotels WHERE hotelownerid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int hotelId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");
                    int rating = resultSet.getInt("rating");
                    filteredHotels.add(new Hotel(hotelId, name, location, rating, id));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredHotels;
    }


    public void saveHotel(String name, String location, int rating, int hotelOwnerId) {
        String query = "INSERT INTO hotels (name, location, rating, hotelownerid) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, location);
            statement.setInt(3, rating);
            statement.setInt(4, hotelOwnerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public Hotel getHotelById(int hotelId) {
        String query = "SELECT * FROM hotels WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");
                    int rating = resultSet.getInt("rating");
                    int hotelOwnerId = resultSet.getInt("hotelownerid");
                    return new Hotel(id, name, location, rating, hotelOwnerId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateName(int id, String name) {
        String query = "UPDATE hotels SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLocation(int id, String location) {
        String query = "UPDATE hotels SET location = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, location);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRating(int id, int rating) {
        String query = "UPDATE hotels SET rating = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, rating);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}