package abbyy.ocrsdk.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParsere extends Activity {

	private TextView tvParsedData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		tvParsedData = (TextView) findViewById(R.id.tv_parsed_xml);

//		parseXMl();
	}

	public void parseXMl(String xmlRecords) {
//		String xmlRecords = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n"
//				+ "<document xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://ocrsdk.com/schema/recognizedBusinessCard-1.0.xsd http://ocrsdk.com/schema/recognizedBusinessCard-1.0.xsd\" xmlns=\"http://ocrsdk.com/schema/recognizedBusinessCard-1.0.xsd\">\n"
//				+ "  <businessCard>\n"
//				+ "    <field type=\"Phone\">\n"
//				+ "      <value>783-37-00</value>\n"
//				+ "    </field>\n"
//				+ "    <field type=\"Fax\">\n"
//				+ "      <value>783-26-63</value>\n"
//				+ "    </field>\n"
//				+ "    <field type=\"Email\">\n"
//				+ "      <value>john_sm@abbyy.com</value>\n"
//				+ "    </field>\n"
//				+ "    <field type=\"Web\">\n"
//				+ "      <value>www.abbyy.com</value>\n"
//				+ "    </field>\n"
//				+ "    <field type=\"Address\">\n"
//				+ "      <value>Otradnaya str., 2b, bld.6, 127273, Moscow, Russia</value>\n"
//				+ "    </field>\n"
//				+ "    <field type=\"Name\">\n"
//				+ "      <value>John Smith</value>\n"
//				+ "    </field>\n"
//				+ "    <field type=\"Company\">\n"
//				+ "      <value>ABBYY Headquarters</value>\n"
//				+ "    </field>\n"
//				+ "    <field type=\"Job\">\n"
//				+ "      <value>Product Analyst</value>\n"
//				+ "    </field>\n"
//				+ "    <field type=\"Text\">\n"
//				+ "      <value>ABBYY ABBYY Headquarters John Smith Product Analyst ABBYY Headquarters Otradnaya str., 2b, bld.6, 127273, Moscow, Russia Tel: 783-37-00 Fax: 783-26-63 john_sm@abbyy.com www.abbyy.com </value>\n"
//				+ "    </field>\n" + "  </businessCard>\n" + "</document>";

		// "<data>" +
		// " <employee>" +
		// "   <name>John</name>" +
		// "   <title>Manager</title>" +
		// " </employee>" +
		// " <employee>" +
		// "   <name>Sara</name>" +
		// "   <title>Clerk</title>" +
		// " </employee>" +
		// "</data>";

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("field");

			String parsedData = "";
			// iterate the employees
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				parsedData += "\n "
						+ element.getAttributes().getNamedItem("type")
								.getNodeValue();
				NodeList name = element.getElementsByTagName("value");
				Element line = (Element) name.item(0);
				System.out
						.println("Name: " + getCharacterDataFromElement(line));
				parsedData += " : " + getCharacterDataFromElement(line);
			}

			System.out.println("/final data>>>"+parsedData);
//			tvParsedData.setText(parsedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * output : Name: John Title: Manager Name: Sara Title: Clerk
		 */

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
