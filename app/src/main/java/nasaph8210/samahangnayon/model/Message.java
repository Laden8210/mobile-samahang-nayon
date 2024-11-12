package nasaph8210.samahangnayon.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Message {

    @SerializedName("MessageId")
    private int messageId;

    @SerializedName("GuestId")
    private int guestId;

    @SerializedName("EmployeeId")
    private int employeeId;

    @SerializedName("IsReadEmployee")
    private int isReadEmployee;

    @SerializedName("IsReadGuest")
    private int isReadGuest;

    @SerializedName("Message")
    private String message;

    @SerializedName("DateSent")
    private String dateSent;

    @SerializedName("TimeSent")
    private String timeSent;

    @SerializedName("isGuestMessage")
    private int isGuestMessage;

    @SerializedName("employee")
    private Employee employee;

    // Constructor
    public Message(int messageId, int guestId, int employeeId, int isReadEmployee, int isReadGuest, String message,
                   String dateSent, String timeSent, int isGuestMessage, Employee employee) {
        this.messageId = messageId;
        this.guestId = guestId;
        this.employeeId = employeeId;
        this.isReadEmployee = isReadEmployee;
        this.isReadGuest = isReadGuest;
        this.message = message;
        this.dateSent = dateSent;
        this.timeSent = timeSent;
        this.isGuestMessage = isGuestMessage;
        this.employee = employee;
    }

    // Inner Employee Class
    public static class Employee {

        @SerializedName("EmployeeId")
        private int employeeId;

        @SerializedName("FirstName")
        private String firstName;

        @SerializedName("LastName")
        private String lastName;

        @SerializedName("Position")
        private String position;

        // Constructor
        public Employee(int employeeId, String firstName, String lastName, String position) {
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.position = position;
        }

        // Getters and Setters
        public int getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
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

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }

    // Getters and Setters for Message class
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getIsReadEmployee() {
        return isReadEmployee;
    }

    public void setIsReadEmployee(int isReadEmployee) {
        this.isReadEmployee = isReadEmployee;
    }

    public int getIsReadGuest() {
        return isReadGuest;
    }

    public void setIsReadGuest(int isReadGuest) {
        this.isReadGuest = isReadGuest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public int getIsGuestMessage() {
        return isGuestMessage;
    }

    public void setIsGuestMessage(int isGuestMessage) {
        this.isGuestMessage = isGuestMessage;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Method to serialize object to JSON
    public static String serialize(List<Message> messageList) {
        Gson gson = new Gson();
        return gson.toJson(messageList);
    }

    // Method to deserialize JSON to List of Messages
    public static List<Message> deserialize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, List.class);
    }
}
