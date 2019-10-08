package local.tomo.medi;

import android.content.Intent;
import android.os.Bundle;

import local.tomo.medi.medicament.DrugActivity;
import local.tomo.medi.ormlite.DatabaseDataCreator;
import lombok.SneakyThrows;

public class MainActivity extends DatabaseAccessActivity {

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseDataCreator databaseDataCreator = new DatabaseDataCreator(getResources(), getHelper());
        databaseDataCreator.execute();

        Intent intent = new Intent(this, DrugActivity.class);
        startActivity(intent);
        finish();
    }
}
