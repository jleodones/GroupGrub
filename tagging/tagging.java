import java.util.*;
import java.io.*;
import java.lang.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Tagging {
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

    //Gets the keywords from Yelp API
    private static YelpAPISearch yelp;

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
        ArrayList<String> restaurantList = yelp.getRestaurants(_wantTags);
        ArrayList<String> dealbreakerList = yelp.getRestaurants(_noWantTags);
        
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

    public static void main(String[] args) {
        
        YelpAPISearch search = new YelpAPISearch();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("burgers");
        //tags.add("pizza");
        //tags.add("sandwiches");
        ArrayList<String> jsons = search.getRestaurants(tags);
        
        Tagging tagger = new Tagging();
        ArrayList<ArrayList<Restaurant> > objs = tagger.parseJson(jsons);
        tagger.intersection(objs);
        tagger.finalRestaurants();
    }
}

