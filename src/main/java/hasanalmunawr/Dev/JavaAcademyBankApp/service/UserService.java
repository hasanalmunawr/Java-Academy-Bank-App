package hasanalmunawr.Dev.JavaAcademyBankApp.service;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.LoginRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.AuthReponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.BankResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.UserRequest;

public interface UserService {


    BankResponse createUser(UserRequest request);

    AuthReponse login(LoginRequest request);

    void activateAccount(String tokenCode);
}
