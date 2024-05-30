package hasanalmunawr.Dev.OnlineWallet.mapper;

import hasanalmunawr.Dev.OnlineWallet.dto.AccountInfo;
import hasanalmunawr.Dev.OnlineWallet.entity.UserEntity;
import lombok.Getter;

@Getter
public class UserMapper {


    public AccountInfo convertUserToACI(UserEntity user) {
        return AccountInfo.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accountNumber(String.valueOf(user.getPrimaryAccount().getAccountNumber()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
