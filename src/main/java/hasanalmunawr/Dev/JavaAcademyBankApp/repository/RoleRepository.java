package hasanalmunawr.Dev.JavaAcademyBankApp.repository;

import hasanalmunawr.Dev.JavaAcademyBankApp.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByName(String name);
}
