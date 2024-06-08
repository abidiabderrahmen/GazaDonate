package com.example.abdou.gazadonate;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.emaill);
        passwordEditText = findViewById(R.id.passw);

        Button loginButton = findViewById(R.id.loginbtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        TextView changePassTextView = findViewById(R.id.changepass);
        changePassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();
            }
        });

        TextView deleteAccTextView = findViewById(R.id.deleteacc);
        deleteAccTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAccountDialog();
            }
        });
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the title
        builder.setTitle("Change Password");
        // Set up the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogbox, null);
        builder.setView(dialogView);
        EditText dialogEmailEditText = dialogView.findViewById(R.id.dialog_email);
        EditText dialogOldPasswordEditText = dialogView.findViewById(R.id.dialog_old_password);
        EditText dialogNewPasswordEditText = dialogView.findViewById(R.id.dialog_new_password);
        // Set up the buttons
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = dialogEmailEditText.getText().toString().trim();
                String oldPassword = dialogOldPasswordEditText.getText().toString().trim();
                String newPassword = dialogNewPasswordEditText.getText().toString().trim();
                if (validatePassword(email, oldPassword, newPassword)) {
                    Toast.makeText(login.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(login.this, "Invalid email or old password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void showDeleteAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the title
        builder.setTitle("Delete Account");
        // Set up the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_account_dialog, null);
        builder.setView(dialogView);
        EditText dialogEmailEditText = dialogView.findViewById(R.id.dialog_emaill);
        EditText dialogPasswordEditText = dialogView.findViewById(R.id.dialog_passwords);
        // Set up the buttons
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = dialogEmailEditText.getText().toString().trim();
                String password = dialogPasswordEditText.getText().toString().trim();
                if (validateEmailAndPassword(email, password)) {
                    deleteAccount(email);
                    Toast.makeText(login.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private boolean validatePassword(String email, String oldPassword, String newPassword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"password"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, oldPassword};
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
        boolean passwordMatches = cursor.getCount() > 0;
        cursor.close();
        if (passwordMatches) {
            updatePasswordInDatabase(email, newPassword);
            return true;
        }
        return false;
    }

    private void updatePasswordInDatabase(String email, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        String selection = "email = ?";
        String[] selectionArgs = {email};
        db.update("users", values, selection, selectionArgs);
    }

    private boolean validateEmailAndPassword(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"email"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    private void deleteAccount(String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "email = ?";
        String[] selectionArgs = {email};
        db.delete("users", selection, selectionArgs);
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"email"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) {
            splash.setLoginState(login.this, true);
            Intent intent = new Intent(login.this, news.class);
            startActivity(intent);
            finish();
            Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}





