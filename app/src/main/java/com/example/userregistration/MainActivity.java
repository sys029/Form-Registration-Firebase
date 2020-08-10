package com.example.userregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.service.autofill.Validator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.userregistration.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtName,edtPassword,edtConfirm,edtPlace;
    MaterialSpinner edtGender;
    Button signin;
    AwesomeValidation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName=(EditText)findViewById(R.id.editName);
        edtPassword=(EditText)findViewById(R.id.editPassword);
        edtConfirm=(EditText)findViewById(R.id.editConfirm);
        edtPlace=(EditText)findViewById(R.id.editPlace);
        edtGender=(MaterialSpinner) findViewById(R.id.editGender);
        edtGender.setItems("Male","Female","Dont specify");


        signin=(Button)findViewById(R.id.btSignUp);

        validation=new AwesomeValidation(ValidationStyle.BASIC);

        validation.addValidation(this,R.id.editName,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        validation.addValidation(this,R.id.editPlace,
                RegexTemplate.NOT_EMPTY,R.string.invalid_place);
        validation.addValidation(this,R.id.editGender,
                RegexTemplate.NOT_EMPTY,R.string.invalid_gender);
        validation.addValidation(this,R.id.editPassword
                ,RegexTemplate.NOT_EMPTY,R.string.invalid_password);
        validation.addValidation(this,R.id.editConfirm
                ,R.id.editPassword,R.string.invalid_confirm_password);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("Users");



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation.validate()){
                    final ProgressDialog mDialog=new ProgressDialog(MainActivity.this);
                    mDialog.setMessage("Please Wait.");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edtName.getText().toString()).exists()){

                                mDialog.dismiss();
                            }else {
                                mDialog.dismiss();
                                Date today = new Date();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                                String dateToStr = format.format(today);
                                Users user=new Users(edtPassword.getText().toString(),edtPlace.getText().toString(),String.valueOf(edtGender.getText()),dateToStr);
                                table_user.child(edtName.getText().toString()).setValue(user);
                                Toast.makeText(MainActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                edtName.getText().clear();
                                edtConfirm.getText().clear();
                                edtPassword.getText().clear();
                                edtPlace.getText().clear();
                              //  edtGender.getText().clear();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    Toast.makeText(MainActivity.this, "Validation failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
