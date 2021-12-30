package io.tarric1.func.lambda.case4;

import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;
        
        List<Transaction> found = new ArrayList<>();

        Seeker.search(trxs,
                trx -> trx.type().equals(Transaction.Type.WITHDRAWAL),
                trx -> found.add(trx));
        
        System.out.println(found);
    }
}
