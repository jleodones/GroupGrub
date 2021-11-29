package tagging;

public class Restaurant {
    private String name;
    private String id;
    private String image_url;
    private boolean is_closed;
    private Double rating;  
    private String price; 
    private Double distance;

    public String getID() {
        return this.id;
    }

    public boolean getIs_closed() {
        return this.is_closed;
    }

    @Override
    public String toString() {
        return "name: " + this.name + "\n" + 
        "id: " + this.id + "\n" +
        "image_url: " + this.image_url+ "\n" +
        "is_closed: " + this.is_closed+ "\n" +
        "rating: " + Double.toString(this.rating) + "\n" +
        "price: " + this.price + "\n" +
        "distance: " + Double.toString(this.distance)+ "\n";
    }
}
