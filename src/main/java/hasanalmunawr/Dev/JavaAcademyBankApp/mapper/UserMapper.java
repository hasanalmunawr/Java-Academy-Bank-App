package hasanalmunawr.Dev.JavaAcademyBankApp.mapper;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.AccountInfo;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
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
                .createAt(user.getCreatedAt().toString())
                .build();
    }
}
