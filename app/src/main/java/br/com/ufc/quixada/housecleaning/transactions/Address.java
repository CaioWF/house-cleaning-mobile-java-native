package br.com.ufc.quixada.housecleaning.transactions;

public class Address {

    private Place place;
    private String street;
    private int number;
    private String complement;

    public Address() {

    }

    public Address(Place place, String street, int number, String complement) {
        this.place = place;
        this.street = street;
        this.number = number;
        this.complement = complement;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
