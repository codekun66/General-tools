package com.general.tools.leetcode;

import java.util.*;

/*
* solution 1-5
* */
public class Solution {

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> x = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int y = target - nums[i];
            if (x.containsKey(y)) {
                return new int[] {x.get(y), i };
            }
            x.put(nums[i],i);
        }
        return null;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int [] nums = {2,7,11,34,45,3,4,5} ;
        int[] ints = s.twoSum(nums, 50);
        Arrays.sort(nums);
        for (Integer element : nums) {
            System.out.printf("%s",element);
            System.out.print("  ");
        }
    }
}
