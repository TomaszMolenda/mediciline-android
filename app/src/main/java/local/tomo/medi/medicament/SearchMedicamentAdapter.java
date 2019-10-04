package local.tomo.medi.medicament;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.DbMedicament;

import static org.apache.commons.lang3.StringUtils.capitalize;


public class SearchMedicamentAdapter extends ScrollArrayAdapter {

    private final static int resource = R.layout.adapter_search_medicament_list_row;

    private final String searchText;

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewPackage)
    TextView textViewPackage;
    @BindView(R.id.textViewForm)
    TextView textViewForm;


    SearchMedicamentAdapter(Context context, List<DbMedicament> drugs, String searchText) {
        super(context, resource, drugs);
        this.searchText = searchText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = prepareView(convertView);

        DbMedicament drug = getItem(position);

        if(Objects.nonNull(drug)) {

            String productName = drug.getProductName();

            if (productName.toLowerCase().startsWith(searchText.toLowerCase())) {

                String sourceString = "<b>" + capitalize(searchText) + "</b>" + StringUtils.removeIgnoreCase(productName, searchText);
                textViewName.setText(Html.fromHtml(sourceString));

            } else {
                textViewName.setText(productName);
            }

            textViewProducer.setText(drug.getProducer());
            textViewPackage.setText(drug.getPack());
            textViewForm.setText(drug.getForm());
        }

        return  view;
    }
}
