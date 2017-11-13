package sthioul.olivier.zikub;

import java.util.List;

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


    @GET("api.php?service=loadFriends")
    Call<List<User>> getFriends(@Query("user") String pseudo);

    @GET("api.php?service=changemusic")
    Call<Boolean> updateMusic(@Query("user") String pseudo, @Query("password") String password , @Query("oldmusic") int oldmusic , @Query("newmusic") long newmusic );

    @GET("api.php?service=register")
    Call<User> register(@Query("user") String pseudo , @Query("password") String password );

    @GET("api.php?service=searchUser")
    Call<List<String>> getnewFriends(@Query("query") String query);

    @GET("api.php?service=inviteFriend")
    Call<Boolean> addFriend(@Query("user") String pseudo, @Query("password") String password , @Query("friend") String ami);
}
