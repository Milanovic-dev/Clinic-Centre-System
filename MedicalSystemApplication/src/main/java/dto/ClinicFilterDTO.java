package dto;

public class ClinicFilterDTO {

	private String name;
    private String address;
    private String city;
    private String state;
    private float rating;
    
    
  
	public ClinicFilterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClinicFilterDTO(String name, String address, String city, String state, float rating) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
    
 	
}
