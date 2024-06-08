package com.example.abdou.gazadonate;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    private EditText datePickerEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DatabaseHelper(this);

        datePickerEditText = findViewById(R.id.datePicker);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.pass);
        confirmPasswordEditText = findViewById(R.id.repass);

        datePickerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Button signupButton = findViewById(R.id.signupbtn);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String birthDate = datePickerEditText.getText().toString().trim();

        // Email validation regex pattern
        Pattern emailPattern = Pattern.compile("^\\w+@[a-zA-Z_]+(?:\\.[a-zA-Z_]+)*\\.[a-zA-Z]{2,3}$");
        Matcher emailMatcher = emailPattern.matcher(email);

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || birthDate.isEmpty()) {
            // Handle empty fields
            Toast.makeText(signup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (!emailMatcher.matches()) {
            // Handle invalid email format
            Toast.makeText(signup.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            // Handle password length less than 6 characters
            Toast.makeText(signup.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            // Handle password mismatch
            Toast.makeText(signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            // All fields are filled and passwords match
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Check if the email already exists in the database
            Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});
            if (cursor.getCount() > 0) {
                // Email already exists, show error message
                Toast.makeText(signup.this, "An account with this email already exists", Toast.LENGTH_SHORT).show();
            } else {
                // Email does not exist, proceed with signup
                ContentValues values = new ContentValues();
                values.put("email", email);
                values.put("password", password);
                values.put("birth_date", birthDate);

                long newRowId = db.insert("users", null, values);
                db.close();

                if (newRowId != -1) {
                    // Signup success, set login state to true and navigate to news activity
                    splash.setLoginState(signup.this, true); // Set login state using splash activity's method
                    Toast.makeText(signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup.this, news.class);
                    startActivity(intent);
                    finish(); // Optional: finish the current activity to prevent going back to signup screen
                } else {
                    // Handle insertion failure
                    Toast.makeText(signup.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(signup.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        datePickerEditText.setText(selectedDate);
                    }
                }, year, month, day);

        calendar.set(2010, Calendar.DECEMBER, 31);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

}
