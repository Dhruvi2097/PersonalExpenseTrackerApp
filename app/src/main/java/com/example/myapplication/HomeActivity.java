package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnAddExpense, btnViewExpenses, btnLogout;
    String username; // logged-in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnViewExpenses = findViewById(R.id.btnViewExpenses);
        btnLogout = findViewById(R.id.btnLogout);

        // ✅ Get username from LoginActivity
        username = getIntent().getStringExtra("username");

        if (username == null || username.trim().isEmpty()) {
            tvWelcome.setText("Welcome!");
        } else {
            tvWelcome.setText("Welcome, " + username + "!");
        }

        // ✅ Pass username to AddExpenseActivity
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, AddExpenseActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        // ✅ Pass username to ViewExpensesActivity
        btnViewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ViewExpensesActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        // ✅ Logout → go back to LoginActivity
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper DB = new DBHelper(HomeActivity.this);

                // Delete user from database
                Boolean deleted = DB.deleteUser(username);

                if (deleted) {
                    Toast.makeText(HomeActivity.this, "Account deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "Error deleting account!", Toast.LENGTH_SHORT).show();
                }

                // Go back to login screen
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
