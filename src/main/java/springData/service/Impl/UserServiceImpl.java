package springData.service.Impl;

import springData.dto.BookingDTO;
import springData.dto.FlightDTO;
import springData.dto.UserDTO;
import springData.model.Booking;
import springData.model.User;
import springData.model.UserDetails;
import springData.repository.UserRepository;
import springData.service.BookingService;
import springData.service.FlightService;
import springData.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BookingService bookingService;
    private FlightService flightService;
    UserServiceImpl(UserRepository userRepository, BookingService bookingService, FlightService flightService) {
        this.userRepository = userRepository;
        this.bookingService = bookingService;
        this.flightService = flightService;
    }

    //    UserRepository users = new UserRepositoryImpl();
    public UserDTO save(User u) {
        User user = userRepository.save(u);
        return converter(user);
    }

    public UserDTO findById(Integer id) {
        Optional <User> user = userRepository.findById(id);
        if (user.isPresent()){
            User user1 = user.get();
            return converter(user1);
        }
        else
            return null;
    }

    @Override
    public User findByIdd(Integer id) {
        Optional <User> user = userRepository.findById(id);
        if (user.isPresent()){
            User user1 = user.get();
            return user1;
        }
        else
            return null;
    }


    public List<UserDTO> findAll() {
        List<UserDTO> userDTOs = userRepository.findAll().stream().map(this::converter).collect(Collectors.toList());
        return userDTOs;
    }

    public UserDTO delete(User u) {
        UserDTO userDTO = findById(u.getId());
        if (userDTO != null){
            userRepository.delete(u);
            return userDTO;
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public UserDTO converter(User u) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(u.getUserName());
        userDTO.setRole(u.getRole());
        userDTO.setFirstName(u.getUserDetails().getFirstName());
        userDTO.setLastName(u.getUserDetails().getLastName());
        userDTO.setEmail(u.getUserDetails().getEmail());
        userDTO.setPhoneNumber(u.getUserDetails().getPhoneNumber());
        return userDTO;
    }

    @Override
    public UserDTO findUserByBookings(Booking b) {
        User user = userRepository.findUserByBookings(b);
        return converter(user);
    }

    @Override
    public List<UserDTO> findAllByRoleContains(String role) {
        List<UserDTO> userDTOs = userRepository.findAllByRoleContains(role).stream().map(this::converter).collect(Collectors.toList());
        return userDTOs;
    }

    @Override
    public List<UserDTO> findAllByUserName(String username) {
        List<UserDTO> userDTOs = userRepository.findAllByUserName(username).stream().map(this::converter).collect(Collectors.toList());
        return userDTOs;
    }

    @Override
    public UserDTO findAllByUserDetails(UserDetails ud) {
         User user = userRepository.findAllByUserDetails(ud);
         return converter(user);
    }

    @Override
    public List<Booking> findAllBookings(Integer id) {
        List<Booking> bookings = userRepository.findAllBookingsById(id);
        return bookings;
    }

    @Override
    public List<FlightDTO> findAllFlights(int id) {
        List<FlightDTO> flightDTOS = userRepository.findAllById(id).stream().map(flightService::converter).collect(Collectors.toList());
        return flightDTOS;
    }

}
