package springData.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springData.dto.BookingDto;
import springData.dto.FlightDto;
import springData.model.Booking;
import springData.model.Flight;
import springData.model.User;
import springData.service.BookingService;
import springData.service.FlightService;
import springData.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    BookingService bookingService;
    FlightService flightService;
    UserService userService;
    BookingController(BookingService bookingService, FlightService flightService, UserService userService){
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.userService = userService;
    }
    @GetMapping
    public List<BookingDto> get(){
        try {
            return bookingService.findAll();
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Flights not found");
        }
    }
    @GetMapping("/{id}")
    public BookingDto getById(@PathVariable(name = "id") Integer id){
        BookingDto bookingDTO = bookingService.findById(id);
        if (bookingDTO != null){
            return bookingDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Booking doesn't exist");
    }
    @PostMapping
    public BookingDto post(@RequestBody BookingDto bookingDto){
        try {
            Booking booking = bookingService.convertDtoToBookingAdd(bookingDto);
            return bookingService.save(booking);
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @PutMapping("/{id}")
    public BookingDto put(@PathVariable(name = "id") Integer id, @RequestBody BookingDto bookingDto){
        try {
            if (bookingService.findById(id) != null){
                Booking booking = bookingService.convertDtoToBookingUpdate(bookingDto, id);
                return bookingService.save(booking);
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @DeleteMapping("/{id}")
    public BookingDto delete(@PathVariable(name = "id") Integer id){
        BookingDto bookingDTO = bookingService.findById(id);
        if (bookingDTO != null){
            bookingService.deleteById(id);
            return bookingDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
    }
    @DeleteMapping
    public List<BookingDto> deleteAll(){
        try {
            return bookingService.deleteAll();
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
    }
}
