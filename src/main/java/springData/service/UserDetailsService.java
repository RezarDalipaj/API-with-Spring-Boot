package springData.service;

import springData.model.User;
import springData.model.UserDetails;

import java.util.List;
import java.util.Optional;
public interface UserDetailsService {
    public UserDetails save(UserDetails u);
    public Optional<UserDetails> findById(Integer id);
    UserDetails findByUser(User user);
    public List<UserDetails> findFirstByFirstName(String fname);
    public List<UserDetails> findFirstByEmail(String email);
    public List<UserDetails> findFirstByPhoneNumber(String phone);

//    public List<UserDetails> findAll();
//    public void delete(UserDetails u);
}
