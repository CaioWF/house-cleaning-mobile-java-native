package br.ufc.quixada.es.housecleaning.transactions;

public class Worker extends Bean {

    private String photo;
    private String name;
    private float rate;

    public Worker(String photo, String name, float rate) {
        this.photo = photo;
        this.name = name;
        this.rate = rate;
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
