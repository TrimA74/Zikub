package sthioul.olivier.zikub;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFriendsFragment extends Fragment {

    private SearchView searchView;
    private User user;
    private ListView listViewNewFriends;
    private GlobalClass globalContext;
    private ZikubService service;
    private ImageButton addFriend;
    public SearchFriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_friends, container, false);

        this.globalContext = (GlobalClass) getActivity().getApplicationContext();
        Log.i("username", this.globalContext.getUser().getUsername());

        service = ZikubService.retrofit.create(ZikubService.class);

        user = this.globalContext.getUser();

        this.listViewNewFriends = (ListView) rootView.findViewById(R.id.newFriends);

        searchView = (SearchView) rootView.findViewById(R.id.searchView);
        addFriend = (ImageButton) rootView.findViewById(R.id.addFriend);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Call<List<String>> call = service.getnewFriends(s,user.getUsername(),user.getPassword());
                call.enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        //Toast.makeText(FriendsActivity.this, "coucou",Toast.LENGTH_LONG).show();
                        //Toast.makeText(FriendsActivity.this, response.body().toString(),Toast.LENGTH_LONG).show();
                        //Log.i("friends", response.body().get(0).getUsername());

                        displaynewFriends(response.body());

                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Toast.makeText(getActivity(), "error",Toast.LENGTH_LONG).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });








    // Inflate the layout for this fragment
        return rootView;
    }

    private void displaynewFriends(final List<String> nicknames){
        SearchFriendsAdapter adapter = new SearchFriendsAdapter(getActivity(), nicknames,user);
        this.listViewNewFriends.setAdapter(adapter);

        this.listViewNewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
