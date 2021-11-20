import java.util.*;
import java.io.*;
import java.lang.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class Restaurant {
    private Double rating;
    private String price;
    private String phone;
    private String id;
    private Map<String, String> categories;
    private int review_count;
    private String name;
    private String url;
    private Map<String, Double> coordinates;
    private String image_url;
    private Map<String, String> location;

    public Double getRating() {
        return this.rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getCategories() {
        return this.categories;
    }

    public void setCategories(Map<String, String> categories) {
        this.categories = categories;
    }

    public int getReview_count() {
        return this.review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String,Double> getCoordinates()

    {
		return this.coordinates;
	}

    public void setCoordinates(Map<String,Double> coords)
    {
		this.coordinates = coords;
	}

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Map<String,String> getLocation()

    {
		return this.location;
	}

    public void setLocation(Map<String,String> loc) 
    {
		this.location = loc;
	}
    /*@Override
    public String toString() {
        return
        "{\n" +
        "\ttotal: " + rating.toString() + ",\n" +
        "\tbusinesses: [\n" +


    }*/
}

/*
 json string might overflow?
*/
class Tagging {
    /*
     * TODO: 
     * - work with ashley + jamie to get tags and dealbreakers
     * - error checking
    */

    //List of tags and dealbreakers that will be provided
    private ArrayList<String> _wantTags;
    private ArrayList<String> _noWantTags;
    private HashSet<Restaurant> _restaurants;
    private HashSet<Restaurant> _dealbreakers;

    //Justin wrote this
    private ArrayList<String> getRestaurants(ArrayList<String> tag) {
        //Returns an Arraylist<JSON strings of restaurants>
        return null;
    }


    //Parses the Json Strings of Restaurants to be added to the hashset of restaurants to be used
    private ArrayList<ArrayList<Restaurant> > parseJson(ArrayList<String> jsonRestaurant) {
        Gson gson = new Gson();

        ArrayList<ArrayList<Restaurant> > restaurantFromTags = new ArrayList<ArrayList<Restaurant> >();
        
        for(String restaurantList : jsonRestaurant) {
            List<Restaurant> tempList = new ArrayList<Restaurant>();
            try {
                tempList = gson.fromJson(restaurantList, new TypeToken<ArrayList<Restaurant>>() {}.getType());
            } catch(Exception ie) {
                System.out.println("Recieved json is not in the correct format |or| Restaurant is not set up correctly");
            }
            restaurantFromTags.add((ArrayList<Restaurant>) tempList);
        }

        return restaurantFromTags;
    }


    //sets the data member, restaurants, to the union of all the ArrayLists of Restaurants
    private void intersection(ArrayList<ArrayList<Restaurant> > rl) {
        ArrayList<Restaurant> common = new ArrayList<Restaurant>(rl.get(0));
        HashSet<Restaurant> tempHash = new HashSet<Restaurant>();

        //find all the common restaurants in each ArrayList
        for(int i=1; i<rl.size(); i++) {
            ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
            for(Restaurant x : rl.get(i)) {
                if(common.contains(x) == true) 
                    temp.add(x);
            }
            common = temp;
        }

        for(Restaurant x : common) {
            tempHash.add(x);
        }

        _restaurants = tempHash; 
    }

    private HashSet<Restaurant> finalRestaurants() {
        //Get the JSON String of an ArrayList of restauraunts we want and don't want
        ArrayList<String> restaurantList = getRestaurants(_wantTags);
        ArrayList<String> dealbreakerList = getRestaurants(_noWantTags);
        
        //assign values to restaurants and dealbreakers
        ArrayList<ArrayList<Restaurant> > tempRestaurants = parseJson(restaurantList);
        ArrayList<ArrayList<Restaurant> > tempDealbreakers = parseJson(dealbreakerList);


        //assign value to data member _restaurants
        if(tempRestaurants.size() == 1) {
            for(Restaurant r : tempRestaurants.get(0)) {
                _restaurants.add(r);
            }
        }

        else 
            intersection(tempRestaurants);


        //assign value to data member _dealbreakers
        for(ArrayList<Restaurant> m : tempDealbreakers) {
            for(Restaurant n : m) {
                _dealbreakers.add(n);
            }
        }


        //build the final list of restaurants
        HashSet<Restaurant> finalList = new HashSet<Restaurant>(_restaurants);

        for(Restaurant x : _dealbreakers) {
            finalList.remove(x);    //removing the dealbreakers
        }

        return finalList;
    }
}

