package sthioul.olivier.zikub;

import android.app.Application;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.AlbumPlayer;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.PlayerState;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

    private TrackPlayer trackPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // replace with your own Application ID
        String applicationID = "254802";
        DeezerConnect deezerConnect = new DeezerConnect(this, applicationID);
        Application app =  getApplication();
        // create the player
        try {
            trackPlayer = new TrackPlayer(app, deezerConnect, new WifiAndMobileNetworkStateChecker());
            // start playing music
            long trackId = 677232;
            DeezerRequest request = DeezerRequestFactory.requestTrack(trackId);
            request.setId("olivier");

            RequestListener listener = new JsonRequestListener() {

                public void onResult(Object result, Object requestId) {
                    Track track = (Track) result;
                    String image_url= track.getArtist().getMediumImageUrl();
                    ImageButton currentImageMusic = (ImageButton) findViewById(R.id.currentImageMusic);
                    Picasso.with(getApplicationContext()).load(image_url).fit().centerCrop().into(currentImageMusic);

                }

                public void onUnparsedResult(String requestResponse, Object requestId) {}

                public void onException(Exception e, Object requestId) {}
            };

            deezerConnect.requestAsync(request, listener);
            trackPlayer.playTrack(trackId);
            trackPlayer.stop();

            final ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(trackPlayer.getPlayerState() == PlayerState.PLAYING){
                        trackPlayer.pause();
                        playButton.setImageResource(android.R.drawable.ic_media_play);
                    } else {
                        trackPlayer.play();
                        playButton.setImageResource(android.R.drawable.ic_media_pause);
                    }

                }

            });

// ...

// to make sure the player is stopped (for instance when the activity is closed)

        } catch ( TooManyPlayersExceptions e){

        } catch ( DeezerError e) {

        }





        //Log.e("position",Integer.toString(location));
    }
    @Override
    public void onStop(){
        super.onStop();
        trackPlayer.stop();
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
