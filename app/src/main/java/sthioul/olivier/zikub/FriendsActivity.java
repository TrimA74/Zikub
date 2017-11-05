package sthioul.olivier.zikub;


import android.app.Application;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

    private GlobalClass globalContext;
    private ListView listViewFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        this.globalContext = (GlobalClass) getApplicationContext();
        Log.i("username", this.globalContext.getUser().getUsername());

        ZikubService service = ZikubService.retrofit.create(ZikubService.class);

        final User user = this.globalContext.getUser();

        this.listViewFriends = (ListView) findViewById(R.id.listFriends);

        Call<List<User>> call = service.getFriends(user.getUsername());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                //Toast.makeText(FriendsActivity.this, "coucou",Toast.LENGTH_LONG).show();
                //Toast.makeText(FriendsActivity.this, response.body().toString(),Toast.LENGTH_LONG).show();
                //Log.i("friends", response.body().get(0).getUsername());
                user.setFriends(response.body());

                displayFriends();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(FriendsActivity.this, "error",Toast.LENGTH_LONG).show();
            }
        });




    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }


    private void displayFriends(){
        FriendAdapter adapter = new FriendAdapter(FriendsActivity.this, this.globalContext.getUser().getFriends());
        this.listViewFriends.setAdapter(adapter);

        this.listViewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FriendsActivity.this,  FriendsActivity.this.globalContext.getUser().getFriends().get(position).getUsername() ,Toast.LENGTH_LONG).show();


            }
        });
    }


}

