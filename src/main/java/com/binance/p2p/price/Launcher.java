package com.binance.p2p.price;

import com.binance.p2p.price.dto.P2PResponceEntryDto;
import com.binance.p2p.price.notifications.NotificationService;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class Launcher {
    private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

    @Autowired
    private DataPullingService dataPullingService;

    @Autowired
    private NotificationService notificationService;

    @Value("${p2p.price}")
    private Double wished;

    @Value("${p2p.sleep}")
    private long sleep;

    @Value("${p2p.twilio.account.sid}")
    private String accountSid;

    @Value("${p2p.twilio.auth.token}")
    private String authToken;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        applicationContext.getBean(Launcher.class).launch();
    }

    private void launch() {
        init();
        while (true) {
            try {
                final List<P2PResponceEntryDto> result = dataPullingService.pull();
                result.stream()
                        .filter(Objects::nonNull)
                        .filter(entry -> entry.getAdv().getPrice() <= wished)
                        .findFirst().ifPresent(notificationService::notifyMe);

                Thread.sleep(sleep);
            } catch (final Exception e) {
                LOG.error("Failed", e);
            }
        }
    }

    private void init() {
        Twilio.init(accountSid, authToken);
    }
}
