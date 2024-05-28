package hasanalmunawr.Dev.JavaAcademyBankApp.entity;

import lombok.Getter;

@Getter
public enum TransactionType {

    DEPOSIT("Deposit"),
    DEPOSIT_DESCRIPTION("Deposit To Primary Account"),
    WITHDRAWAL("WithDrawal"),
    WITHDRAWAL_DESCRIPTION("Withdrawal From Primary Account"),
    TRANSFER("Transfer"),
    TRANSFER_DESCRIPTION("Transfer To Another Primary Account"),;

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }
}
