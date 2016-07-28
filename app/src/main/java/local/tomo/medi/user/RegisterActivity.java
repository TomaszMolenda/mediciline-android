package local.tomo.medi.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import local.tomo.medi.R;
import local.tomo.medi.network.RestIntefrace;
import local.tomo.medi.network.RetrofitBuilder;
import local.tomo.medi.network.Utills;
import local.tomo.medi.ormlite.data.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "meditomo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextConfirmEmail = (EditText) findViewById(R.id.editTextConfirmEmail);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        final EditText editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);

        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String confirmEmail = editTextConfirmEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if(!email.equals(confirmEmail))
                    Toast.makeText(getApplicationContext(), "Emaile różnią się od siebie", Toast.LENGTH_SHORT).show();
                if(!password.equals(confirmPassword))
                    Toast.makeText(getApplicationContext(), "Hasła różnią się od siebie", Toast.LENGTH_SHORT).show();
                if(password.length() < 4)
                    Toast.makeText(getApplicationContext(), "Hasło musi mieć min 4 znaki", Toast.LENGTH_SHORT).show();
                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher matcher = pattern.matcher(email);
                if(!matcher.matches())
                    Toast.makeText(getApplicationContext(), "Email jest nie poprawny", Toast.LENGTH_SHORT).show();
                User user = new User(name, email, confirmEmail, password, confirmPassword);
                register(user);
            }
        });

    }

    private void register(User user) {
        RestIntefrace restIntefrace = RetrofitBuilder.getRestIntefrace();
        Call<User> call = restIntefrace.register(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponse: " + response.message());
                try {
                    Log.d(TAG, "onResponse: " + response.errorBody().string());
                    String s = Utills.readJsonError(response.errorBody().string());
                    Log.d(TAG, "onResponse: " + s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Konto założone. Potwierdź rejestracje w emailu", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

}
