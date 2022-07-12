package springData.service;

import springData.dto.BookingDto;
import springData.dto.FlightDto;
import springData.model.Booking;

import java.util.List;

public interface BookingService {
    BookingDto save(Booking booking);
    BookingDto findById(Integer id);
    Booking findByIdd(Integer id);
    List<BookingDto> findAll();
    void delete(Booking booking);
    void deleteById(Integer id);
    List<BookingDto> deleteAll();
    BookingDto convertBookingToDto(Booking b);
    Booking convertDtoToBookingAdd(BookingDto bookingDto);
    Booking convertDtoToBookingUpdate(BookingDto bookingDto, Integer id);
    List<FlightDto> findAllFlights(Integer id);

}
