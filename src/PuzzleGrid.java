import java.util.Scanner;

public class PuzzleGrid {
	private int gridSize;
	private char[][] tileList;
	//private String gridStatus;
	private Point agentPos;
	
	public PuzzleGrid(int gridSize){
		this.gridSize = gridSize;
		this.tileList = new char[gridSize][gridSize];
	}
	
	public Point getAgentPos(){
		return agentPos;
	}
	
	public char[][] getTileList(){
		return tileList;
	}
	
	public void setAgentPos(int xPos, int yPos){
		this.setTile('@', xPos, yPos);
		agentPos = new Point(xPos, yPos);
	}
	
	public void setTile(char c, int xCoord, int yCoord){
		tileList[xCoord][yCoord] = c;
	}
	
	public char getTile(int xPos, int yPos){
		return tileList[xPos][yPos];
	}
	
	public void initiateGrid(){
		Scanner scanner = new Scanner(System.in);
		String gridStatus = "";
		
		while(true){
			System.out.println("Input grid config as a string (e.g. aaaa for grid size 2):");
			gridStatus = scanner.next();
			
			if(gridStatus.length() == gridSize*gridSize){
				break;
			}else{
				System.out.println("invalid configuration. Configuration should have the same number of characters as the gridsize (" + gridSize+").");
			}
		}
		this.setGrid(gridStatus);
		/*int pos = 0;
		
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				char currentChar = gridStatus.charAt(pos);
				tileList[j][i] = currentChar;
				if(currentChar == '@'){
					this.agentPos = new Point(i,j);
				}
				pos += 1;
			}
		}*/
		
		//outputGrid();
	}
	
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
	
	public void copyGrid(PuzzleGrid grid){
		char[][] gridTileList = grid.getTileList();
		
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				tileList[j][i] = gridTileList[j][i];
			}
		}
	}
	
	public boolean checkForGoal(PuzzleGrid goalGrid){
		/*for(int i=0; i<gridSize*gridSize; i++){
			System.out.println(gridStatus);
			if((gridStatus.charAt(i) != goal.charAt(i))&&(gridStatus.charAt(i)!='@')){
				return false;
			}
		}*/
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
	
	public void outputGrid(){
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				System.out.print(tileList[j][i]+" ");
			}
			
			System.out.println();
		}
		
		System.out.println();
	}
}
