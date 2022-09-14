package com.binance.p2p.price.notifications;

import com.binance.p2p.price.dto.P2PResponceEntryDto;

public interface Notifier {

    boolean notifyMe(final P2PResponceEntryDto entryDto);
}
