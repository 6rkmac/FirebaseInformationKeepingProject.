package yazdaniscodelab.firebasesimplenewproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edittextemail;
    private EditText edittextpassword;
    private Button signup;
    private TextView signin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }


        edittextemail=(EditText)findViewById(R.id.edittext_email);
        edittextpassword=(EditText)findViewById(R.id.edittext_password);
        signup=(Button)findViewById(R.id.registerbutton_xml);
        signin=(TextView)findViewById(R.id.signinhere_xml);
        dialog=new ProgressDialog(this);
        signup.setOnClickListener(this);
        signin.setOnClickListener(this);

    }


    private void signupdetails() {

        final String email=edittextemail.getText().toString().trim();
        final String password=edittextpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Input Email Address",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Input Password",Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.setMessage("Registration Processing...");
        dialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Registration Successfully",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),LogInActivity.class));
                            dialog.dismiss();

                        }

                    }
                });



    }


    @Override
    public void onClick(View view) {

        if (view==signup){

            signupdetails();

        }

        if (view==signin){
            finish();
            startActivity(new Intent(getApplicationContext(),LogInActivity.class));
        }

    }
}
