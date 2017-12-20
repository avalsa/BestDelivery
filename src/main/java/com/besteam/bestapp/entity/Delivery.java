package com.besteam.bestapp.entity;

public enum Delivery {
    PostRf,
    Iml,
    PonyExpress,
    Cdek,
    Dhl,
    Dpd,
    Boxberry;

    @Override
    public String toString() {
        if (this == PostRf) {
            return "Почта России";
        }
        return super.toString();
    }
}
