package local.tomo.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    Button buttonRegister;
    EditText editTextRegisterUserName;
    EditText editTextRegisterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextRegisterUserName = (EditText) findViewById(R.id.editTextRegisterUserName);
        editTextRegisterPassword = (EditText) findViewById(R.id.editTextRegisterPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);


    }

    public void registerClick(View view) {

    }
}
