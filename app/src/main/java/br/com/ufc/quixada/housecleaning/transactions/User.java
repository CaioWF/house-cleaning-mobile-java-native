package br.com.ufc.quixada.housecleaning.transactions;

import java.util.ArrayList;
import java.util.List;

public class User extends Bean {

    private String photo;
    private String name;
    private String email;
    private String password;
    private boolean worker;
    private List<Float> ratings;
    private List<Place> servicePlaces;

    public User() {
        super();
        this.ratings = new ArrayList<>();
        this.servicePlaces = new ArrayList<>();
    }

    public User(String name, String email, String password) {
        this();
        this.photo = null;
        this.name = name;
        this.email = email;
        this.password = password;
        this.worker = false;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isWorker() {
        return worker;
    }

    public void setWorker(boolean worker) {
        this.worker = worker;
    }

    public List<Float> getRatings() {
        return ratings;
    }

    public void setRatings(List<Float> ratings) {
        this.ratings = ratings;
    }

    public Float getRating() {
        float rating = 0f;

        if (!ratings.isEmpty()) {
            for (Float rate : ratings) {
                rating += rate;
            }

            return rating / ratings.size();
        }

        return rating;
    }

    public void addRating(Float rating) {
        ratings.add(rating);
    }

    public List<Place> getServicePlaces() {
        return servicePlaces;
    }

    public void setServicePlaces(List<Place> servicePlaces) {
        this.servicePlaces = servicePlaces;
    }

    public void addServicePlace(Place place) {
        servicePlaces.add(place);
    }
}