package sthioul.olivier.zikub;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFriendsFragment extends Fragment {

    private GlobalClass globalContext;
    private ListView listViewFriends;

    public ListFriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_friends, container, false);

        this.globalContext = (GlobalClass) getActivity().getApplicationContext();
        Log.i("username", this.globalContext.getUser().getUsername());

        ZikubService service = ZikubService.retrofit.create(ZikubService.class);

        final User user = this.globalContext.getUser();

        this.listViewFriends = (ListView) rootView.findViewById(R.id.listFriends);

        Call<List<User>> call = service.getFriends(user.getUsername());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                //Toast.makeText(FriendsActivity.this, "coucou",Toast.LENGTH_LONG).show();
                //Toast.makeText(FriendsActivity.this, response.body().toString(),Toast.LENGTH_LONG).show();
                //Log.i("friends", response.body().get(0).getUsername());
                user.setFriends(response.body());

                displayFriends();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "error",Toast.LENGTH_LONG).show();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

    }

    private void displayFriends(){
        FriendAdapter adapter = new FriendAdapter(getActivity(), this.globalContext.getUser().getFriends());
        this.listViewFriends.setAdapter(adapter);

        this.listViewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(FriendsActivity.this,  FriendsActivity.this.globalContext.getUser().getFriends().get(position).getUsername() ,Toast.LENGTH_LONG).show();
                ListFriendsFragment.this.globalContext.setCurrrentUser(ListFriendsFragment.this.globalContext.getUser().getFriends().get(position));
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

}
