package springData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springData.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRole(String role);
    void deleteById(Integer id);
    @Override
    void deleteAll();
}
