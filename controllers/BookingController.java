import java.util.List;
import java.time.LocalDate;

public class BookingController {
    // Store the database that we get in our constructor
    private BookingDB bookingDB;

    // The constructor for the controller, takes in a BookingDB 
    // and stores it as a local booking database
    public BookingController(BookingDB bookingDB) {
        this.bookingDB = bookingDB;
    }


    // makeBooking takes in a User object, Roomtype object, 
    // localdate for check-in and localdate for check-out
    // and an int for total prize and makes a new booking
    // that is stored in the BookingDB.
    //
    // This is how it is done
    //
    // # 1 
    // Three local int varaibles are made to store the user id 
    // the hotel id and room type id 
    //
    // # 2
    // List of available rooms is made and it is checked for 
    // chekc in and out dates and hotel id. Gving us all the
    // availble rooms from that hotel
    //
    // # 3
    // We make a Room object that is null and go through the available 
    // rooms and if we find a room that has a matching room type id as
    // we asked for we store it in the room object
    //
    // # 4
    // We check if the Room object is still nul and if so we return 0 and 
    // if its not null we get the id of the romm and make a new booking and
    // return 1
    // 
    public int makeBooking(User user, RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate, int totalPrice) {
        // 1
        int userId = user.getId();
        int hotelId = roomType.getHotelId();
        int roomTypeId = roomType.getId();
        
        // 2
        List<Room> availableRooms = Main.roomController.getAvailableRoomsByHotelId(checkInDate, checkOutDate, hotelId);
        
        // 3
        Room availableRoom = null;

        for (Room room : availableRooms) {
            if (room.getRoomTypeId() == roomTypeId) {
                availableRoom = room;
                break;
            }
        }

        // 4
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