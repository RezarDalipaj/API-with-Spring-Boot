package springData.service;

import springData.dto.RoleDto;
import springData.model.Role;

import java.util.List;

public interface RoleService {
    Role deleteById(Integer id);
    List<Role> deleteAll();
    Role findById(Integer id);
}
