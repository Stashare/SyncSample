package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 15/08/2017.
 */

public class SavedData {

    private int id;
    private String country;
    private String lat;
    private String longitude;

    public SavedData(int id, String country, String lat, String longitude) {
        this.id = id;
        this.country = country;
        this.lat = lat;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getLat() {
        return lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
