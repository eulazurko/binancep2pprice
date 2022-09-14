package com.binance.p2p.price.notifications;

import com.binance.p2p.price.dto.P2PResponceEntryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class NotificationService implements Notifier{

    @Value("#{'${p2p.notifier.types}'.split(',')}")
    private List<String> types;

    private Map<String, Notifier> notifiers;



    public Map<String, Notifier> getNotifiers() {
        return notifiers;
    }

    public void setNotifiers(Map<String, Notifier> notifiers) {
        this.notifiers = notifiers;
    }

    @Override
    public boolean notifyMe(final P2PResponceEntryDto entryDto) {
        types.forEach(type -> ofNullable(notifiers.get(type)).ifPresent(it -> it.notifyMe(entryDto)));
        return true;
    }
}
