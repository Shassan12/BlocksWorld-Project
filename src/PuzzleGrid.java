import java.util.Scanner;

//class to store a grid state which is used as a node
public class PuzzleGrid implements Comparable<PuzzleGrid>{
	private int gridSize;
	private char[][] tileList;
	private Point agentPos;
	private int distanceFromGoal;
	private int distanceFromRoot;
	private int evalValue;
	
	public PuzzleGrid(int gridSize){
		this.gridSize = gridSize;
		this.tileList = new char[gridSize][gridSize];
	}
	
	//return position of the agent
	public Point getAgentPos(){
		return agentPos;
	}
	
	//return the list of tiles
	public char[][] getTileList(){
		return tileList;
	}
	
	//return the number of moves needed to reach the goal from this node
	public int getDistanceFromGoal(){
		return distanceFromGoal;
	}
	
	//sets the number of moves away this node is from the root node
	public void setDistanceFromGoal(int distanceFromGoal){
		this.distanceFromGoal = distanceFromGoal;
	}
	
	//return the number of moves away this node is from the root node
	public int getDistanceFromRoot(){
		return distanceFromRoot;
	}
	
	//set the number of moves away this node is from the root node
	public void setDistanceFromRoot(int distanceFromRoot){
		this.distanceFromRoot = distanceFromRoot;
	}
	
	//return the result of this nodes evaluation function
	public int getEvalValue(){
		return evalValue;
	}
	
	//set the result of this nodes evaluation function
	public void setEvalValue(int evalValue){
		this.evalValue = evalValue;
	}
	
	//set the position of the agent
	public void setAgentPos(int xPos, int yPos){
		this.setTile('@', xPos, yPos);
		agentPos = new Point(xPos, yPos);
	}
	
	//set the character of a specific tile
	public void setTile(char c, int xCoord, int yCoord){
		tileList[xCoord][yCoord] = c;
	}
	
	//returns the character at a specific tile
	public char getTile(int xPos, int yPos){
		return tileList[xPos][yPos];
	}
	
	//Gets the configuration of this grid from the user (used for the state of the root node)
	public void initiateGrid(){
		Scanner scanner = new Scanner(System.in);
		String gridStatus = "";
		
		while(true){
			System.out.println("Input grid config as a string (e.g. **A@ for grid size 2):");
			gridStatus = scanner.next();
			
			if(gridStatus.length() == gridSize*gridSize){
				break;
			}else{
				System.out.println("invalid configuration. Configuration should have the same "
						+ "number of characters as the gridsize (" + gridSize+").");
			}
		}
		this.setGrid(gridStatus);
	}
	
	//creates this nodes grid
	public void setGrid(String gridStatus){
		int pos = 0;

		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				char currentChar = gridStatus.charAt(pos);
				tileList[j][i] = currentChar;
				if(currentChar == '@'){
					this.agentPos = new Point(i,j);
				}
				pos += 1;
			}
		}
	}
	
	//copies the grid of a different node to this nodes grid
	public void copyGrid(PuzzleGrid grid){
		char[][] gridTileList = grid.getTileList();
		
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				tileList[j][i] = gridTileList[j][i];
			}
		}
	}
	
	//checks if this nodes grid is the same as the goalstates grid
	public boolean checkForGoal(PuzzleGrid goalGrid){
		char[][] goalGridList = goalGrid.getTileList();
		char gridChar = ' ';
		
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				gridChar = this.tileList[j][i];
				if(gridChar == '@'){gridChar = '*';}
				if(gridChar != goalGridList[j][i]){
					return false;
				}
			}
		}
		
		return true;
	}
	
	//output the state of this grid
	public void outputGrid(){
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				System.out.print(tileList[j][i]+" ");
			}
			
			System.out.println();
		}
		
		System.out.println();
	}

	//compare function that compares this nodes evaluation function to another node
	public int compareTo(PuzzleGrid g2) {
		if(this.getEvalValue() < g2.getEvalValue()){
			return -1;
		}
		
		if(this.getEvalValue() > g2.getEvalValue()){
			return 1;
		}
		
		return 0;
	}
}
