package sthioul.olivier.zikub;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by florian on 19/09/2017.
 */

public interface ZikubService {
    public static final String ENDPOINT = "";

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://zikub.livehost.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("api.php?service=login")
    Call<User> getUser(@Query("user") String pseudo , @Query("password") String password );
}
