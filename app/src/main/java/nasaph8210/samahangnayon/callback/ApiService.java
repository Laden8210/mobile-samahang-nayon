package nasaph8210.samahangnayon.callback;

import nasaph8210.samahangnayon.model.Barangay;
import nasaph8210.samahangnayon.model.City;
import nasaph8210.samahangnayon.model.Region;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {

    @GET("api/provinces/")
    Call<List<Region>> fetchRegions();


    @GET("api/provinces/{province}/cities-municipalities/")
    Call<List<City>> fetchCities(@Path("province") String province);

    @GET("api/cities/{city}/barangays.json")
    Call<List<Barangay>> fetchBarangays(@Path("city") String city);
}


