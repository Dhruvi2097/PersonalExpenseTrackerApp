package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ViewExpensesActivity extends AppCompatActivity {

    TextView tvExpenses;
    DBHelper DB;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        tvExpenses = findViewById(R.id.tvHeading);
        DB = new DBHelper(this);

        username = getIntent().getStringExtra("username");
        if (username == null) {
            Toast.makeText(this, "Error: No user info passed", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initially load expenses
        loadExpenses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload expenses every time activity comes to foreground
        loadExpenses();
    }

    private void loadExpenses() {
        LinearLayout expenseContainer = findViewById(R.id.expenseContainer);
        expenseContainer.removeAllViews(); // clear old views

        Cursor cursor = DB.getExpenses(username);

        if (cursor.getCount() == 0) {
            TextView noData = new TextView(this);
            noData.setText("No expenses found.");
            noData.setTextSize(18);
            noData.setTextColor(Color.WHITE);
            expenseContainer.addView(noData);
        } else {
            while (cursor.moveToNext()) {
                final int expenseId = cursor.getInt(0);
                String name = cursor.getString(2);
                double amount = cursor.getDouble(3);
                String category = cursor.getString(4);
                String date = cursor.getString(5);

                // Create CardView
                CardView card = new CardView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 16, 0, 16);
                card.setLayoutParams(params);
                card.setRadius(20);
                card.setCardElevation(8);
                card.setContentPadding(24, 24, 24, 24);
                card.setCardBackgroundColor(Color.parseColor("#2E3B4E"));

                // Inner layout
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                TextView tv = new TextView(this);
                tv.setText("Name: " + name
                        + "\nAmount: ₹" + amount
                        + "\nCategory: " + category
                        + "\nDate: " + date);
                tv.setTextSize(16);
                tv.setTextColor(Color.WHITE);

                layout.addView(tv);

                // ---- 🧩 ADD DELETE BUTTON BELOW ----
                Button btnDelete = new Button(this);
                btnDelete.setText("Delete");
                btnDelete.setTextSize(14);
                btnDelete.setAllCaps(false);
                btnDelete.setTextColor(Color.WHITE);

// Use your new rounded red background
                btnDelete.setBackgroundResource(R.drawable.btn_delete_rounded);

// Adjust size & margin to look perfect
                LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                deleteParams.setMargins(0, 12, 0, 0);
                btnDelete.setLayoutParams(deleteParams);
                btnDelete.setPadding(32, 6, 32, 6);

// Handle delete click
                btnDelete.setOnClickListener(v -> showDeleteDialog(name, amount));

                layout.addView(btnDelete);

                // ------------------------------------

                card.addView(layout);
                expenseContainer.addView(card);

                // Long press to edit
                card.setOnLongClickListener(v -> {
                    Intent intent = new Intent(ViewExpensesActivity.this, UpdateExpenseActivity.class);
                    intent.putExtra("id", expenseId);
                    intent.putExtra("name", name);
                    intent.putExtra("amount", amount);
                    intent.putExtra("category", category);
                    intent.putExtra("date", date);
                    startActivity(intent);
                    return true;
                });
            }
        }

        cursor.close();
    }

    // ---- 🧩 CUSTOM DELETE POPUP METHOD ----
    private void showDeleteDialog(String name, double amount) {
        // Inflate custom layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_delete, null);

        TextView tvDialogMessage = dialogView.findViewById(R.id.tvDialogMessage);
        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        // Set message dynamically
        tvDialogMessage.setText("Are you sure you want to delete this expense?\n\n" + name + " (₹" + amount + ")");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        btnYes.setOnClickListener(view -> {
            boolean deleted = DB.deleteExpense(username, name);
            if (deleted) {
                Toast.makeText(this, "Expense Deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                loadExpenses(); // refresh after deletion
            } else {
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
            }
        });

        btnNo.setOnClickListener(view -> dialog.dismiss());
    }
}
