package io.tarric1.func.lambda.case5;

import java.util.*;
import java.util.function.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static void search(List<Transaction> trxs,
            Predicate<Transaction> criteria,
            Consumer<Transaction> action,
            Function<Transaction, Transaction> processor) {
        for (Transaction trx : trxs) {
            if (criteria.test(trx)) {
                action.accept(processor.apply(trx));
            }
        }
    }
}
