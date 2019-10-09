package local.tomo.medi;

import android.widget.ListAdapter;

public interface AdapterFactory<E extends ListAdapter> {

    E createAdapter(String searchText);

    void closeDatabaseConnection();
}
