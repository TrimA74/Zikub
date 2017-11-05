package sthioul.olivier.zikub;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.Toast;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by florian on 17/10/2017.
 */

public class ZikubPlayer {
    private int idMusic;

    public ZikubPlayer (int id){
        this.idMusic = id;
    }

    public void launchPlayer (MediaPlayer mp) {

    }
}
