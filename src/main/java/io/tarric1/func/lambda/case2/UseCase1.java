package io.tarric1.func.lambda.case2;

import java.math.BigDecimal;
import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase1 {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;

        List<Transaction> found = Seeker.search(trxs, new Criteria() {
            @Override
            public boolean test(Transaction trx) {
                return trx.amount().compareTo(BigDecimal.valueOf(100)) > 0;
            }
        });
        
        System.out.println(found);
    }
}
