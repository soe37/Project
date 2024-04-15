import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class RoomTypeController {
    private RoomTypeDB roomTypeDB;

    public RoomTypeController(RoomTypeDB roomTypeDB) {
        this.roomTypeDB = roomTypeDB;
    }


    public List<RoomType> getAvailableRoomTypes(LocalDate checkInDate, LocalDate checkOutDate, int hotelId) {
        Set<RoomType> availableRoomTypes = new HashSet<>();
        List<Room> availableRooms = Main.roomController.getAvailableRoomsByHotelId(checkInDate, checkOutDate, hotelId);

        for (Room room : availableRooms) {
            RoomType roomType = getRoomTypeById(room.getRoomTypeId());
            availableRoomTypes.add(roomType);
        }
        return new ArrayList<>(availableRoomTypes);
    }
    

    public List<RoomType> roomTypeSearch(LocalDate checkInDate, LocalDate checkOutDate, int numberOfPersons, int maxPrice, int hotelId) {
        List<RoomType> roomTypesByMinCapacity = roomTypeDB.getRoomTypesByMinCapacity(numberOfPersons, hotelId);
        List<RoomType> roomTypesByPrice = roomTypeDB.getRoomTypesByMaxPrice(maxPrice, hotelId);
        List<RoomType> availableRoomTypes = getAvailableRoomTypes(checkInDate, checkOutDate, hotelId);

        List<RoomType> intersection = roomTypesByMinCapacity.stream()
                .filter(roomTypesByPrice::contains)
                .filter(availableRoomTypes::contains)
                .collect(Collectors.toList());
    
        return intersection;
    }


    public int highestPriceByHotelId(int hotelId) {
        List<RoomType> roomTypes = roomTypeDB.getRoomTypesByHotelId(hotelId);
        
        int maxPrice = 0;
    
        for (RoomType roomType : roomTypes) {
            if (roomType.getPricePerNight() > maxPrice) {
                maxPrice = roomType.getPricePerNight();
            }
        }
    
        return maxPrice;
    }


    public int highestCapacityByHotelId(int hotelId) {
        List<RoomType> roomTypes = roomTypeDB.getRoomTypesByHotelId(hotelId);
        
        int maxCapacity = 1;
    
        for (RoomType roomType : roomTypes) {
            if (roomType.getCapacity() > maxCapacity) {
                maxCapacity = roomType.getCapacity();
            }
        }
    
        return maxCapacity;
    }

    public void addRoomType(String name, int capacity, int pricePerNight, int hotelId) {
        roomTypeDB.saveRoomType(name, capacity, pricePerNight, hotelId);
    }


    public List<RoomType> getRoomTypesByHotelId(int hotelId) {
        return roomTypeDB.getRoomTypesByHotelId(hotelId);
    }


    public RoomType getRoomTypeById(int id) {
        return roomTypeDB.getRoomTypeById(id);
    }
}