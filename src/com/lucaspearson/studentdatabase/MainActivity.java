package com.lucaspearson.studentdatabase;
/* programmer: Lucas Pearson
 * date: 3-18-2014
 * description: This app has a SQLite database that has student info.  
 */
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	// call StudentData.getSqlDatabae
	StudentData studentData;
	String fname, lname, email;
	EditText etFirstName;
	EditText etLastName;
	EditText etEmail;
	Button btnCreate, btnShowTable, btnClose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		studentData = new StudentData(MainActivity.this);
		btnCreate = (Button) findViewById(R.id.btnCreate);
		btnShowTable = (Button) findViewById(R.id.btnShowTable);
		btnClose = (Button) findViewById(R.id.btnClose);
		btnCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					fname = etFirstName.getText().toString();
					lname = etLastName.getText().toString();
					email = etEmail.getText().toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				long id = studentData.insertData(fname, lname, email);
				if (id != -1) {
					Toast.makeText(getBaseContext(),
							"Student made with id: " + String.valueOf(id),
							Toast.LENGTH_LONG).show();
				} else {

					Toast.makeText(getBaseContext(), "Student was not made!",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		btnShowTable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ShowTable.class));
			}
		});
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		etFirstName = (EditText) findViewById(R.id.firstname);
		etLastName = (EditText) findViewById(R.id.lastname);
		etEmail = (EditText) findViewById(R.id.email);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
