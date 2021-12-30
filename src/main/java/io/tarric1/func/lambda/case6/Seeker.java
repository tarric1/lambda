package io.tarric1.func.lambda.case6;

import java.util.*;
import java.util.function.*;

import io.tarric1.func.lambda.Transaction;

public class Seeker {
    public static void search(Supplier<List<Transaction>> supplier,
            Predicate<Transaction> criteria,
            Consumer<Transaction> action,
            Function<Transaction, Transaction> processor) {
        for (Transaction trx : supplier.get()) {
            if (criteria.test(trx)) {
                action.accept(processor.apply(trx));
            }
        }
    }
}
