package local.tomo.medi.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.File;

/**
 * Created by tomo on 2016-08-04.
 */
public class FilesAdapter extends BaseAdapter {

    private List<File> files;
    private final LayoutInflater inflater;

    public FilesAdapter(Context context, ForeignCollection<File> files) {
        this.inflater = LayoutInflater.from(context);
        this.files = new ArrayList<>(files);
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public File getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return files.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ImageView squareImageViewPicture;
        TextView textViewName;

        if (v == null) {
            v = inflater.inflate(R.layout.grid_item, parent, false);
        }
        squareImageViewPicture = (ImageView) v.findViewById(R.id.squareImageViewPicture);
        textViewName = (TextView) v.findViewById(R.id.textViewName);

        File file = getItem(position);
        byte[] bytes = file.getFile();
        if(bytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            squareImageViewPicture.setImageBitmap(bitmap);
        }
        textViewName.setText(file.getName());
        return v;
    }
}
