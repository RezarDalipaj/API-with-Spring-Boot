package springData.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springData.dto.BookingDTO;
import springData.dto.UserDTO;
import springData.model.Booking;
import springData.model.User;
import springData.model.UserDetails;
import springData.service.UserDetailsService;
import springData.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    UserDetailsService userDetailsService;
    UserController(UserService userService, UserDetailsService userDetailsService){
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }
    @GetMapping
    public List<UserDTO> get(){
        try {
            return userService.findAll();
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Users not found");
        }
    }
    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable(name = "id") Integer id){
        UserDTO userDTO = userService.findById(id);
        if (userDTO != null){
            return userDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
    }
    @GetMapping("/username/{username}")
    public List<UserDTO> getUsersByUsername(@PathVariable(name = "username") String username){
            List<UserDTO> userDTOList = userService.findAllByUserName(username);
            if (userDTOList.isEmpty())
                throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
            return userDTOList;
    }
    @GetMapping("/role/{role}")
    public List<UserDTO> getUsersByRole(@PathVariable(name = "role") String role){
        List<UserDTO> userDTOList = userService.findAllByRoleContains(role);
        if (userDTOList.isEmpty())
            throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
        return userDTOList;
    }
    @PostMapping
    public UserDTO post(@RequestBody User user){
        try {
            user.setId(null);
            user.getUserDetails().setTheUser(user);
            UserDTO userDTO = userService.save(user);
            userDetailsService.save(user.getUserDetails());
            return userDTO;
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }

    @PutMapping("/{id}")
    public UserDTO put(@PathVariable(name = "id") Integer id, @RequestBody User user){
        try {
            if (userService.findById(id) != null){
                user.getUserDetails().setTheUser(user);
                user.setId(id);
                user.getUserDetails().setId(id);
                userService.save(user);
                userDetailsService.save(user.getUserDetails());
                return userService.save(user);
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Not found");
        }
         catch (Exception e){
             throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
         }
    }
    @DeleteMapping("/{id}")
    public UserDTO delete(@PathVariable(name = "id") Integer id){
        UserDTO userDTO = userService.findById(id);
        if (userDTO != null){
            userService.deleteById(id);
            return userDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
    }
}
