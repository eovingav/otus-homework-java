package ru.otus.hw01maven;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {
        List<Integer> example = new ArrayList<Integer>();
        int min = 0;
        int max = 999999;
        for (int i = min; i < max + 1; i++) {
            example.add(i);
        }

        List<Integer> result = new ArrayList<Integer>();
        Collections.shuffle(example);
        result.addAll(Lists.reverse(example));
        System.out.println(result);
    }
}
