package com.binance.p2p.price.notifications;

import com.binance.p2p.price.dto.P2PResponceEntryDto;
import com.binance.p2p.price.notifications.messages.MessageComposer;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioNotifier implements Notifier {
    private static final Logger LOG = LoggerFactory.getLogger(TwilioNotifier.class);

    @Autowired
    private MessageComposer smsMessageComposer;

    @Value("${p2p.twilio.phone.toNotify}")
    private String toNotify;

    @Value("${p2p.twilio.phone.notifier}")
    private String notifier;

    @Override
    public boolean notifyMe(final P2PResponceEntryDto entryDto) {
        final String message = smsMessageComposer.compose(entryDto);
        LOG.info("Sending SMS {} to {}", message, toNotify);

        Message.creator(new PhoneNumber(toNotify), new PhoneNumber(notifier), message).create();
        return true;
    }
}
