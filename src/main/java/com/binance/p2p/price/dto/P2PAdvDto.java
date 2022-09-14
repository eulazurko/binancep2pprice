package com.binance.p2p.price.dto;

public class P2PAdvDto {
    private Double price;
    private Double maxSingleTransAmount;
    private Double minSingleTransAmount;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMaxSingleTransAmount() {
        return maxSingleTransAmount;
    }

    public void setMaxSingleTransAmount(Double maxSingleTransAmount) {
        this.maxSingleTransAmount = maxSingleTransAmount;
    }

    public Double getMinSingleTransAmount() {
        return minSingleTransAmount;
    }

    public void setMinSingleTransAmount(Double minSingleTransAmount) {
        this.minSingleTransAmount = minSingleTransAmount;
    }
}
