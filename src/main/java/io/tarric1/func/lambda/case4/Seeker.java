package io.tarric1.func.lambda.case4;

import java.util.*;
import java.util.function.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static void search(List<Transaction> trxs,
            Predicate<Transaction> criteria,
            Consumer<Transaction> action) {
        for (Transaction trx : trxs) {
            if (criteria.test(trx)) {
                action.accept(trx);
            }
        }
    }
}
