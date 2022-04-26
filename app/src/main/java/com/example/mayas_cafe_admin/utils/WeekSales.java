package com.example.mayas_cafe_admin.utils;

public class WeekSales {

   String weeks;
   int sales;

    public WeekSales(String weeks, int sales) {
        this.weeks = weeks;
        this.sales = sales;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
