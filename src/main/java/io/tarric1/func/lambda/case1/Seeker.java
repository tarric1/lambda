package io.tarric1.func.lambda.case1;

import java.math.BigDecimal;
import java.util.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static List<Transaction> searchTrxsWithAmtGreaterThan(List<Transaction> trxs,
            BigDecimal amount) {
        List<Transaction> found = new ArrayList<>();
        for (Transaction trx : trxs) {
            if (trx.amount().compareTo(amount) > 0) {
                found.add(trx);
            }
        }
        return found;
    }
}