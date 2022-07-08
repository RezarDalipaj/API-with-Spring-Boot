package springData.service;

import springData.dto.BookingDTO;
import springData.dto.FlightDTO;
import springData.dto.UserDTO;
import springData.model.Booking;
import springData.model.User;
import springData.model.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO save(User u);
    UserDTO findById(Integer id);
    User findByIdd(Integer id);
    List<UserDTO> findAll();
    UserDTO delete(User u);
    void deleteById(Integer id);
    UserDTO converter(User u);
    UserDTO findUserByBookings(Booking b);
    List<UserDTO> findAllByRoleContains(String s);
    List<UserDTO> findAllByUserName(String username);
    UserDTO findAllByUserDetails(UserDetails ud);
    List<Booking> findAllBookings(Integer id);
    List<FlightDTO> findAllFlights(int id);


}
