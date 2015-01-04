package fr.kensai.utils;

import com.googlecode.jcsv.annotations.MapToColumn;

/**
 * Created by vivian on 22/12/14.
 */
public class CompagnyRank {

    @MapToColumn(column=0)
    private int year;

    @MapToColumn(column=1)
    private int rank;

    @MapToColumn(column=2)
    private String company;

    @MapToColumn(column=3)
    private double revenue;

    @MapToColumn(column=4)
    private double profit;

    public CompagnyRank(){
        // Default constructor
    }

    public CompagnyRank(int year, int rank, String company, double revenue, double profit) {
        this.year = year;
        this.rank = rank;
        this.company = company;
        this.revenue = revenue;
        this.profit = profit;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompagnyRank)) return false;

        CompagnyRank that = (CompagnyRank) o;

        if (Double.compare(that.profit, profit) != 0) return false;
        if (rank != that.rank) return false;
        if (Double.compare(that.revenue, revenue) != 0) return false;
        if (year != that.year) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = year;
        result = 31 * result + rank;
        result = 31 * result + (company != null ? company.hashCode() : 0);
        temp = Double.doubleToLongBits(revenue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(profit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CompagnyRank{" +
                "year=" + year +
                ", rank=" + rank +
                ", company='" + company + '\'' +
                ", revenue=" + revenue +
                ", profit=" + profit +
                '}';
    }
}
