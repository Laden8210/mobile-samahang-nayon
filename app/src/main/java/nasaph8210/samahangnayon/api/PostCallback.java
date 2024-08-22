package nasaph8210.samahangnayon.api;

public interface PostCallback {
    void onPostSuccess(String responseData);

    void onPostError(String errorMessage);
}
