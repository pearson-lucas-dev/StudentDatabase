package com.lucaspearson.studentdatabase;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowTable extends Activity {
	TableLayout TableLayoutDB;
	StudentData studentData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_table);
		TableLayoutDB = (TableLayout) findViewById(R.id.TableLayoutDB);
		studentData = new StudentData(ShowTable.this);
		setupTable();
	}

	public void setupTable() {
		Cursor c = studentData.returnAllCursor();
		int count = c.getCount();
		c.moveToFirst();
		TableLayoutDB.setVerticalScrollBarEnabled(true);
		TextView first_name, last_name, email;
		Button btnDelete;
		TableRow tableRow;
		for (Integer j = 0; j < count; j++) {
			tableRow = new TableRow(getApplicationContext());
			btnDelete = new Button(getApplicationContext());
			tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
			first_name = new TextView(getApplicationContext());
			first_name.setGravity(Gravity.LEFT);
			first_name.setText(c.getString(c.getColumnIndex("first_name")));
			last_name = new TextView(getApplicationContext());
			last_name.setText(c.getString(c.getColumnIndex("last_name")));
			email = new TextView(getApplicationContext());
			email.setText(c.getString(c.getColumnIndex("email")));
			first_name.setPadding(20, 20, 20, 20);
			last_name.setPadding(20, 20, 20, 20);
			email.setPadding(20, 20, 20, 20);
			first_name.setTextColor(Color.BLACK);
			last_name.setTextColor(Color.BLACK);
			email.setTextColor(Color.BLACK);
			final int deleteThisID = c.getInt(c.getColumnIndex("_id"));
			btnDelete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					studentData.deleteStudentWithID(deleteThisID);
					finish();
					startActivity(getIntent());
				}
			});
			btnDelete.setBackgroundColor(Color.LTGRAY);
			btnDelete.setTextColor(Color.BLACK);
			btnDelete.setText("Delete");
			tableRow.addView(first_name);
			tableRow.addView(last_name);
			tableRow.addView(email);
			tableRow.addView(btnDelete);
			TableLayoutDB.addView(tableRow);
			c.moveToNext();
		}
	}

}
