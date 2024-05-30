package hasanalmunawr.Dev.OnlineWallet.repository;

import hasanalmunawr.Dev.OnlineWallet.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByName(String name);
}
