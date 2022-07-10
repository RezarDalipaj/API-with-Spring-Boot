package springData.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springData.dto.FlightDto;
import springData.model.Flight;
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
    public List<FlightDto> get(){
        try {
            return flightService.findAll();
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Flights not found");
        }
    }
    @GetMapping("/{id}")
    public FlightDto getById(@PathVariable(name = "id") Integer id){
        FlightDto flightDTO = flightService.findById(id);
        if (flightDTO != null){
            return flightDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Flight doesn't exist");
    }
    @PostMapping
    public FlightDto post(@RequestBody FlightDto flightDto){
        try {
        if (flightDto.getFlightNumber() == null)
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        Flight flight = flightService.convertDtoToFlightAdd(flightDto);
        return flightService.save(flight);
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @PutMapping("/{id}")
    public FlightDto put(@PathVariable(name = "id") Integer id, @RequestBody FlightDto flightDto){
        try {
            if (flightDto.getFlightNumber() == null)
                throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
            if (flightService.findByIdd(id) != null){
                Flight flight = flightService.convertDtoToFlightUpdate(flightDto, id);
                if (flight != null)
                    return flightService.save(flight);
                throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @DeleteMapping("/{id}")
    public FlightDto delete(@PathVariable(name = "id") Integer id){
        FlightDto flightDTO = flightService.findById(id);
        if (flightDTO != null){
            flightService.deleteById(id);
            return flightDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
    }
    @DeleteMapping
    public List<FlightDto> deleteAll(){
        try {
            return flightService.deleteAll();
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
    }
}
