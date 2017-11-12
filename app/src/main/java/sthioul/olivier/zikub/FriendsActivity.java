package sthioul.olivier.zikub;


import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

    private GlobalClass globalContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        this.globalContext = (GlobalClass) getApplicationContext();

        /***
         * on mets ListFriendsFragment par défaut pour afficher la liste des amis
         */
        changeFragment(new ListFriendsFragment(),false,false);

        ImageButton notif = (ImageButton) findViewById(R.id.notifButton);
        /***
         * OnClickListener pour pouvoir switcher de vue entre :
         * vue des amis
         * vue des invitations
         * et vue de recherche de nouveaux amis
         */
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "youyou" ,Toast.LENGTH_LONG).show();
                changeFragment(new FriendInvitationFragment(),false,false);
            }
        });

        ImageButton friendButton = (ImageButton) findViewById(R.id.FriendsButton);
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "youyou" ,Toast.LENGTH_LONG).show();
                changeFragment(new ListFriendsFragment(),false,false);
            }
        });

        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "youyou" ,Toast.LENGTH_LONG).show();
                changeFragment(new SearchFriendsFragment(),false,false);
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }



    /**
     * Change the current displayed fragment by a new one.
     * - if the fragment is in backstack, it will pop it
     * - if the fragment is already displayed (trying to change the fragment with the same), it will not do anything
     *
     * @param frag            the new fragment to display
     * @param saveInBackstack if we want the fragment to be in backstack
     * @param animate         if we want a nice animation or not
     */
    private void changeFragment(Fragment frag, boolean saveInBackstack, boolean animate) {
        String backStateName = ((Object) frag).getClass().getName();

        try {
            FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

            if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
                //fragment not in back stack, create it.
                FragmentTransaction transaction = manager.beginTransaction();
                /***
                 * @fragment_container correspond à l'ID de tous nos fragments et de la frameLayout par défault qu'on a dans @activity_friends.xml
                 */
                transaction.replace(R.id.fragment_container, frag, backStateName);

                transaction.commit();
            } else {
                // custom effect if fragment is already instanciated
            }
        } catch (IllegalStateException exception) {
            Log.e("youyou", "Unable to commit fragment, could be activity as been killed in background. " + exception.toString());
        }
    }

}

