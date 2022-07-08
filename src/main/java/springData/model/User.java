package springData.model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table
//@NamedQueries({
//        @NamedQuery(name = "flightsOfAUser", query = "SELECT flight_id FROM user_flight uf WHERE uf.user_id = :userId"),
//        @NamedQuery(name = "bookingsOfAUser", query = "SELECT b FROM booking b WHERE user_id = :userId"),
//        @NamedQuery(name = "usersOfAFlight", query = "SELECT user_id FROM user_flight uf WHERE uf.flight_id = :flightId")
//})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;
    @Column(unique = true)
    private String userName;
    @Column
    private String password;
    @Column
    private String role;
    @OneToOne(mappedBy = "theUser", cascade = CascadeType.ALL)
    private UserDetails userDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Booking> bookings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", userdetails: first name="+ userDetails.getFirstName() + ", last name="+ userDetails.getLastName() + ", email="+ userDetails.getEmail() + ", phone number="+ userDetails.getPhoneNumber() +
                '}';
    }
}

