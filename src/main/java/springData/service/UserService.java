package springData.service;

import springData.dto.*;
import springData.model.Booking;
import springData.model.User;
import springData.model.UserDetails;

import java.util.List;

public interface UserService {
    UserDto save(User user);
    UserDto findById(Integer id);
    User findByIdd(Integer id);
    List<UserDto> findAll();
    UserDto delete(User u);
    void deleteById(Integer id);
    List<UserDto> deleteAll();
    UserDto convertUserToDto(User u);
    User convertDtoToUserAdd(UserDto userDto);
    User convertDtoToUserUpdate(UserDto userDto, Integer id);
    User setRoles(User user, RoleDto roleDto);
    UserDto findUserByBookings(Booking b);
    UserDto findUserByUserName(String username);
    User findByUserName(String username);
    UserDto findAllByUserDetails(UserDetails ud);
    List<BookingDto> findAllBookings(Integer id);
    List<FlightDto> findAllFlights(int id);


}
