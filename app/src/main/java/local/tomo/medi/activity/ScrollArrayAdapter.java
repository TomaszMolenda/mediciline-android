package local.tomo.medi.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ScrollArrayAdapter<E, H> extends ArrayAdapter<E> {

    private final int resource;
    private final Function<View, H> holderCreator;

    public ScrollArrayAdapter(Context context, int resource, List<E> list, Function<View, H> holderCreator) {
        super(context, resource, list);
        this.resource = resource;
        this.holderCreator = holderCreator;
    }

    protected ViewWithHolder getView(View convertView, ViewGroup parent) {

        H viewHolder;
        final View view;

        if (Objects.isNull(convertView)) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = holderCreator.apply(convertView);
            view = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (H) convertView.getTag();
            view = convertView;
        }

        return new ViewWithHolder<>(view, viewHolder);
    }
}
