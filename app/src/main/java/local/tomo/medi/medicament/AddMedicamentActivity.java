package local.tomo.medi.medicament;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import local.tomo.medi.MainActivity;
import local.tomo.medi.R;
import local.tomo.medi.json.MedicamentExclusion;
import local.tomo.medi.model.Months;
import local.tomo.medi.network.RestIntefrace;
import local.tomo.medi.network.RetrofitBuilder;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.Medicament;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddMedicamentActivity extends Activity {

    private DatabaseHelper databaseHelper = null;

    private final List<String> months = Months.getMonths();

    TextView textViewAddMedicamentInfo;

    EditText editTextSearch;
    EditText editTextName;
    EditText editTextProducer;
    EditText editTextKind;
    EditText editTextPack;
    EditText editTextPrice;

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

    Button buttonSave;

    String searchText;

    private ListView listView ;
    private AddMedicamentAdapter addMedicamentAdapter;
    List<DbMedicament> dbMedicaments;

    Calendar calendar = Calendar.getInstance();
    private DbMedicament dbMedicament;
    private Medicament medicament;
    private long date;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicament);

        if(savedInstanceState == null) {
            Bundle b = getIntent().getExtras();
            if(b != null) {
                if(b.getBoolean("scan")) {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                    intentIntegrator.setPrompt(" ");
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.setOrientationLocked(true);
                    intentIntegrator.initiateScan();
                }
            }
        }

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);

        listView = (ListView) findViewById(R.id.listView);

        textViewAddMedicamentInfo = (TextView) findViewById(R.id.textViewInfo);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextProducer = (EditText) findViewById(R.id.editTextProducer);
        editTextKind = (EditText) findViewById(R.id.editTextKind);
        editTextPack = (EditText) findViewById(R.id.editTextPack);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);

        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);

        textViewMonth = (TextView) findViewById(R.id.textViewMonth);
        textViewYear = (TextView) findViewById(R.id.textViewYear);

        imageButtonMonthDown = (ImageButton) findViewById(R.id.imageButtonMonthDown);
        imageButtonMonthUp = (ImageButton) findViewById(R.id.imageButtonMonthUp);
        imageButtonYearDown = (ImageButton) findViewById(R.id.imageButtonYearDown);
        imageButtonYearUp = (ImageButton) findViewById(R.id.imageButtonYearUp);

        buttonSave = (Button) findViewById(R.id.buttonSave);

        calendar.setTime(new Date());
        date = calendar.getTimeInMillis();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        String monthString = months.get(month);

        textViewMonth.setText(monthString);
        textViewYear.setText(year+"");




        dbMedicaments = new ArrayList<DbMedicament>();
        addMedicamentAdapter = null;

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setVisibility(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = textViewAddMedicamentInfo.getText().toString();
                searchText = s.toString();
                if(searchText.length() >= 3) {
                    try {
                        Dao<DbMedicament, Integer> medicamentDbDao = getHelper().getMedicamentDbDao();
                        QueryBuilder<DbMedicament, Integer> queryBuilder = medicamentDbDao.queryBuilder();
                        queryBuilder.where().like("productName", "%"+searchText+"%").or().like("ean", "%"+searchText+"%");
                        PreparedQuery<DbMedicament> prepare = queryBuilder.prepare();
                        dbMedicaments = medicamentDbDao.query(prepare);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    addMedicamentAdapter = new AddMedicamentAdapter(getApplicationContext(), R.layout.adapter_add_medicament_list_row, (ArrayList<DbMedicament>) dbMedicaments);
                    listView.setAdapter(addMedicamentAdapter);
                }
                else {
                    listView.setAdapter(null);
                    dbMedicaments.clear();
                }
                int i = text.indexOf("(");
                if(i != -1)
                    text = text.substring(0, i - 1);
                textViewAddMedicamentInfo.setText(text + " (znaleziono " + dbMedicaments.size() + " szt.)");

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(false);
                listView.setAdapter(addMedicamentAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                dbMedicament = (DbMedicament) parent.getItemAtPosition(position);
                listView.setAdapter(null);
                editTextName.setText(dbMedicament.getProductName());
                editTextProducer.setText("Producent: " + dbMedicament.getProducer());
                editTextPack.setText("Opakowanie: " + dbMedicament.getPack());
                editTextKind.setText("Rodzaj: " + dbMedicament.getForm());
                editTextPrice.setText(Double.toString(dbMedicament.getPrice()));
                setVisibility(true);
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbMedicament.setPrice(Double.parseDouble(editTextPrice.getText().toString()));
                        medicament = new Medicament(dbMedicament);
                        medicament.setDate(date);
                        try {
                            Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
                            medicamentDao.create(medicament);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Gson gson = new GsonBuilder()
                                .setExclusionStrategies(new MedicamentExclusion())
                                .create();
                        RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace(gson);
                        Call<Medicament> call = restIntefrace.saveMedicament(medicament);
                        call.enqueue(new Callback<Medicament>() {
                            @Override
                            public void onResponse(Call<Medicament> call, Response<Medicament> response) {
                                try {
                                    Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
                                    medicamentDao.update(response.body());
                                    Toast.makeText(getApplicationContext(), "Lek  " + medicament.getName() + " wysłano na serwer", Toast.LENGTH_SHORT).show();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<Medicament> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Błąd wysłania leku  " + medicament.getName() + "  na serwer", Toast.LENGTH_SHORT).show();
                            }
                        });
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
                int i = months.indexOf(s);
                if(i == 0) i = 11;
                else i--;
                textViewMonth.setText(months.get(i));
                month = i;
                date = createDate();
            }
        });
        imageButtonMonthUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textViewMonth.getText().toString();
                int i = months.indexOf(s);
                if(i == 11) i = 0;
                else i++;
                textViewMonth.setText(months.get(i));
                month = i;
                date = createDate();
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
                date = createDate();
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
                date = createDate();
            }
        });

    }

    private long createDate() {
        calendar.set(year, month, 1);
        return calendar.getTimeInMillis();
    }


    public void setVisibility(boolean visibility) {
        if (visibility) {
            editTextName.setVisibility(EditText.VISIBLE);
            editTextProducer.setVisibility(EditText.VISIBLE);
            editTextKind.setVisibility(EditText.VISIBLE);
            editTextPack.setVisibility(EditText.VISIBLE);
            editTextPrice.setVisibility(EditText.VISIBLE);
            buttonSave.setVisibility(Button.VISIBLE);
            linearLayout1.setVisibility(LinearLayout.VISIBLE);
            linearLayout2.setVisibility(LinearLayout.VISIBLE);
            linearLayout3.setVisibility(LinearLayout.VISIBLE);
        }
        else {
            editTextName.setVisibility(EditText.INVISIBLE);
            editTextProducer.setVisibility(EditText.INVISIBLE);
            editTextKind.setVisibility(EditText.INVISIBLE);
            editTextPack.setVisibility(EditText.INVISIBLE);
            editTextPrice.setVisibility(EditText.INVISIBLE);
            buttonSave.setVisibility(Button.INVISIBLE);
            linearLayout1.setVisibility(LinearLayout.INVISIBLE);
            linearLayout2.setVisibility(LinearLayout.INVISIBLE);
            linearLayout3.setVisibility(LinearLayout.INVISIBLE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() != null) {
            String barCode = result.getContents().toString();
            editTextSearch.setText(barCode);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
