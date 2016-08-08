package local.tomo.medi.file;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.File;

public class FilesAdapter extends BaseAdapter {

    @BindView(R.id.squareImageViewPicture)
    ImageView squareImageViewPicture;
    @BindView(R.id.textViewName)
    TextView textViewName;

    private List<File> files;
    private Context context;

    public FilesAdapter(Context context, ForeignCollection<File> files) {
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
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            ButterKnife.bind(this, view);
        }

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

        return view;
    }

    public Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "", null);
        return Uri.parse(path);
    }


}
