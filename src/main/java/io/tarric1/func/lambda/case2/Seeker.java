package io.tarric1.func.lambda.case2;

import java.util.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static List<Transaction> search(List<Transaction> trxs,
            Criteria criteria) {
        List<Transaction> found = new ArrayList<>();
        for (Transaction trx : trxs) {
            if (criteria.test(trx)) {
                found.add(trx);
            }
        }
        return found;
    }
}
