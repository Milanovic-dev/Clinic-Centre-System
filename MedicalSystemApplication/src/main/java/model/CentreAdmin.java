package model;

import javax.persistence.*;
import java.util.ArrayList;

public class CentreAdmin extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArrayList<RegistrationRequest> registrationRequests;


    public CentreAdmin() {
    }

    public CentreAdmin(String username, String password, String email, String name, String surname, String city, String address, String state, String phone, ClinicCenter clinicCentre) {
        super(username, password, email, name, surname, city, address, state, phone, UserRole.CentreAdmin);
        this.registrationRequests = new ArrayList<RegistrationRequest>();
    }

    public ArrayList<RegistrationRequest> getRegistrationRequests() {
        return registrationRequests;
    }

    public void setRegistrationRequests(ArrayList<RegistrationRequest> registrationRequests) {
        this.registrationRequests = registrationRequests;
    }

}
