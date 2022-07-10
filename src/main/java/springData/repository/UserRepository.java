package springData.repository;

import springData.model.Booking;
import springData.model.Flight;
import springData.model.User;
import springData.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAllByRoleContains(String s);
    public User findUserByBookings(Booking b);
    public User findUserByUserName(String username);
    public User findAllByUserDetails(UserDetails ud);
    void deleteUserById(Integer id);
    @Override
    void deleteAll();

    @Query(value = "SELECT * FROM booking WHERE user_id = :id", nativeQuery = true)
    public List<Booking> findAllBookingsById(@Param("id") Integer id);
    @Query(value = "SELECT * FROM flight WHERE flight_id IN (SELECT flight_id FROM booking_flight WHERE booking_id = :bookingId)", nativeQuery = true)
    public List<Flight> findAllById(@Param("bookingId") int id);

}
