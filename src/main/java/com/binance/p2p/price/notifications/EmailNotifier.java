package com.binance.p2p.price.notifications;

import com.binance.p2p.price.dto.P2PResponceEntryDto;

public class EmailNotifier implements Notifier {

    @Override
    public boolean notifyMe(final P2PResponceEntryDto entryDto) {
        return false;
    }
}
