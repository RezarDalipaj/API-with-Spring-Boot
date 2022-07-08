package springData.service.Impl;

import springData.dto.FlightDTO;
import springData.model.Flight;
import springData.repository.FlightRepository;
import springData.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {
    private FlightRepository flightRepository;
    private FlightDTO flightDTO;
    FlightServiceImpl(FlightRepository flightRepository, FlightDTO flightDTO){
        this.flightRepository = flightRepository;
        this.flightDTO = flightDTO;
    }
//    FlightRepository flights = new FlightRepositoryImpl();
    public FlightDTO save(Flight f){
        Flight flight = flightRepository.save(f);
        return converter(flight);
    }
    public FlightDTO findById(Integer id){
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()){
            Flight flight = optionalFlight.get();
            return converter(flight);
        }
        return null;
    }
    public Flight findByIdd(Integer id){
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()){
            Flight flight = optionalFlight.get();
            return flight;
        }
        return null;
    }
    public List<FlightDTO> findAll(){
        List<FlightDTO> flightDTOS = flightRepository.findAll().stream().map(this::converter).collect(Collectors.toList());
        return flightDTOS;
    }
    public void delete(Flight f){
        flightRepository.delete(f);
    }

    @Override
    public void deleteById(Integer id) {
        flightRepository.deleteById(id);
    }

    @Override
    public FlightDTO converter(Flight f1) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setAirline(f1.getAirline());
        flightDTO.setOrigin(f1.getOrigin());
        flightDTO.setDestination(f1.getDestination());
        flightDTO.setStatus(f1.getStatus());
        flightDTO.setDepartureDate(f1.getDepartureDate());
        flightDTO.setArrivalDate(f1.getArrivalDate());
        return flightDTO;
    }

    @Override
    public List<Integer> findAllBookings(Integer id) {
        return flightRepository.findAllById(id);
    }

    @Override
    public List<Integer> findAllUsers(int id) {
        return flightRepository.findAllById(id);
    }
}
