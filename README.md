# 💰 Personal Expense Tracker App

A simple yet powerful Android application built using **Java** and **SQLite** that helps users manage their daily expenses efficiently.  
It includes secure **user registration**, **login**, and **CRUD (Create, Read, Update, Delete)** operations for expense management — all wrapped in a clean, modern UI.

---

## 📱 Features

✅ **User Authentication**
- Register new users with username and password  
- Secure login system using SQLite  
- Logout with session clear

✅ **Expense Management**
- Add expenses with name, amount, category, and date  
- View all recorded expenses in a scrollable card layout  
- Update existing expenses  
- Delete expenses (with confirmation popup)

✅ **Beautiful UI**
- Material-inspired design  
- CardView-based layouts  
- Smooth navigation between screens  
- Consistent color theme for modern look

---

## 🧩 Technologies Used

| Component | Technology |
|------------|-------------|
| **Frontend** | Java, XML (Android Studio) |
| **Database** | SQLite |
| **Backend Logic** | DBHelper class (JDBC-like SQLite integration) |
| **IDE** | Android Studio |
| **Version Control** | Git & GitHub |

---

## 🗂️ Project Structure

app/src/main/java/com/example/myapplication/
│
├── DBHelper.java # Handles SQLite database operations
├── LoginActivity.java # User login page
├── RegisterActivity.java # User registration
├── HomeActivity.java # Dashboard after login
├── AddExpenseActivity.java # Add new expenses
├── ViewExpensesActivity.java # View, Update, Delete expenses
├── UpdateExpenseActivity.java # Update expense details
│
└── res/layout/
├── activity_login.xml
├── activity_register.xml
├── activity_home.xml
├── activity_add_expense.xml
├── activity_view_expenses.xml
├── dialog_confirm_delete.xml

---

## 🧠 How It Works

1. User **registers** and the details are stored in the SQLite database.
2. On login, credentials are verified through `DBHelper`.
3. The **Home Page** provides options to:
   - Add a new expense  
   - View, update, or delete existing expenses  
4. Expenses are displayed dynamically using **CardViews** inside a **ScrollView**.
5. Each expense can be long-pressed to **update** or **delete** with confirmation.

---

## 🚀 Setup & Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/Dhruvi2097/PersonalExpenseTrackerApp.gitOpen the project in Android Studio

2. Sync Gradle and build the project
3. Run on an emulator or connect your Android device via USB
## 🖼️ Screenshots

<p align="center">
  <img src="login.jpg" alt="Login Screen" width="180" style="margin: 10px;"/>
  <img src="home.jpg" alt="Home Page" width="180" style="margin: 10px;"/>
  <img src="addexpense.jpg" alt="Add Expense" width="180" style="margin: 10px;"/>
  <img src="screenshots/view_expense.jpg" alt="View Expenses" width="180" style="margin: 10px;"/>
</p>

<p align="center">
  <b>Login</b>  <b>Home</b>  <b>Add Expense</b>  <b>View Expense</b>
</p>

