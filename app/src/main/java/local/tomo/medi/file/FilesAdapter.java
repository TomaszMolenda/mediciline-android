package local.tomo.medi.file;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.ForeignCollection;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.File;

/**
 * Created by tomo on 2016-08-04.
 */
public class FilesAdapter extends BaseAdapter {

    public static final String TAG = "meditomo";

    private List<File> files;
    private final LayoutInflater inflater;
    private Context context;

    public FilesAdapter(Context context, ForeignCollection<File> files) {
        this.inflater = LayoutInflater.from(context);
        this.files = new ArrayList<>(files);
        this.context = context;
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
        final byte[] bytes = file.getFile();
        if(bytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            squareImageViewPicture.setImageBitmap(bitmap);
        }
        textViewName.setText(file.getName());
        squareImageViewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);
                Uri imageUri = getImageUri(context, bitmap);
                intent.setDataAndType(imageUri, "image/*");
                context.startActivity(intent);
            }
        });

        return v;
    }

    public Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


}
