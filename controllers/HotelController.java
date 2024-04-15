import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private HotelDB hotelDB;

    public HotelController(HotelDB hotelDB) {
        this.hotelDB = hotelDB;
    }

    public String[] getDistinctHotelLocations() {
        List<String> locationsList = hotelDB.getDistinctHotelLocations();
        String[] locationsArray = new String[locationsList.size()];
        locationsArray = locationsList.toArray(locationsArray);
        return locationsArray;
    }
    
    public List<Hotel> hotelSearch(String[] locations, int minRating, int maxRating) {
        List<Hotel> hotelsByLocation = hotelDB.getHotelsByLocation(locations);
        List<Hotel> hotelsByRating = hotelDB.getHotelsByRating(minRating, maxRating);

        // Create a new list to store the hotels that match both location and rating criteria
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