package hasanalmunawr.Dev.JavaAcademyBankApp.repository;

import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
//    boolean userExistByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPrimaryAccountId(Integer id);

}
