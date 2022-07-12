package springData.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springData.dto.BookingDto;
import springData.dto.FlightDto;
import springData.model.Booking;
import springData.model.Flight;
import springData.model.User;
import springData.security.config.JwtTokenUtil;
import springData.service.BookingService;
import springData.service.FlightService;
import springData.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    BookingService bookingService;
    FlightService flightService;
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    BookingController(BookingService bookingService, FlightService flightService, UserService userService){
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.userService = userService;
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<BookingDto> get(){
        try {
            return bookingService.findAll();
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Flights not found");
        }
    }
    @GetMapping("/{id}")
    public BookingDto getById(@PathVariable(name = "id") Integer id, HttpServletRequest request){
            BookingDto bookingDTO = bookingService.findById(id);
            if (bookingDTO != null){
                String token = request.getHeader("Authorization").substring(7);
                if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(bookingDTO.getUserName()))
                    throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
                return bookingDTO;
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Booking doesn't exist");
    }
    @GetMapping("/{id}/flights")
    public List<FlightDto> getFlightsOfABooking(@PathVariable(name = "id") Integer id, HttpServletRequest request){
            BookingDto bookingDTO = bookingService.findById(id);
            if (bookingDTO == null)
                throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
            String token = request.getHeader("Authorization").substring(7);
            if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(bookingDTO.getUserName()))
                throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
            List<FlightDto> flightDtoList = bookingService.findAllFlights(id);
            return flightDtoList;
    }

    @PostMapping
    public BookingDto post(@RequestBody BookingDto bookingDto, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization").substring(7);
            if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(bookingDto.getUserName()))
                throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
            Booking booking = bookingService.convertDtoToBookingAdd(bookingDto);
            return bookingService.save(booking);
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @PutMapping("/{id}")
    public BookingDto put(@PathVariable(name = "id") Integer id, @RequestBody BookingDto bookingDto, HttpServletRequest request){
        try {
            BookingDto bookingDTO = bookingService.findById(id);
            if (bookingDTO != null){
                String token = request.getHeader("Authorization").substring(7);
                if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(bookingDto.getUserName()))
                    throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
                else {
                    Booking booking = bookingService.convertDtoToBookingUpdate(bookingDto, id);
                    return bookingService.save(booking);
                }
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @DeleteMapping("/{id}")
    public BookingDto delete(@PathVariable(name = "id") Integer id, HttpServletRequest request){
            BookingDto bookingDTO = bookingService.findById(id);
            if (bookingDTO != null){
                String token = request.getHeader("Authorization").substring(7);
                if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(bookingDTO.getUserName()))
                    throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
                bookingService.deleteById(id);
                return bookingDTO;
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<BookingDto> deleteAll(){
        try {
            return bookingService.deleteAll();
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
    }
}
