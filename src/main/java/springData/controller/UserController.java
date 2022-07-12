package springData.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springData.dto.*;
import springData.model.Role;
import springData.model.User;
import springData.repository.RoleRepository;
import springData.security.config.JwtTokenUtil;
import springData.service.RoleService;
import springData.service.UserDetailsService;
import springData.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    UserDetailsService userDetailsService;
    JwtTokenUtil jwtTokenUtil;
    RoleRepository roleRepository;
    RoleService roleService;
    UserController(UserService userService, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, RoleRepository roleRepository, RoleService roleService){
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> get(){
        try {
            return userService.findAll();
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Users not found");
        }
    }
    @PutMapping("/{id}/change/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDto changeRoles(@PathVariable(name = "id") Integer id, @RequestBody RoleDto roleDto){
        try {
            if (userService.findById(id) == null)
                throw  new ResponseStatusException(HttpStatus.resolve(404), "Users not found");
            User user = userService.findByIdd(id);
            user = userService.setRoles(user,roleDto);
            return userService.save(user);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Users not found");
        }
    }
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable(name = "id") Integer id, HttpServletRequest request){
            UserDto userDTO = userService.findById(id);
            if (userDTO != null){
                String token = request.getHeader("Authorization").substring(7);
                if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(userDTO.getUserName()))
                    throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
                return userDTO;
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
    }
    @GetMapping("/username")
    public UserDto getUsersByUsername(@RequestParam(name = "username") String username, HttpServletRequest request){
            UserDto userDTO = userService.findUserByUserName(username);
            if (userDTO == null)
                throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
            String token = request.getHeader("Authorization").substring(7);
            if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(username))
                throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
            return userDTO;
    }
    @GetMapping("/{id}/bookings")
    public List<BookingDto> getBookingsOfAUser(@PathVariable(name = "id") Integer id, HttpServletRequest request){
            UserDto userDto = userService.findById(id);
            if (userDto == null)
                throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
            String token = request.getHeader("Authorization").substring(7);
            if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(userDto.getUserName()))
                throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
            List<BookingDto> bookingDtoList = userService.findAllBookings(id);
            return bookingDtoList;
    }
    @GetMapping("/{id}/flights")
    public List<FlightDto> getFlightsOfAUser(@PathVariable(name = "id") Integer id, HttpServletRequest request){
            UserDto userDto = userService.findById(id);
            if (userDto == null)
                throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
            String token = request.getHeader("Authorization").substring(7);
            if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(userDto.getUserName()))
                throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
            List<FlightDto> flightDtoList = userService.findAllFlights(id);
            return flightDtoList;
    }
    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable(name = "id") Integer id, HttpServletRequest request){
            UserDto userDTO = userService.findById(id);
            if (userDTO != null){
                String token = request.getHeader("Authorization").substring(7);
                if (!(jwtTokenUtil.getUsernameFromToken(token)).equals(userDTO.getUserName()))
                    throw  new ResponseStatusException(HttpStatus.resolve(401), "User doesn't exist");
                userService.deleteById(id);
                return userDTO;
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> deleteAll(){
        try {
            return userService.deleteAll();
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
    }
    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Role> getRoles(){
        try {
            return roleRepository.findAll();
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
    }
    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role getRoles(@PathVariable(name = "id") Integer id){
        try {
            return roleService.findById(id);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
    }
    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role addRole(@RequestBody Role role){
        try {
            return roleRepository.save(role);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @PutMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role putRole(@PathVariable(name = "id") Integer id, @RequestBody Role role){
        try {
            Role role1 = roleService.findById(id);
            role1.setRole(role.getRole());
            roleRepository.save(role1);
            return role1;
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role deleteRole(@PathVariable(name = "id") Integer id){
        try {
            return roleService.deleteById(id);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
    @DeleteMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Role> deleteRoles(){
        try {
            return roleService.deleteAll();
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }
}
