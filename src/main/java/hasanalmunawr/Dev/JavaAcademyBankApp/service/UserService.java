package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.LoginRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.AuthReponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.RegisterRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.RegisterResponse;

public interface UserService {


    RegisterResponse register(RegisterRequest request);

    AuthReponse authentication(LoginRequest request);

    void activateAccount(String tokenCode);
}
