package local.tomo.medi.medicament;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import local.tomo.medi.MainActivity;
import local.tomo.medi.R;
import local.tomo.medi.json.MedicamentExclusion;
import local.tomo.medi.model.Months;
import local.tomo.medi.network.RestIntefrace;
import local.tomo.medi.network.RetrofitBuilder;
import local.tomo.medi.ormlite.DatabaseHelper;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.Medicament;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddMedicamentActivity extends Activity {

    private final List<String> months = Months.getMonths();

    private DatabaseHelper databaseHelper;

    @BindView(R.id.textViewInfo)
    TextView textViewInfo;
    @BindView(R.id.editTextSearch)
    EditText editTextSearch;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewProducer)
    TextView textViewProducer;
    @BindView(R.id.textViewKind)
    TextView textViewKind;
    @BindView(R.id.textViewPack)
    TextView textViewPack;
    @BindView(R.id.textViewPrice)
    TextView textViewPrice;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.textViewMonth)
    TextView textViewMonth;
    @BindView(R.id.textViewYear)
    TextView textViewYear;
    @BindView(R.id.imageButtonMonthUp)
    ImageButton imageButtonMonthUp;
    @BindView(R.id.imageButtonMonthDown)
    ImageButton imageButtonMonthDown;
    @BindView(R.id.imageButtonYearUp)
    ImageButton imageButtonYearUp;
    @BindView(R.id.imageButtonYearDown)
    ImageButton imageButtonYearDown;
    @BindView(R.id.buttonSave)
    Button buttonSave;
    @BindView(R.id.list)
    ListView listView;

    private int month;
    private int year;
    private AddMedicamentAdapter addMedicamentAdapter;
    private List<DbMedicament> dbMedicaments;
    private Calendar calendar = Calendar.getInstance();
    private DbMedicament dbMedicament;
    private Medicament medicament;
    private long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicament);
        ButterKnife.bind(this);

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

        calendar.setTime(new Date());
        date = calendar.getTimeInMillis();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        String monthString = months.get(month);

        textViewMonth.setText(monthString);
        textViewYear.setText(year+"");

        dbMedicaments = new ArrayList<>();
    }
    @OnTextChanged(R.id.editTextSearch)
    @SneakyThrows
    void search(CharSequence s, int start, int before, int count){
        String text = textViewInfo.getText().toString();
        String searchText = s.toString();
        if(searchText.length() >= 3) {
            Dao<DbMedicament, Integer> medicamentDbDao = getHelper().getMedicamentDbDao();
            QueryBuilder<DbMedicament, Integer> queryBuilder = medicamentDbDao.queryBuilder();
            queryBuilder.where().like("productName", "%"+ searchText +"%").or().like("ean", "%"+ searchText +"%");
            PreparedQuery<DbMedicament> prepare = queryBuilder.prepare();
            dbMedicaments = medicamentDbDao.query(prepare);

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
        textViewInfo.setText(getString(R.string.medicamentSearchInfo, text, dbMedicaments.size()));
    }

    @OnClick(R.id.editTextSearch)
    void backToSearch() {
        setVisibility(false);
        listView.setAdapter(addMedicamentAdapter);
    }

    @OnItemClick(R.id.list)
    void chooseMedicament(AdapterView<?> parent, View view, int position, long id) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        dbMedicament = (DbMedicament) parent.getItemAtPosition(position);
        listView.setAdapter(null);
        textViewName.setText(dbMedicament.getProductName());

        textViewProducer.setText(getString(R.string.producent, dbMedicament.getProducer()));
        textViewPack.setText(getString(R.string.pack, dbMedicament.getPack()));
        textViewKind.setText(getString(R.string.kind, dbMedicament.getForm()));
        textViewPrice.setText(getString(R.string.price, Double.toString(dbMedicament.getPrice())));
        setVisibility(true);
    }

    @OnClick(R.id.buttonSave)
    @SneakyThrows
    void save() {
        medicament = new Medicament(dbMedicament);
        medicament.setDate(date);
        final Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
        medicamentDao.create(medicament);
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new MedicamentExclusion())
                .create();
        RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace(gson);
        Call<Medicament> call = restIntefrace.saveMedicament(medicament);
        call.enqueue(new Callback<Medicament>() {
            @Override
            @SneakyThrows
            public void onResponse(Call<Medicament> call, Response<Medicament> response) {
                Dao<Medicament, Integer> medicamentDao = getHelper().getMedicamentDao();
                medicamentDao.update(response.body());
                Toast.makeText(getApplicationContext(), getString(R.string.sendMedicamentSuccess, medicament.getName()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Medicament> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.sendMedicamentFail, medicament.getName()), Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.imageButtonMonthDown)
    void monthDown() {
        String s = textViewMonth.getText().toString();
        int i = months.indexOf(s);
        if(i == 0) i = 11;
        else i--;
        textViewMonth.setText(months.get(i));
        month = i;
        date = createDate();
    }

    @OnClick(R.id.imageButtonMonthUp)
    void monthUp() {
        String s = textViewMonth.getText().toString();
        int i = months.indexOf(s);
        if(i == 11) i = 0;
        else i++;
        textViewMonth.setText(months.get(i));
        month = i;
        date = createDate();
    }

    @OnClick(R.id.imageButtonYearUp)
    void yearUp() {
        String s = textViewYear.getText().toString();
        int i = Integer.parseInt(s);
        i++;
        textViewYear.setText(i+"");
        year = i;
        date = createDate();
    }

    @OnClick(R.id.imageButtonYearDown)
    void yearDown() {
        String s = textViewYear.getText().toString();
        int i = Integer.parseInt(s);
        i--;
        textViewYear.setText(i+"");
        year = i;
        date = createDate();
    }

    private long createDate() {
        calendar.set(year, month, 1);
        return calendar.getTimeInMillis();
    }


    public void setVisibility(boolean visibility) {
        if (visibility) {
            textViewName.setVisibility(View.VISIBLE);
            textViewProducer.setVisibility(View.VISIBLE);
            textViewKind.setVisibility(View.VISIBLE);
            textViewPack.setVisibility(View.VISIBLE);
            textViewPrice.setVisibility(View.VISIBLE);
            buttonSave.setVisibility(View.VISIBLE);
            linearLayout1.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            linearLayout3.setVisibility(View.VISIBLE);
        }
        else {
            textViewName.setVisibility(View.INVISIBLE);
            textViewProducer.setVisibility(View.INVISIBLE);
            textViewKind.setVisibility(View.INVISIBLE);
            textViewPack.setVisibility(View.INVISIBLE);
            textViewPrice.setVisibility(View.INVISIBLE);
            buttonSave.setVisibility(View.INVISIBLE);
            linearLayout1.setVisibility(View.INVISIBLE);
            linearLayout2.setVisibility(View.INVISIBLE);
            linearLayout3.setVisibility(View.INVISIBLE);
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
