import java.util.*;

public class Restaurant {
    //missing image_url
    private String name;    //
    private String id;  //
    private String alias;   //
    private boolean is_claimed;
    private boolean is_closed;  //
    private String url; //
    private String phone;
    private String display_phone;
    private int review_count;   //
    private float rating;   //
    private String price;   //
    private float distance;
    private Category categories;    //
    private Location location;  //
    private Coordinates coordinates;    //
    private String photos;
    private Hours hours;
    private Review reviews;
    private Transactions transactions;  //
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

    public class Review {
        private String id;
        private String text;
        private int rating;
        private String time_created;
        private String url;
        private User user;
        private PublicReviewResponse public_response;

        public class User {
            private String id;
            private String profile_url;
            private String name;
            private String image_url;
        }

        public class PublicReviewResponse {
            private String text;
            private String time_created;
            private BusinessUser business_user;

            public class BusinessUser{
                private String name;
                private String role;
                private String photo_url;
            }
        }
    }

    public class Transactions { //incorrectly formatted
        private RestaurantReservations restaurant_reservations;

        public class RestaurantReservations {
            private String url;
        }
    }

    public class BusinessMessaging {
        private String url;
        private String use_case_text;

    }

    public class SpecialHours {
        private String date;
        private Boolean is_closed;
        private String start;
        private String end;
        private Boolean is_overnight;
    }

    public class BusinessAttributes {
        private BusinessAttribute gender_neutral_restrooms;
        private BusinessAttribute open_to_all;
        private BusinessAttribute wheelchair_accessible;

        public class BusinessAttribute{
            private String name_code;
            private String value_code;
        }
    }
}
