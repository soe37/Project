import java.util.List;
import java.time.LocalDate;

public class BookingController {
    private BookingDB bookingDB;

    public BookingController(BookingDB bookingDB) {
        this.bookingDB = bookingDB;
    }


    public int makeBooking(User user, RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate, int totalPrice) {
        int userId = user.getId();
        int hotelId = roomType.getHotelId();
        int roomTypeId = roomType.getId();
        
        List<Room> availableRooms = Main.roomController.getAvailableRoomsByHotelId(checkInDate, checkOutDate, hotelId);
        
        // Sennilega til betri leið að gera þetta (í staðinn fyrir að velja bara það fyrsta)
        // (s.s. upp á að maximize-a bókanir (að það séu ekki mörg bil á milli bókanna))
        Room availableRoom = null;

        for (Room room : availableRooms) {
            if (room.getRoomTypeId() == roomTypeId) {
                availableRoom = room;
                break;
            }
        }

        if(availableRoom == null) {
            return 0;
        }

        int roomId = availableRoom.getId();
        
        bookingDB.saveBooking(userId, roomId, hotelId, checkInDate, checkOutDate, totalPrice);

        return 1;
    }


    public List<Booking> getBookingsByUserId(int userId) {
        return bookingDB.getBookingsByUserId(userId);
    }

}