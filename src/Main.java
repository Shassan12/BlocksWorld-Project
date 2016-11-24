
public class Main {

	public static void main(String[] args) {
		int gridSize = 2;
		PuzzleGrid grid = new PuzzleGrid(gridSize);
		grid.initiateGrid();
		BreadthFirstSearch bfs = new BreadthFirstSearch(gridSize);
		int answer = bfs.startBreadthFirstSearch(grid);
		System.out.println(answer);
	}

}
