package sthioul.olivier.zikub;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.AsyncDeezerTask;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {


    private SearchView searchView;
    private DeezerConnect deezerConnect;
    private ListView trackList;
    private int idMusic;
    private User user;
    ArrayList<Track> listTrack;
    private GlobalClass globalContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.globalContext = (GlobalClass) getApplicationContext();
        user = this.globalContext.getUser();

        String applicationID = "254802";
        deezerConnect = new DeezerConnect(this, applicationID);

        Intent intent = getIntent();
        idMusic = intent.getIntExtra("id",0);

        searchView = (SearchView) findViewById(R.id.searchView);

        trackList = (ListView) findViewById(R.id.trackList);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                DeezerRequest request = DeezerRequestFactory.requestSearchTracks(s);

                AsyncDeezerTask task = new AsyncDeezerTask(deezerConnect, new JsonRequestListener() {

                    public void onResult(Object result, Object requestId) {
                        listTrack = (ArrayList<Track>) result ;
                        Log.e("test",Integer.toString(listTrack.get(0).getDuration()));
                        displayTracks();


                    }

                    public void onUnparsedResult(String requestResponse, Object requestId) {
                    }

                    public void onException(Exception e, Object requestId) {
                    }
                });
                task.execute(request);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }

    private void displayTracks(){
        TrackAdaper adapter = new TrackAdaper(SearchActivity.this,this.listTrack);
        this.trackList.setAdapter(adapter);

        this.trackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long newmusic = SearchActivity.this.listTrack.get(position).getId();
                ZikubService service = ZikubService.retrofit.create(ZikubService.class);
                user.getPlaylist().remove(new Integer(idMusic));
                user.getPlaylist().add( (int) newmusic);
                Call<Boolean> call = service.updateMusic(user.getUsername(),user.getPassword(),idMusic,newmusic);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Boolean rep = response.body().booleanValue();
                        Toast.makeText(SearchActivity.this,  "La musique a été ajouté/remplacé" ,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, "La musique n'a pas été ajouté/remplacé",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
}
