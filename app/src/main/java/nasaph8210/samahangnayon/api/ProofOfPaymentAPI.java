package nasaph8210.samahangnayon.api;

import nasaph8210.samahangnayon.model.ProofPaymentRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProofOfPaymentAPI {
    @POST("uploadProofPayment")
    Call<ProofPaymentRequest> uploadProofPayment(@Body ProofPaymentRequest request);
}
