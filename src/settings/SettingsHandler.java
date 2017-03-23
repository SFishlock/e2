package settings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class SettingsHandler {

	private String filePath;

	/**
	 * Constructor for filepath
	 */
	public SettingsHandler() {
		filePath = "src/settings/settings.xml";
	}

	/**
	 * Changes the saved value of a particular setting.
	 * 
	 * @param settingName
	 *            Name of the setting to be changed
	 * @param newValue
	 *            New value of the setting (as a string)
	 */
	public void changeSetting(String settingName, String newValue) {
		try {
			// Create document from file
			SAXBuilder saxBuilder = new SAXBuilder();
			File file = new File(filePath);
			Document doc = null;
			try {
				doc = saxBuilder.build(file);
			} catch (IOException e) {
				System.out.println("Error reading Settings file: " + e);
				System.exit(1);
			}
			Element settingsElement = doc.getRootElement();

			// Retrieve and change the requested element
			Element elem = settingsElement.getChild(settingName);
			elem.setText(newValue);

			// Output XML back to file
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			try {
				xmlOutput.output(doc, new FileWriter(filePath));
			} catch (IOException e) {
				System.out.println("Error writing XML to file: " + e);
			}
		} catch (JDOMException e) {
			System.out.println("Error parsing Settings file with JDOM: " + e);
			System.exit(1);
		}
	}

	/**
	 * Reads a particular setting from the settings file
	 * 
	 * @param settingName
	 *            Name of the setting to be read
	 * @return Value of the setting (as a string)
	 */
	public String readSetting(String settingName) {
		try {
			// Create document from file
			SAXBuilder saxBuilder = new SAXBuilder();
			File file = new File(filePath);
			Document doc = null;
			try {
				doc = saxBuilder.build(file);
			} catch (IOException e) {
				System.out.println("Error reading Settings file: " + e);
				System.exit(1);
			}
			Element settingsElement = doc.getRootElement();

			// Retrieve the requested element
			Element elem = settingsElement.getChild(settingName);
			return elem.getText();
		} catch (JDOMException e) {
			System.out.println("Error parsing Settings file with JDOM: " + e);
			System.exit(1);
			return null;
		}
	}

	/**
	 * Returns a HashMap of setting names to setting values for all settings
	 */
	public HashMap<String, String> readAllSettings() {
		try {
			// Create document from file
			SAXBuilder saxBuilder = new SAXBuilder();
			File file = new File(filePath);
			Document doc = null;
			try {
				doc = saxBuilder.build(file);
			} catch (IOException e) {
				System.out.println("Error reading Settings file: " + e);
				System.exit(1);
			}
			Element settingsElement = doc.getRootElement();

			// Get all children of the element
			List<Element> elementList = settingsElement.getChildren();

			// Add names and value of each child to the HashMap and return
			HashMap<String, String> settings = new HashMap<String, String>();
			for (int i = 0; i < elementList.size(); i++) {
				Element elem = elementList.get(i);
				settings.put(elem.getName(), elem.getText());
			}
			return settings;
		} catch (JDOMException e) {
			System.out.println("Error parsing Settings file with JDOM: " + e);
			System.exit(1);
			return null;
		}
	}

	/**
	 * Main method for debugging
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Test the SettingsHandler SettingsHandler h = new SettingsHandler();
		 * System.out.println(h.readSetting("testSetting"));
		 * h.changeSetting("testSetting", "32");
		 * System.out.println(h.readSetting("testSetting"));
		 * h.changeSetting("testSetting", "68");
		 * System.out.println(h.readSetting("testSetting"));
		 */
	}
}
