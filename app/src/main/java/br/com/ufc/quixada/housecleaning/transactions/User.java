package br.com.ufc.quixada.housecleaning.transactions;

public class User extends Bean {

    private String photo;
    private String name;
    private String email;
    private String password;
    private boolean worker;
    private float rate;

    public User(String photo, String name, String email, String password, boolean worker, float rate) {
        super();
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.password = password;
        this.worker = worker;
        this.rate = rate;
    }

    public User(/*String photo, */String name, String email, String password) {
        super();
        //this.photo = photo;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String photo, String name) {
        super();
        this.photo = photo;
        this.name = name;
    }

    public User() {
        super();
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
