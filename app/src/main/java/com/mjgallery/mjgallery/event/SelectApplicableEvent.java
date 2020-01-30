package com.mjgallery.mjgallery.event;

public class SelectApplicableEvent {
    private String year;
    private String termsByYear;

    public SelectApplicableEvent(String year, String termsByYear) {
        this.year = year;
        this.termsByYear = termsByYear;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTermsByYear() {
        return termsByYear;
    }

    public void setTermsByYear(String termsByYear) {
        this.termsByYear = termsByYear;
    }
}
