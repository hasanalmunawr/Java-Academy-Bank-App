package hasanalmunawr.Dev.JavaAcademyBankApp.entity;

import lombok.Getter;

@Getter
public enum TransactionType {

    DEPOSIT("Deposit"),
    WITHDRAWAL("WithDrawal"),
    TRANSFER("Transfer");

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }
}
