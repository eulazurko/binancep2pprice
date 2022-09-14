package com.binance.p2p.price;

import com.binance.p2p.price.notifications.EmailNotifier;
import com.binance.p2p.price.notifications.NotificationService;
import com.binance.p2p.price.notifications.Notifier;
import com.binance.p2p.price.notifications.TwilioNotifier;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan("com.binance.p2p.price")
@PropertySource("classpath:p2p.properties")
public class AppConfig {

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    @Bean
    NotificationService notificationService() {
        NotificationService notificationService = new NotificationService();
        Map<String, Notifier> notifiers = new HashMap<>();
        notifiers.put("sms", twilioNotifier());
        notifiers.put("email", emailNotifier());

        notificationService.setNotifiers(notifiers);
        return notificationService;
    }

    @Bean
    TwilioNotifier twilioNotifier() {
        return new TwilioNotifier();
    }

    @Bean
    EmailNotifier emailNotifier() {
        return new EmailNotifier();
    }
}
