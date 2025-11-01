package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateExpenseActivity extends AppCompatActivity {
    EditText etExpenseName, etAmount;
    Spinner spCategory;
    DatePicker dpDate;
    Button btnUpdate;
    DBHelper DB;
    int expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);

        // Initialize views
        etExpenseName = findViewById(R.id.etExpenseName);
        etAmount = findViewById(R.id.etAmount);
        spCategory = findViewById(R.id.spCategory);
        dpDate = findViewById(R.id.dpDate);
        btnUpdate = findViewById(R.id.btnUpdate);

        DB = new DBHelper(this);

        // Get data from Intent
        expenseId = getIntent().getIntExtra("id", -1);
        String name = getIntent().getStringExtra("name");
        double amount = getIntent().getDoubleExtra("amount", 0);
        String category = getIntent().getStringExtra("category");
        String date = getIntent().getStringExtra("date");

        // Pre-fill data
        etExpenseName.setText(name);
        etAmount.setText(String.valueOf(amount));

        // Set spinner categories
        String[] categories = {"Food", "Travel", "Shopping", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        if (category != null) {
            int pos = adapter.getPosition(category);
            if (pos >= 0) spCategory.setSelection(pos);
        }

        // Pre-fill date
        if (date != null && date.contains("/")) {
            try {
                String[] parts = date.split("/");
                if (parts.length == 3) {
                    int day = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]) - 1; // months start at 0
                    int year = Integer.parseInt(parts[2]);
                    dpDate.updateDate(year, month, day);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Update button click
        btnUpdate.setOnClickListener(v -> {
            String newName = etExpenseName.getText().toString().trim();
            String amountStr = etAmount.getText().toString().trim();
            String newCategory = spCategory.getSelectedItem().toString();
            String newDate = dpDate.getDayOfMonth() + "/" + (dpDate.getMonth() + 1) + "/" + dpDate.getYear();

            if (newName.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double newAmount = Double.parseDouble(amountStr);
                    boolean updated = DB.updateExpense(expenseId, newName, newAmount, newCategory, newDate);
                    if (updated) {
                        Toast.makeText(this, "Expense Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("updated", true);
                        setResult(RESULT_OK, intent);
                        finish();

                    } else {
                        Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
