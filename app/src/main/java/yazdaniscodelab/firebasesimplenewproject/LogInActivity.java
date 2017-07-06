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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextemail;
    private EditText editTextpassword;
    private Button signin;
    private TextView signup;
    private TextView forgetpassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        firebaseAuth=FirebaseAuth.getInstance();



        if (firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        editTextemail=(EditText)findViewById(R.id.edittext_email);
        editTextpassword=(EditText)findViewById(R.id.edittext_password);
        signin=(Button)findViewById(R.id.login_xml);
        signup=(TextView)findViewById(R.id.signuphere_xml);
        forgetpassword=(TextView)findViewById(R.id.forgetpassword_xml);
        progressDialog=new ProgressDialog(this);


        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);

    }


    private void signindetails(){

        String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Input email Address..",Toast.LENGTH_LONG).show();
            return;
        }


        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Input password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Login Processing");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){



                            Toast.makeText(getApplicationContext(),"Login Successfully..",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            progressDialog.dismiss();
                        }

                        else {
                            Toast.makeText(getApplicationContext(),"Login Failed..",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }

                    }
                });
    }




    @Override
    public void onClick(View view) {

        if (view==signin){

            signindetails();

        }

        if (view==signup){
            finish();
            startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
        }

        if (view==forgetpassword){
            finish();
            //startActivity(new Intent(getApplicationContext(),ResetPasswordActivity.class));
        }

    }
}
