package sthioul.olivier.zikub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEBIRE on 13/11/2017.
 */
public class SearchFriendsAdapter extends ArrayAdapter<String> {

    private ZikubService service;
    private GlobalClass globalContext;
    private User user;

    //tweets est la liste des models à afficher
    public SearchFriendsAdapter(Context context, List<String> nicknames, User user) {
        super(context, 0, nicknames);
        this.user = user;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_searchfriends,parent, false);
        }

        FriendViewHolder viewHolder = (FriendViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new FriendViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.friendsearch);
            viewHolder.addFriend = (ImageButton) convertView.findViewById(R.id.addFriend);
            convertView.setTag(viewHolder);

            service = ZikubService.retrofit.create(ZikubService.class);


            viewHolder.addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<Boolean> call = service.addFriend(user.getUsername(),user.getPassword(),getItem(position));
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            remove(getItem(position));
                            Boolean rep = response.body().booleanValue();
                            Toast.makeText(getContext(), "Invitation has been sent",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getContext(), "error",Toast.LENGTH_LONG).show();
                        }
                    });
                    //Toast.makeText(getContext(),  getItem(position) ,Toast.LENGTH_LONG).show();

                }
            });
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        String nickname = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(nickname);

        return convertView;
    }

    private class FriendViewHolder{
        public TextView pseudo;
        public ImageButton addFriend;
    }
}
