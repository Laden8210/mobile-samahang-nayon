package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("PaymentId")
    private int paymentId;

    @SerializedName("GuestId")
    private int guestId;

    @SerializedName("ReservationId")
    private int reservationId;

    @SerializedName("AmountPaid")
    private String amountPaid;

    @SerializedName("DateCreated")
    private String dateCreated;

    @SerializedName("TimeCreated")
    private String timeCreated;

    @SerializedName("Status")
    private String status;

    @SerializedName("PaymentType")
    private String paymentType;

    @SerializedName("ReferenceNumber")
    private String referenceNumber;

    @SerializedName("Purpose")
    private String purpose;


    public Payment() {
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}