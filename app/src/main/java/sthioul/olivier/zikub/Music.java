package sthioul.olivier.zikub;

import android.media.AudioManager;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.Toast;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.JsonUtils;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by florian on 17/10/2017.
 */

public class Music {
    private ImageButton image;

    private int id;

    private String url;

    private String title;

    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Music(ImageButton image, int id) {
        this.image = image;
        this.id = id;
    }

    public ImageButton getImage() {

        return image;
    }

    public void setImage(ImageButton image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void loadUrl (DeezerConnect deezerConnect) throws  IOException, DeezerError {
        DeezerRequest request = DeezerRequestFactory.requestTrack(this.id);
        request.setId("olivier");
        RequestListener listener = new JsonRequestListener() {

            public void onResult(Object result, Object requestId) {
                Track track = (Track) result;
                Music.this.url = track.getPreviewUrl();

            }

            public void onUnparsedResult(String requestResponse, Object requestId) {
            }

            public void onException(Exception e, Object requestId) {
            }
        };

        try {
            Object o = JsonUtils.deserializeJson(deezerConnect.requestSync(request));
            Track track = (Track) o ;
            Music.this.url = track.getPreviewUrl();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
