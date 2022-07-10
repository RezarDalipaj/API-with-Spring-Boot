package springData.service.Impl;

import springData.dto.FlightDto;
import springData.dto.UserDto;
import springData.model.Booking;
import springData.model.User;
import springData.model.UserDetails;
import springData.repository.UserDetailsRepository;
import springData.repository.UserRepository;
import springData.service.BookingService;
import springData.service.FlightService;
import springData.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;
    private BookingService bookingService;
    private FlightService flightService;
    UserServiceImpl(UserRepository userRepository, BookingService bookingService, FlightService flightService, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.userDetailsRepository = userDetailsRepository;
    }

    //    UserRepository users = new UserRepositoryImpl();
    public UserDto save(User u) {
        User user = userRepository.save(u);
        return convertUserToDto(user);
    }

    public UserDto findById(Integer id) {
        Optional <User> user = userRepository.findById(id);
        if (user.isPresent()){
            User user1 = user.get();
            return convertUserToDto(user1);
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


    public List<UserDto> findAll() {
        List<UserDto> userDTOs = userRepository.findAll().stream().map(this::convertUserToDto).collect(Collectors.toList());
        return userDTOs;
    }

    public UserDto delete(User u) {
        UserDto userDTO = findById(u.getId());
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

    @Override
    public List<UserDto> deleteAll() {
        List<UserDto> userDtoList = findAll();
        userRepository.deleteAll();
        return userDtoList;
    }
    public UserDto convertUserToDto(User u) {
        UserDto userDTO = new UserDto();
        userDTO.setUserName(u.getUserName());
        userDTO.setPassword(u.getPassword());
        userDTO.setRole(u.getRole());
        userDTO.setFirstName(u.getUserDetails().getFirstName());
        userDTO.setLastName(u.getUserDetails().getLastName());
        userDTO.setEmail(u.getUserDetails().getEmail());
        userDTO.setPhoneNumber(u.getUserDetails().getPhoneNumber());
        return userDTO;
    }

    @Override
    public User convertDtoToUserAdd(UserDto userDto) {
        User user = new User();
        if (findUserByUserName(userDto.getUserName())!=null)
            return null;
        return setUserAdd(user, userDto);
    }

    @Override
    public User convertDtoToUserUpdate(UserDto userDto, Integer id) {
        User user = findByIdd(id);
        if (user.getUserName().equals(userDto.getUserName())){
            return setUserUpdate(user, userDto);
        }
        else if (findUserByUserName(userDto.getUserName()) != null) {
            System.out.println(user.getUserName() + " " + userDto.getUserName());
            return null;
        }
        else
            return setUserUpdate(user, userDto);
    }

    public User setUserAdd(User user1, UserDto userDTO){
            setUser(user1, userDTO);
            UserDetails userDetails = new UserDetails();
            setUserDetails(user1,userDTO,userDetails);
            user1.setUserDetails(userDetails);
            save(user1);
            userDetailsRepository.save(userDetails);
            return user1;
    }
    public void setUser(User user, UserDto userDTO){
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
    }
    public void setUserDetails(User user1, UserDto userDTO, UserDetails userDetails){
        userDetails.setFirstName(userDTO.getFirstName());
        userDetails.setLastName(userDTO.getLastName());
        userDetails.setEmail(userDTO.getEmail());
        userDetails.setPhoneNumber(userDTO.getPhoneNumber());
        userDetails.setTheUser(user1);
    }
    public User setUserUpdate(User user, UserDto userDTO){
        setUser(user,userDTO);
        return setUserDetailsUpdate(user,userDTO);
    }
    public User setUserDetailsUpdate(User user1, UserDto userDTO){
        user1.getUserDetails().setFirstName(userDTO.getFirstName());
        user1.getUserDetails().setLastName(userDTO.getLastName());
        user1.getUserDetails().setEmail(userDTO.getEmail());
        user1.getUserDetails().setPhoneNumber(userDTO.getPhoneNumber());
        save(user1);
        userDetailsRepository.save(user1.getUserDetails());
        return user1;
    }
    @Override
    public UserDto findUserByBookings(Booking b) {
        User user = userRepository.findUserByBookings(b);
        return convertUserToDto(user);
    }

    @Override
    public List<UserDto> findAllByRoleContains(String role) {
        List<UserDto> userDTOs = userRepository.findAllByRoleContains(role).stream().map(this::convertUserToDto).collect(Collectors.toList());
        return userDTOs;
    }

    @Override
    public UserDto findUserByUserName(String username) {
        if(userRepository.findUserByUserName(username) != null)
            return convertUserToDto(userRepository.findUserByUserName(username));
        else
            return null;
    }

    @Override
    public UserDto findAllByUserDetails(UserDetails ud) {
         User user = userRepository.findAllByUserDetails(ud);
         return convertUserToDto(user);
    }

    @Override
    public List<Booking> findAllBookings(Integer id) {
        List<Booking> bookings = userRepository.findAllBookingsById(id);
        return bookings;
    }

    @Override
    public List<FlightDto> findAllFlights(int id) {
        List<FlightDto> flightDTOS = userRepository.findAllById(id).stream().map(flightService::converter).collect(Collectors.toList());
        return flightDTOS;
    }

}
