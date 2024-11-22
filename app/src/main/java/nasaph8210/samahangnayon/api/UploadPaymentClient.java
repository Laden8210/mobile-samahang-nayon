package nasaph8210.samahangnayon.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadPaymentClient {
    private static final String BASE_URL = ApiAddress.url + "uploadProofPayment";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
