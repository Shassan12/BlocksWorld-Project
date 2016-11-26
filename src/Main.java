
public class Main {

	public static void main(String[] args) {
		int gridSize = 4;
		PuzzleGrid grid = new PuzzleGrid(gridSize);
		grid.initiateGrid();
		SearchMethods bfs = new SearchMethods(gridSize);
		//int answer = bfs.startBreadthFirstSearch(grid);
		//int answer = bfs.startDepthFirstSearch(grid);
		//int answer = bfs.startHeuristicASearch(grid);
		int answer = bfs.startIterativeDeepeningSearch(grid);
		System.out.println(answer);
	}

}
