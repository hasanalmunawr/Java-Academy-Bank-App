package hasanalmunawr.Dev.OnlineWallet.repository;

import hasanalmunawr.Dev.OnlineWallet.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
//    boolean userExistByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPrimaryAccountId(Integer id);

}
