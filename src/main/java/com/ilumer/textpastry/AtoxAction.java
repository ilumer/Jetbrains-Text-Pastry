package com.ilumer.textpastry;

import com.google.common.collect.Lists;

import java.util.List;

public class AtoxAction extends TextPastryBaseAction {


    public static List<String> words = Lists.newArrayList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");

    /**
     * @param index from zero
     * @return append str from a
     */
    @Override
    public String generateAppendStr(int index) {
        return words.get(index % words.size());
    }
}
