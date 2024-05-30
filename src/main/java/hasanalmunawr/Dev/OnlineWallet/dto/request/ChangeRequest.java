package hasanalmunawr.Dev.OnlineWallet.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

public record ChangeRequest(
        @NonNull
        String oldPassword,
        @NotBlank
        @Size(min = 8)
        String newPassword,
        String confirmPassword
) {
    // Additional validation logic or methods if needed
}

