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


    // Returns a list of RoomTypes that are available on
    // certain dates in a certain hotel
    // #1
    // Make a Hash set that takes in roomtypes
    //
    // #2
    // Make a list of rooms that are available at the hotel
    // 
    // #3
    // go through all the available rooms and when we find a room
    // that fits our parameters we add it to the set and then return
    // the list of available room types
    //
    public List<RoomType> getAvailableRoomTypes(LocalDate checkInDate, LocalDate checkOutDate, int hotelId) {
        // 1
        Set<RoomType> availableRoomTypes = new HashSet<>();
        
        // 2
        List<Room> availableRooms = Main.roomController.getAvailableRoomsByHotelId(checkInDate, checkOutDate, hotelId);

        // 3
        for (Room room : availableRooms) {
            RoomType roomType = getRoomTypeById(room.getRoomTypeId());
            availableRoomTypes.add(roomType);
        }
        return new ArrayList<>(availableRoomTypes);
    }
    

    // Returns a list of RoomTypes that match the search criteria
    // based on certain dates, number of persons, maximum price, and hotel ID.
    // #1
    // Retrieves room types by minimum capacity for the specified hotel.
    //
    // #2
    // Retrieves room types by maximum price for the specified hotel.
    //
    // #3
    // Retrieves available room types for the specified dates and hotel.
    //
    // #4
    // Finds the intersection of room types by minimum capacity, maximum price, and availability.
    // Filters the room types by minimum capacity and maximum price, then filters by availability.
    // Collects the intersecting room types into a list and returns it.
    public List<RoomType> roomTypeSearch(LocalDate checkInDate, LocalDate checkOutDate, int numberOfPersons, int maxPrice, int hotelId) {
        // 1
        List<RoomType> roomTypesByMinCapacity = roomTypeDB.getRoomTypesByMinCapacity(numberOfPersons, hotelId);
        // 2
        List<RoomType> roomTypesByPrice = roomTypeDB.getRoomTypesByMaxPrice(maxPrice, hotelId);
        // 3
        List<RoomType> availableRoomTypes = getAvailableRoomTypes(checkInDate, checkOutDate, hotelId);

        // 4
        List<RoomType> intersection = roomTypesByMinCapacity.stream()
                .filter(roomTypesByPrice::contains)
                .filter(availableRoomTypes::contains)
                .collect(Collectors.toList());
    
        return intersection;
    }


    // Returns the highest price per night among all room types available at a specific hotel.
    // #1
    // Retrieves all room types available at the specified hotel.
    //
    // #2
    // Initializes a variable to store the maximum price, initially set to 0.
    //
    // #3
    // Goes through each room tyo and checks if the price per night of the 
    // current room type is greater than the current maximum price.
    // If so, updates the maximum price to the price per night of the current room type.
    //
    // #4
    // Returns the maximum price found among all room types.
    public int highestPriceByHotelId(int hotelId) {
        // 1
        List<RoomType> roomTypes = roomTypeDB.getRoomTypesByHotelId(hotelId);
        
        // 2
        int maxPrice = 0;
    
        // 3
        for (RoomType roomType : roomTypes) {
            if (roomType.getPricePerNight() > maxPrice) {
                maxPrice = roomType.getPricePerNight();
            }
        }
    
        // 4
        return maxPrice;
    }


    
    // Returns the highest capacity among all room types available at a specific hotel.
    // #1
    // Retrieves all room types available at the specified hotel.
    //
    // #2
    // Initializes a variable to store the maximum capacity, initially set to 1.
    //
    // #3
    // Ges through each roomtype and checks if the capacity of the current 
    // room type is greater than the current maximum capacity.
    // If so, updates the maximum capacity to the capacity of the current room type.
    //
    // #4
    // Returns the maximum capacity found among all room types.
    public int highestCapacityByHotelId(int hotelId) {
        // 1
        List<RoomType> roomTypes = roomTypeDB.getRoomTypesByHotelId(hotelId);
        
        // 2
        int maxCapacity = 1;
    
        // 3
        for (RoomType roomType : roomTypes) {
            if (roomType.getCapacity() > maxCapacity) {
                maxCapacity = roomType.getCapacity();
            }
        }
    
        // 4
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