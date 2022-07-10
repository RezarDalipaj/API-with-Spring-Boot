package springData.service.Impl;

import springData.dto.FlightDto;
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
    private FlightDto flightDTO;
    FlightServiceImpl(FlightRepository flightRepository, FlightDto flightDTO){
        this.flightRepository = flightRepository;
        this.flightDTO = flightDTO;
    }
//    FlightRepository flights = new FlightRepositoryImpl();
    public FlightDto save(Flight f){
        return converter(flightRepository.save(f));
    }
    public FlightDto findById(Integer id){
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
    public List<FlightDto> findAll(){
        List<FlightDto> flightDTOS = flightRepository.findAll().stream().map(this::converter).collect(Collectors.toList());
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
    public List<FlightDto> deleteAll() {
        List<FlightDto> flightDtoList = findAll();
        flightRepository.deleteAll();
        return flightDtoList;
    }

    @Override
    public FlightDto converter(Flight f1) {
        FlightDto flightDTO = new FlightDto();
        flightDTO.setFlightNumber(f1.getFlightNumber());
        flightDTO.setAirline(f1.getAirline());
        flightDTO.setOrigin(f1.getOrigin());
        flightDTO.setDestination(f1.getDestination());
        flightDTO.setStatus(f1.getStatus());
        flightDTO.setDepartureDate(f1.getDepartureDate());
        flightDTO.setArrivalDate(f1.getArrivalDate());
        return flightDTO;
    }
    @Override
    public Flight convertDtoToFlightAdd(FlightDto flightDTO){
        Flight flight = new Flight();
        if (findFlightByFlightNumber(flightDTO.getFlightNumber()) != null)
            return null;
        return setFlight(flight, flightDTO);
    }
    public Flight setFlight(Flight flight, FlightDto flightDTO){
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setAirline(flightDTO.getAirline());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setStatus(flightDTO.getStatus());
        flight.setDepartureDate(flightDTO.getDepartureDate());
        flight.setArrivalDate(flightDTO.getArrivalDate());
        return flight;
    }
    @Override
    public Flight convertDtoToFlightUpdate(FlightDto flightDTO, Integer id) {
        Flight flight = findByIdd(id);
        String model = String.valueOf(flight.getFlightNumber());
        String dto = String.valueOf(flightDTO.getFlightNumber());
        if (model.equalsIgnoreCase(dto))
            return setFlight(flight, flightDTO);
        else if (findFlightByFlightNumber(flightDTO.getFlightNumber()) != null){
            return null;
        }
        else
            return setFlight(flight, flightDTO);
    }

    @Override
    public Flight findFlightByFlightNumber(Integer flightNumber) {
        return flightRepository.findFlightByFlightNumber(flightNumber);
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
