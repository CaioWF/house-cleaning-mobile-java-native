package br.com.ufc.quixada.housecleaning.transactions;

public class Place extends Bean {

    private String city;
    private String neighborhood;

    public Place() {
        super();
    }

    public Place(String city, String neighborhood) {
        this();
        this.city = city;
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Override
    public String toString() {
        return city + ", " + neighborhood;
    }
}
