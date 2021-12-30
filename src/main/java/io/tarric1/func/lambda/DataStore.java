package io.tarric1.func.lambda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DataStore {
    public static final List<Transaction> trxs = Arrays.asList(
            new Transaction(LocalDate.of(2021, 1, 17), BigDecimal.valueOf(100), Transaction.Type.WITHDRAWAL, Transaction.Status.TO_BE_PROCESSED),
            new Transaction(LocalDate.of(2021, 2, 20), BigDecimal.valueOf(500), Transaction.Type.DEPOSIT, Transaction.Status.TO_BE_PROCESSED),
            new Transaction(LocalDate.of(2021, 2, 21), BigDecimal.valueOf(200), Transaction.Type.WITHDRAWAL, Transaction.Status.TO_BE_PROCESSED),
            new Transaction(LocalDate.of(2021, 3, 28), BigDecimal.valueOf(1000), Transaction.Type.TRANSFER, Transaction.Status.TO_BE_PROCESSED),
            new Transaction(LocalDate.of(2021, 4, 15), BigDecimal.valueOf(450), Transaction.Type.PAYMENT, Transaction.Status.TO_BE_PROCESSED));
}
