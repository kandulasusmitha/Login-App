package ApiPackage;

import com.google.gson.JsonElement;

import response.login.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIClass {

    @POST("user/login")
    Call<LoginResponse> login(@Body JsonElement jsonElement);//fo/1.0.0/users/{tenantId}/revoke

}
