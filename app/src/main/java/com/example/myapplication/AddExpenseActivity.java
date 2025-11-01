package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etExpenseName, etAmount;
    Spinner spCategory;
    DatePicker dpDate;
    Button btnSave;
    DBHelper DB;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Initialize UI
        etExpenseName = findViewById(R.id.etExpenseName);
        etAmount = findViewById(R.id.etAmount);
        spCategory = findViewById(R.id.spCategory);
        dpDate = findViewById(R.id.dpDate);
        btnSave = findViewById(R.id.btnSave);

        DB = new DBHelper(this);

        // Get username from intent (sent from HomeActivity)
        username = getIntent().getStringExtra("username");

        // Setup Spinner values
        String[] categories = {"Food", "Travel", "Shopping", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        // Button click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etExpenseName.getText().toString().trim();
                String amountStr = etAmount.getText().toString().trim();
                String category = spCategory.getSelectedItem().toString();
                String date = dpDate.getDayOfMonth() + "/" + (dpDate.getMonth() + 1) + "/" + dpDate.getYear();

                if (name.isEmpty() || amountStr.isEmpty()) {
                    Toast.makeText(AddExpenseActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        Boolean inserted = DB.insertExpense(username, name, amount, category, date);
                        if (inserted) {
                            Toast.makeText(AddExpenseActivity.this, "Expense Saved!", Toast.LENGTH_SHORT).show();
                            finish(); // return to Home
                        } else {
                            Toast.makeText(AddExpenseActivity.this, "Error saving expense", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(AddExpenseActivity.this, "Enter a valid number for amount", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
