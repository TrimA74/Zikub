package sthioul.olivier.zikub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deezer.sdk.model.Track;

import java.util.List;

/**
 * Created by SEBIRE on 07/11/2017.
 */

public class TrackAdaper extends ArrayAdapter<Track> {

    private List<Track> listTrack;

    public TrackAdaper(@NonNull Context context, @NonNull List<Track> objects) {
        super(context,0, objects);
        this.listTrack = objects;
    }

    @Nullable
    @Override
    public Track getItem(int position) {
        return listTrack.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_track,parent, false);
        }
        TrackViewHolder viewHolder = (TrackViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TrackViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.titleTrack);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Track track = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.title.setText(track.getTitle()+ " - " + track.getArtist().getName());
        return convertView;
    }

    private class TrackViewHolder{
        public TextView title;
    }
}
