package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.LoginRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.AuthResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.RegisterRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.RegisterResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;

public interface UserService {


    RegisterResponse register(RegisterRequest request);

    AuthResponse authentication(LoginRequest request);

    void activateAccount(String tokenCode);

    void logout(UserEntity currentUser);
}
