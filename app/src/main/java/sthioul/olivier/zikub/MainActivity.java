package sthioul.olivier.zikub;

import android.app.Application;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.player.AlbumPlayer;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.PlayerState;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout f = (FrameLayout) findViewById(R.id.image5);
        f.bringToFront();

        // replace with your own Application ID
        String applicationID = "254802";
        DeezerConnect deezerConnect = new DeezerConnect(this, applicationID);
        Application app =  getApplication();
        // create the player
        try {
            final TrackPlayer trackPlayer = new TrackPlayer(app, deezerConnect, new WifiAndMobileNetworkStateChecker());
            // start playing music
            long trackId = 677232;
            trackPlayer.playTrack(trackId);
            trackPlayer.pause();

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
    public void onFragmentInteraction(Uri uri){

    }
}
