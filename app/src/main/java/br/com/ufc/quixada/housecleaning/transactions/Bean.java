package br.com.ufc.quixada.housecleaning.transactions;

import java.util.UUID;

public abstract class Bean {

    private String id;

    public Bean() {
        id = UUID.randomUUID().toString();
    }

    public Bean(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
