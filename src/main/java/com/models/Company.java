package com.models;


public class Company {

    private String[] dates;
    private Double[] openPrices;
    private Double[] highPrices;
    private Double[] lowPrices;
    private Double[] closePrices;

    public Company( String[] dates, Double[] openPrices, Double[] highPrices, Double[] lowPrices, Double[] closePrices) {
        this.dates = dates;
        this.openPrices = openPrices;
        this.highPrices = highPrices;
        this.lowPrices = lowPrices;
        this.closePrices = closePrices;
    }

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public Double[] getOpenPrices() {
        return openPrices;
    }

    public void setOpenPrices(Double[] openPrices) {
        this.openPrices = openPrices;
    }

    public Double[] getHighPrices() {
        return highPrices;
    }

    public void setHighPrices(Double[] highPrices) {
        this.highPrices = highPrices;
    }

    public Double[] getLowPrices() {
        return lowPrices;
    }

    public void setLowPrices(Double[] lowPrices) {
        this.lowPrices = lowPrices;
    }

    public Double[] getClosePrices() {
        return closePrices;
    }

    public void setClosePrices(Double[] closePrices) {
        this.closePrices = closePrices;
    }
}
