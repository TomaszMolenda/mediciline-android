package local.tomo.medi.activity.drug.list;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListAdapter;

import local.tomo.medi.activity.SearchActivity;

public abstract class HideKeyboardActivity<E extends ListAdapter> extends SearchActivity<E> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
}
