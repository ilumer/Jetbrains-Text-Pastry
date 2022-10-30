package com.ilumer.textpastry;

public class OnetoxAction extends TextPastryBaseAction {

    /**
     * @param index from zero
     * @return append str from 1
     */
    @Override
    public String generateAppendStr(int index) {
        return (index + 1) + "";
    }
}
