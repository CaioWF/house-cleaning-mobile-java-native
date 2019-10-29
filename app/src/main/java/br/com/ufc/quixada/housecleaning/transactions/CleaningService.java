package br.com.ufc.quixada.housecleaning.transactions;

import java.util.Date;

public class CleaningService extends Bean {

    private float cleaningAreaSize;
    private float price;
    private Date date;
    private User responsible;
    private User requester;
    private Address address;
    private Status status;
    private String additionalComments;

    public CleaningService() {

    }

    public CleaningService(float cleaningAreaSize, float price, Date date, User responsible, User requester, Address address, Status status, String additionalComments) {
        this.cleaningAreaSize = cleaningAreaSize;
        this.price = price;
        this.date = date;
        this.responsible = responsible;
        this.requester = requester;
        this.address = address;
        this.status = status;
        this.additionalComments = additionalComments;
    }

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
