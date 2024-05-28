package hasanalmunawr.Dev.JavaAcademyBankApp;

import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class JavaAcademyBankAppApplicationTests {


	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
		String password = "password123";
		UserEntity user = new UserEntity();
		user.setFirstName("John");
		user.setEmail("email@email.com");
		user.setPassword(passwordEncoder.encode(password));
		UserEntity save = userRepository.save(user);

		Assertions.assertFalse(passwordEncoder.matches(save.getPassword(), password));
		Assertions.assertTrue(passwordEncoder.matches(password,save.getPassword()));


	}

}
