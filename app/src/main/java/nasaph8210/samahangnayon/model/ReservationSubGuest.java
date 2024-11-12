package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;

public class ReservationSubGuest {

    @SerializedName("SubGuestId")
    private int subGuestId;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("MiddleName")
    private String middleName;

    @SerializedName("Birthdate")
    private String birthdate;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("ContactNumber")
    private String contactNumber;

    @SerializedName("EmailAddress")
    private String emailAddress;

    @SerializedName("GuestId")
    private Integer guestId; // Nullable

    @SerializedName("ReservationId")
    private int reservationId;

    public ReservationSubGuest() {
    }

    public int getSubGuestId() {
        return subGuestId;
    }

    public void setSubGuestId(int subGuestId) {
        this.subGuestId = subGuestId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
}
