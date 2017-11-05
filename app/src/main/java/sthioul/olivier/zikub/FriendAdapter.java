package sthioul.olivier.zikub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by olivier on 05/11/2017.
 */

public class FriendAdapter extends ArrayAdapter<User> {

    //tweets est la liste des models à afficher
    public FriendAdapter(Context context, List<User> friends) {
        super(context, 0, friends);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_friend,parent, false);
        }

        FriendViewHolder viewHolder = (FriendViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new FriendViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.username);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        User friend = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(friend.getUsername());

        return convertView;
    }

    private class FriendViewHolder{
        public TextView pseudo;
    }
}
