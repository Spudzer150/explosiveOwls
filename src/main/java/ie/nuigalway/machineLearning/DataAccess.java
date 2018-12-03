package ie.nuigalway.machineLearning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
  /**
   * A method to print out the predicted and the actual classifications to a text file
   * 
   * @param predicted
   * @param actual
   * @param accuracy
   */
	public static void writeData(ArrayList<String> predicted, ArrayList<String> actual, double accuracy){
		try {
			Writer fileWriter = new FileWriter("Output.txt", true);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			
			
			bw.write("Predicted: ");
			for (String classType : predicted) {
				bw.write(classType + "; ");
			}
			bw.newLine();
			
			bw.write("Actual: ");
			for (String classType : actual) {
				bw.write(classType + "; ");
			}
			bw.newLine();
			
			bw.write("Accuracy: " + accuracy * 100 + "%");
			
			bw.flush();
			fileWriter.flush();
			
			bw.close();
			fileWriter.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
}

