package springData.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springData.dto.FlightDTO;
import springData.dto.UserDTO;
import springData.model.Flight;
import springData.model.User;
import springData.service.FlightService;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    FlightService flightService;
    FlightController(FlightService flightService){
        this.flightService = flightService;
    }
    @GetMapping
    public List<FlightDTO> get(){
        try {
            return flightService.findAll();
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Flights not found");
        }
    }
    @GetMapping("/{id}")
    public FlightDTO getById(@PathVariable(name = "id") Integer id){
        FlightDTO flightDTO = flightService.findById(id);
        if (flightDTO != null){
            return flightDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Flight doesn't exist");
    }
    @PostMapping
    public FlightDTO post(@RequestBody Flight flight){
        try {
        flight.setId(null);
        return flightService.save(flight);
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @PutMapping("/{id}")
    public FlightDTO put(@PathVariable(name = "id") Integer id, @RequestBody Flight flight){
        try {
            if (flightService.findById(id) != null){
                flight.setId(id);
                return flightService.save(flight);
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @DeleteMapping("/{id}")
    public FlightDTO delete(@PathVariable(name = "id") Integer id){
        FlightDTO flightDTO = flightService.findById(id);
        if (flightDTO != null){
            flightService.deleteById(id);
            return flightDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
    }
}
