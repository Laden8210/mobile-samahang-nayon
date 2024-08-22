package nasaph8210.samahangnayon.api;

public interface DeleteCallback {
    void onDeleteSuccess(String response);
    void onDeleteFail(String error);
}
