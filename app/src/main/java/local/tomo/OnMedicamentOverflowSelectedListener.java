package local.tomo;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.Medicament;

/**
 * Created by tomo on 2016-07-01.
 */
public class OnMedicamentOverflowSelectedListener implements View.OnClickListener {

    private Medicament mMedicament;
    private Context mContext;

    public OnMedicamentOverflowSelectedListener(Medicament medicament, Context context) {
        mMedicament = medicament;
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        Log.d("medi", "medi: " + mMedicament.getName());
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menuMedicamentArchive:
                            Toast.makeText(mContext, "Kliknąłeś: " + mMedicament.getName(), Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.menuMedicamentChangeDate:
                            Toast.makeText(mContext, "Kliknąłeś: " + mMedicament.getName(), Toast.LENGTH_SHORT).show();
                            return true;
                        default:
                            return false;
                    }

            }
        });
        popupMenu.inflate(R.menu.medicaments_poupup_menu);
        popupMenu.show();
    }
}
