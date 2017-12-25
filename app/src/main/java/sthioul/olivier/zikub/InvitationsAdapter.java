package sthioul.olivier.zikub;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
 * Created by SEBIRE on 15/11/2017.
 */

public class InvitationsAdapter extends ArrayAdapter<String> {
    private ZikubService service;
    private GlobalClass globalContext;
    private User user;
    private Fragment f;
    private FragmentManager fM;

    //tweets est la liste des models à afficher
    public InvitationsAdapter(Context context, List<String> nicknames, User user, Fragment f, FragmentManager fM) {
        super(context, 0, nicknames);
        this.user = user;
        this.f=f;
        this.fM = fM;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_invitations,parent, false);
        }

        InvitationsAdapter.InvitationViewHolder viewHolder = (InvitationsAdapter.InvitationViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new InvitationsAdapter.InvitationViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.invitation);
            viewHolder.addFriend = (ImageButton) convertView.findViewById(R.id.addFriend);
            convertView.setTag(viewHolder);

            service = ZikubService.retrofit.create(ZikubService.class);


            viewHolder.addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<Boolean> call = service.acceptFriend(user.getUsername(),user.getPassword(),getItem(position));
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            remove(getItem(position));
                            Boolean rep = response.body().booleanValue();
                            /***
                             * refresh friendFragment quand on accept une invitation
                             */
                            final FragmentTransaction ft = fM.beginTransaction();
                            ft.detach(f);
                            ft.attach(f);
                            ft.commit();

                            Toast.makeText(getContext(), Boolean.toString(rep),Toast.LENGTH_LONG).show();
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

    private class InvitationViewHolder{
        public TextView pseudo;
        public ImageButton addFriend;
    }
}
