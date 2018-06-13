package com.example.sudarshan.entertainmentsearch;

public class Episode {

    private String title;
    private String airdate;
    private String plot;
    private String actors;
    private String genre;
    private String poster;

    public Episode(String title, String airdate, String plot, String actors, String genre, String poster){
        this.title = title;
        this.airdate = airdate;
        this.plot = plot;
        this.actors = actors;
        this.genre = genre;
        this.poster = poster;
    }

    public String getActors() {
        return actors;
    }

    public String getAirdate() {
        return airdate;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }
}
