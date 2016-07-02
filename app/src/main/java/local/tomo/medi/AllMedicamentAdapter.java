package local.tomo.medi;



import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
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

import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;

/**
 * Created by tomo on 2016-05-27.
 */
public class AllMedicamentAdapter extends ArrayAdapter<Medicament> {

    private ArrayList<Medicament> medicaments;

    private final List<String> months = Months.getMonths();

    private MedicamentActivity medicamentActivity;

    private Context mContext;

    private DatabaseHelper databaseHelper = null;

    private ListView mListView;



    public AllMedicamentAdapter(ListView listView, MedicamentActivity medicamentActivity, Context context, int textViewResourceId, ArrayList<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.medicaments = medicaments;
        this.medicamentActivity = medicamentActivity;
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
            v = vi.inflate(R.layout.all_medicament_list_row, null);
        }

        final Medicament medicament = getItem(position);

        if(medicament!=null) {
            TextView rowAllMedicamentName = (TextView) v.findViewById(R.id.rowAllMedicamentName);
            TextView rowAllMedicamentProducer = (TextView) v.findViewById(R.id.rowAllMedicamentProducer);
            TextView rowAllMedicamentId = (TextView) v.findViewById(R.id.rowAllMedicamentId);
            TextView rowAllMedicamentIdServer = (TextView) v.findViewById(R.id.rowAllMedicamentIdServer);
            TextView rowAllMedicamentProductLineID = (TextView) v.findViewById(R.id.rowAllMedicamentProductLineID);
            TextView rowAllMedicamentPackageID = (TextView) v.findViewById(R.id.rowAllMedicamentPackageID);
            TextView rowAllMedicamentPrice = (TextView) v.findViewById(R.id.rowAllMedicamentPrice);
            TextView rowAllMedicamentDate = (TextView) v.findViewById(R.id.rowAllMedicamentDate);
            TextView rowAllMedicamentPack = (TextView) v.findViewById(R.id.rowAllMedicamentPack);

            ImageView rowAllMedicamentNoSynchro = (ImageView) v.findViewById(R.id.rowAllMedicamentNoSynchro);
            View menuIcon = v.findViewById(R.id.rowAllMedicamentMenu);
            if(medicamentActivity.getChooseMedicaments() == medicamentActivity.ACTIVE_MEDICAMENTS) {
                menuIcon.setVisibility(View.VISIBLE);
            }
            menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mContext, v);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.menuMedicamentArchive:
                                    AlertDialog alertDialog = confirmDelete(medicament);
                                    alertDialog.show();
                                    return true;
                                case R.id.menuMedicamentChangeDate:
                                    //// TODO: 2016-07-02 implement change date
                                    return true;
                                default:
                                    return false;
                            }

                        }
                    });
                    popupMenu.inflate(R.menu.medicaments_poupup_menu);
                    popupMenu.show();
                }
            });


            rowAllMedicamentName.setText(medicament.getName());
            rowAllMedicamentProducer.setText("Producent: " + medicament.getProducent());

            rowAllMedicamentId.setText(""+medicament.getId());
            int idServer = medicament.getIdServer();
            if (idServer == 0) rowAllMedicamentNoSynchro.setVisibility(ImageView.VISIBLE);
            else rowAllMedicamentNoSynchro.setVisibility(ImageView.INVISIBLE);
            rowAllMedicamentIdServer.setText("idServer: "+ idServer);
            rowAllMedicamentProductLineID.setText("ProductLineId: "+medicament.getProductLineID());
            rowAllMedicamentPackageID.setText("PackageId: "+medicament.getPackageID());
            rowAllMedicamentPrice.setText("Cena: " + medicament.getPrice());
            rowAllMedicamentPack.setText("Rodzaj: " + medicament.getKind());

            long date = medicament.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            rowAllMedicamentDate.setText("Data: " + months.get(month) + " " + year);
        }
        return  v;
    }

    private AlertDialog confirmDelete(final Medicament medicament)
    {

        AlertDialog alertDialog = new AlertDialog.Builder(medicamentActivity)
                .setTitle("Potwierdź zużycie")
                .setMessage("operacja jest nieodwracalna")
                .setIcon(R.drawable.trash_icon)
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
                            medicament.setArchive(true);
                            medicamentDao.update(medicament);
                            Log.d("medi", "archiwizuje " + medicament.getName());
                            //// TODO: 2016-07-02 implements send info to server
                            medicamentActivity.setActiveMedicaments();
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
