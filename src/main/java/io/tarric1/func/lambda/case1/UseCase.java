package io.tarric1.func.lambda.case1;

import java.math.BigDecimal;
import java.util.*;

import io.tarric1.func.lambda.DataStore;
import io.tarric1.func.lambda.Transaction;

public class UseCase {
    public static void main(String[] args) {
        List<Transaction> trxs = DataStore.trxs;

        List<Transaction> found = Seeker.searchTrxsWithAmtGreaterThan(trxs, BigDecimal.valueOf(100));
        
        System.out.println(found);
    }
}
