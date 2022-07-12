package springData.service.impl;

import org.springframework.stereotype.Service;
import springData.model.Role;
import springData.repository.RoleRepository;
import springData.service.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @Override
    public Role deleteById(Integer id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()){
            Role role = optionalRole.get();
            roleRepository.deleteById(id);
            return role;
        }
        return null;
    }

    @Override
    public List<Role> deleteAll() {
        List<Role> roleList = roleRepository.findAll();
        if (!(roleList.isEmpty()))
        {
            roleRepository.deleteAll();
            return roleList;
        }
        return null;
    }

    @Override
    public Role findById(Integer id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()){
            Role role = optionalRole.get();
            return role;
        }
        return null;
    }
}
