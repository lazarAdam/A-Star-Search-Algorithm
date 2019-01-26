import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

// this Ui uses javaFX Application API
public class UI extends Application {

	
	// variables used for the create the user interface 
	private static ArrayList<String> CityList = new ArrayList<String>();
	private Label lb0 = new Label("Note!: after prompet for file selection \nSelect the two input files by holding CTRL.\n"
			+ "Please use space input files! (｡◕‿◕｡)");
	private Label lb1 = new Label("Select the starting city");
	private Label lb2= new Label("select the target city");
	private  ComboBox<String> bx1= new ComboBox<String>() ;
	private  ComboBox<String> bx2= new ComboBox<String>() ;
	private Button bt1= new Button("Find Path");
	public static String SourceCity;
	public static String GoalCity;
	public static int SOIndex=0;
	public static int DEIndex=0;
	
	  
	  

	  
	public void start(Stage primaryStage) {
		
		
		
	
		//grid pane for holding all the nodes 
		GridPane pane1 = new GridPane();
		pane1.setAlignment(Pos.TOP_RIGHT);
		pane1.setHgap(0);
		pane1.setVgap(10);
		pane1.add(lb1, 0, 0);
		pane1.add(bx1, 1, 0);
		pane1.add(lb2, 0, 1);
		pane1.add(bx2, 1, 1);
		pane1.add(bt1,1,2);
		pane1.add(lb0, 0, 3);
		
	// frame of type HBOX to hold a pane
		HBox frame = new HBox(30);
		frame.setPadding(new Insets(15.5, 25.5, 55.5, 50));
		frame.setAlignment(Pos.CENTER);
		frame.getChildren().add(pane1);
		frame.setSpacing((30.0));
		
		//a scene for the frame
		Scene scene = new Scene(frame);
		
		
		//primaryStage creates a plane space to hold the scene
		primaryStage.setWidth(500);
		primaryStage.setHeight(265);
		primaryStage.setTitle("A*");
		primaryStage.setScene(scene); 
		primaryStage.show();
		primaryStage.setResizable(false);
		bx1.getItems().addAll(CityList);
		bx2.getItems().addAll(CityList);
		
	
		// methods for handling the events 
		bx1.addEventHandler(ActionEvent.ACTION, (e)-> {
			
			SourceCity = bx1.getValue();
			
		
			
		});
		
		
		bx2.addEventHandler(ActionEvent.ACTION, (e)-> {
		
			GoalCity = bx2.getValue();
			
			
			
		});
		
		bt1.addEventHandler(ActionEvent.ACTION, (e)-> {
			try {
				RunALL();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		});
		
	}
	
	
	//calculate the index of the staring city and ending city
	public void FindeCityIndex() throws IOException{
		  
		 int k=0;
		
		 while(k<CityList.size()){
			 
			if(CityList.get(k).compareToIgnoreCase(SourceCity)==0){
				
				SOIndex=k;

				
				k++;
			}
			
			else if(CityList.get(k).compareToIgnoreCase(GoalCity)==0){
				
				DEIndex=k;
			
				
				k++;
			}
			
			else
				k++;
		 }
		  
		 	
		 
		 
		 
	  }
	
	
	//method that will generate all the cities to the comboBox fields
	public static void InputHnandler() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	    
	    
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
	    FileNameExtensionFilter  filter = new FileNameExtensionFilter("Text files", "txt");
	    
	    JFileChooser fileChooser = new JFileChooser();
	    
	    fileChooser.setFileFilter(filter);
	    
	    fileChooser.setDialogTitle("Select actual or heuristic input files to genrate the citites");
	    
	    fileChooser.setCurrentDirectory(new File("."));
	    
	    fileChooser.showOpenDialog(fileChooser);
	    
	    File inputFile = fileChooser.getSelectedFile();
	    
	    Scanner scanner = new Scanner(inputFile);
	    
	    
	   
	    
	    
	    while(scanner.hasNextLine()){
	  	  
	  	 String input = scanner.nextLine();
	  	  input=input.split(" ")[0];
	  	 
	  	if(input.contains("TO")||input.contains("TW")){continue;}
	  	  
	  	  CityList.add(input);
	    
	        }
	        
	  
	    

	    
	}
	
	/**
	 * 
	 * @param list
	 * @throws FileNotFoundException
	 */
	   public static void outputHandler(ArrayList<String> list) throws FileNotFoundException {
	      	
	      	String SourceToTarget =UI.SourceCity + " to " + UI.GoalCity;
	     	String outPutString = SourceToTarget.concat( ".txt" );
	      	File outputFile = new File(outPutString);
	  		
	  		PrintWriter file = new PrintWriter(outputFile);
	  		
	  		String string;
	      	
	  		
	  		file.println("The result of this path search starts form "+SourceCity+" to "+GoalCity+"\r\n");
	      	file.println("»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»");
	      	int b=0;
	      	while( b < list.size()) {
	      		string = list.get(b);
	      		file.println(string);
	      	b++;}
	      	
	      	file.close();
	  		 	
	      	System.out.println("The path is  in the text file located at: "+ outputFile.getAbsolutePath());
	      	
	      }
	
	   //this method handles all the calls necessary to complete the execution of all the program components in the correct order
	   public void RunALL() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException{
		   FindeCityIndex();
		   MatrixClass run = new MatrixClass();
			run.FileLoader();
			ASTARalgo.initializer( run.getRows(), run.getColumns(), UI.SOIndex, 0, UI.DEIndex, 0);
			outputHandler(ASTARalgo.list);
			System.exit(0);
	   }
	   
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

		// a call to inputHnadler method that generates the string of the cities into comoboBox fields
		InputHnandler();
		//run the UI application
		Application.launch(args);
		
		
	
		
		
	}
}
