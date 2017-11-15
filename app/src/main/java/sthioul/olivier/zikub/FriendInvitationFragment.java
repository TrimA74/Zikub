package sthioul.olivier.zikub;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendInvitationFragment extends Fragment {

    private User user;
    private ImageButton addFriend;
    private ListView listViewInvitations;
    private GlobalClass globalContext;
    private ZikubService service;
    public FriendInvitationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend_invitation, container, false);

        this.globalContext = (GlobalClass) getActivity().getApplicationContext();
        Log.i("username", this.globalContext.getUser().getUsername());

        service = ZikubService.retrofit.create(ZikubService.class);

        user = this.globalContext.getUser();

        this.listViewInvitations = (ListView) rootView.findViewById(R.id.newInvitations);

        addFriend = (ImageButton) rootView.findViewById(R.id.addFriend);

        Call<List<String>> call = service.getInvitatoins(user.getUsername(),user.getPassword());
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                //Toast.makeText(FriendsActivity.this, "coucou",Toast.LENGTH_LONG).show();
                //Toast.makeText(FriendsActivity.this, response.body().toString(),Toast.LENGTH_LONG).show();
                //Log.i("friends", response.body().get(0).getUsername());

                displayInvitations(response.body());

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "error",Toast.LENGTH_LONG).show();
            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }

    private void displayInvitations(final List<String> nicknames){

        Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("sthioul.olivier.zikub.ListFriendsFragment");
        FragmentManager fM = getActivity().getSupportFragmentManager();
        InvitationsAdapter adapter = new InvitationsAdapter(getActivity(), nicknames,user,f,fM);
        this.listViewInvitations.setAdapter(adapter);

        this.listViewInvitations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(),  nicknames.get(position) ,Toast.LENGTH_LONG).show();
                /*SearchFriendsFragment.this.globalContext.setCurrrentUser(SearchFriendsFragment.this.globalContext.getUser().getFriends().get(position));
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);*/
            }
        });
    }

}
