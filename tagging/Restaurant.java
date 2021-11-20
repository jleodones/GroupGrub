import java.util.*;

public class Restaurant {
    private String name;
    private String id;
    private String alias;
    private boolean is_claimed;
    private boolean is_closed;
    private String url;
    private String phone;
    private String display_phone;
    private int review_count;
    private float rating;
    private String price;
    private float distance;
    private Category categories;
    private Location location;
    private Coordinates coordinates;
    private String photos;
    private Hours hours;
    private Review reviews;
    private Transactions transactions;
    private BusinessMessaging messaging;
    private SpecialHours special_hours;
    private BusinessAttributes attributes;

    public class Category {
        private String title;
        private String alias;
        private Category[] parent_category;
        private Country[] country_whitelist;
        private Country[] country_blacklist;

        public class Country {
            private String code;
            private Locale[] locales;
        }
    }

    public class Location {
        private String address1;
        private String address2;
        private String address3;
        private String city;
        private String state;
        private String postal_code;
        private String country;
        private String formatted_address;
    }

    public class Coordinates {
        private Float latitude;
        private Float longitude;
    }

    public class Hours {
        private String hours_type;
        private OpenHours open;
        private Boolean is_open_now;

        public class OpenHours {
            private boolean is_overnight;
            private String end;
            private String start;
            private int day;
        }
    }
}
