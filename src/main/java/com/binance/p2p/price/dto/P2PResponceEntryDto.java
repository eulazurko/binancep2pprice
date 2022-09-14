package com.binance.p2p.price.dto;

public class P2PResponceEntryDto {
    private P2PAdvDto adv;
    private P2PAdvertiserDto advertiser;

    public P2PAdvDto getAdv() {
        return adv;
    }

    public void setAdv(P2PAdvDto adv) {
        this.adv = adv;
    }

    public P2PAdvertiserDto getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(P2PAdvertiserDto advertiser) {
        this.advertiser = advertiser;
    }
}
