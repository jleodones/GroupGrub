package main.java.salgrub.tagging;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
//import java.lang.reflect.Type;
import java.util.Objects;
import java.util.*;
import java.lang.String;

public class YelpAPISearch {
    public String location = "3551 Trousdale Pkwy, Los Angeles, CA 90089";
    public int search_radius = 40000;
    public int search_limit = 1000;

    //Search and get a list of restaurants by using the tag as a keyword
    //Returns list of restaurants as one json string
    public String SearchKeyWord(String tag) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            String builder = "https://api.yelp.com/v3/businesses/search" + "?term=" + tag +
            "&location=" + location + "&radius=" + search_radius + "&limit=" + 50 +
            "&offset" + search_limit + "&open_now=true"; 

            Request request = new Request.Builder().url(builder).method("GET", null).addHeader(
                "Authorization",
                "Bearer 8Mu2xwLuka36svOHtIxkDTIrhxBbhSgB0FQG8Y88OlCa5u6YKErhyByIMjeHTO9SqyFYzDgscUwYSGDlH2J2zLoEH6-6qF_GdxSWiEJPwUBv5ExHVCKH69ViDqR9YXYx")
                .build();
            Response response = client.newCall(request).execute();

            String restaurantJson = Objects.requireNonNull(response.body()).string();
            return restaurantJson;
        } catch (IOException ios) {
            ios.printStackTrace();
        }
        return null;
    }
    
    //Search for all the restaurants using a list of tags
    //Returns a list of jsons that associate with each tag 
    public ArrayList<String> getRestaurants(ArrayList<String> tags) {
        ArrayList<String> listOfJsons = new ArrayList<>();

        if(tags == null)
            return listOfJsons;

        for (String t : tags) {
            String search = SearchKeyWord(t);
            listOfJsons.add(search);
        }

        return listOfJsons;
    }

    public void SetLocation(String s) {
        this.location = s;
    }

    public void SetSearchRadius(int n) {
        search_radius = n;
    }

    public void SetSearchLimit(int n) {
        search_limit = n;
    }

    /*public static void main(String[] args) {
        YelpAPISearch search = new YelpAPISearch();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("burgers");
        tags.add("pizza");
        tags.add("sandwiches");
        ArrayList<String> jsons = search.getRestaurants(tags);
        System.out.println(jsons.toString());
    }*/
}
