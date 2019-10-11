package local.tomo.medi.activity.drug.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import local.tomo.medi.R;
import local.tomo.medi.activity.ScrollArrayAdapter;
import local.tomo.medi.activity.ViewWithHolder;
import local.tomo.medi.ormlite.data.Drug;
import local.tomo.medi.ormlite.data.UserDrug;


public class UserDrugAdapter extends ScrollArrayAdapter<UserDrug, ViewHolder> {

    private final static int resource = R.layout.adapter_list_drug;
    private final static Function<View, ViewHolder> VIEW_HOLDER_PROVIDER = view -> {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textViewName = view.findViewById(R.id.textViewName);
        viewHolder.textViewProducer = view.findViewById(R.id.textViewProducer);
        viewHolder.textViewPackage = view.findViewById(R.id.textViewPackage);
        viewHolder.textViewForm = view.findViewById(R.id.textViewForm);
        viewHolder.textViewExpirationDate = view.findViewById(R.id.textViewExpirationDate);
        viewHolder.archiveButton = view.findViewById(R.id.archiveButton);
        return viewHolder;
    };

    private final Consumer<UserDrug> userDrugArchiver;

    UserDrugAdapter(Context context, List<UserDrug> drugs, Consumer<UserDrug> userDrugArchiver) {
        super(context, resource, drugs, VIEW_HOLDER_PROVIDER);
        this.userDrugArchiver = userDrugArchiver;
    }

    private AlertDialog createAlertDialog(UserDrug userDrug) {

        Context context = getContext();

        return new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.confirm_archive_drug))
                .setPositiveButton(context.getString(R.string.confirm_true), (dialog, which) -> userDrugArchiver.accept(userDrug))
                .setNegativeButton(context.getString(R.string.confirm_false), (dialog, which) -> dialog.cancel())
                .create();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserDrug userDrug = getItem(position);
        ViewWithHolder viewWithHolder = getView(convertView, parent);
        ViewHolder viewHolder = (ViewHolder) viewWithHolder.getHolder();

        if(Objects.nonNull(userDrug)) {

            Drug drug = userDrug.getDrug();

            viewHolder.textViewName.setText(drug.getName());
            viewHolder.textViewProducer.setText(drug.getProducer());
            viewHolder.textViewPackage.setText(drug.getPack());
            viewHolder.textViewForm.setText(drug.getForm());
            viewHolder.textViewExpirationDate.setText(userDrug.getExpirationDate(getContext()));
            viewHolder.archiveButton.setOnClickListener(v -> createAlertDialog(userDrug).show());
        }

        return  viewWithHolder.getView();
    }
}
