package local.tomo.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.model.Medicament;
import local.tomo.login.model.MedicamentDb;
import local.tomo.login.network.InternetActivity;
import local.tomo.login.network.MedicamentSaveRequest;
import local.tomo.login.network.RestIntefrace;
//import retrofit.Callback;
//import retrofit.RestAdapter;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMedicamentActivity extends InternetActivity {//implements Callback<Medicament>{

    EditText editTextAddMedicament;
    EditText editTextAddMedicamentName;
    EditText editTextAddMedicamentProducer;
    EditText editTextAddMedicamentKind;
    EditText editTextAddMedicamentPrice;

    Button buttonAddMedicamentSave;

    DatePicker datePickerAddMedicament;

    String searchText;

    //private MedicamentDAO medicamentDAO;
    //private MedicamentDbDAO medicamentDbDAO;

    private ListView listView ;
    private MedicamentDbAdapter medicamentDbAdapter;
    List<MedicamentDb> medicamentDbs;

    Calendar calendar = Calendar.getInstance();
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicament);
        databaseHandler = new DatabaseHandler(getApplicationContext(), null, null, 1);
        //medicamentDAO = new MedicamentDAO(this);
        //medicamentDbDAO = new MedicamentDbDAO(this);

        editTextAddMedicament = (EditText) findViewById(R.id.editTextAddMedicament);
        listView = (ListView) findViewById(R.id.listViewAddMedicament);
        editTextAddMedicamentName = (EditText) findViewById(R.id.editTextAddMedicamentName);
        editTextAddMedicamentProducer = (EditText) findViewById(R.id.editTextAddMedicamentProducer);
        editTextAddMedicamentKind = (EditText) findViewById(R.id.editTextAddMedicamentKind);
        editTextAddMedicamentPrice = (EditText) findViewById(R.id.editTextAddMedicamentPrice);

        datePickerAddMedicament = (DatePicker) findViewById(R.id.datePickerAddMedicament);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = 1;
        datePickerAddMedicament.updateDate(year, month, day);

        buttonAddMedicamentSave = (Button) findViewById(R.id.buttonAddMedicamentSave);


        medicamentDbs = new ArrayList<MedicamentDb>();
        medicamentDbAdapter = null;

        editTextAddMedicament.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setVisibility(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchText = s.toString();
                if(searchText.length() >= 3) {
                    //medicamentDbs = medicamentDbDAO.search(searchText);
                    //medicamentDbs = databaseHandler.getMedicamentDbDAO().search(searchText);

                    medicamentDbs = databaseHandler.searchMedicamentsDb(searchText);
                    medicamentDbAdapter = new MedicamentDbAdapter(getApplicationContext(), R.layout.add_medicament_list_row, (ArrayList<MedicamentDb>) medicamentDbs);
                    listView.setAdapter(medicamentDbAdapter);
                }
                else listView.setAdapter(null);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextAddMedicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(false);
                listView.setAdapter(medicamentDbAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final MedicamentDb medicamentDb = (MedicamentDb) parent.getItemAtPosition(position);
                listView.setAdapter(null);
                editTextAddMedicamentName.setText(medicamentDb.getProductName());
                editTextAddMedicamentProducer.setText(medicamentDb.getProducer());
                editTextAddMedicamentKind.setText(medicamentDb.getPack());
                editTextAddMedicamentPrice.setText(Double.toString(medicamentDb.getPrice()));
                setVisibility(true);
                buttonAddMedicamentSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int day = datePickerAddMedicament.getDayOfMonth();
                        int month = datePickerAddMedicament.getMonth();
                        int year = datePickerAddMedicament.getYear();
                        calendar.set(year,month,day);
                        Date date = calendar.getTime();
                        Medicament medicament = new Medicament(medicamentDb);
                        medicament.setDateFormatExpiration(date);
                        //medicamentDAO.add(medicament);
                        //databaseHandler.getMedicamentDAO().add(medicament);
                        databaseHandler.addMedicament(medicamentDb, date);
                        medicament.getDateExpirationYearMonth().setYear(year);
                        medicament.getDateExpirationYearMonth().setMonthId(month);
                        Log.d("tomo", "przed wyslaniem1123");
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://83.19.31.10:8080")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);
                        restIntefrace.saveMedicament(medicament);
                        //RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://212.244.79.82:8080").build();
                        //RestIntefrace restIntefrace = restAdapter.create(RestIntefrace.class);

                        //restIntefrace.saveMedicament(medicament);

/*
                        MedicamentSaveRequest medicamentSaveRequest = new MedicamentSaveRequest(medicament);
                        //spiceManager.getFromCacheAndLoadFromNetworkIfExpired(medicamentSaveRequest, null,null);
                        spiceManager.execute(medicamentSaveRequest, new RequestListener<Medicament>() {
                            @Override
                            public void onRequestFailure(SpiceException spiceException) {
                                Log.d("tomo", "Nie udało sie wyslac");
                            }

                            @Override
                            public void onRequestSuccess(Medicament medicament) {
                                Log.d("tomo", "wyslano");
                            }
                        });
*/
                        finish();
                    }
                });


            }
        });


    }



    public void setVisibility(boolean visibility) {
        if (visibility) {
            editTextAddMedicamentName.setVisibility(EditText.VISIBLE);
            editTextAddMedicamentProducer.setVisibility(EditText.VISIBLE);
            editTextAddMedicamentKind.setVisibility(EditText.VISIBLE);
            editTextAddMedicamentPrice.setVisibility(EditText.VISIBLE);
            buttonAddMedicamentSave.setVisibility(Button.VISIBLE);
            datePickerAddMedicament.setVisibility(DatePicker.VISIBLE);
        }
        else {
            editTextAddMedicamentName.setVisibility(EditText.INVISIBLE);
            editTextAddMedicamentProducer.setVisibility(EditText.INVISIBLE);
            editTextAddMedicamentKind.setVisibility(EditText.INVISIBLE);
            editTextAddMedicamentPrice.setVisibility(EditText.INVISIBLE);
            buttonAddMedicamentSave.setVisibility(Button.INVISIBLE);
            datePickerAddMedicament.setVisibility(DatePicker.INVISIBLE);
        }

    }
}
