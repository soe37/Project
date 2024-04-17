import java.time.LocalDate;
import java.util.List;

public class RoomController {

    // Store the database that we get in our constructor
    private RoomDB roomDB;

    // The constructor for the controller, takes in a roomlDB
    // and stores it as a local room database
    public RoomController(RoomDB roomDB) {
        this.roomDB = roomDB;
    }


    // Returns a list of rooms with the type of room
    // they are looking for
    public List<Room> getRoomsByRoomTypeId(int roomTypeId) {
        return roomDB.getRoomsByRoomTypeId(roomTypeId);
    }


    // Returns a list of rooms from a specific hotel
    public List<Room> getRoomsByHotelId(int hotelId) {
        return roomDB.getRoomsByHotelId(hotelId);
    }


    // Returns a Room with a specific room id
    public Room getRoomById(int id) {
        return roomDB.getRoomById(id);
    }


    // Finds available room with a specific check in and out
    // dates within a single hotel
    public List<Room> getAvailableRoomsByHotelId(LocalDate checkInDate, LocalDate checkOutDate, int hotelId) {
        return roomDB.getAvailableRoomsByHotelId(checkInDate, checkOutDate, hotelId);
    }

    // Adds #numberOfRooms of specific room type for a specific hotel
    public void addRooms(int roomTypeId, int hotelId, int numberOfRooms) {
        roomDB.saveRooms(roomTypeId, hotelId, numberOfRooms);
    }

}