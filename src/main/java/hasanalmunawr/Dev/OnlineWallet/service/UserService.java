package hasanalmunawr.Dev.OnlineWallet.service;

import hasanalmunawr.Dev.OnlineWallet.dto.request.ChangeRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.request.LoginRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.response.AuthResponse;
import hasanalmunawr.Dev.OnlineWallet.dto.request.RegisterRequest;
import hasanalmunawr.Dev.OnlineWallet.dto.response.RegisterResponse;
import hasanalmunawr.Dev.OnlineWallet.entity.UserEntity;

public interface UserService {


    RegisterResponse register(RegisterRequest request);

    AuthResponse authentication(LoginRequest request);

    void activateAccount(String tokenCode);

    void changePassword(UserEntity currentUser, ChangeRequest request);
}
