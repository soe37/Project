import java.time.LocalDate;
import java.util.List;

public class RoomController {
    private RoomDB roomDB;

    public RoomController(RoomDB roomDB) {
        this.roomDB = roomDB;
    }


    public List<Room> getRoomsByRoomTypeId(int roomTypeId) {
        return roomDB.getRoomsByRoomTypeId(roomTypeId);
    }


    public List<Room> getRoomsByHotelId(int hotelId) {
        return roomDB.getRoomsByHotelId(hotelId);
    }


    public Room getRoomById(int id) {
        return roomDB.getRoomById(id);
    }


    public List<Room> getAvailableRoomsByHotelId(LocalDate checkInDate, LocalDate checkOutDate, int hotelId) {
        return roomDB.getAvailableRoomsByHotelId(checkInDate, checkOutDate, hotelId);
    }

}