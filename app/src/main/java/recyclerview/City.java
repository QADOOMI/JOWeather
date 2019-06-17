package recyclerview;

import java.util.HashMap;

public class City {

    private String name;
    private int image;


    public City(String name, int image) {

        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", image=" + image +
                '}';
    }

    public static class CitiesConstants {
        public static final int AMMAN_ID;
        public static final int IRBID_ID;
        public static final int AQABA_ID;


        public static final String REQUESTED_CITY = "city";
        public static final String AMMAN;
        public static final String IRBID;
        public static final String AQABA;

        // coord sorted in the array with latitude then longtitude
        public static final HashMap<String, Float[]> citiesCoord = new HashMap<>();


        static {
            AMMAN = "Amman";
            IRBID = "Irbid";
            AQABA = "Aqaba";

            AMMAN_ID = 250441;
            IRBID_ID = 248944;
            AQABA_ID = 443122;

            citiesCoord.put(AMMAN, new Float[]{31.955219f, 35.94503f});
            citiesCoord.put(IRBID, new Float[]{32.5f, 35.833328f});
            citiesCoord.put(AQABA, new Float[]{29.75f, 35.333328f});
        }
    }
}