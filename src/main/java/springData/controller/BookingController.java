package springData.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springData.dto.BookingDTO;
import springData.dto.FlightDTO;
import springData.model.Booking;
import springData.model.Flight;
import springData.model.User;
import springData.service.BookingService;
import springData.service.FlightService;
import springData.service.UserService;

import java.util.ArrayList;
import java.util.Iterator;
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
    public List<BookingDTO> get(){
        try {
            return bookingService.findAll();
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Flights not found");
        }
    }
    @GetMapping("/{id}")
    public BookingDTO getById(@PathVariable(name = "id") Integer id){
        BookingDTO bookingDTO = bookingService.findById(id);
        if (bookingDTO != null){
            return bookingDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Flight doesn't exist");
    }
    @PostMapping
    public BookingDTO post(@RequestBody Booking booking){
        try {
            booking.setId(null);
            setFlights(booking);
            setUser(booking);
            return bookingService.save(booking);
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @PutMapping("/{id}")
    public BookingDTO put(@PathVariable(name = "id") Integer id, @RequestBody Booking booking){
        try {
            booking.setId(id);
            setFlights(booking);
            setUser(booking);
            return bookingService.save(booking);
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    void setFlights(Booking booking){
        List<Flight> flightList = booking.getFlights();
        for (Integer i = 0;i<flightList.size();i++) {
            if (flightService.findById(flightList.get(i).getId()) == null){
                throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
            }
            Flight flight1 = flightService.findByIdd(flightList.get(i).getId());
            booking.getFlights().set(i,flight1);
        }
    }
    void setUser(Booking booking){
        User user = booking.getUser();
        if (userService.findById(user.getId()) == null){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
        User user1 = userService.findByIdd(user.getId());
        booking.setUser(user1);
    }
    @DeleteMapping("/{id}")
    public BookingDTO delete(@PathVariable(name = "id") Integer id){
        BookingDTO bookingDTO = bookingService.findById(id);
        if (bookingDTO != null){
            bookingService.deleteById(id);
            return bookingDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
    }
}
