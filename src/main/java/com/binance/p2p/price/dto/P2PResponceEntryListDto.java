package com.binance.p2p.price.dto;

import java.util.List;

public class P2PResponceEntryListDto {
    private List<P2PResponceEntryDto> data;

    public List<P2PResponceEntryDto> getData() {
        return data;
    }

    public void setData(List<P2PResponceEntryDto> data) {
        this.data = data;
    }
}
