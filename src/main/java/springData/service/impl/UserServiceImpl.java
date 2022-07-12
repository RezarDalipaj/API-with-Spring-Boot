package springData.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import springData.dto.*;
import springData.model.Booking;
import springData.model.Role;
import springData.model.User;
import springData.model.UserDetails;
import springData.repository.RoleRepository;
import springData.repository.UserDetailsRepository;
import springData.repository.UserRepository;
import springData.service.BookingService;
import springData.service.FlightService;
import springData.service.UserService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;
    private BookingService bookingService;
    private FlightService flightService;
    @Autowired
    private RoleRepository roleRepository;
    UserServiceImpl(UserRepository userRepository, BookingService bookingService, FlightService flightService, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.userDetailsRepository = userDetailsRepository;
    }
    public UserDto save(User u) {
        User user = userRepository.save(u);
        return convertUserToDto(user);
    }
    public UserDto findById(Integer id) {
        Optional <User> user = userRepository.findById(id);
        if (user.isPresent()){
            User user1 = user.get();
            return convertUserToDto(user1);
        }
        else
            return null;
    }
    @Override
    public User findByIdd(Integer id) {
        Optional <User> user = userRepository.findById(id);
        if (user.isPresent()){
            User user1 = user.get();
            return user1;
        }
        else
            return null;
    }
    public List<UserDto> findAll() {
        List<UserDto> userDTOs = userRepository.findAll().stream().map(this::convertUserToDto).collect(Collectors.toList());
        return userDTOs;
    }
    public UserDto delete(User u) {
        UserDto userDTO = findById(u.getId());
        if (userDTO != null){
            removeUserFromPreviousRoles(u);
            userRepository.delete(u);
            return userDTO;
        }
        return null;
    }
    @Override
    public void deleteById(Integer id) {
        User user = findByIdd(id);
        removeUserFromPreviousRoles(user);
        userRepository.deleteById(id);
    }
    @Override
    public List<UserDto> deleteAll() {
        List<UserDto> userDtoList = findAll();
        List<User> userList = userRepository.findAll();
        for (User user: userList) {
            removeUserFromPreviousRoles(user);
        }
        userRepository.deleteAll();
        return userDtoList;
    }
    @Override
    public UserDto convertUserToDto(User u) {
        UserDto userDTO = new UserDto();
        userDTO.setUserName(u.getUserName());
        userDTO.setPassword(u.getPassword());
        userDTO.setFirstName(u.getUserDetails().getFirstName());
        userDTO.setLastName(u.getUserDetails().getLastName());
        userDTO.setEmail(u.getUserDetails().getEmail());
        userDTO.setPhoneNumber(u.getUserDetails().getPhoneNumber());
        return getRoles(u, userDTO);
    }
    public UserDto getRoles(User u, UserDto userDTO){
        Collection<Role> roleCollection = u.getRoles();
        if (roleCollection == null)
            return userDTO;
        List<String> roles = new ArrayList<>();
        for (Role role:roleCollection) {
            String role1 = role.getRole();
            roles.add(role1);
        }
        userDTO.setRoles(roles);
        return userDTO;
    }
    @Override
    public User convertDtoToUserAdd(UserDto userDto) {
        User user = new User();
        if (findUserByUserName(userDto.getUserName())!=null)
            return null;
        user = setUserAdd(user, userDto);
        return setRolesUser(user);
    }

    @Override
    public User convertDtoToUserUpdate(UserDto userDto, Integer id) {
        User user = findByIdd(id);
        if (user.getUserName().equals(userDto.getUserName())){
            return setUserUpdate(user, userDto);
        }
        else if (findUserByUserName(userDto.getUserName()) != null) {
            System.out.println(user.getUserName() + " " + userDto.getUserName());
            return null;
        }
        else
            return setUserUpdate(user, userDto);
    }
    public void createRolesIfEmpty(){
        if (roleRepository.findAll().isEmpty()){
            List<String> roles = List.of("ADMIN","USER","CLIENT","EMPLOYEE","GUEST","MANAGER","TEAM_LEADER");
            for (int i = 0; i < roles.size(); i++) {
                Role role = new Role();
                role.setRole(roles.get(i));
                roleRepository.save(role);
            }
        } else if (roleRepository.findRoleByRole("ADMIN") == null) {
            Role role = new Role();
            role.setRole("ADMIN");
            roleRepository.save(role);
        }
    }
    public Boolean adminExists(){
        List<UserDto> userDtoList = findAll();
        if (userDtoList.isEmpty())
            return false;
        for (int i=0;i< userDtoList.size();i++) {
            if (userDtoList.get(i).getRoles() == null)
                continue;
            for(int j=0; j<userDtoList.get(i).getRoles().size();j++){
                if (userDtoList.get(i).getRoles().get(j).equals("ADMIN"))
                    return true;
            }
        }
        return false;
    }
    public User setRolesUser(User user){
        createRolesIfEmpty();
        Role role;
        if (!adminExists() && user.getUserName().equals("rezari")) {
            role = roleRepository.findRoleByRole("ADMIN");
        }
        else {
            role = roleRepository.findRoleByRole("USER");
        }
        if (role.getUsers() == null) {
            Collection<User> userCollection = new ArrayList<>();
            role.setUsers(userCollection);
            role.getUsers().add(user);
        } else {
            if (!(role.getUsers().contains(user)))
                role.getUsers().add(user);
        }
        Collection<Role> roleCollection = new ArrayList<>();
        user.setRoles(roleCollection);
        user.getRoles().add(role);
        return user;
    }
    public User setRoles(User user, RoleDto roleDto){
        List<String> roles;
        if (roleDto.getRoles() != null) {
            removeUserFromPreviousRoles(user);
            roles = roleDto.getRoles();
            Collection<Role> roleCollection = new ArrayList<>();
            addRoles(roles, user, roleCollection);
            return saveRoles(user, roleCollection);
        }
        else {
            return user;
        }
    }
    public void removeUserFromPreviousRoles(User user){
        Collection<Role> roleCollection2 = user.getRoles();
        for (Role r:roleCollection2) {
            r.getUsers().remove(user);
        }
    }
    public void addRoles(List<String> roles, User user, Collection<Role> roleCollection){
        for (String string : roles) {
            Role role1 = roleRepository.findRoleByRole(string);
            if (role1 == null)
                continue;
            if (role1.getUsers() == null) {
                Collection<User> userCollection = new ArrayList<>();
                role1.setUsers(userCollection);
                role1.getUsers().add(user);
            } else {
                if (!(role1.getUsers().contains(user)))
                    role1.getUsers().add(user);
            }
            roleCollection.add(role1);
            roleRepository.save(role1);
        }
    }
    public User saveRoles(User user, Collection<Role> roleCollection){
        Collection<Role> roleCollection1 = new ArrayList<>();
        user.setRoles(roleCollection1);
        user.getRoles().addAll(roleCollection);
        userRepository.save(user);
        return user;
    }
    public User setUserAdd(User user1, UserDto userDTO){
            setUser(user1, userDTO);
            UserDetails userDetails = new UserDetails();
            setUserDetails(user1,userDTO,userDetails);
            user1.setUserDetails(userDetails);
            save(user1);
            userDetailsRepository.save(userDetails);
            return user1;
    }
    public void setUser(User user, UserDto userDTO){
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
    }
    public void setUserDetails(User user1, UserDto userDTO, UserDetails userDetails){
        userDetails.setFirstName(userDTO.getFirstName());
        userDetails.setLastName(userDTO.getLastName());
        userDetails.setEmail(userDTO.getEmail());
        userDetails.setPhoneNumber(userDTO.getPhoneNumber());
        userDetails.setTheUser(user1);
    }
    public User setUserUpdate(User user, UserDto userDTO){
        setUser(user,userDTO);
        return setUserDetailsUpdate(user,userDTO);
    }
    public User setUserDetailsUpdate(User user1, UserDto userDTO){
        user1.getUserDetails().setFirstName(userDTO.getFirstName());
        user1.getUserDetails().setLastName(userDTO.getLastName());
        user1.getUserDetails().setEmail(userDTO.getEmail());
        user1.getUserDetails().setPhoneNumber(userDTO.getPhoneNumber());
        save(user1);
        userDetailsRepository.save(user1.getUserDetails());
        return user1;
    }
    @Override
    public UserDto findUserByBookings(Booking b) {
        User user = userRepository.findUserByBookings(b);
        return convertUserToDto(user);
    }

    @Override
    public UserDto findUserByUserName(String username) {
        if(userRepository.findUserByUserName(username) != null)
            return convertUserToDto(userRepository.findUserByUserName(username));
        else
            return null;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findUserByUserName(username);
    }

    @Override
    public UserDto findAllByUserDetails(UserDetails ud) {
         User user = userRepository.findAllByUserDetails(ud);
         return convertUserToDto(user);
    }

    @Override
    public List<BookingDto> findAllBookings(Integer id) {
        List<Integer> bookingIds = userRepository.findAllBookingsOfAUser(id);
        List<BookingDto> bookingDtoList = new ArrayList<>();
        for (Integer i:bookingIds) {
            BookingDto bookingDto = bookingService.findById(i);
            bookingDtoList.add(bookingDto);
        }
        return bookingDtoList;
    }

    @Override
    public List<FlightDto> findAllFlights(int id) {
        List<Integer> flightIds = userRepository.findAllFlightsOfAUser(id);
        List<FlightDto> flightDtoList = new ArrayList<>();
        for (Integer i:flightIds) {
            FlightDto flightDto = flightService.findById(i);
            flightDtoList.add(flightDto);
        }
        return flightDtoList;
    }

}
