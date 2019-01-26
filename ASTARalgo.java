

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;



public class ASTARalgo {


static MatrixClass edgeMatrix[][]; 
static ArrayList<node> openEdge;
static String str = "";
static String prevCity;
static ArrayList<String> list = new ArrayList<String>();
static node [][] map;
static boolean closededge[][];
static int startX, starty;
static int endx, endy; 


    public static void setEdgeMatrix(MatrixClass[][] edgeMatrix) {
    	ASTARalgo.edgeMatrix = edgeMatrix;
    }
    
    
    
    public static void setStartCell(int i, int j){
        startX = i;
        starty = j;
    }
    
    public static void setEndCell(int i, int j){
        endx = i;
        endy = j;
    }
    
    
    
    /**
     * 
     * @param tCase
     * @param rowSize
     * @param colSize
     * @param startingXcord
     * @param staringYcord
     * @param endingXcord
     * @param endingYcord
     * @throws FileNotFoundException
     */
    public static void initializer( int rowSize, int colSize, int startingXcord, int staringYcord, int endingXcord, int endingYcord) throws FileNotFoundException{
          
            //Reset
           map = new node[rowSize][colSize];
           closededge = new boolean[rowSize][colSize];
           openEdge = new ArrayList<node>();
           //Set start position
           setStartCell(startingXcord, staringYcord);  
           
           //Set End Location
           setEndCell(endingXcord, endingYcord);
           
           for(int i=0;i<rowSize;++i){
              for(int j=0;j<colSize;++j){
                  map[i][j] = new node(i, j);
                  
                
                  
                  if((edgeMatrix[i][j]!=null)&&(edgeMatrix[i][j].getDistance()!=0)){
                  
                map[i][j].finalCost =edgeMatrix[i][j].getFinalCost();
                  map[i][j].heuristicCost =edgeMatrix[i][j].getHeur();
                  
                  }
              }
           
           }
           edgeMatrix[startingXcord][staringYcord]=new MatrixClass(edgeMatrix[startingXcord][staringYcord].getU(),edgeMatrix[startingXcord][staringYcord].getV(),0,0);
           
           
           map[startingXcord][staringYcord].finalCost = 0;
           
       
           
           
           
            for(int i=0;i<rowSize;++i){
                for(int j=0;j<colSize;++j){
                   if(i==startingXcord&&j==staringYcord);
                   else if(i==endingXcord && j==endingYcord);
                   else if(((edgeMatrix[i][j]!=null)))
                	   
                	  ;
                   else{
                	   
                	   map[i][j]=null;
                	 ;}
                }
               
            }
      
           
           AS(rowSize,colSize );
    }
    
   
    public static void AS(int x, int y) throws FileNotFoundException{
        
    	openEdge.add(map[startX][starty]);	
        node currentCity;        
        
        //loops until the openEdge arraylist is empty
        while(openEdge.size() > 0){
        	
            currentCity = openEdge.get(0); //set currentCity
            openEdge.remove(0);//remove the first element of openEdge after assigning it to currentCity
            closededge[currentCity.i][currentCity.j]=true; 
            str = "";
            
            //Checks if the currentCity is the target city and if it is, passes the list to outputhandler and returns
            if((currentCity.j == endx)){ 
            	UI.outputHandler(list);
                return;
            }

            node targetcity;
            node tempcell;
        	node oldcell;
        	
        	//Loops in if currentCity is the start city
            if(map[currentCity.i][currentCity.j] == map[startX][starty]) {
            	prevCity= edgeMatrix[currentCity.i][0].getU().substring(0, 3); //gets the parent of it
            	for(int n= 1;n<x;n++){ //loops for all remaining columns of the row
                	if(map[currentCity.i][n] != null) {
                		targetcity = map[currentCity.i][n];
                		targetcity.costfromsource = edgeMatrix[currentCity.i][currentCity.j].getDistance() + edgeMatrix[targetcity.i][n].getDistance();
                		//evaluate the target city
                		evaluateCost(currentCity,targetcity,edgeMatrix[currentCity.i][currentCity.j].getDistance() + edgeMatrix[targetcity.i][n].getDistance());
                	}                	
                }
            }
            //Loops in if currentCity is not the start city
            else {
            	prevCity= edgeMatrix[currentCity.j][0].getU().substring(0, 3); //get the parent
            	for(int p=1; p<x;p++) { //loop through all the columns
            		if(map[currentCity.j][p] != null) {
            			targetcity = map[currentCity.j][p];
            			targetcity.costfromsource = currentCity.costfromsource + edgeMatrix[targetcity.i][p].getDistance();
            			//evaluate the target city
            			evaluateCost(currentCity,targetcity,targetcity.costfromsource);
            		}
            	}
            }
            	//Sorting of the openEdge list
            	Collections.sort(openEdge, new Comparator<node>() {
            		@Override
            		public int compare(node city1, node city2) {
            			//Checks each city's final cost and sorts them based on ascending size order
            			return Integer.compare(city1.getFinalCost(), city2.getFinalCost());
            		}
            	});
       		 	
            	int n = 0;
            	while( n < openEdge.size()){
            		tempcell = openEdge.get(n); //Initialize the cell based on the openList counter
            		oldcell = tempcell.exNode; //Get the parent cell of it
            		if(oldcell.j == 0  || map[currentCity.i][currentCity.j] == map[startX][starty] ) { 
            			str = str + edgeMatrix[tempcell.i][tempcell.j].getV() + " -> " + tempcell.finalCost + " ->  " + edgeMatrix[oldcell.i][0].getU().substring(0,3) + " /";
            		}
            		else {
                		str = str + edgeMatrix[tempcell.i][tempcell.j].getV() + " -> " + tempcell.finalCost + " -> " + edgeMatrix[oldcell.j][0].getU().substring(0,3) + " / ";
            		}n++;	 
            	}
            	//add the queue to the list
            	list.add(prevCity + "->" + str);
        	}
        //Pass the list to the outputHandler to print it to the output file
        
        	
        }
    
    
    
  
 
    
    
    /**
     * this method Does the main evaluation for neighboring nodes to see if they have a better path to the target city.
     * also sets the parent of it and updates the final cost, and the openEdge at the end.
     */
    static void evaluateCost(node current, node t, int cost){
   	 if(closededge[t.i][t.j])return;         
   	 int targetTotalCost = t.heuristicCost+cost;
        boolean inOpen = openEdge.contains(t);
        
        if(!inOpen || targetTotalCost<=t.finalCost){
        	        	
            t.finalCost = targetTotalCost;
            t.exNode = current;
            if(!inOpen)openEdge.add(t);
            
            int c = t.j;
            int b = t.i;
            node tcell;
            	int a = 0;
            	while( a < openEdge.size()) {
                	tcell = openEdge.get(a);
               
                	
                	if(openEdge.get(a).j == c && openEdge.get(a).i != b) {
                		if(tcell.finalCost > t.finalCost) {
                			openEdge.remove(a);
                			openEdge.add(t);
                			
                		}
                		else {
                			openEdge.remove(t);
                			}
                		
                	}a++;
                	}
        }
        
   }
    
}