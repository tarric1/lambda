package io.tarric1.func.lambda.case2;

import io.tarric1.func.lambda.Transaction;

public interface Criteria {
    public boolean test(Transaction trx);
}