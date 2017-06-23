package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new HabitDbHelper(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        displayDatabaseInfo();
    }


    private Cursor read() {


            // Create and/or open a database to read from it
            SQLiteDatabase db = mDbHelper.getReadableDatabase();


            String[] projection = {
                    HabitEntry._ID,
                    HabitEntry.COLUMN_HABIT_NAME,
                    HabitEntry.COLUMN_HABIT_TYPE,
                    HabitEntry.COLUMN_HABIT_DAY,
                    HabitEntry.COLUMN_HABIT_ENJOY
            };

            // Perform a query on the habits table
            Cursor cursor = db.query(
                    HabitEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null);


            TextView displayView = (TextView) findViewById(R.id.text_view_habit);

            try {
                // Create a header in the Text View that looks like this:
                //
                // The habits table contains <number of rows in Cursor> habits.
                // _id - name - type - day - enjoy
                //
                // In the while loop below, iterate through the rows of the cursor and display
                // the information from each column in this order.
                displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
                displayView.append(HabitEntry._ID + " - " +
                        HabitEntry.COLUMN_HABIT_NAME + " - " +
                        HabitEntry.COLUMN_HABIT_TYPE + " - " +
                        HabitEntry.COLUMN_HABIT_DAY + " - " +
                        HabitEntry.COLUMN_HABIT_ENJOY + "\n");

                // Figure out the index of each column
                int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
                int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
                int typeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TYPE);
                int dayColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DAY);
                int enjoyColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_ENJOY);

                // Iterate through all the returned rows in the cursor
                while (cursor.moveToNext()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    int currentID = cursor.getInt(idColumnIndex);
                    String currentName = cursor.getString(nameColumnIndex);
                    String currentType = cursor.getString(typeColumnIndex);
                    int currentDay = cursor.getInt(dayColumnIndex);
                    int currentEnjoy = cursor.getInt(enjoyColumnIndex);
                    // Display the values from each column of the current row in the cursor in the TextView
                    displayView.append(("\n" + currentID + " - " +
                            currentName + " - " +
                            currentType + " - " +
                            currentDay + " - " +
                            currentEnjoy));
                }
            } finally {
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }

        return cursor;
        }


        /**
         * Temporary helper method to display information in the onscreen TextView about the state of
         * the exercises database.
         */
        private void displayDatabaseInfo() {
            Cursor cursor = read();
        }

    private void insertHabit(){
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's habits attributes are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Toto");
        values.put(HabitEntry.COLUMN_HABIT_TYPE, "Terrier");
        values.put(HabitEntry.COLUMN_HABIT_DAY, HabitEntry.DAY_TUESDAY);
        values.put(HabitEntry.COLUMN_HABIT_ENJOY, 7);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the habits table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        Log.v("CatalogActivity", "New Row ID " +newRowId );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

