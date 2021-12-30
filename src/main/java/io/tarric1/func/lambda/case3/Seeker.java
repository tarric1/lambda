package io.tarric1.func.lambda.case3;

import java.util.*;
import java.util.function.Predicate;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static List<Transaction> search(List<Transaction> trxs,
            Predicate<Transaction> criteria) {
        List<Transaction> found = new ArrayList<>();
        for (Transaction trx : trxs) {
            if (criteria.test(trx)) {
                found.add(trx);
            }
        }
        return found;
    }
}
