package ie.nuigalway.machineLearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Murphy Berry - 15489068 
 *
 */
public class DataReader
{
	static String FileLocation = "C:\\Users\\murph\\Desktop\\owls.csv";
	static String line = "";
	
	public static ArrayList<Instance> readData(){
		ArrayList<Instance> Data = new ArrayList<Instance>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(FileLocation));
		
		
		while((line = br.readLine()) != null){
		String[] StringData = line.split(",");
		Data.add(new Instance(StringData));
		}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		return Data;
	}
  
    
}

