import java.util.Scanner;

public class PuzzleGrid {
	private int gridSize;
	private char[][] tileList;
	private String gridStatus;
	
	public PuzzleGrid(int gridSize){
		this.gridSize = gridSize;
		this.tileList = new char[gridSize][gridSize];
		initiateGrid();
	}
	
	private void initiateGrid(){
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.println("Input grid config as a string (e.g. aaaa for grid size 2):");
			this.gridStatus = scanner.next();
			
			if(gridStatus.length() == gridSize*gridSize){
				break;
			}else{
				System.out.println("invalid configuration. Configuration should have the same number of characters as the gridsize (" + gridSize+").");
			}
		}
		
		int pos = 0;
		
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				tileList[i][j] = gridStatus.charAt(pos);
				pos += 1;
			}
		}
		
		outputGrid();
	}
	
	private boolean checkForGoal(String goal){
		int pos = 0;
		for(int i=0; i<gridSize; i++){
			if(gridStatus.charAt(pos) != goal.charAt(pos)){
				return false;
			}
		}
		
		return true;
	}
	
	private void outputGrid(){
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				System.out.print(tileList[i][j]+" ");
			}
			
			System.out.println();
		}
	}
}
