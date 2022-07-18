package csie.aplicatielicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

import csie.aplicatielicenta.Models.User;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email, password, firstName, lastName;
    MaterialButton register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String registerEmail = email.getText().toString();
                String registerFirstName = firstName.getText().toString();
                String registerLastName = lastName.getText().toString();
                String registerPassword = password.getText().toString();
                if (registerFirstName.isEmpty()){
                    firstName.setError(("First name is required!"));
                    firstName.requestFocus();
                }else
                if (registerLastName.isEmpty()){
                    lastName.setError(("Last name is required!"));
                    lastName.requestFocus();
                }else
                if (registerEmail.isEmpty()){
                    email.setError(("Email is required!"));
                    email.requestFocus();
                } else
                if (registerPassword.isEmpty()){
                    password.setError(("Password is required!"));
                    password.requestFocus();
                }else
                if(!Patterns.EMAIL_ADDRESS.matcher(registerEmail).matches()){
                    email.setError("Please provide a valid email!");
                    email.requestFocus();
                }
                else {
                    createAccount(registerFirstName, registerLastName, registerEmail, registerPassword);
                }
            }
        });



//        if(!MemoryData.getData(this).isEmpty()){
//            Intent intent = new Intent(RegisterActivity.this, MessagesActivity.class);
//            intent.putExtra("id", MemoryData.getData(this));
//            intent.putExtra("name", MemoryData.getData(this));
//            intent.putExtra("email", MemoryData.getData(this));
//        }

    }

    private void createAccount(String firstName, String lastName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(firstName, lastName, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has been registered!", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "User has not been registered!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(RegisterActivity.this, "User has not been registered!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}