package local.tomo.medi.activity;

import android.view.View;

public class ViewWithHolder<H> {

    private final View view;
    private final H holder;

    public ViewWithHolder(View view, H holder) {
        this.view = view;
        this.holder = holder;
    }

    public View getView() {
        return view;
    }

    public H getHolder() {
        return holder;
    }
}
