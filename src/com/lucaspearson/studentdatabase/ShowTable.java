package com.lucaspearson.studentdatabase;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ShowTable extends Activity {
	TableLayout TableLayoutDB;
	StudentData studentData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_table);
		TableLayoutDB = (TableLayout) findViewById(R.id.TableLayoutDB);
		studentData = new StudentData(ShowTable.this);
		int textSize = 9;
		// if small screen change this programmatically
		setupTable(textSize);
	}

	public void setupTable(int textSize) {
		Cursor c = studentData.returnAllCursor();
		int count = c.getCount();
		c.moveToFirst();
		TableLayoutDB.setVerticalScrollBarEnabled(true);
		TextView first_name, last_name, email;
		Button btnDelete;
		Button btnUpdate;
		TableRow tableRow;
		for (Integer j = 0; j < count; j++) {
			tableRow = new TableRow(getApplicationContext());
			btnDelete = new Button(getApplicationContext());
			btnUpdate = new Button(getApplicationContext());
			tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
			first_name = new TextView(getApplicationContext());
			first_name.setText(c.getString(c.getColumnIndex("first_name")));
			last_name = new TextView(getApplicationContext());
			last_name.setText(c.getString(c.getColumnIndex("last_name")));
			email = new TextView(getApplicationContext());
			email.setText(c.getString(c.getColumnIndex("email")));
			first_name.setPadding(10, 10, 10, 10);
			last_name.setPadding(10, 10, 10, 10);
			email.setPadding(10, 10, 10, 10);
			first_name.setTextColor(Color.BLACK);
			last_name.setTextColor(Color.BLACK);
			email.setTextColor(Color.BLACK);
			first_name.setTextSize(textSize);
			last_name.setTextSize(textSize);
			email.setTextSize(textSize);
			final int thisID = c.getInt(c.getColumnIndex("_id"));

			btnDelete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					studentData.deleteStudentWithID(thisID);
					finish();
					startActivity(getIntent());
				}
			});
			btnDelete.setBackgroundColor(Color.LTGRAY);
			btnDelete.setTextColor(Color.BLACK);
			btnDelete.setText("Delete");
			btnDelete.setPadding(10, 10, 10, 10);
			btnUpdate.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Cursor c = studentData.returnCursorWithID(thisID);

					AlertDialog.Builder alert = new AlertDialog.Builder(
							ShowTable.this);
					alert.setTitle("Update Student record");
					alert.setMessage("Please select OK once all the data is correct.");

					final EditText fname = new EditText(ShowTable.this);
					c.moveToFirst();
					fname.setText(c.getString(c.getColumnIndex("first_name")));
					fname.setHint("Enter First Name");
					final EditText lname = new EditText(ShowTable.this);
					lname.setText(c.getString(c.getColumnIndex("last_name")));
					lname.setHint("Enter Last Name");
					final EditText email = new EditText(ShowTable.this);
					email.setText(c.getString(c.getColumnIndex("email")));
					email.setHint("Enter Email");
					LinearLayout ll = new LinearLayout(ShowTable.this);
					ll.setOrientation(LinearLayout.VERTICAL);
					ll.addView(fname);
					ll.addView(lname);
					ll.addView(email);
					alert.setView(ll);
					alert.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									studentData.updateStudentWithID(thisID,
											fname.getText().toString(), lname
													.getText().toString(),
											email.getText().toString());
									finish();
									startActivity(getIntent());

								}
							});

					alert.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(ShowTable.this,
											"You cancel the update",
											Toast.LENGTH_SHORT).show();

								}
							});

					alert.show();
					// studentData.updateStudentWithID(thisID,);
					// finish();
					// startActivity(getIntent());
				}
			});
			btnUpdate.setBackgroundColor(Color.LTGRAY);
			btnUpdate.setTextColor(Color.BLACK);
			btnUpdate.setText("Update");
			btnUpdate.setPadding(10, 10, 10, 10);
			tableRow.addView(first_name);
			tableRow.addView(last_name);
			tableRow.addView(email);
			tableRow.addView(btnUpdate);
			tableRow.addView(btnDelete);
			TableLayoutDB.addView(tableRow);
			c.moveToNext();
		}
	}

}
