package local.tomo.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import local.tomo.login.database.DatabaseHandler;
import local.tomo.login.model.Medicament;
import local.tomo.login.model.MedicamentDb;
import local.tomo.login.model.Months;
import local.tomo.login.network.Synchronize;



public class AddMedicamentActivity extends Activity {

    private final List<String> months = Months.getMonths();

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
    private Medicament medicament;
    private long date;


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
        date = calendar.getTimeInMillis();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        String monthString = months.get(month);

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
                        medicament = new Medicament(medicamentDb);
                        medicament.setDate(date);
                        databaseHandler.addMedicament(medicament);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        Synchronize synchronize = new Synchronize(getApplicationContext());
                        synchronize.synchronizeAllMedicaments();
                        Log.d("tomo", "po synchro");
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
