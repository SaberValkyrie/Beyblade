package com.example.demo.entity;

public class SellerOrderInfo {
    private User seller;
    private double totalOrderAmount;    private double totalPercent;

    public SellerOrderInfo(User seller, double totalPercent) {
        this.seller = seller;
        this.totalOrderAmount = totalOrderAmount;
    }

    public SellerOrderInfo() {


    }


    public double getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(double totalPercent) {
        this.totalPercent = totalPercent;
    }

    public SellerOrderInfo(User seller, double totalOrderAmount, double totalPercent) {
        this.seller = seller;
        this.totalOrderAmount = totalOrderAmount;
        this.totalPercent = totalPercent;

    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(double totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }
}
