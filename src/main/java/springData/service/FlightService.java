package springData.service;

import springData.dto.BookingDto;
import springData.dto.FlightDto;
import springData.dto.UserDto;
import springData.model.Flight;

import java.util.List;

public interface FlightService {
    FlightDto save(Flight f);
    FlightDto findById(Integer id);
    public Flight findByIdd(Integer id);
    List<FlightDto> findAll();
    void delete(Flight f);
    void deleteById(Integer id);
    List<FlightDto> deleteAll();
    FlightDto convertFlightToDto(Flight f);
    Flight convertDtoToFlightAdd(FlightDto flightDTO);
    Flight convertDtoToFlightUpdate(FlightDto flightDTO, Integer id);
    Flight findFlightByFlightNumber(Integer flightNumber);
    List<BookingDto> findAllBookings(Integer id);
    List<UserDto> findAllUsers(Integer id);

}
