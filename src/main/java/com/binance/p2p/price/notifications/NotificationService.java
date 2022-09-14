package com.binance.p2p.price.notifications;

import com.binance.p2p.price.cache.NotificationStatusManager;
import com.binance.p2p.price.dto.P2PResponceEntryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Service
public class NotificationService implements Notifier {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationStatusManager statusManager;

    @Value("#{'${p2p.notifier.types}'.split(',')}")
    private List<String> types;

    private Map<String, Notifier> notifiers;

    @Override
    public boolean notifyMe(final P2PResponceEntryDto entryDto) {
        types.forEach(type -> ofNullable(notifiers.get(type)).ifPresent(it -> {
            if (it.notifyMe(entryDto)) {
                statusManager.markAlreadySent(entryDto);
            } else {
                LOG.debug("Something wrong with notifying to {}", type);
            }
        }));

        return true;
    }

    public Map<String, Notifier> getNotifiers() {
        return notifiers;
    }

    public void setNotifiers(Map<String, Notifier> notifiers) {
        this.notifiers = notifiers;
    }
}
