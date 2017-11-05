package sthioul.olivier.zikub;

import android.app.Application;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.AsyncDeezerTask;
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

import java.io.IOException;
import java.net.URI;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener , SeekBar.OnSeekBarChangeListener {

    private MediaPlayer mediaPlayer;

    private ArrayList<Music> musics;
    private SeekBar seekbar;
    private DeezerConnect deezerConnect;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private TextView textDuration,textTimer,textTitle;
    private ImageButton playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDuration = (TextView)findViewById(R.id.textDuration);
        textTimer = (TextView)findViewById(R.id.textTimer);
        textTitle = (TextView)findViewById(R.id.textTitle);

        // replace with your own Application ID
        String applicationID = "254802";
        deezerConnect = new DeezerConnect(this, applicationID);

        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(this);



        this.musics = new ArrayList<>();
        mediaPlayer = new MediaPlayer();

        this.musics.add(new Music((ImageButton) findViewById(R.id.currentImageMusic) , 677232));
        this.musics.add(new Music((ImageButton) findViewById(R.id.music1) , 116287436));
        this.musics.add(new Music((ImageButton) findViewById(R.id.music3) , 3148009));
        this.musics.add(new Music((ImageButton) findViewById(R.id.music4) , 56889651));
        this.musics.add(new Music((ImageButton) findViewById(R.id.music2) , 93087054));

        for(final Music m : this.musics){
            DeezerRequest request = DeezerRequestFactory.requestTrack(m.getId());
            request.setId(Integer.toString(m.getId()));

            m.getImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.reset(); // if not the app crash
                    Uri myUri = Uri.parse(m.getUrl()); // initialize Uri here
                    try {
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(getApplicationContext(), myUri);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        playButton.setImageResource(android.R.drawable.ic_media_pause);

                        finalTime = mediaPlayer.getDuration();
                        startTime = mediaPlayer.getCurrentPosition();

                        textDuration.setText(String.format(Locale.US,"0:%02d",
                                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                finalTime)))
                        );

                        textTimer.setText(String.format(Locale.US,"0:%02d",
                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                startTime)))
                        );
                        textTitle.setText(m.getTitle() + " - " + m.getArtist());

                        seekbar.setProgress((int)startTime);
                        seekbar.setMax((int) finalTime);
                        myHandler.postDelayed(UpdateSongTime,100);
                        Toast.makeText(getApplicationContext(), "music ready", Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                            Log.e("error","something bad happen here");
                    }
                }
            });
            AsyncDeezerTask task = new AsyncDeezerTask(deezerConnect, new JsonRequestListener() {

                public void onResult(Object result, Object requestId) {
                    Track track = (Track) result;
                    m.setUrl(track.getPreviewUrl());
                    m.setTitle(track.getTitle());
                    m.setArtist(track.getArtist().getName());
                    String image_url = track.getAlbum().getMediumImageUrl();
                    Picasso.with(getApplicationContext()).load(image_url).fit().centerCrop().into(m.getImage());
                }

                public void onUnparsedResult(String requestResponse, Object requestId) {
                }

                public void onException(Exception e, Object requestId) {
                }

            });

            task.execute(request);
        }
        /*  play/pause button */
        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playButton.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mediaPlayer.start();
                    playButton.setImageResource(android.R.drawable.ic_media_pause);
                }

            }

        });



    }
    @Override
    public void onStop(){
        super.onStop();
        mediaPlayer.stop();
    }
    /* je sais pas si cela va servir ... on vera */
    @Override
    public void onFragmentInteraction(Uri uri){

    }
    /**
     * Background Runnable thread
     * */
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            seekbar.setProgress(mediaPlayer.getCurrentPosition());
            textTimer.setText(String.format(Locale.US,"0:%02d",
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    startTime)))
            );
            myHandler.postDelayed(this, 100);
        }
    };


    /**
     *
     * */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    /**
     * When user starts moving the progress handler
     * */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        myHandler.removeCallbacks(UpdateSongTime);
    }

    /**
     * When user stops moving the progress hanlder
     * */
    @Override
    public void onStopTrackingTouch(SeekBar sseekBar) {
        myHandler.removeCallbacks(UpdateSongTime);

        // forward or backward to certain seconds
        mediaPlayer.seekTo(sseekBar.getProgress());

        // update timer progress again
        myHandler.postDelayed(UpdateSongTime, 100);
    }
}
