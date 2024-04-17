import java.util.ArrayList;
import java.util.List;

public class HotelController {

    // Store the database that we get in our constructor
    private HotelDB hotelDB;


    // The constructor for the controller, takes in a hotelDB
    // and stores it as a local hotel database
    public HotelController(HotelDB hotelDB) {
        this.hotelDB = hotelDB;
    }


    // Gives a String[] filled with every distinct hotel location
    // in the database
    // # 1
    // Make a list of strings to store all the distinct locations
    // from the database
    //
    // # 2
    // Make an Array to store the locations in the same size as our list 
    // of strings, then we add the list to the array and return the 
    // Array
    public String[] getDistinctHotelLocations() {
        // 1
        List<String> locationsList = hotelDB.getDistinctHotelLocations();
        
        // 2
        String[] locationsArray = new String[locationsList.size()];
        locationsArray = locationsList.toArray(locationsArray);
        return locationsArray;
    }
    
    // Returns a list of hotel objects 
    // within the parameters of location and
    // min and max rating
    //
    // # 1
    // Make 2 lists of hotels, one with the location parameters
    // and one with the rating parameters
    //
    // # 2
    // Create a new list to store the hotels 
    // that match both location and rating criteria
    //
    // # 3 
    // Return the combined list
    public List<Hotel> hotelSearch(String[] locations, int minRating, int maxRating) {
        // 1
        List<Hotel> hotelsByLocation = hotelDB.getHotelsByLocation(locations);
        List<Hotel> hotelsByRating = hotelDB.getHotelsByRating(minRating, maxRating);

        // 2
        List<Hotel> hotels = new ArrayList<>();
    
        for (Hotel locationHotel : hotelsByLocation) {
            for (Hotel ratingHotel : hotelsByRating) {
                if (locationHotel.getId() == ratingHotel.getId()) {
                    hotels.add(locationHotel);
                    break;
                }
            }
        }

        return hotels;
    }


    public List<Hotel> getHotelsByUserId(int id) {
        return hotelDB.getHotelsByUserId(id);
    }


    public void addHotel(String name, String location, int rating, int hotelOwnerId) {
        hotelDB.saveHotel(name, location, rating,  hotelOwnerId);
    }

    public Hotel getHotelById(int hotelId) {
        return hotelDB.getHotelById(hotelId);
    }

    public void updateName(int id, String name) {
        hotelDB.updateName(id, name);
    }

    public void updateLocation(int id, String location) {
        hotelDB.updateLocation(id, location);
    }

    public void updateRating(int id, int rating) {
        hotelDB.updateRating(id, rating);
    }
}