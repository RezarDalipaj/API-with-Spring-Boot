package springData.service;

import springData.dto.FlightDto;
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
    FlightDto converter(Flight f);
    Flight convertDtoToFlightAdd(FlightDto flightDTO);
    Flight convertDtoToFlightUpdate(FlightDto flightDTO, Integer id);
    Flight findFlightByFlightNumber(Integer flightNumber);
    List<Integer> findAllBookings(Integer id);
    List<Integer> findAllUsers(int id);

}
