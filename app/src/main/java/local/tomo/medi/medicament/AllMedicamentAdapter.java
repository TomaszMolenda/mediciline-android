package local.tomo.medi.medicament;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.model.Months;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.Medicament;
import lombok.SneakyThrows;

public class AllMedicamentAdapter extends ArrayAdapter<Medicament> {

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewPrice)
    TextView textViewPrice;
    @BindView(R.id.textViewDate)
    TextView textViewDate;
    @BindView(R.id.textViewPack)
    TextView textViewPack;
    @BindView(R.id.textViewKind)
    TextView textViewKind;
    @BindView(R.id.textViewDiseases)
    TextView textViewDiseases;
    @BindView(R.id.imageViewNoSynchro)
    ImageView imageViewNoSynchro;
    @BindView(R.id.imageButtonMenu)
    View imageButtonMenu;

    private MedicamentsActivity medicamentsActivity;

    private DatabaseHelper databaseHelper;

    public AllMedicamentAdapter(MedicamentsActivity medicamentsActivity, Context context, int textViewResourceId, ArrayList<Medicament> medicaments) {
        super(context, textViewResourceId, medicaments);
        this.medicamentsActivity = medicamentsActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.adapter_all_medicament_list_row, null);
            ButterKnife.bind(this, view);
        }

        final Medicament medicament = getItem(position);

        if(medicament!=null) {
            imageButtonMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), v);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Intent intent;
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

            String name = medicament.getName();
            if(name.length() > 25)
                name = name.substring(0,25);
            textViewName.setText(name);
            textViewProducer.setText(getContext().getString(R.string.producent, medicament.getProducent()));
            if (medicament.getIdServer() == 0) imageViewNoSynchro.setVisibility(ImageView.VISIBLE);
            else imageViewNoSynchro.setVisibility(ImageView.INVISIBLE);
            textViewPrice.setText(getContext().getString(R.string.price, medicament.getPrice()));
            textViewPack.setText(getContext().getString(R.string.pack, medicament.getPack()));
            textViewKind.setText(getContext().getString(R.string.kind, medicament.getKind()));
            textViewDiseases.setText(getContext().getString(R.string.DiseasesCount, medicament.getMedicament_diseases().size()));
            if(medicament.isOverdue() & !medicament.isArchive())
                textViewDate.setTextColor(Color.parseColor("#DF013A"));
            textViewDate.setText(getContext().getString(R.string.Date_parametr, Months.createDate(medicament.getDate())));
        }
        return  view;
    }

    private AlertDialog confirmDelete(final Medicament medicament)
    {

        return new AlertDialog.Builder(medicamentsActivity)
                .setTitle(getContext().getString(R.string.Confirm_use))
                .setMessage(getContext().getString(R.string.operation_is_irreversible))
                .setIcon(R.drawable.trash_icon)
                .setPositiveButton(getContext().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @SneakyThrows
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
                        medicament.setArchive(true);
                        medicamentDao.update(medicament);
                        //// TODO: 2016-07-02 implements send info to server
                        medicamentsActivity.setActiveMedicaments();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton(getContext().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getContext(),DatabaseHelper.class);
        }
        return databaseHelper;

    }


}
