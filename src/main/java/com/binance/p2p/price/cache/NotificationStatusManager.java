package com.binance.p2p.price.cache;

import com.binance.p2p.price.dto.P2PResponceEntryDto;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class NotificationStatusManager {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationStatusManager.class);

    private CacheLoader<String, String> loader = new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            return key;
        }
    };

    private LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .build(loader);

    public void markAlreadySent(final P2PResponceEntryDto entryDto) {
        if (isNull(entryDto.getAdvertiser()) || isEmpty(entryDto.getAdvertiser().getUserNo())) {
            return;
        }
        cache.put(entryDto.getAdvertiser().getUserNo(), StringUtils.EMPTY);
    }

    public boolean isAlreadySentNotification(final P2PResponceEntryDto entryDto) {
        try {
            return nonNull(entryDto.getAdvertiser())
                    && nonNull(cache.getIfPresent(entryDto.getAdvertiser().getUserNo()));
        } catch (Exception e) {
            LOG.warn("Could not get data from cache", e);
            return false;
        }
    }
}
