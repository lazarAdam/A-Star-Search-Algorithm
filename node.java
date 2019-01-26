

public class node {
	int heuristicCost = 0; 
    int finalCost = 0; 
    int i, j; 
    int costfromsource; 
    node exNode;
    
    node(int i, int j){
        this.i = i;
        this.j = j;
    }
    
    
    
    @Override
    public String toString(){
        return "["+this.i+", "+this.j+"]";
    }



	public int getFinalCost() {
		return finalCost;
	}
}
