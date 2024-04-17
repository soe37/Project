import java.util.Objects;

public class Hotel {
    private int id;
    private String name;
    private String location;
    private int rating;
    private int hotelOwnerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return id == hotel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public Hotel(int id, String name, String location, int rating, int hotelOwnerId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.hotelOwnerId = hotelOwnerId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getHotelOwnerId() {
        return hotelOwnerId;
    }

    public void setHotelOwnerId(int hotelOwnerId) {
        this.hotelOwnerId = hotelOwnerId;
    }
}
