package local.tomo.login;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.json.exclusion.MedicamentExclusion;
import local.tomo.login.model.Medicament;
import local.tomo.login.model.MedicamentDb;
import local.tomo.login.network.RestIntefrace;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class AddMedicamentActivity extends Activity {

    private final String[] months = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
            "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};
    private final List<String> monthsList = Arrays.asList(months);

    TextView textViewAddMedicamentInfo;

    EditText editTextAddMedicament;
    EditText editTextAddMedicamentName;
    EditText editTextAddMedicamentProducer;
    EditText editTextAddMedicamentKind;
    EditText editTextAddMedicamentPrice;

    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;

    TextView textViewMonth;
    TextView textViewYear;

    ImageButton imageButtonMonthUp;
    ImageButton imageButtonMonthDown;
    ImageButton imageButtonYearUp;
    ImageButton imageButtonYearDown;

    private int month;
    private int year;

    Button buttonAddMedicamentSave;

    String searchText;

    private ListView listView ;
    private AddMedicamentAdapter addMedicamentAdapter;
    List<MedicamentDb> medicamentDbs;

    Calendar calendar = Calendar.getInstance();
    private DatabaseHandler databaseHandler;
    private MedicamentDb medicamentDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicament);

        databaseHandler = new DatabaseHandler(getApplicationContext(), null, null, 1);

        editTextAddMedicament = (EditText) findViewById(R.id.editTextAddMedicament);

        listView = (ListView) findViewById(R.id.listViewAddMedicament);

        textViewAddMedicamentInfo = (TextView) findViewById(R.id.textViewAddMedicamentInfo);

        editTextAddMedicamentName = (EditText) findViewById(R.id.editTextAddMedicamentName);
        editTextAddMedicamentProducer = (EditText) findViewById(R.id.editTextAddMedicamentProducer);
        editTextAddMedicamentKind = (EditText) findViewById(R.id.editTextAddMedicamentKind);
        editTextAddMedicamentPrice = (EditText) findViewById(R.id.editTextAddMedicamentPrice);

        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);

        textViewMonth = (TextView) findViewById(R.id.textViewMonth);
        textViewYear = (TextView) findViewById(R.id.textViewYear);

        imageButtonMonthDown = (ImageButton) findViewById(R.id.imageButtonMonthDown);
        imageButtonMonthUp = (ImageButton) findViewById(R.id.imageButtonMonthUp);
        imageButtonYearDown = (ImageButton) findViewById(R.id.imageButtonYearDown);
        imageButtonYearUp = (ImageButton) findViewById(R.id.imageButtonYearUp);

        buttonAddMedicamentSave = (Button) findViewById(R.id.buttonAddMedicamentSave);

        calendar.setTime(new Date());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        String monthString = months[month];

        textViewMonth.setText(monthString);
        textViewYear.setText(year+"");




        medicamentDbs = new ArrayList<MedicamentDb>();
        addMedicamentAdapter = null;

        editTextAddMedicament.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setVisibility(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = textViewAddMedicamentInfo.getText().toString();
                searchText = s.toString();
                if(searchText.length() >= 3) {
                    medicamentDbs = databaseHandler.searchMedicamentsDb(searchText);


                    addMedicamentAdapter = new AddMedicamentAdapter(getApplicationContext(), R.layout.add_medicament_list_row, (ArrayList<MedicamentDb>) medicamentDbs);
                    listView.setAdapter(addMedicamentAdapter);
                }
                else {
                    listView.setAdapter(null);
                    medicamentDbs.clear();
                }
                int i = text.indexOf("(");
                Log.d("tomo", text + i);
                if(i != -1)
                    text = text.substring(0, i - 1);
                textViewAddMedicamentInfo.setText(text + " (znaleziono " + medicamentDbs.size() + " szt.)");

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextAddMedicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(false);
                listView.setAdapter(addMedicamentAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                medicamentDb = (MedicamentDb) parent.getItemAtPosition(position);
                listView.setAdapter(null);
                editTextAddMedicamentName.setText(medicamentDb.getProductName());
                editTextAddMedicamentProducer.setText("Producent: " + medicamentDb.getProducer());
                editTextAddMedicamentKind.setText("Rodzaj: " + medicamentDb.getPack());
                editTextAddMedicamentPrice.setText(Double.toString(medicamentDb.getPrice()));
                setVisibility(true);
                buttonAddMedicamentSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        int day = datePickerAddMedicament.getDayOfMonth();
//                        int month = datePickerAddMedicament.getMonth();
//                        int year = datePickerAddMedicament.getYear();
//                        calendar.set(year,month,day);
//                        date = calendar.getTime();
                        final Medicament medicament = new Medicament(medicamentDb);
                        //medicament.setDateFormatExpiration(date);
                        medicament.getDateExpirationYearMonth().setYear(year);
                        medicament.getDateExpirationYearMonth().setMonthId(month);

                        Gson gson = new GsonBuilder()
                                .setExclusionStrategies(new MedicamentExclusion())
                                .create();

                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(RestIntefrace.url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(client)
                                .build();
                        RestIntefrace restIntefrace = retrofit.create(RestIntefrace.class);
                        Call<Medicament> call = restIntefrace.saveMedicament(medicament);
                        call.enqueue(new Callback<Medicament>() {
                            @Override
                            public void onResponse(Call<Medicament> call, Response<Medicament> response) {
                                Medicament body = response.body();
                                medicamentDb.setIdServer(body.getId());
                                databaseHandler.setIdServer(body);
                                databaseHandler.addMedicament(medicamentDb, month + 1, year);
                                Toast.makeText(getApplicationContext(), "Wysłano lek  " + medicament.getName() + " na serwer", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Medicament> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Nie udało się wysłać leku  " + medicament.getName() + " na serwer", Toast.LENGTH_LONG).show();
                            }
                        });
                        databaseHandler.addMedicament(medicamentDb, month + 1, year);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });


            }
        });

        imageButtonMonthDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textViewMonth.getText().toString();
                int i = monthsList.indexOf(s);
                if(i == 0) i = 11;
                else i--;
                textViewMonth.setText(monthsList.get(i));
                month = i;
            }
        });
        imageButtonMonthUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textViewMonth.getText().toString();
                int i = monthsList.indexOf(s);
                if(i == 11) i = 0;
                else i++;
                textViewMonth.setText(monthsList.get(i));
                month = i;
            }
        });
        imageButtonYearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textViewYear.getText().toString();
                int i = Integer.parseInt(s);
                i++;
                textViewYear.setText(i+"");
                year = i;
            }
        });
        imageButtonYearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textViewYear.getText().toString();
                int i = Integer.parseInt(s);
                i--;
                textViewYear.setText(i+"");
                year = i;
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
            linearLayout1.setVisibility(LinearLayout.VISIBLE);
            linearLayout2.setVisibility(LinearLayout.VISIBLE);
            linearLayout3.setVisibility(LinearLayout.VISIBLE);
        }
        else {
            editTextAddMedicamentName.setVisibility(EditText.INVISIBLE);
            editTextAddMedicamentProducer.setVisibility(EditText.INVISIBLE);
            editTextAddMedicamentKind.setVisibility(EditText.INVISIBLE);
            editTextAddMedicamentPrice.setVisibility(EditText.INVISIBLE);
            buttonAddMedicamentSave.setVisibility(Button.INVISIBLE);
            linearLayout1.setVisibility(LinearLayout.INVISIBLE);
            linearLayout2.setVisibility(LinearLayout.INVISIBLE);
            linearLayout3.setVisibility(LinearLayout.INVISIBLE);
        }

    }


}
