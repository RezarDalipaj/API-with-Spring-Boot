package springData.service.Impl;

import springData.model.User;
import springData.model.UserDetails;
import springData.repository.UserDetailsRepository;
import springData.service.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserDetailsRepository userDetailsRepository;
    UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository){
        this.userDetailsRepository = userDetailsRepository;
    }
//    UserDetailsRepository users = new UserDetailsRepositoryImpl();
    public UserDetails save(UserDetails u){
        return userDetailsRepository.save(u);
    }

    @Override
    public Optional<UserDetails> findById(Integer id) {
        return userDetailsRepository.findById(id);
    }

    @Override
    public UserDetails findByUser(User user) {
        return userDetailsRepository.findFirstByTheUser(user);
    }

    public List<UserDetails> findFirstByFirstName(String fname){
        return userDetailsRepository.findAllByFirstNameContainsIgnoreCase(fname);
    }

    @Override
    public List<UserDetails> findFirstByEmail(String email) {
        return userDetailsRepository.findAllByEmailContainsIgnoreCase(email);
    }

    public List<UserDetails> findFirstByPhoneNumber(String phone){
        return userDetailsRepository.findAllByPhoneNumberContainsIgnoreCase(phone);
    }

//    public List<UserDetails> findAll(){
//        return users.findAll();
//    }
//    public void delete(UserDetails u){users.delete(u);}
}
