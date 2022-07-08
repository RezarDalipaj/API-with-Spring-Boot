package springData.service.Impl;

import springData.dto.BookingDTO;
import springData.dto.FlightDTO;
import springData.model.Booking;
import springData.model.Flight;
import springData.repository.BookingRepository;
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
    private FlightService flightService;
    public BookingServiceImpl(BookingRepository bookingRepository, FlightService flightService) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
    }

    public BookingDTO save(Booking b){
        Booking booking = bookingRepository.save(b);
        return converter(booking);
    }
    public BookingDTO findById(Integer id){
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()){
            Booking booking = optionalBooking.get();
            return converter(booking);
        }
        return null;
    }
    public List<BookingDTO> findAll(){
        List<BookingDTO> bookingDTOS = bookingRepository.findAll().stream().map(this::converter).collect(Collectors.toList());
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
    public BookingDTO converter(Booking b) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUserName(b.getUser().getUserName());
        bookingDTO.setBookingDate(b.getBookingDate());
        bookingDTO.setStatus(b.getStatus());
        List<FlightDTO> flightDTOList = new ArrayList<>();
        List<Flight> flightList = b.getFlights();
        convertFlights(flightDTOList, flightList);
        bookingDTO.setFlightList(flightDTOList);
        return bookingDTO;
    }
    void convertFlights (List<FlightDTO> flightDTOList, List<Flight> flightList){
        for (Flight f:flightList) {
            FlightDTO flightDTO = new FlightDTO();
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
