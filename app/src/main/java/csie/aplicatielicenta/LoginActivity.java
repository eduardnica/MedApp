package csie.aplicatielicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    TextView register, forgotPass;
    MaterialButton btnLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        btnLogin = (MaterialButton) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }

    private void userLogin(){
        String loginEmail = email.getText().toString();
        String loginPassowrd = password.getText().toString();
        if (loginEmail.isEmpty()){
            email.setError(("Email is required!"));
            email.requestFocus();
        } else
        if (loginPassowrd.isEmpty()){
            password.setError(("Password is required!"));
            password.requestFocus();
        }else
        if(!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()){
            email.setError("Please provide a valid email!");
            email.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(loginEmail, loginPassowrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent firstActivity = new Intent(LoginActivity.this, FirstActivity.class);
                        Intent chat = new Intent(LoginActivity.this, MessagesActivity.class);

                        if(!loginEmail.equals("medic@gmail.com")){
                            startActivity(firstActivity);
                        }else{
                            startActivity(chat);
                        }


                    }else {
                        Toast.makeText(LoginActivity.this, "Failed to login! Check your credentials!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}