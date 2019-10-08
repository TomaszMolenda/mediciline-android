package local.tomo.medi.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Optional;

import butterknife.ButterKnife;

public class ScrollArrayAdapter<E> extends ArrayAdapter<E> {

    private final int resource;

    ScrollArrayAdapter(Context context, int resource, List<E> drugs) {
        super(context, resource, drugs);
        this.resource = resource;
    }

    View prepareView(View convertView) {

        return Optional.ofNullable(convertView)
                .orElseGet(this::createNewView);
    }

    private View createNewView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(resource, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

}
