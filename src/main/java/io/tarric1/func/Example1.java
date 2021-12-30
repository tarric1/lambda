package io.tarric1.func;

public class Example1 {
    public static Double avgOfEvenNums(int[] nums) {
        int n = 0;
        double s = 0;
        Double avg = null;
        for (int num : nums) {
            if (num % 2 == 0) {
                n++;
                s += num;
            }
        }
        if (n > 0) {
            avg = s / n;
        }
        return avg;
    }

    public static void main(String[] args) {
        int[] nums = new int[] { 1, 10, 47, 14, 39, 50, 2, 6, 55, 56, 48 };
        System.out.println(avgOfEvenNums(nums));
    }
}