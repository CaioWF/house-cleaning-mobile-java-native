package br.com.ufc.quixada.housecleaning.transactions;

import java.util.Date;

public class CleaningService extends Bean {

    private float numberOfRooms;
    private float price;
    private Date date;
    private User responsible;
    private User requester;
    private Address address;
    private Status status;
    private String additionalComments;

    public CleaningService() {

    }

    public CleaningService(float numberOfRooms, float price, Date date, User responsible, User requester, Address address, Status status, String additionalComments) {
        this.numberOfRooms = numberOfRooms;
        this.price = price;
        this.date = date;
        this.responsible = responsible;
        this.requester = requester;
        this.address = address;
        this.status = status;
        this.additionalComments = additionalComments;
    }

    public float getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(float numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public enum Status {
        PENDENT(0), ACCEPTED(1), REFUSED(2), DONE(3);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            if (value == 0)
                return "PENDENT";

            if (value == 1)
                return "ACCEPTED";

            if (value == 2)
                return "REFUSED";

            if (value == 3)
                return "DONE";

            return "";
        }
    }

}
