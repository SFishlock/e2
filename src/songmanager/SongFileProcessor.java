package songmanager;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class SongFileProcessor {
	/**
	 * Constructor
	 */
	public SongFileProcessor() {

	}

	/**
	 * Converts a SongObject into an XML file.
	 * 
	 * @param Obj
	 *            SongObject
	 * @param outputPath
	 *            Path to write file to
	 */
	public void writeSongObjectToXML(SongObject obj, String outputPath) {
		try {
			// Root element
			Element songElement = new Element("song");
			Document doc = new Document(songElement);

			// Singular elements
			Element titleElement = new Element("title");
			titleElement.setText(obj.getTitle());
			songElement.addContent(titleElement);

			Element artistElement = new Element("artist");
			artistElement.setText(obj.getArtist());
			songElement.addContent(artistElement);

			Element songLengthElement = new Element("songLength");
			songLengthElement.setText(String.valueOf(obj.getSongLength()));
			songElement.addContent(songLengthElement);

			Element averageTempoElement = new Element("averageTempo");
			averageTempoElement.setText(String.valueOf(obj.getAverageTempo()));
			songElement.addContent(averageTempoElement);

			Element startBeatElement = new Element("startBeat");
			startBeatElement.setText(String.valueOf(obj.getStartBeat()));
			songElement.addContent(startBeatElement);

			// Beat elements
			Element beatsElement = new Element("beats");
			Beat[] beats = obj.getBeats();
			for (int i = 0; i < beats.length; i++) {
				Element beatElement = new Element("beat");
				beatElement.setAttribute(new Attribute("time", String.valueOf(beats[i].getTime())));
				beatElement.setAttribute(new Attribute("measure", String.valueOf(beats[i].getMeasure())));
				beatsElement.addContent(beatElement);
			}
			songElement.addContent(beatsElement);

			// Note elements
			Element notesElement = new Element("notes");
			Note[] notes = obj.getNotes();
			for (int i = 0; i < notes.length; i++) {
				Element noteElement = new Element("note");
				noteElement.setAttribute(new Attribute("time", String.valueOf(notes[i].getTime())));
				noteElement.setAttribute(new Attribute("sustain", String.valueOf(notes[i].getSustain())));

				boolean[] buttons = notes[i].getButtons();
				for (int j = 0; j < 4; j++) {
					if (buttons[j]) {
						noteElement.setAttribute(new Attribute("button" + j, "1"));
					} else {
						noteElement.setAttribute(new Attribute("button" + j, "0"));
					}
				}

				notesElement.addContent(noteElement);
			}
			songElement.addContent(notesElement);

			// Output XML
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(outputPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads a game-ready XML file and produces a SongObject.
	 * 
	 * @param inputPath
	 */
	public SongObject readSongObjectFromXML(String inputPath) {
		try {
			// Create document from file
			SAXBuilder saxBuilder = new SAXBuilder();
			File inputFile = new File(inputPath);
			Document doc = saxBuilder.build(inputFile);

			// Set up variables for song information
			String title, artist;
			int songLength, startBeat, averageTempo;
			Note[] notes;
			Beat[] beats;

			// Retrieve singular elements
			Element songElement = doc.getRootElement();
			title = songElement.getChild("title").getText();
			artist = songElement.getChild("artist").getText();
			averageTempo = Integer.valueOf(songElement.getChild("averageTempo").getText());
			songLength = Integer.valueOf(songElement.getChild("songLength").getText());
			startBeat = Integer.valueOf(songElement.getChild("startBeat").getText());

			// Retrieve beats
			Element beatsElement = songElement.getChild("beats");
			List<Element> beatElements = beatsElement.getChildren();
			int beatCount = beatElements.size();
			beats = new Beat[beatCount];

			for (int i = 0; i < beatElements.size(); i++) {
				int time = beatElements.get(i).getAttribute("time").getIntValue();
				int measure = beatElements.get(i).getAttribute("measure").getIntValue();
				beats[i] = new Beat(time, measure);
			}

			// Retrieve notes
			Element notesElement = songElement.getChild("notes");
			List<Element> noteElements = notesElement.getChildren();
			int noteCount = noteElements.size();
			notes = new Note[noteCount];

			for (int i = 0; i < noteElements.size(); i++) {
				int time = noteElements.get(i).getAttribute("time").getIntValue();
				int sustain = noteElements.get(i).getAttribute("sustain").getIntValue();

				ArrayList<Integer> buttonsList = new ArrayList<Integer>();
				for (int j = 0; j < 4; j++) {
					int buttonActive = noteElements.get(i).getAttribute("button" + j).getIntValue();
					buttonsList.add(buttonActive);
				}

				boolean[] buttons = { false, false, false, false };
				for (int j = 0; j < 4; j++) {
					if (buttonsList.get(j) == 1) {
						buttons[j] = true;
					}
				}

				notes[i] = new Note(time, sustain, buttons);
			}
			notes = sortNotes(notes);

			// Create and return a SongObject
			SongObject obj = new SongObject(title, artist, songLength, averageTempo, startBeat, notes, beats);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Creates an object which represents the contents of a song file.
	 * 
	 * @param songObj
	 *            A song object for the song
	 * @param audioPath
	 *            The path to the audio file for the song
	 * @param coverPath
	 *            The path to the covert art of the song
	 * @return
	 */
	public SongFile createSongFile(String notesFilePath, String audioPath, String coverPath) {
		try {
			BufferedImage coverArt = ImageIO.read(new FileInputStream(coverPath));
			SongObject songObj = readSongObjectFromXML(notesFilePath);
			SongFile fileObj = new SongFile(coverArt, audioPath, songObj);
			return fileObj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Writes a SongFile object into a file.
	 * 
	 * First 4 bytes: size of XML file. Second 4 bytes: size of audio file.
	 * Third 4 bytes: size of image file. Rest: file contents.
	 * 
	 * @param songObj
	 *            A song object for the song
	 */
	public void writeSongFile(SongFile songFileObj, String filePath) {
		try {
			// Read in xml file
			String noteFilePath = "data/test/tempnotefile.xml";
			writeSongObjectToXML(songFileObj.getSong(), noteFilePath);
			File notesFile = new File(noteFilePath);
			byte[] notesFileBytes = new byte[(int) notesFile.length()];
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(notesFile));
			inStream.read(notesFileBytes);
			inStream.close();

			// Read in audio file
			File audioFile = new File(songFileObj.getAudioInputPath());
			byte[] audioFileBytes = new byte[(int) audioFile.length()];
			inStream = new BufferedInputStream(new FileInputStream(audioFile));
			inStream.read(audioFileBytes);
			inStream.close();

			// Read in image file
			BufferedImage image = songFileObj.getCoverArt();
			ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
			ImageIO.write(image, "png", byteOutStream);
			byte[] imageFileBytes = byteOutStream.toByteArray();
			byteOutStream.close();

			// Write to binary file
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(filePath));
			byte[] notesSizeBytes = ByteBuffer.allocate(4).putInt(notesFileBytes.length).array();
			byte[] audioSizeBytes = ByteBuffer.allocate(4).putInt(audioFileBytes.length).array();
			byte[] imageSizeBytes = ByteBuffer.allocate(4).putInt(imageFileBytes.length).array();
			outStream.write(notesSizeBytes);
			outStream.write(audioSizeBytes);
			outStream.write(imageSizeBytes);
			outStream.write(notesFileBytes);
			outStream.write(audioFileBytes);
			outStream.write(imageFileBytes);
			outStream.close();
		} catch (IOException e) {
			System.out.println("Error reading input files for writeSongFile: " + e);
		}
	}

	/**
	 * Reads a song file into a SongFile object.
	 * 
	 * @param inputPath
	 *            Path to the song file
	 * @return A song file object representing the contents of the song file
	 */
	public SongFile readSongFile(String inputPath) {
		try {
			// Read in SongFile
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(inputPath));

			byte[] notesSizeBytes = new byte[4];
			byte[] audioSizeBytes = new byte[4];
			byte[] imageSizeBytes = new byte[4];
			inStream.read(notesSizeBytes);
			inStream.read(audioSizeBytes);
			inStream.read(imageSizeBytes);
			int notesSize = ByteBuffer.wrap(notesSizeBytes).getInt();
			int audioSize = ByteBuffer.wrap(audioSizeBytes).getInt();
			int imageSize = ByteBuffer.wrap(imageSizeBytes).getInt();

			byte[] notesFileBytes = new byte[notesSize];
			byte[] audioFileBytes = new byte[audioSize];
			byte[] imageFileBytes = new byte[imageSize];
			inStream.read(notesFileBytes);
			inStream.read(audioFileBytes);
			inStream.read(imageFileBytes);
			inStream.close();

			// Get SongObject
			String xmlPath = "src/songmanager/tempnotefile.xml";
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(xmlPath));
			outStream.write(notesFileBytes);
			SongObject songObj = readSongObjectFromXML(xmlPath);
			String songTitle = songObj.getTitle().replaceAll(" ", "").toLowerCase();
			outStream.close();

			// Get audio file path
			String audioPath = "src/songmanager/" + songTitle + ".wav";
			outStream = new BufferedOutputStream(new FileOutputStream(audioPath));
			outStream.write(audioFileBytes);
			outStream.close();

			// Get cover art
			ByteArrayInputStream byteInStream = new ByteArrayInputStream(imageFileBytes);
			BufferedImage coverArt = ImageIO.read(byteInStream);

			// Create and return SongFile object
			return new SongFile(coverArt, audioPath, songObj);

		} catch (FileNotFoundException e) {
			System.out.println("Song file not found: " + e);
			System.exit(1);
		} catch (IOException e) {
			System.out.println("IO error in readSongFile: " + e);
			System.exit(1);
		}
		return null;
	}

	/**
	 * Reads all song files
	 * 
	 * @return List of all song files
	 */
	public static SongFile[] readAllSongFiles() {
		/*
		 * // Get names of all song files in data File dataFolder = new
		 * File("data"); File[] dataFiles = dataFolder.listFiles(); String[]
		 * filePaths = new String[dataFiles.length]; for (int i = 0; i <
		 * dataFiles.length; i++) { filePaths[i] = dataFiles[i].getPath(); }
		 * 
		 * // Read all song files into array SongFile[] songFiles = new
		 * SongFile[dataFiles.length]; SongFileProcessor processor = new
		 * SongFileProcessor(); for (int i = 0; i < dataFiles.length; i++) {
		 * songFiles[i] = processor.readSongFile(filePaths[i]); } return
		 * songFiles;
		 */

		// Read all data into SongFile objects and return
		File[] xmlFiles = (new File("data/xml")).listFiles();
		File[] audioFiles = (new File("data/audio")).listFiles();
		File[] imageFiles = (new File("data/image")).listFiles();
		SongFileProcessor processor = new SongFileProcessor();
		SongFile[] songFiles = new SongFile[xmlFiles.length];
		String xmlPath, audioPath, imagePath;
		for (int i = 0; i < xmlFiles.length; i++) {
			xmlPath = xmlFiles[i].getPath();
			audioPath = audioFiles[i].getPath();
			imagePath = imageFiles[i].getPath();
			songFiles[i] = processor.createSongFile(xmlPath, audioPath, imagePath);
		}
		return songFiles;
	}

	/**
	 * Sorts a Note array into time order.
	 * 
	 * @param notes
	 *            Unsorted Note array
	 * @return Sorted Note array
	 */
	public Note[] sortNotes(Note[] notes) {
		// Add each element of array to a TreeMap
		TreeMap<Integer, Note> notesMap = new TreeMap<Integer, Note>();
		int noteArrayLength = notes.length;
		for (int i = 0; i < noteArrayLength; i++) {
			notesMap.put(notes[i].getTime(), notes[i]);
		}

		// Extract from the TreeMap in order into a new array
		Note[] newNotes = new Note[noteArrayLength];
		Integer[] notesMapKeysArray = new Integer[noteArrayLength];
		Integer[] notesMapKeys = notesMap.keySet().toArray(notesMapKeysArray);
		for (int i = 0; i < noteArrayLength; i++) {
			newNotes[i] = notesMap.get(notesMapKeys[i]);
		}
		return newNotes;
	}

	/**
	 * Main method used for debugging
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Convert RS2 Bass to SongFile XML EofRepacker repacker = new
		 * EofRepacker(); SongObject obj =
		 * repacker.getSongObjectFromBassFile("data/PART REAL_BASS_RS2.xml");
		 * SongFileProcessor processor = new SongFileProcessor();
		 * processor.writeSongObjectToXML(obj, "data/xml/laser.xml");
		 */

		/*
		 * Create a song object from the SongFile XML SongFileProcessor
		 * processor = new SongFileProcessor(); SongObject obj =
		 * processor.readSongObjectFromXML("src/songmanager/songfile.xml");
		 */

		/*
		 * Create Song File String imagePath = "src/songmanager/tetris.png";
		 * String audioPath = "src/songmanager/Tetris.wav"; String notesPath =
		 * "src/songmanager/songfile.xml"; BufferedImage image; try { image =
		 * ImageIO.read(new File(imagePath)); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); image = null; }
		 * SongFileProcessor processor = new SongFileProcessor(); SongObject
		 * songObj = processor.readSongObjectFromXML(notesPath); SongFile
		 * songFileObj = new SongFile(image, audioPath, songObj);
		 * processor.writeSongFile(songFileObj, "data/tetris.song"); SongFile
		 * readFile = processor.readSongFile("src/songmanager/tetris.song");
		 */

		/*
		 * Test readAllSongFiles() SongFile[] songFiles =
		 * SongFileProcessor.readAllSongFiles();
		 * System.out.println(songFiles.length);
		 */
	}
}
