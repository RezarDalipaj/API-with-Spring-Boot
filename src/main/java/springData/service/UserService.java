package springData.service;

import springData.dto.FlightDto;
import springData.dto.UserDto;
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
    User convertDtoToUserAdd(UserDto userDto);
    User convertDtoToUserUpdate(UserDto userDto, Integer id);
    UserDto findUserByBookings(Booking b);
    List<UserDto> findAllByRoleContains(String s);
    UserDto findUserByUserName(String username);
    UserDto findAllByUserDetails(UserDetails ud);
    List<Booking> findAllBookings(Integer id);
    List<FlightDto> findAllFlights(int id);


}
