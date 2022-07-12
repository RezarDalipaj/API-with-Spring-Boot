package springData.service.impl;

import org.springframework.context.annotation.Lazy;
import springData.dto.BookingDto;
import springData.dto.FlightDto;
import springData.dto.UserDto;
import springData.model.Flight;
import springData.repository.FlightRepository;
import springData.service.BookingService;
import springData.service.FlightService;
import org.springframework.stereotype.Service;
import springData.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {
    private FlightRepository flightRepository;
    private FlightDto flightDTO;
    private UserService userService;
    private BookingService bookingService;
    FlightServiceImpl(FlightRepository flightRepository, FlightDto flightDTO, @Lazy UserService userService, @Lazy BookingService bookingService){
        this.flightRepository = flightRepository;
        this.flightDTO = flightDTO;
        this.userService = userService;
        this.bookingService = bookingService;
    }
    public FlightDto save(Flight f){
        return convertFlightToDto(flightRepository.save(f));
    }
    public FlightDto findById(Integer id){
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()){
            Flight flight = optionalFlight.get();
            return convertFlightToDto(flight);
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
        List<FlightDto> flightDTOS = flightRepository.findAll().stream().map(this::convertFlightToDto).collect(Collectors.toList());
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
    public FlightDto convertFlightToDto(Flight f1) {
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
    public List<BookingDto> findAllBookings(Integer id) {
        List<Integer> bookingIds = flightRepository.findAllBookingsOfAFlight(id);
        List<BookingDto> bookingDtoList = new ArrayList<>();
        for (Integer i:bookingIds) {
            BookingDto bookingDto = bookingService.findById(i);
            bookingDtoList.add(bookingDto);
        }
        return bookingDtoList;
    }

    @Override
    public List<UserDto> findAllUsers(Integer id) {
        List<Integer> userIds = flightRepository.findAllUsersOfAFlight(id);
        List<UserDto> userDtoList = new ArrayList<>();
        for (Integer i:userIds) {
            UserDto userDto = userService.findById(i);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
