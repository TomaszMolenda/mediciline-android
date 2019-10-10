package local.tomo.medi.activity.drug.add;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Function;

import local.tomo.medi.R;
import local.tomo.medi.activity.ScrollArrayAdapter;
import local.tomo.medi.activity.ViewWithHolder;
import local.tomo.medi.ormlite.data.Drug;

import static org.apache.commons.lang3.StringUtils.capitalize;


public class SearchDrugAdapter extends ScrollArrayAdapter<Drug, ViewHolder> {

    private final static int resource = R.layout.adapter_search_drug;
    private final static Function<View, ViewHolder> VIEW_HOLDER_PROVIDER = view -> {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textViewName = view.findViewById(R.id.textViewName);
        viewHolder.textViewProducer = view.findViewById(R.id.textViewProducer);
        viewHolder.textViewPackage = view.findViewById(R.id.textViewPackage);
        viewHolder.textViewForm = view.findViewById(R.id.textViewForm);
        return viewHolder;
    };

    private final String searchText;

    SearchDrugAdapter(Context context, List<Drug> drugs, String searchText) {
        super(context, resource, drugs, VIEW_HOLDER_PROVIDER);
        this.searchText = searchText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Drug drug = getItem(position);
        ViewWithHolder viewWithHolder = getView(convertView, parent);
        ViewHolder viewHolder = (ViewHolder) viewWithHolder.getHolder();

        String productName = drug.getName();

        if (productName.toLowerCase().startsWith(searchText.toLowerCase())) {

            String sourceString = "<b>" + capitalize(searchText) + "</b>" + StringUtils.removeIgnoreCase(productName, searchText);
            viewHolder.textViewName.setText(Html.fromHtml(sourceString));

        } else {
            viewHolder.textViewName.setText(productName);
        }

        viewHolder.textViewProducer.setText(drug.getProducer());
        viewHolder.textViewPackage.setText(drug.getPack());
        viewHolder.textViewForm.setText(drug.getForm());

        return viewWithHolder.getView();
    }
}
