package springData.service.Impl;

import springData.dto.BookingDto;
import springData.dto.FlightDto;
import springData.model.Booking;
import springData.model.Flight;
import springData.model.User;
import springData.repository.BookingRepository;
import springData.repository.UserRepository;
import springData.service.BookingService;
import springData.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private FlightService flightService;
    public BookingServiceImpl(BookingRepository bookingRepository, FlightService flightService, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.userRepository = userRepository;
    }

    public BookingDto save(Booking b){
        Booking booking = bookingRepository.save(b);
        return converter(booking);
    }
    public BookingDto findById(Integer id){
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()){
            Booking booking = optionalBooking.get();
            return converter(booking);
        }
        return null;
    }

    @Override
    public Booking findByIdd(Integer id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()){
            Booking booking = optionalBooking.get();
            return booking;
        }
        return null;
    }

    public List<BookingDto> findAll(){
        List<BookingDto> bookingDTOS = bookingRepository.findAll().stream().map(this::converter).collect(Collectors.toList());
        return bookingDTOS;
    }
    public void delete(Booking u){
            bookingRepository.delete(u);
    }

    @Override
    public void deleteById(Integer id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingDto> deleteAll() {
        List<BookingDto> bookingDtoList = findAll();
        bookingRepository.deleteAll();
        return bookingDtoList;
    }

    @Override
    public BookingDto converter(Booking b) {
        BookingDto bookingDTO = new BookingDto();
        bookingDTO.setUserName(b.getUser().getUserName());
        bookingDTO.setBookingDate(b.getBookingDate());
        bookingDTO.setStatus(b.getStatus());
        List<FlightDto> flightDTOList = new ArrayList<>();
        List<Flight> flightList = b.getFlights();
        convertFlights(flightDTOList, flightList);
        bookingDTO.setFlightList(flightDTOList);
        return bookingDTO;
    }

    @Override
    public Booking convertDtoToBookingAdd(BookingDto bookingDto) {
        Booking booking = new Booking();
        return setBooking(bookingDto, booking);
    }
    public Booking setBooking(BookingDto bookingDto, Booking booking){
        User user = userRepository.findUserByUserName(bookingDto.getUserName());
        if (user == null)
            return null;
        booking.setUser(user);
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setStatus(bookingDto.getStatus());
        List<Integer> flightNumbers = bookingDto.getFlightNumbers();
        List<Flight> flightList = new ArrayList<>();
        for (Integer i:flightNumbers) {
            if (flightService.findFlightByFlightNumber(i) == null)
                return null;
            Flight flight = flightService.findFlightByFlightNumber(i);
            flightList.add(flight);
        }
        booking.setFlights(flightList);
        return booking;
    }
    @Override
    public Booking convertDtoToBookingUpdate(BookingDto bookingDto, Integer id) {
        Booking booking = findByIdd(id);
        return setBooking(bookingDto, booking);
    }

    void convertFlights (List<FlightDto> flightDTOList, List<Flight> flightList){
        for (Flight f:flightList) {
            FlightDto flightDTO = new FlightDto();
            flightDTO.setAirline(f.getAirline());
            flightDTO.setOrigin(f.getOrigin());
            flightDTO.setDestination(f.getDestination());
            flightDTOList.add(flightDTO);
        }
    }
    @Override
    public List<Integer> findAllFlights(Integer id) {
        return bookingRepository.findAllById(id);
    }
}
