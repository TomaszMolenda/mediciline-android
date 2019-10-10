package local.tomo.medi.activity.drug.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;
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
        return viewHolder;
    };

    UserDrugAdapter(Context context, List<UserDrug> drugs) {
        super(context, resource, drugs, VIEW_HOLDER_PROVIDER);
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
            viewHolder.textViewExpirationDate.setText(userDrug.getExpirationDate());
        }

        return  viewWithHolder.getView();
    }
}
