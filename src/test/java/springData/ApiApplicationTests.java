package springData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.Nullable;
import springData.model.User;
import springData.repository.UserRepository;
import springData.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@SpringBootTest
class ApiApplicationTests {
    @MockBean
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Test
    void getAllUsersSuccess() {
        User user = new User();
        user.setUserName("arbri");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(1, userService.findAll().size(),"ok");
    }
    @Test
    void getAllUsersFail() {
        when(userRepository.findAll()).thenReturn(List.of());
        assertEquals(0, userService.findAll().size(), "ok");
    }
}
