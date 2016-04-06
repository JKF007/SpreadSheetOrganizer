import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class excelOrganizer {
	
	
	public static void main(String args[]) throws IOException{
		
		ArrayList<String> filteredMainArray  = new ArrayList<String>();
		
		excelOrganizer.rowsFilter mainFilter = new excelOrganizer.rowsFilter();
		
		BufferedReader uneditedFile = new BufferedReader ( new FileReader("coh2dps.csv") );
					
		filteredMainArray = mainFilter.filterFile(uneditedFile);
		
		excelOrganizer.printResult(filteredMainArray, "filteredCoh2Dps.csv");		
		
		ArrayList<String> subFilter = new ArrayList<String>();
		
		
		subFilter.add("USAInf.txt");
		subFilter.add("wehrInf.txt");
		subFilter.add("SovInf.txt");
		subFilter.add("okwInf.txt");
		subFilter.add("britInf.txt");
		
		subFilter.add("USAVeh.txt");		
		subFilter.add("wehrVeh.txt");
		subFilter.add("SovVeh.txt");
		subFilter.add("okwVeh.txt");
		subFilter.add("britVeh.txt");
				
		ArrayList<String> sortedArray  = new ArrayList<String>();
		
		for (int x = 0 ; x < subFilter.size() ; x++){
			
			excelOrganizer.rowsFilter tempFilter = new excelOrganizer.rowsFilter( subFilter.get(x) );
			
			sortedArray.add( subFilter.get(x) );
			
			sortedArray.addAll( tempFilter.filterArray(filteredMainArray) ) ;						
		}
		
		excelOrganizer.printResult(sortedArray, "sortedCoh2Dps.csv");
				
	}//end of main	
	
	public static void printResult(ArrayList<String> resultArray, String fileName)throws IOException{
		
		Path destination = Paths.get(fileName);
		
		Files.write(destination, resultArray, Charset.forName("UTF-8") );		
		
	}
	
	static class rowsFilter{
			
		private ArrayList<String>  relevantWeapons = new ArrayList<String>();
		
		public rowsFilter()throws IOException{		
						
			BufferedReader inputFile = new BufferedReader ( new FileReader("small_arms.txt") );		
			
			while (inputFile.ready()){
				String relevantName = inputFile.readLine();
				relevantWeapons.add(relevantName);				
			}
			inputFile.close();
		}//end of default constructor	
		
		public rowsFilter(String fileName)throws IOException{		
			
			BufferedReader inputFile = new BufferedReader ( new FileReader(fileName) );		
			
			while (inputFile.ready()){
				String relevantName = inputFile.readLine();
				relevantWeapons.add(relevantName);				
			}			
			inputFile.close();
		}//end of assignment constructor	
		
		public boolean contains(String weaponName ){
			
			return relevantWeapons.contains(weaponName);
		}//end of method contains		
		
		public ArrayList<String> filterFile(BufferedReader inputList)throws IOException{
			
			ArrayList<String> filteredList  = new ArrayList<String>();
			
			while (inputList.ready()){
				String row = inputList.readLine();	
				String [] tokens = row.split(",");
				String weaponName = tokens[0];
					
				if ( this.contains(weaponName) ){
					filteredList.add(row);					
				}			
			}
			
			return filteredList; 
			
		}//end of filterFile
		
		public ArrayList<String> filterArray(ArrayList<String> inputArray){
			
			ArrayList<String> filteredArray  = new ArrayList<String>();
			
			for (String row: inputArray){
				String [] tokens = row.split(",");
				String weaponName = tokens[0];
					
				if ( this.contains(weaponName) ){
					filteredArray.add(row);					
				}			
			}
			
			return filteredArray; 
			
		}//end of method filterArray
		
	}//end of class rowsFilter	
	
}//end of class excelOrganizer


