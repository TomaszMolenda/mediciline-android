package local.tomo.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.swipe.DiseaseFragment;
import local.tomo.login.swipe.SwipeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static String PREF_NAME = "tomo_pref";

    FragmentManager fragmentManager = getSupportFragmentManager();

    private DatabaseHandler databaseHandler;

    private TextView textViewMenuUserName;
    private TextView textViewMenuEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new SwipeFragment())
                .commit();


        databaseHandler = new DatabaseHandler(getApplicationContext(), null, null, 1);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        textViewMenuUserName = (TextView) header.findViewById(R.id.textViewMenuUserName);
        textViewMenuUserName.setText(LoginActivity.userName);
        textViewMenuEmail = (TextView) header.findViewById(R.id.textViewMenuEmail);
        textViewMenuEmail.setText(LoginActivity.email);
    }

    //http://stackoverflow.com/a/13578600
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStack();
            return;
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Proszę kilknąć cofnij jeszcze raz aby zamknąć program", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_your_medicaments_leyout) {
//            TODO add fragment with your medicaments
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new AllMedicamentsFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_all_medicaments_leyout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new DiseaseFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_files_leyout) {
//            fragmentManager.beginTransaction()
//                    .replace(R.id.content_frame, new FilesFragment())
//                    .commit();
        } else if (id == R.id.nav_logout) {
            getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit().clear().commit();
            databaseHandler.removeAllMedicaments();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
