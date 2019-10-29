package br.com.ufc.quixada.housecleaning.transactions;

public class User extends Bean {

    private String photo;
    private String name;
    private String email;
    private String password;


    public User(/*String photo, */String name, String email, String password) {
        //this.photo = photo;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String photo, String name) {
        this.photo = photo;
        this.name = name;
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
}
