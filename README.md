# baby

GitHub repo for our CSCI 201 project!

Changelog:
    Brandon Kim - 11/20/21 19:04 commit "Huge tagging push #1":
        Added:
            QueryResults.java
                - java Class File used to convert Yelp API Query Results to Restaurant Objects
        
        Updated:
            Restaurant.java
                - altered the object to include data that we may use in the future
                    - id: used to identify unique restaurants
                    - name: used to identify restaurant
                    - image_url: in case we want to display the image in frontend
                    - is_closed: checks to see if restaurant is accessible
                    - rating: frontend uses this to portray the rating of restaurant
                    - price: ^
                    - distance: may be used for future calculations/frontend
            
            Tagging.java
                - Finds the common Restaurants from all the tags => deletes all
                dealbreaker restaurants and closed restaurants