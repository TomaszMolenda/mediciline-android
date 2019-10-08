package local.tomo.medi.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.Drug;
import local.tomo.medi.ormlite.data.UserDrug;


public class ListDrugAdapter extends ScrollArrayAdapter<UserDrug> {

    private final static int resource = R.layout.adapter_list_drug;

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewPackage)
    TextView textViewPackage;
    @BindView(R.id.textViewForm)
    TextView textViewForm;
    @BindView(R.id.textViewExpirationDate)
    TextView textViewExpirationDate;



    ListDrugAdapter(Context context, List<UserDrug> drugs) {
        super(context, resource, drugs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = prepareView(convertView);

        UserDrug userDrug = getItem(position);

        if(Objects.nonNull(userDrug)) {

            Drug drug = userDrug.getDrug();

            textViewName.setText(drug.getName());
            textViewProducer.setText(drug.getProducer());
            textViewPackage.setText(drug.getPack());
            textViewForm.setText(drug.getForm());
            textViewExpirationDate.setText(userDrug.getExpirationDate());
        }

        return  view;
    }
}
