package com.jumio.mobile.sample;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import com.jumio.mobile.sdk.*;
import com.jumio.netswipe.sdk.*;
import com.jumio.netverify.sdk.*;

/**
 * sample activity using the JumioSDK
 */
public class SampleActivity extends Activity {

	/* PUT YOUR NETVERIFY API TOKEN AND SECRET HERE */
	private static String NETVERIFY_API_TOKEN = "";
	private static String NETVERIFY_API_SECRET = "";

	/* PUT YOUR NETSWIPE API TOKEN AND SECRET HERE */
	private static String NETSWIPE_API_TOKEN = "";
	private static String NETSWIPE_API_SECRET = "";

	private final static String TAG = "JumioMobileSDKSample";

	private Button startNetverifyButton;
	private NetverifySDK netverifySdk;

	private Button startNetswipeButton;
	private NetswipeSDK netswipeSdk;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		startNetswipeButton = (Button) findViewById(R.id.startNetswipeButton);
		startNetswipeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (netswipeSdk != null)
					netswipeSdk.start();
			}
		});

		startNetverifyButton = (Button) findViewById(R.id.startNetverifyButton);
		startNetverifyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (netverifySdk != null)
					netverifySdk.start();
			}
		});

		initializeNetswipeSDK();
		initializeNetverifySDK();

	}

	private void initializeNetswipeSDK() {
		try {
			// Use the following property to identify the scan in your reports (max. 100 characters).
			netswipeSdk = new NetswipeSDK(SampleActivity.this, NETSWIPE_API_TOKEN, NETSWIPE_API_SECRET, "YOURREPORTINGCRITERIA");

			// You can enable debug output to the logcat
			// netswipeSdk.setDebugLog(true);

			// To restrict supported card types, pass a bitmask of NetswipeCreditCardTypes to the property supportedCreditCardTypes.
			// ArrayList<CreditCardType> creditCardTypes = new ArrayList<CreditCardType>();
			// creditCardTypes.add(CreditCardType.VISA);
			// creditCardTypes.add(CreditCardType.MASTER_CARD);
			// creditCardTypes.add(CreditCardType.AMERICAN_EXPRESS);
			// creditCardTypes.add(CreditCardType.DINERS_CLUB);
			// creditCardTypes.add(CreditCardType.DISCOVER);
			// creditCardTypes.add(CreditCardType.CHINA_UNIONPAY);
			// creditCardTypes.add(CreditCardType.JCB);
			// netswipeSdk.setSupportedCreditCardTypes(creditCardTypes);

			// You can enable the recognition of card holder name, sort code and account number.
			// Manual entry, expiry recognition and CVV entry are enabled by default and can be disabled.
			// netswipeSdk.setCardHolderNameRequired(true);
			// netswipeSdk.setSortCodeAndAccountNumberRequired(true);
			// netswipeSdk.setManualEntryEnabled(false);
			// netswipeSdk.setExpiryRequired(false);
			// netswipeSdk.setCvvRequired(false);

			// The user can edit the recognized card holder name if cardHolderNameEditable is enabled.
			// netswipeSdk.setCardHolderNameEditable(true);

			// You can add additional fields to "Manual entry" and the confirmation page.
			// netswipeSdk.addCustomField("zipCodeId", getString(R.string.zip_code), getString(R.string.zip_code_hint), InputType.TYPE_CLASS_NUMBER, "[0-9]{4,}");

			// Use the following property to identify the scan in your reports (max. 100 characters).
			// netswipeSdk.setMerchantReportingCriteria("YOURREPORTINGCRITERIA");

			// Use the following method to pass first and last name to Netswipe for name match.
			// netswipeSdk.setName("FIRSTNAME" "LASTNAME");

			// You can set a short vibration (only on iPhone) and sound effect to notify the user that the card has been detected.
			// netswipeSdk.setVibrationEffectEnabled(true);
			// netswipeSdk.setSoundEffect(R.raw.shutter_sound);

			if (NetswipeSDK.isRooted())
				Log.w(TAG, "Device is rooted");

		} catch (PlatformNotSupportedException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "This platform is not supported", Toast.LENGTH_LONG).show();
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Resource files are missing", Toast.LENGTH_LONG).show();
		}
	}

	private void initializeNetverifySDK() {
		try {
			netverifySdk = NetverifySDK.createNetverifySDK(SampleActivity.this, NETVERIFY_API_TOKEN, NETVERIFY_API_SECRET);

			// You can enable debug output to the logcat
			// netverifySdk.setDebugLog(true);

			// You can specify issuing country (ISO 3166-1 alpha 3 country code) and ID type or let the user choose them during the verification process.
			// netverifySdk.setPreselectedCountry("AUT");
			// netverifySdk.setPreselectedDocumentType(NVDocumentType.PASSPORT);

			// The merchant scan reference allows you to identify the scan (max. 100 characters).
			// Note: Must not contain sensitive data like PII (Personally Identifiable Information) or account login.
			// netverifySdk.setMerchantScanReference("YOURSCANREFERENCE");

			// Use the following property to identify the scan in your reports (max. 100 characters).
			// netverifySdk.setMerchantReportingCriteria("YOURREPORTINGCRITERIA");

			// You can also set a customer identifier (max. 100 characters).
			// Note: The customer ID should not contain sensitive data like PII (Personally Identifiable Information) or account login.
			// netverifySdk.setCustomerId("CUSTOMERID");

			// Enable ID verification to receive a verification status and verified data positions (see Callback chapter).
			// Note: Not possible for accounts configured as Fastfill only
			// netverifySdk.setRequireVerification(true);

			// You can enable face match during the ID verification for a specific transaction. This setting overrides your default Jumio merchant settings.
			// netverifySdk.setRequireFaceMatch(true);

			// Use the following method to pass first and last name to the ID verification for name match.
			// netverifySdk.setName("FIRSTNAME LASTNAME");

			// Use the following method to pass first and last name to the ID verification.
			// netverifySdk.setFirstAndLastName("FIRSTNAME", "LASTNAME");

		} catch (PlatformNotSupportedException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "This platform is not supported", Toast.LENGTH_LONG).show();
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Resource files are missing", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == NetverifySDK.REQUEST_CODE) {
			if (data == null)
				return;
			if (resultCode == NetverifySDK.RESULT_CODE_SUCCESS || resultCode == NetverifySDK.RESULT_CODE_BACK_WITH_SUCCESS) {
				String scanReference = (data == null) ? "" : data.getStringExtra(NetverifySDK.RESULT_DATA_SCAN_REFERENCE);
				NetverifyDocumentData documentData = (data == null) ? null : (NetverifyDocumentData) data.getParcelableExtra(NetverifySDK.RESULT_DATA_SCAN_DATA);
				NetverifyMrzData mrzData = documentData != null ? documentData.getMrzData() : null;
			} else if (resultCode == NetverifySDK.RESULT_CODE_CANCEL) {
				String errorMessage = data.getStringExtra(NetverifySDK.RESULT_DATA_ERROR_MESSAGE);
				int errorCode = data.getIntExtra(NetverifySDK.RESULT_DATA_ERROR_CODE, 0);
			}
		} else if (requestCode == NetswipeSDK.REQUEST_CODE) {
			if (data == null)
				return;
			ArrayList<String> scanAttempts = data.getStringArrayListExtra(NetswipeSDK.EXTRA_SCAN_ATTEMPTS);

			if (resultCode == Activity.RESULT_OK) {
				NetswipeCardInformation cardInformation = data.getParcelableExtra(NetswipeSDK.EXTRA_CARD_INFORMATION);

				cardInformation.clear();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				String errorMessage = data.getStringExtra(NetswipeSDK.EXTRA_ERROR_MESSAGE);
				int errorCode = data.getIntExtra(NetswipeSDK.EXTRA_ERROR_CODE, 0);
			}
		}
	}
}
