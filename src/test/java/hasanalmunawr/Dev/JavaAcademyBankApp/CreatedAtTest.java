package hasanalmunawr.Dev.JavaAcademyBankApp;

import hasanalmunawr.Dev.OnlineWallet.entity.UserEntity;
import hasanalmunawr.Dev.OnlineWallet.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreatedAtTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void test() {
        UserEntity user = new UserEntity();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@doe.com");
        user.setPassword("password");
        UserEntity save = userRepository.save(user);

        Assertions.assertNotNull(save.getCreatedAt());
        System.out.printf(save.getCreatedAt().toString());

    }

}
