package com.binance.p2p.price.notifications.messages;

import com.binance.p2p.price.dto.P2PResponceEntryDto;

public interface MessageComposer {

    String compose(final P2PResponceEntryDto entryDto);
}
