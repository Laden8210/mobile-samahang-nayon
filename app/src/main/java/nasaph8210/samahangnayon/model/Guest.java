package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Guest {

    @SerializedName("GuestId")
    private Long guestId;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("MiddleName")
    private String middleName;

    @SerializedName("Street")
    private String street;

    @SerializedName("City")
    private String city;

    @SerializedName("Province")
    private String province;

    @SerializedName("Birthdate")
    private Date birthdate;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("ContactNumber")
    private String contactNumber;

    @SerializedName("EmailAddress")
    private String emailAddress;

    @SerializedName("Username")
    private String username;

    @SerializedName("Password")
    private String password;

    @SerializedName("DateCreated")
    private Date dateCreated;

    @SerializedName("TimeCreated")
    private String timeCreated;

    public Guest() {
    }

    public Guest(String firstName, String lastName, String middleName, String street, String city, String province, Date birthdate, String gender, String contactNumber, String emailAddress, String username, String password, Date dateCreated, String timeCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.street = street;
        this.city = city;
        this.province = province;
        this.birthdate = birthdate;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
        this.timeCreated = timeCreated;
    }

    public Guest(Long guestId, String firstName, String lastName, String middleName, String street, String city, String province, Date birthdate, String gender, String contactNumber, String emailAddress, String username, String password, Date dateCreated, String timeCreated) {
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.street = street;
        this.city = city;
        this.province = province;
        this.birthdate = birthdate;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
        this.timeCreated = timeCreated;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }
}
