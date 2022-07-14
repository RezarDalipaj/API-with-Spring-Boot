package springData.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import springData.model.User;
import springData.repository.RoleRepository;
import springData.repository.UserDetailsRepository;
import springData.repository.UserRepository;
import springData.service.BookingService;
import springData.service.FlightService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
public class UserServiceImplTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserDetailsRepository userDetailsRepository = Mockito.mock(UserDetailsRepository.class);
    private final BookingService bookingService = Mockito.mock(BookingService.class);
    private final FlightService flightService = Mockito.mock(FlightService.class);
    private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository,bookingService,flightService,userDetailsRepository,roleRepository);
    @Test
    void findAll() {
            User user = new User();
            user.setUserName("arbri");
            List<User> userList = new ArrayList<>();
            userList.add(user);
            when(userRepository.findAll()).thenReturn(userList);
            assertEquals(1, userServiceImpl.findAll().size());

    }
    @Test
    void getAllUsersFail() {
        when(userRepository.findAll()).thenReturn(List.of());
        assertEquals(0, userServiceImpl.findAll().size());
    }
}