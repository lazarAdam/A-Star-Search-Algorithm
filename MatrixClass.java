

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
/*

 * 
 * /
 */
public class MatrixClass {

//all varibles used to create an object of type MatrixClass which represent each city as an object with attributes
private String U;
private String V;
private int distance;
private int heuristic;
private int columns;
private int rows;
private int finalCost;


public MatrixClass(){}



//object constructor 
public MatrixClass(String u, String v, int dis, int hu){
	
	this.V=v;
	this.U=u;
	this.distance=dis;
	this.heuristic=hu;
	this.finalCost=this.distance+this.heuristic;

}
	



// all the getter and setters
public String getU() {
	return U;
}


public String getV() {
	return V;
}


public int getDistance() {
	return distance;
}


public int getHeur() {
	return heuristic;
}


public int getRows() {
	return rows;
}


public int getColumns() {
	return columns;
}

public int getFinalCost() {
	return finalCost;
}

public void setColumns(int columns) {
	this.columns = columns;
}

public void setRows(int rows) {
	this.rows = rows;
}




// the main job for this method is to create two matrices of type string to copy all the data form the input files
//this primary step in creating a matrix with objects of cities which hold the attributes that this class provides
public void FileLoader() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
    /** create a filter that shows only files with .txt extension */
    
	 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    FileNameExtensionFilter  filter = new FileNameExtensionFilter("Text files", "txt");
    
    JFileChooser fileChooser = new JFileChooser();
    
    fileChooser.setFileFilter(filter);
    
    fileChooser.setDialogTitle("Select the two input files by holding CTRL");
   
    fileChooser.setCurrentDirectory(new File("."));
    fileChooser.setMultiSelectionEnabled(true);// when prompt select two files at he same time by holding ctrl
    fileChooser.showOpenDialog(fileChooser);
    
    File inputFile = fileChooser.getSelectedFiles()[0];//first file which is actual distances
    File inputFile2 = fileChooser.getSelectedFiles()[1];//second file which is the heuristic
    
    Scanner scanner0 = new Scanner(inputFile);//this scanner will be used to calculate the number rows and columns
    Scanner scanner1 = new Scanner(inputFile);//this scanner will be used for the actual distance file 
    Scanner scanner2 = new Scanner(inputFile2);//this scanner will be used for the heuristic file 
    
    
   
   this.columns = scanner0.nextLine().split(scanner0.delimiter().pattern()).length;//GET THE NUMBER OF COLUMNS
   this.rows = (int) Files.lines(inputFile.toPath(), Charset.defaultCharset()).count();//GET THE NUMBER OF ROWS
   String matrix[][] = new String[this.rows][this.columns];//Initialize Sting  matrix for  actual distance file 
   String matrix2[][] = new String[this.rows][this.columns];//Initialize String  matrix for  heuristic file 
   
   
   String []inputspt1;//this array is reserved for the split() call (actual distance file)
   String []inputspt2;//this array is reserved for the split() call (heuristic distance file)
   int i =0;
  
   
   // Loops that build the each matrix 
    while(scanner1.hasNextLine()){
    	
    	//grab next line from each file an 
    	String line1 = scanner1.nextLine();
    	String line2 = scanner2.nextLine();
    	
    	int j=0;
    	
    	
    	//Pattern matching for split function(Use space files)
    	inputspt1 = line1.split("\\s+");
    	inputspt2 = line2.split("\\s+");
    	//inputspt1 = line1.split("(?<=\\s)(?=\\S)");
    	
    	
    
    	// Go through the length of each line on each string form actual and heuristic files
    	while(j<inputspt2.length){
    		
    		matrix[i][j]=inputspt1[j];
    		matrix2[i][j]=inputspt2[j];
    		
    		j++;
    	}
    	i++;
    
    }
   
    
    
    // the result will be two matrices 
    
    makeEdgeObject(matrix,matrix2);// follow the call
	
}
	public void makeEdgeObject(String mx1[][], String mx2[][]){
			
		
		this.setRows(rows-1);
		this.setColumns(columns-1);
		
		
		MatrixClass[][] edgeMatrix= new MatrixClass[this.rows][this.columns];// Initialize a edgeMatrix of MatrixClass to hold the objects
		
		
		// loop to run through each cell in the matrix 
		for(int i=0;i<this.rows;i++){//row length
			for(int k=0;k<this.columns;k++){//Column length
			
				
				
				if(k==0){
					
					//create an object for each first column in each row (this will come handy in the A* implementation)
					//technically each first in each Column in the input file is has a - which means it is a null although 
					//the implementation of A* for this kind of input 
					edgeMatrix[i][k]=new MatrixClass(this.U=mx1[i+1][0],this.V=mx1[0][k+1],100000000,0);
				
					continue;
				}
				
				// a condition that checks for blocked paths and assign null to the object
				//note!: the condition only need to check the for blocked paths on the the actual distances matrix
				//if the distance from city a to b is blocked we don't consider the heuristics 
				if((mx1[i+1][k+1]).contains("-")){
					
					edgeMatrix[i][k]=null;
							}
				else
					edgeMatrix[i][k]=new MatrixClass(this.U=mx1[i+1][0],this.V=mx1[0][k+1],this.distance=Integer.parseInt(mx1[i+1][k+1]),this.heuristic=Integer.parseInt(mx2[i+1][k+1]));
					
		
			}
	
		}
		
		//call to setEdgeMatrix which will pass the edgeMatrix to the main algorithm  class
		ASTARalgo.setEdgeMatrix(edgeMatrix);
	}
	
	
		
	
	public String toString(){
		
		
		
		return this.U+" to "+this.V+" dist:"+this.distance+"heuristic:"+this.heuristic;
		
	}
	
	
	
	// RunMe method gets a call for the UI class which will start execution of all the methods that are used for this class and A* algorithm class(replaces the role of main )
	public static void RunMe() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		
		
		
	}

}
