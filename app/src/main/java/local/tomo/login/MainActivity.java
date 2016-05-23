package local.tomo.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.network.InternetActivity;

public class MainActivity extends InternetActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String PREF_NAME = "tomo_pref";

    //private MedicamentDAO medicamentDAO;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHandler = new DatabaseHandler(getApplicationContext(), null, null, 1);
        final FloatingActionButton fabAddMedicamentHandy = (FloatingActionButton) findViewById(R.id.fabAddMedicamentHandy);
        final FloatingActionButton fabAddMedicamentPhoto = (FloatingActionButton) findViewById(R.id.fabAddMedicamentPhoto);
        final FloatingActionButton fabAddMedicament = (FloatingActionButton) findViewById(R.id.fabAddMedicament);
        fabAddMedicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fabAddMedicamentHandy.getVisibility() == FloatingActionButton.VISIBLE){
                    fabAddMedicamentHandy.setVisibility(FloatingActionButton.INVISIBLE);
                    fabAddMedicamentPhoto.setVisibility(FloatingActionButton.INVISIBLE);
                }
                else {
                    fabAddMedicamentHandy.setVisibility(FloatingActionButton.VISIBLE);
                    fabAddMedicamentPhoto.setVisibility(FloatingActionButton.VISIBLE);
                }
            }
        });
        fabAddMedicamentHandy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMedicamentActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_your_medicaments_leyout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new YourMedicamentsFragment())
                    .commit();
        } else if (id == R.id.nav_all_medicaments_leyout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new AllMedicamentsFragment())
                    .commit();
        } else if (id == R.id.nav_files_leyout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new FilesFragment())
                    .commit();
        } else if (id == R.id.nav_logout) {
            getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit().clear().commit();
            //medicamentDAO.deleteAll();
            //databaseHandler.getMedicamentDAO().deleteAll();
            databaseHandler.delete();
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
