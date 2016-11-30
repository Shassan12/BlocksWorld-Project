public class Main {

	public static void main(String[] args) {
		Choice userChoice = new Choice();
		int gridSize = userChoice.getSize();
		PuzzleGrid grid = new PuzzleGrid(gridSize);
		grid.initiateGrid();
		SearchMethods bfs = new SearchMethods(gridSize);
		int choice;
		
		while(true){
			int answer = 0;
			System.out.println("Enter 1 for bfs, 2 for dfs, 3 for A*, 4 for IDS and 5 to exit");
			choice = userChoice.getChoice();
			
			if(choice == 1){answer = bfs.startBreadthFirstSearch(grid);}
			if(choice == 2){answer = bfs.startDepthFirstSearch(grid);}
			if(choice == 3){answer = bfs.startHeuristicASearch(grid);}
			if(choice == 4){answer = bfs.startIterativeDeepeningSearch(grid);}
			if(choice == 5){break;}
			
			System.out.println(answer);
		}
	}
}
