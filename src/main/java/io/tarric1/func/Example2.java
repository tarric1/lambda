package io.tarric1.func;

import java.util.Arrays;

public class Example2 {
    public static double avgOfEvenNums(int[] nums) {
        return Arrays.stream(nums)
                .filter(num -> num % 2 == 0)
                .average()
                .getAsDouble();
    }
    
    public static void main(String[] args) {
        int[] nums = new int[] { 1, 10, 47, 14, 39, 50, 2, 6, 55, 56, 48 };
        System.out.println(avgOfEvenNums(nums));
    }
}