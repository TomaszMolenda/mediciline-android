package local.tomo.medi.medicament;



import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;

/**
 * Created by tomo on 2016-05-27.
 */
public class AllMedicamentAdapter extends ArrayAdapter<Medicament> {

    private ArrayList<Medicament> medicaments;

    private final List<String> months = Months.getMonths();

    private MedicamentsActivity medicamentsActivity;

    private Context mContext;

    private DatabaseHelper databaseHelper = null;

    private ListView mListView;



    public AllMedicamentAdapter(ListView listView, MedicamentsActivity medicamentsActivity, Context context, int textViewResourceId, ArrayList<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.medicaments = medicaments;
        this.medicamentsActivity = medicamentsActivity;
        mContext = context;
        mListView = listView;

    }



    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_all_medicament_list_row, null);
        }

        final Medicament medicament = getItem(position);

        if(medicament!=null) {
            TextView textViewName = (TextView) v.findViewById(R.id.textViewName);
            TextView textViewProducer = (TextView) v.findViewById(R.id.textViewProducer);
            TextView textViewPrice = (TextView) v.findViewById(R.id.textViewPrice);
            TextView textViewDate = (TextView) v.findViewById(R.id.textViewDate);
            TextView textViewPack = (TextView) v.findViewById(R.id.textViewPack);
            TextView textViewKind = (TextView) v.findViewById(R.id.textViewKind);
            TextView textViewDiseases = (TextView) v.findViewById(R.id.textViewDiseases);

            ImageView imageViewNoSynchro = (ImageView) v.findViewById(R.id.imageViewNoSynchro);
            View menuIcon = v.findViewById(R.id.imageButtonMenu);

            menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mContext, v);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Intent intent = null;
                            switch (item.getItemId()) {
                                case R.id.archive:
                                    AlertDialog alertDialog = confirmDelete(medicament);
                                    alertDialog.show();
                                    return true;
                                case R.id.info:
                                    intent = new Intent(getContext(), MedicamentDbDetailsActivity.class);
                                    intent.putExtra("packageID", medicament.getPackageID());
                                    medicamentsActivity.startActivity(intent);
                                    return true;
                                case R.id.diseases:
                                    intent = new Intent(getContext(), DiseasesInMedicamentActivity.class);
                                    intent.putExtra("medicamentId", medicament.getId());
                                    medicamentsActivity.startActivity(intent);
                                    return true;
                                default:
                                    return false;
                            }

                        }
                    });
                    popupMenu.inflate(R.menu.medicaments_poupup_menu);
                    Menu menu = popupMenu.getMenu();
                    if(medicamentsActivity.isHideArchive()) {
                        MenuItem item = menu.findItem(R.id.archive);
                        item.setEnabled(false);

                    }
                    if(medicament.getMedicament_diseases().size() == 0) {
                        MenuItem item = menu.findItem(R.id.diseases);
                        item.setEnabled(false);
                    }
                    popupMenu.show();
                }
            });


            textViewName.setText(medicament.getName());
            textViewProducer.setText("Producent: " + medicament.getProducent());

            int idServer = medicament.getIdServer();
            if (idServer == 0) imageViewNoSynchro.setVisibility(ImageView.VISIBLE);
            else imageViewNoSynchro.setVisibility(ImageView.INVISIBLE);
            textViewPrice.setText("Cena: " + medicament.getPrice());
            textViewPack.setText("Opakowanie: " + medicament.getPack());
            textViewKind.setText("Rodzaj: " + medicament.getKind());
            textViewDiseases.setText("Choroby: " + medicament.getMedicament_diseases().size());

            long date = medicament.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            if(medicament.isOverdue() & medicament.isArchive() == false)
                textViewDate.setTextColor(Color.parseColor("#DF013A"));
            textViewDate.setText("Data: " + months.get(month) + " " + year);
        }
        return  v;
    }

    private AlertDialog confirmDelete(final Medicament medicament)
    {

        AlertDialog alertDialog = new AlertDialog.Builder(medicamentsActivity)
                .setTitle("Potwierdź zużycie")
                .setMessage("operacja jest nieodwracalna")
                .setIcon(R.drawable.trash_icon)
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
                            medicament.setArchive(true);
                            medicamentDao.update(medicament);

                            //// TODO: 2016-07-02 implements send info to server
                            medicamentsActivity.setActiveMedicaments();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return alertDialog;

    }



    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(mContext,DatabaseHelper.class);
        }
        return databaseHelper;

    }


}
