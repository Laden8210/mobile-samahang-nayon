package nasaph8210.samahangnayon.model;

public class SubGuest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthdate;
    private String contactNumber;

    private String gender;

    public SubGuest() {
    }

    public SubGuest(String firstName, String lastName, String middleName, String birthdate, String contactNumber, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.contactNumber = contactNumber;

        this.gender = gender;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
