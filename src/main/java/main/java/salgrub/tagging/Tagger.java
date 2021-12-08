package main.java.salgrub.tagging;

import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Tagger {

    //List of tags and dealbreakers that will be provided
    private ArrayList<String> _wantTags;
    private ArrayList<String> _noWantTags;

    //Key: ID, Val: Restaurant Object
    private HashMap<String, Restaurant> _restaurants = new HashMap<String, Restaurant>();
    private HashMap<String, Restaurant> _dealbreakers = new HashMap<String, Restaurant>();
    
    private static HashMap<String, Restaurant> _finalList = new HashMap<String, Restaurant>();

    //Gets the keywords from Yelp API
    private static YelpAPISearch yelp;

    //
    public Tagger(ArrayList<String> want, ArrayList<String> noWant, double latitude, double longitude) {
        this._wantTags = want;
        this._noWantTags = noWant;
        yelp = new YelpAPISearch();
        yelp.SetLocation(latitude, longitude);
    }
    
    public HashMap<String, Restaurant> getFinalList() {
    	return _finalList;
    }
    
    //Parses the Json Strings of Restaurants to be added to the hashset of restaurants to be used
    private ArrayList<QueryResults> parseJson(ArrayList<String> jsonRestaurant) {
        Gson gson = new Gson();

        ArrayList<QueryResults> restaurantFromTags = new ArrayList<QueryResults>();
        
        for(String restaurantList : jsonRestaurant) {
            QueryResults temp = null;
            try {
                temp = gson.fromJson(restaurantList, new TypeToken<QueryResults>() {}.getType());
            } catch(Exception ie) {
                System.out.println("Recieved json is not in the correct format |or| Restaurant is not set up correctly");
            }
            restaurantFromTags.add((QueryResults) temp);
        }

        return restaurantFromTags;
    }


    //sets the data member, restaurants, to the union of all the ArrayLists of Restaurants
    private void intersection(ArrayList<QueryResults> rl) {
        ArrayList<Restaurant> common = new ArrayList<Restaurant>(rl.get(0).getBusinesses());
        
        HashMap<String, Restaurant> tempHash = new HashMap<String, Restaurant>();

        //deletes all closed restaurants from the list
        for(Restaurant r : common ){
            if(r.getIs_closed())
                common.remove(r);
        }
        
        //Adds restaurants to list that do not already exist.
        for(int i=1; i<rl.size(); i++) {
            ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
            
            //Were there any businesses returned?
            if(rl.get(i).getBusinesses() == null) {
            	continue;
            }
            
            //If there was a business, check if it's closed and if it already exists in the master list.
            for(Restaurant x : rl.get(i).getBusinesses()) {
                if(!x.getIs_closed() && !common.contains(x)) {
                	//If not closed and not already in list, add.
                   common.add(x);
                }
            }
        }

        for(Restaurant x : common) {
            tempHash.put(x.getID(), x);
        }

        _restaurants = tempHash; 
    }

    public ArrayList<Restaurant> finalRestaurants() {
        //Get the JSON String of an ArrayList of restaurants we want and don't want
        ArrayList<String> restaurantList = yelp.getRestaurants(_wantTags);
        ArrayList<String> dealbreakerList = yelp.getRestaurants(_noWantTags);
        
        //assign values to restaurants and dealbreakers
        ArrayList<QueryResults> tempRestaurants = parseJson(restaurantList);
        ArrayList<QueryResults > tempDealbreakers = parseJson(dealbreakerList);
                
        //assign value to data member _restaurants
        if(tempRestaurants.size() == 1) {
            for(Restaurant r : tempRestaurants.get(0).getBusinesses()) {
                if(!r.getIs_closed())
                    _restaurants.put(r.getID(), r);
            }
        }
        else {
            intersection(tempRestaurants);
        }
            
        //assign value to data member _dealbreakers
        for(QueryResults qr: tempDealbreakers) {
        	if(qr.getBusinesses() == null) {
        		continue;
        	}
            for(Restaurant r : qr.getBusinesses()) {
                _dealbreakers.put(r.getID(), r);
            }
        }
        
        //build the final list of restaurants
        HashMap<String, Restaurant> finalList = new HashMap<String, Restaurant>(_restaurants);

        for(Map.Entry<String, Restaurant>  x : _dealbreakers.entrySet()) {
            finalList.remove(x.getKey());    //removing the dealbreakers
        }

        _finalList = finalList;
        
        Collection <Restaurant> setRestaurants = finalList.values();
        ArrayList<Restaurant> fl = new ArrayList<>(setRestaurants);
        
        return fl;
    }
}

