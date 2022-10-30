package com.ilumer.textpastry;

import java.util.UUID;

public class UuidAction extends TextPastryBaseAction{
    @Override
    public String generateAppendStr(int index) {
        return UUID.randomUUID().toString();
    }
}
