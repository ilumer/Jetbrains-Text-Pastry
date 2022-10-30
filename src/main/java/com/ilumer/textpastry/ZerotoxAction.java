package com.ilumer.textpastry;

public class ZerotoxAction extends TextPastryBaseAction {

    /**
     * @param index from zero
     * @return append str from zero
     */
    @Override
    public String generateAppendStr(int index) {
        return index + "";
    }
}
