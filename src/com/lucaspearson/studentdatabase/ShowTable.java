package com.lucaspearson.studentdatabase;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowTable extends ListActivity {
	StudentData studentData;
	private ArrayList<Student> results = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		studentData = new StudentData(ShowTable.this);
		TextView tv = new TextView(this);
		tv.setText("Click for update. Long Click for delete.");
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// This is so the app doesnt crash!

			}
		});
		getListView().addHeaderView(tv);
		this.getListView().setLongClickable(true);
		this.getListView().setOnItemLongClickListener(
				new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View v, int position, long id) {
						//use position - 1 because the array is 0 based.
						final Student student = results.get(position - 1);
						AlertDialog.Builder alert = new AlertDialog.Builder(
								ShowTable.this);
						alert.setTitle("Are you sure you want to delete?");
						alert.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										studentData
												.deleteStudentWithID(student.id);
										setListAdapter(null);
										setupTable();
										displayResultList();
										Toast.makeText(getBaseContext(),
												"You deleted a student!",
												Toast.LENGTH_SHORT).show();
									}
								});

						alert.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Toast.makeText(ShowTable.this,
												"You canceled the delete.",
												Toast.LENGTH_SHORT).show();

									}
								});

						alert.show();

						return true;
					}
				});
		setupTable();
		displayResultList();
	}

	private void setupTable() {
		results = new ArrayList<Student>();
		Cursor c = studentData.returnAllCursor();
		int count = c.getCount();
		c.moveToFirst();
		for (Integer j = 0; j < count; j++) {
			Student s = new Student();
			s.fname = c.getString(c.getColumnIndex("first_name"));
			s.lname = c.getString(c.getColumnIndex("last_name"));
			s.email = c.getString(c.getColumnIndex("email"));
			s.id = c.getInt(c.getColumnIndex("_id"));
			results.add(s);

			c.moveToNext();
		}

	}

	private void displayResultList() {
		setListAdapter(new ArrayAdapter<Student>(this,
				android.R.layout.simple_list_item_1, results));
		getListView().setTextFilterEnabled(true);

	}

	@Override
	protected void onListItemClick(ListView l, View view, int position, long id) {

		/*
		 * This is not a clean fix.... What this does is ignores a click on the
		 * header of the listview I tried to setClickable to false with no
		 * success
		 */

		if (position == 0) {
			return;
		}
		super.onListItemClick(l, view, position, id);

		//use position - 1 because the array is 0 based.
		final Student student = results.get(position - 1);
		AlertDialog.Builder alert = new AlertDialog.Builder(ShowTable.this);
		alert.setTitle("Update Student record");
		alert.setMessage("Please select OK once all the data is correct.");

		final EditText fname = new EditText(ShowTable.this);
		fname.setText(student.fname);
		fname.setHint("Enter First Name");
		final EditText lname = new EditText(ShowTable.this);
		lname.setText(student.lname);
		lname.setHint("Enter Last Name");
		final EditText email = new EditText(ShowTable.this);
		email.setText(student.email);
		email.setHint("Enter Email");
		LinearLayout ll = new LinearLayout(ShowTable.this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.addView(fname);
		ll.addView(lname);
		ll.addView(email);
		alert.setView(ll);
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				studentData.updateStudentWithID(student.id, fname.getText()
						.toString(), lname.getText().toString(), email
						.getText().toString());
				setupTable();
				displayResultList();
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(ShowTable.this,
								"You canceled the update", Toast.LENGTH_SHORT)
								.show();

					}
				});

		alert.show();
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
	}
}
