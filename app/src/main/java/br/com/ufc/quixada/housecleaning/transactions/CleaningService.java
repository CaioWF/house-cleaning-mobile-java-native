package br.com.ufc.quixada.housecleaning.transactions;

import java.sql.Timestamp;

public class CleaningService extends Bean {

    private float cleaningAreaSize;
    private float price;
    private Timestamp datetime;
    private Worker responsible;
    private User requester;
    private Address address;
    private Status status;
    private String additionalComments;

    public float getCleaningAreaSize() {
        return cleaningAreaSize;
    }

    public void setCleaningAreaSize(float cleaningAreaSize) {
        this.cleaningAreaSize = cleaningAreaSize;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Worker getResponsible() {
        return responsible;
    }

    public void setResponsible(Worker responsible) {
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
        PENDENT(0), DOING(1), DONE(2);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
