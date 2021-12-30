package io.tarric1.func.lambda.case2;

import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase3 {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;

        List<Transaction> found = Seeker.search(trxs, new Criteria() {
            @Override
            public boolean test(Transaction trx) {
                return trx.type().equals(Transaction.Type.WITHDRAWAL);
            }
        });
        
        System.out.println(found);
    }
}
