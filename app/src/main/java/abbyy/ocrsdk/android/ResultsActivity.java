package abbyy.ocrsdk.android;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ResultsActivity extends Activity {

	String outputPath;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 tv = new TextView(this);
		 setContentView(tv);

		String imageUrl = "unknown";

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			imageUrl = extras.getString("IMAGE_PATH");
			outputPath = extras.getString("RESULT_PATH");
		}

		// Starting recognition process
		new AsyncProcessTask(this).execute(imageUrl, outputPath);
	}

	public void updateResults(Boolean success) {
		if (!success)
			return;
		try {
			StringBuffer contents = new StringBuffer();

			FileInputStream fis = openFileInput(outputPath);

			System.out.println("45" + outputPath);
			try {
				Reader reader = new InputStreamReader(fis, "UTF-8");
				BufferedReader bufReader = new BufferedReader(reader);
				System.out.println("49" + bufReader);
				String text = null;
				while ((text = bufReader.readLine()) != null) {
					contents.append(text).append(
							System.getProperty("line.separator"));
				}
			} finally {
				fis.close();
			}

			displayMessage(contents.toString());
		} catch (Exception e) {
			displayMessage("Error: " + e.getMessage());
		}
	}

	public void displayMessage(String text) {
		// System.out.println("63" + text);
		tv.post(new MessagePoster(text));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_results, menu);
		return true;
	}

	class MessagePoster implements Runnable {
		public MessagePoster(String message) {
			_message = message;
		}

		public void run() {
//			tv.append(_message + "\n");
//			setContentView(tv);
			System.out.println("82" + _message);
			ParseValue(_message);
		}

		private final String _message;
	}

	public void ParseValue(String xmlRecords) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			// InputSource is = new InputSource();
			// is.setCharacterStream(new StringReader(xmlRecords));

			writeToFile(xmlRecords);
			// readFromFile();
			// InputStream is = getAssets().open("file.xml");
			InputStream is = new ByteArrayInputStream(readFromFile().getBytes());
			// System.out.println("1is>>>> " + xmlRecords);
			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("field");

			String parsedData = "", showData = ""
					, strText = "", strPhone = "", strEmail = "", strAddress = "", strCompany = "",strName="",strJob="",strMobile="",strFax=""
					,strWeb="";
			// iterate the employees
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				parsedData += "\n "
						+ element.getAttributes().getNamedItem("type")
								.getNodeValue();
				System.out.println("00000>>>" + parsedData);
				NodeList name = element.getElementsByTagName("value");
				Element line = (Element) name.item(0);

//				if (element.getAttributes().getNamedItem("type").getNodeValue()
//						.equals("Phone")
//						|| element.getAttributes().getNamedItem("type")
//								.getNodeValue().equals("Email")
//						|| element.getAttributes().getNamedItem("type")
//								.getNodeValue().equals("Address")
//						|| element.getAttributes().getNamedItem("type")
//								.getNodeValue().equals("Company")
//						|| element.getAttributes().getNamedItem("type")
//								.getNodeValue().equals("Text")) {
//					showData += "\n "
//							+ element.getAttributes().getNamedItem("type")
//									.getNodeValue() + ":"
//							+ getCharacterDataFromElement(line);
//				}
				if (element.getAttributes().getNamedItem("type").getNodeValue()
						.equals("Name")){
					strName = getCharacterDataFromElement(line);
				}else if (element.getAttributes().getNamedItem("type").getNodeValue()
						.equals("Phone")){
					strPhone = getCharacterDataFromElement(line);
				}else if (element.getAttributes().getNamedItem("type").getNodeValue()
						.equals("Email")){
					strEmail = getCharacterDataFromElement(line);
				}else if (element.getAttributes().getNamedItem("type").getNodeValue()
						.equals("Company")){
					strCompany = getCharacterDataFromElement(line);
				}else if (element.getAttributes().getNamedItem("type").getNodeValue()
						.equals("Address")){
					strAddress = getCharacterDataFromElement(line);
				}else if (element.getAttributes().getNamedItem("type").getNodeValue()
						.equals("Text")){
					strText = getCharacterDataFromElement(line);
				}else if (element.getAttributes().getNamedItem("type").getNodeValue()
						.equals("Job")){
					strJob = getCharacterDataFromElement(line);
				}else if (element.getAttributes().getNamedItem("type").getNodeValue()
						.equals("Web")){
					strWeb = getCharacterDataFromElement(line);
				}
				else if (element.getAttributes().getNamedItem("type").getNodeValue()
						.equals("Fax")){
					strFax = getCharacterDataFromElement(line);
				}
				System.out
						.println("Name: " + getCharacterDataFromElement(line));
				parsedData += " : " + getCharacterDataFromElement(line);
			}

			ResultsActivity.this.finish();
			Intent showResult = new Intent(getApplicationContext(), ShowResult.class);
			
			showResult.putExtra("Name", strName);
			showResult.putExtra("Phone", strPhone);
			showResult.putExtra("Email", strEmail);
			showResult.putExtra("Address", strAddress);
			showResult.putExtra("Company", strCompany);
			showResult.putExtra("Text", strText);
			showResult.putExtra("Job", strJob);
			showResult.putExtra("Web", strWeb);
			showResult.putExtra("Fax", strFax);
			
			
			
			startActivity(showResult);
//			tv.setText(showData);
			System.out.println("/final data>>>" + parsedData);
			// tvParsedData.setText(parsedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private Result CreateXML(String xmlString) {
	// // TODO Auto-generated method stub
	// // Use String reader
	// DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	//
	// DocumentBuilder builder;
	// try {
	// builder = factory.newDocumentBuilder();
	//
	// // Use String reader
	// Document document = builder.parse(new InputSource(new StringReader(
	// xmlString)));
	//
	// TransformerFactory tranFactory = TransformerFactory.newInstance();
	// Transformer aTransformer = tranFactory.newTransformer();
	// Source src = new DOMSource(document);
	// Result dest = new StreamResult(new File("xmlFileName.xml"));
	// aTransformer.transform(src, dest);
	// return dest;
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }

	// private static Document convertStringToDocument(String xmlStr) {
	// DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	// DocumentBuilder builder;
	// try {
	// builder = factory.newDocumentBuilder();
	// Document doc = builder.parse(new InputSource(new StringReader(
	// xmlStr)));
	// return doc;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	private void writeToFile(String data) {
		try {
			System.out.println("11111file b4 created" + data);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput("file.xml", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
			System.out.println("11111file created" + data);
		} catch (IOException e) {
			Log.e("test", "File write failed: " + e.toString());
		}

	}

	private String readFromFile() {
		String ret = "";
		try {
			InputStream inputStream = openFileInput("file.xml");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.e("test", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("test", "Can not read file: " + e.toString());
		}
		System.out.println("11111file read" + ret);
		return ret;
	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}
}
