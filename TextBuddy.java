/**
 * author: zhang chenwei
 * This is to help user write, delete, clear and display content in file
 * File will be saved when user exit the program.Sample format is as below:
 * 
 * c:>java TextBuddy TestInput.txt
 * Welcome to TextBuddy. TestInput.txt is ready for use
 * command: add jumped over the moon
 * added to TestInput.txt : jumped over the moon
 * command: display
 * 1. jumped over the moon
 * command:delete 1
 * deleted from TestInput.txt: jumped over the moon
 * command:display
 * TestInput.txt is empty
 * command: clear
 * all content deleted from TestInput.txt
 * command: exit
 * 
 * c:>
 * */

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class TextBuddy {
	public static String fileName;
	public static ArrayList<String> content;
	//assume input format is correct eg: delete 1
	//Content in input file will be kept instead of being overwrite
	public static void main(String[] argv) throws Exception{

		if(argv.length != 1) {
			System.err.println("Invalid command line, exactly one argument required");
			System.exit(1);
		}
		fileName = argv[0];
		System.out.println("Welcome to TextBuddy."+fileName+" is ready for use. ");

		content = readFileToList();
		BufferedReader readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));//read from keyboard;	
		while (true) {
			System.out.print("command: ");
			StringTokenizer st = new StringTokenizer(readFromKeyboard.readLine());
			String command = st.nextToken();
			executeCommand(st, command);	
		}		
	}


	public static void executeCommand(StringTokenizer st, String command)
					throws Exception {
		switch(command.toLowerCase()){
		case "add": {
			doAdd(st);
			break;
		}
		case "display":	{
			doDisplay();
			break;
		}
		case "delete": {
			doDelete(st);
			break;
		}
		case "clear": {
			doClear();
			break;
		}
		case "sort": {
			/*doSort();*/
			break;
		}
		case "search":{
			/*doSearch(st); */
			break;
		}

		case "exit": {
			doExit();
		}
		default :
			System.out.println("WTF IS THIS COMMAND?!");
		}
	}


	/**************************************** level 2**********************************************************************************************************/
	public static void doExit() throws Exception {
		writeIntoFile();
		System.exit(0);
	}



	public static void doAdd(
			StringTokenizer st) {
		String whatToAdd = st.nextToken();
		while(st.hasMoreElements())
			whatToAdd+=" "+st.nextToken();					
		content.add(whatToAdd);
		System.out.println("added to "+fileName+": "+whatToAdd);
	}



	public static void doDisplay() {
		if(content.size() ==0)
			System.out.println(fileName+" is empty. ");
		else{
			int j = 1;
			for(String line: content){
				System.out.println(j+". "+line);
				j++;
			} 
		}
	}



	public static void doDelete(
			StringTokenizer st) {
		String lineToDelete = st.nextToken();
		//there is no line to delete
		if(lineToDelete.length()!=0){
			String whatRemoved = content.remove(Integer.parseInt(lineToDelete)-1);
			System.out.println("deleted from "+fileName+": "+whatRemoved);
		}
		else
			System.out.println("Which line to delete?");
	}



	public static void doClear() {
		content.clear();
		System.out.println("all content deleted from "+fileName);
	}



	public static void doSort() {
		Collections.sort(content);
		System.out.println(fileName+" sorted.");
	}



	public static void doSearch(StringTokenizer st) {
		int j = 1;
		String searchingFor = st.nextToken();
		for(String line: content){
			if(line.contains(searchingFor)){
				System.out.println(j + ". "+line);
				j++;
			}
		}
	}

	/**************************************** level 3**********************************************************************************************************/

	//Read the content of the input file into an arraylist of string
	public static ArrayList<String> readFileToList() throws Exception{

		String line = null;	
		ArrayList<String> content = new ArrayList<>();

		BufferedReader readFromFile = new BufferedReader(new FileReader(fileName));
		while ((line = readFromFile.readLine()) != null) 
			content.add(line);
		readFromFile.close();

		return content;
	}

	//Write from the ArrayList into the inputfile
	public static void writeIntoFile() throws Exception {

		PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
		for(String line: content)
			pw.println(line);
		pw.close();	
	}

}
