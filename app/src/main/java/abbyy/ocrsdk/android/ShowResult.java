package abbyy.ocrsdk.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class ShowResult extends Activity {

	EditText etPhone, etEmail, etAddress, etCompany, etOther,etName
	,etWeb,etJob,etFax;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_form);

		etAddress = (EditText) findViewById(R.id.etAddress);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPhone = (EditText) findViewById(R.id.etPhone);
		etCompany = (EditText) findViewById(R.id.etCompany);
		etOther = (EditText) findViewById(R.id.etOther);
		
		etName = (EditText) findViewById(R.id.etName);
		etJob = (EditText) findViewById(R.id.etJob);
		etFax = (EditText) findViewById(R.id.etFax);
		etWeb = (EditText) findViewById(R.id.etWeb);
		
		
		
		

		if (getIntent().getExtras() != null) {

			String strText = "", strPhone = "", strEmail = "", strAddress = "", strCompany = "", strName = "", strJob = "", strFax = ""	, strWeb="";
			
			strPhone = getIntent().getExtras().getString("Phone");
			strEmail = getIntent().getExtras().getString("Email");
			strAddress = getIntent().getExtras().getString("Address");
			strCompany = getIntent().getExtras().getString("Company");
			strText = getIntent().getExtras().getString("Text");
			strName = getIntent().getExtras().getString("Name");
			strJob = getIntent().getExtras().getString("Job");
			strFax = getIntent().getExtras().getString("Fax");
			strWeb = getIntent().getExtras().getString("Web");
			

			etAddress.setText(strAddress);
			etPhone.setText(strPhone);
			etEmail.setText(strEmail);
			etCompany.setText(strCompany);
			etOther.setText(strText);
			
			etJob.setText(strJob);
			etFax.setText(strFax);
			etWeb.setText(strWeb);
			etName.setText(strName);
			
		}
	}
}
