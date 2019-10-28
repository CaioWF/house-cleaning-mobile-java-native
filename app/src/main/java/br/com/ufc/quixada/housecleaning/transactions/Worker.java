package br.com.ufc.quixada.housecleaning.transactions;

public class Worker extends User {

    private float rate;

    public Worker(String photo, String name, float rate) {
        super(photo, name);
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
