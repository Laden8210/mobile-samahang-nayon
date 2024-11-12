package nasaph8210.samahangnayon.model;


import com.google.gson.annotations.SerializedName;
public class Inbox {

    @SerializedName("employee_id")
    private int EmployeeID;

    @SerializedName("employee_name")
    private String EmployeeName;

    @SerializedName("position")
    private String EmployeePosition;

    @SerializedName("is_read_guest")
    private int unreadCount;

    @SerializedName("message")
    private String lastMessage;

    @SerializedName("date_sent")
    private String lastMessageDate;
    @SerializedName("total_unread_messages")
    private int totalUnreadMessage;

    public Inbox() {
    }

    public Inbox(int employeeID, String employeeName, String employeePosition, int unreadCount, String lastMessage, String lastMessageDate, int totalUnreadMessage) {
        EmployeeID = employeeID;
        EmployeeName = employeeName;
        EmployeePosition = employeePosition;
        this.unreadCount = unreadCount;
        this.lastMessage = lastMessage;
        this.lastMessageDate = lastMessageDate;
        this.totalUnreadMessage = totalUnreadMessage;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmployeePosition() {
        return EmployeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        EmployeePosition = employeePosition;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public int getTotalUnreadMessage() {
        return totalUnreadMessage;
    }

    public void setTotalUnreadMessage(int totalUnreadMessage) {
        this.totalUnreadMessage = totalUnreadMessage;
    }
}
