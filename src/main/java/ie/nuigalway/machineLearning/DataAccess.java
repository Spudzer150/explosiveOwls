package ie.nuigalway.machineLearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Author: Murphy Berry - 15489068 
 *
 */
public class DataAccess
{
	static String line = "";
	
	public static ArrayList<Instance> readData(String FileLocation){
		ArrayList<Instance> Data = new ArrayList<Instance>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(FileLocation));
		
		
		while((line = br.readLine()) != null){
		String[] StringData = line.split(",");
		Data.add(new Instance(StringData));
		}
		
		br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Data;
	}
  
	public static void writeData(ArrayList<String> predicted, ArrayList<String> actual, double accuracy){
		try {
			Writer fileWriter = new FileWriter("Output.txt");
			
			
			
			for (String classType : predicted) {
				fileWriter.write(classType + "; ");
			}
			fileWriter.write(" ");
			
			for (String classType : actual) {
				fileWriter.write(classType + "; ");
			}
			fileWriter.write(" ");
			
			fileWriter.write("Accuracy: " + accuracy * 100 + "%");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
}

