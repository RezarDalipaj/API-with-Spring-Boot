package springData.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springData.dto.UserDto;
import springData.model.Flight;
import springData.model.User;
import springData.service.UserDetailsService;
import springData.service.UserService;

import javax.persistence.NonUniqueResultException;
import java.util.List;

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
    public List<UserDto> get(){
        try {
            return userService.findAll();
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Users not found");
        }
    }
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable(name = "id") Integer id){
        UserDto userDTO = userService.findById(id);
        if (userDTO != null){
            return userDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
    }
    @GetMapping("/username")
    public UserDto getUsersByUsername(@RequestParam(name = "username") String username){
            UserDto userDTO = userService.findUserByUserName(username);
            if (userDTO == null)
                throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
            return userDTO;
    }
    @GetMapping("/role/{role}")
    public List<UserDto> getUsersByRole(@PathVariable(name = "role") String role){
        List<UserDto> userDTOList = userService.findAllByRoleContains(role);
        if (userDTOList.isEmpty())
            throw  new ResponseStatusException(HttpStatus.resolve(404), "User doesn't exist");
        return userDTOList;
    }
    @PostMapping
    public UserDto post(@RequestBody UserDto userDTO){
        try {
            if (userDTO.getUserName() == null)
                throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
            User user = userService.convertDtoToUserAdd(userDTO);
            return userService.save(user);
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
        }
    }

    @PutMapping("/{id}")
    public UserDto put(@PathVariable(name = "id") Integer id, @RequestBody UserDto userDTO){
        try {
            if (userDTO.getUserName() == null)
                throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
            if (userService.findById(id) != null){
                User user = userService.convertDtoToUserUpdate(userDTO, id);
                if (user!=null)
                    return userService.save(user);
                throw  new ResponseStatusException(HttpStatus.resolve(400), "Not found");
            }
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Not found");
        }
         catch (Exception e){
             throw  new ResponseStatusException(HttpStatus.resolve(400), "Invalid data");
         }
    }
    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable(name = "id") Integer id){
        UserDto userDTO = userService.findById(id);
        if (userDTO != null){
            userService.deleteById(id);
            return userDTO;
        }
        throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
    }
    @DeleteMapping
    public List<UserDto> deleteAll(){
        try {
            return userService.deleteAll();
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.resolve(404), "Invalid data");
        }
    }
}
