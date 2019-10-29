package br.com.ufc.quixada.housecleaning.transactions;

public class Bean {

    private static int nextId = 1;
    private int id;

    public Bean() {
        this.id = nextId;

        nextId++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
