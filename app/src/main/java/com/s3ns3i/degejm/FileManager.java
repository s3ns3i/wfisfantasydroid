package com.s3ns3i.degejm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
	private static final String TAG_ID = "player_id";
	private static final String FILE_NAME = "DO_NOT_LOOK_HERE.dkb";
	
	/**
	 * @param filesDir - you can get a file directory from context.getFilesDir() (if you want a default apps internal directory).
	 * @param fileName - specify a files name that you want to read
	 */
	public static String readFile(File filesDir, String fileName) throws FileNotFoundException, IOException{
		File file = new File(filesDir, fileName);
		FileInputStream inputStream = new FileInputStream(file);
		int checker = inputStream.read();
		char buffer = (char) checker;
		String data = new String();
		while( checker != -1){
			data += buffer;
			checker = inputStream.read();
			buffer = (char) checker;
		}
		inputStream.close();
		return data;
		
	}
	
	/**
	 * Method that can write data into file in given directory.
	 * Warning! Keep in mind that if you will give a name of the file that already exist, this method will overwrite it!
	 * @author s3ns3i
	 * @param filesDir - you can get a file directory from context.getFilesDir() (if you want a default apps internal directory.
	 * @param fileName - specify a files name that you want to write into.
	 * @param content - Put here data that you want to write into file.
	 */
	public static void writeFile(File filesDir, String fileName, String content) throws FileNotFoundException, IOException{
		File file = new File(filesDir, fileName);
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(content.getBytes());
		outputStream.close();
	}
	
	public static String readPlayersIDFromFile() throws FileNotFoundException, IOException{
		File file = new File(FILE_NAME, TAG_ID);
		FileInputStream inputStream = new FileInputStream(file);
		int checker = inputStream.read();
		char buffer = (char) checker;
		String data = new String();
		while( checker != -1){
			data += buffer;
			checker = inputStream.read();
			buffer = (char) checker;
		}
		inputStream.close();
		return data;
		
	}
}
