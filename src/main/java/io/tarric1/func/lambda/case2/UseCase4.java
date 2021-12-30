package io.tarric1.func.lambda.case2;

import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase4 {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;

        List<Transaction> found = Seeker.search(trxs,
                trx -> trx.type().equals(Transaction.Type.WITHDRAWAL));
        
        System.out.println(found);
    }
}
