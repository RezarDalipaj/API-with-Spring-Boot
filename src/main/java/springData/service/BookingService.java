package springData.service;

import springData.dto.BookingDTO;
import springData.model.Booking;

import java.util.List;
import java.util.Optional;
public interface BookingService {
    BookingDTO save(Booking booking);
    BookingDTO findById(Integer id);
    List<BookingDTO> findAll();
    void delete(Booking booking);
    void deleteById(Integer id);
    BookingDTO converter(Booking b);
    List<Integer> findAllFlights(Integer id);

}
