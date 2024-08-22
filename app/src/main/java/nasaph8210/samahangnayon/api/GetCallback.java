package nasaph8210.samahangnayon.api;

public interface GetCallback {

    void onGetSuccess(String responseData);

    void onGetError(String errorMessage);
}
