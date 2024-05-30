package hasanalmunawr.Dev.OnlineWallet.dto.request;

import lombok.*;


@Getter
public record CodeActivate(
        @NonNull
        String avticateCode
) {
}
