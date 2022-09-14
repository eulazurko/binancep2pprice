package com.binance.p2p.price;

import com.binance.p2p.price.dto.P2PResponceEntryDto;
import com.binance.p2p.price.dto.P2PResponceEntryListDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataPullingService {
    private static final Logger LOG = LoggerFactory.getLogger(DataPullingService.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Value("${p2p.request}")
    private String request;

    @Value("${p2p.uri}")
    private String uri;

    /**
     *
     * curl --location --request POST 'https://p2p.binance.com/bapi/c2c/v2/friendly/c2c/adv/search' \
     * --header 'content-type: application/json' \
     * --header 'Cookie: cid=lKnmPKMM' \
     * --data-raw '{"proMerchantAds":false,"page":1,"rows":10,"payTypes":["Wise"],"countries":[],"publisherType":null,"asset":"USDT","fiat":"USD","tradeType":"BUY"}'
     *
     * */
    public List<P2PResponceEntryDto> pull() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "*/*");
        headers.setContentLength(request.getBytes().length);
        HttpEntity<String> httpEntity = new HttpEntity<>(request, headers);

        LOG.debug("BINANCE REQUEST: \n" + httpEntity.getBody());


        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, httpEntity, String.class);
        LOG.debug("BINANCE RESPONSE: \n" + responseEntity.getBody());

        try {
            return MAPPER.readValue(responseEntity.getBody(), P2PResponceEntryListDto.class).getData();
        } catch (JsonProcessingException e) {
            LOG.error("Could not read response",  e);
            return new ArrayList();
        }
    }
}
