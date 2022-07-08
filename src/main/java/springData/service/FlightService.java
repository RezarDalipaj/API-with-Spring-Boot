package springData.service;

import springData.dto.FlightDTO;
import springData.model.Flight;

import java.util.List;
import java.util.Optional;
public interface FlightService {
    FlightDTO save(Flight f);
    FlightDTO findById(Integer id);
    public Flight findByIdd(Integer id);
    List<FlightDTO> findAll();
    void delete(Flight f);
    void deleteById(Integer id);
    FlightDTO converter(Flight f);
    List<Integer> findAllBookings(Integer id);
    List<Integer> findAllUsers(int id);

}
