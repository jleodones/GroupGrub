package main.java.salgrub;

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

	public void setId(String id) {
		this.id = id;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
    	return this.name;
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
