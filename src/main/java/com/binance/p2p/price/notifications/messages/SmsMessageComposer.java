package com.binance.p2p.price.notifications.messages;

import com.binance.p2p.price.dto.P2PResponceEntryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class SmsMessageComposer implements MessageComposer {
    private static final Logger LOG = LoggerFactory.getLogger(SmsMessageComposer.class);
    private static final String DEFAULT_SMS = "CHECK P2P!";

    @Value("${p2p.advertiser.uri}")
    private String advertiserUrl;

    @Override
    public String compose(final P2PResponceEntryDto entryDto) {
        if (isNull(entryDto) || isNull(entryDto.getAdvertiser())) {
            LOG.warn("No advertiser # is available, returning default message");
            return DEFAULT_SMS;
        }

        return advertiserUrl + entryDto.getAdvertiser().getUserNo();
    }
}
