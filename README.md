# SqliteLoginValidation

Created login screen with username and password fileds when user enter the data that data checks before going to store into the 
database table.

If already username and password exists in the table it cann't store the user information and it shows "TOAST" user already exist.

Used SQLiteOpenHelper abstract class and implemented onCreate(), onUpgrad(), table creation, database creation, inserted data
into the table, checked validation " AND querey " and also debugged entair table data.


// To check validation get data into cursor object

Cursor cursor = sqLiteDatabase.rawQuery("SELECT name, password FROM users WHERE name = ? AND password = ?", new String[]{name1, password1});

// To print the entair table data

// Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users", null);
