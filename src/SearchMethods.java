import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class SearchMethods {
	private int gridSize;
	private PuzzleGrid goalGrid;
	private ArrayList<Point> goalPositions;
	private FileWriter fileWriter;
	
	public SearchMethods(int gridSize){
		this.gridSize = gridSize;
		this.fileWriter = new FileWriter(gridSize);
		this.getGoalState();
	}
	
	private void getGoalState(){
		Scanner scanner = new Scanner(System.in);
		String goalState = "";
		
		while(true){
			System.out.println("Input goal state config as a string (e.g. **A@ for grid size 2):");
			goalState = scanner.next();
			
			if(goalState.length() == gridSize*gridSize){
				break;
			}else{
				System.out.println("invalid goal state. Goal state should have the same number of characters as the gridsize (" + gridSize+").");
			}
		}
		
		goalGrid = new PuzzleGrid(gridSize);
		goalGrid.setGrid(goalState);;
	}
	
	public int startBreadthFirstSearch(PuzzleGrid rootNode){
		int numOfNodesSearched = 0;
		String fileName = "bfsResults.txt";
		PuzzleGrid node = rootNode;
		Queue<PuzzleGrid> frontier = new LinkedList<PuzzleGrid>();
		frontier.add(rootNode);
		fileWriter.deleteFile(fileName);
		
		while(!frontier.isEmpty()){
			node = frontier.poll();
			node.outputGrid();
			fileWriter.saveNodeToFile(fileName, node);
			numOfNodesSearched += 1;
			
			if(node.checkForGoal(goalGrid)){
				fileWriter.saveNodesExpanded(fileName, numOfNodesSearched, frontier.size());
				return numOfNodesSearched;
			}
			
			if(canMoveRight(node)){
				frontier.add(moveRight(node));
			}
			
			if(canMoveLeft(node)){
				frontier.add(moveLeft(node));
			}
			
			if(canMoveUp(node)){
				frontier.add(moveUp(node));
			}
			
			if(canMoveDown(node)){
				frontier.add(moveDown(node));
			}
		}
		
		return numOfNodesSearched;
	}
	
	public int startDepthFirstSearch(PuzzleGrid rootNode){
		int numOfNodesSearched = 0;
		String fileName = "dfsResults.txt";
		Random rand = new Random();
		fileWriter.deleteFile(fileName);
		PuzzleGrid node = rootNode;
		Queue<PuzzleGrid> frontier = new LinkedList<PuzzleGrid>();
		frontier.add(rootNode);
		
		while(!frontier.isEmpty()){
			node = frontier.poll();
			node.outputGrid();
			System.out.println(frontier.size());
			numOfNodesSearched += 1;
			fileWriter.saveNodeToFile(fileName, node);
			boolean childMade = false;
			
			if(node.checkForGoal(goalGrid)){
				fileWriter.saveNodesExpanded(fileName, numOfNodesSearched, numOfNodesSearched);
				return numOfNodesSearched;
			}
			
			while(!childMade){
				int choice = rand.nextInt(4);
				
				if(choice == 0){
					if(canMoveRight(node)){
						frontier.add(moveRight(node));
						childMade = true;
					}
				}
				
				if(choice == 1){
					if(canMoveLeft(node)){
						frontier.add(moveLeft(node));
						childMade = true;
					}
				}
				
				if(choice == 2){
					if(canMoveUp(node)){
						frontier.add(moveUp(node));
						childMade = true;
					}
				}
				
				if(choice == 3){
					if(canMoveDown(node)){
						frontier.add(moveDown(node));
						childMade = true;
					}
				}
			}
		}
		
		return numOfNodesSearched;
	}
	int nodesStored = 0;
	int nodesSearched = 0;
	public int startIterativeDeepeningSearch(PuzzleGrid rootNode){
		PuzzleGrid node = rootNode;
		String fileName = "IDSResults.txt";
		fileWriter.deleteFile(fileName);
		int limit = 1;
		boolean found = false;
		while(!found){	
			found = depthLimitedSearch(node, limit);
			limit++;
			nodesStored = 0;
		}
		
		return nodesSearched;
	}
	
	public boolean depthLimitedSearch(PuzzleGrid node, int limit){
		String fileName = "IDSResults.txt";
		nodesSearched++;
		node.outputGrid();
		fileWriter.saveNodeToFile(fileName, node);
		System.out.println(limit);
		boolean result = false;
		nodesStored = nodesStored + 1;
		if(node.checkForGoal(goalGrid)){
			fileWriter.saveNodesExpanded(fileName, nodesSearched,nodesStored);
			return true;
		}

		if(limit <= 0){
			return false;
		}

		if(canMoveRight(node)){
			result = depthLimitedSearch(moveRight(node), limit - 1);
			if(result){
				return true;
			}
		}

		if(canMoveLeft(node)){
			result = depthLimitedSearch(moveLeft(node), limit - 1);
			if(result){
				return true;
			}
		}

		if(canMoveUp(node)){
			result = depthLimitedSearch(moveUp(node), limit - 1);
			if(result){
				return true;
			}
		}

		if(canMoveDown(node)){
			result = depthLimitedSearch(moveDown(node), limit - 1);
			if(result){
				return true;
			}
		}
		return result;

	}

	public int startHeuristicASearch(PuzzleGrid rootNode){
		int numOfNodesSearched = 0;
		String fileName = "AResults.txt";
		fileWriter.deleteFile(fileName);
		getGoalPositions(goalGrid);
		PriorityQueue<PuzzleGrid> frontier = new PriorityQueue<PuzzleGrid>();
		PuzzleGrid node = rootNode;	
		calculateEvalFunc(rootNode, -1);
		frontier.add(rootNode);
		
		while(!frontier.isEmpty()){
			node = frontier.poll();
			node.outputGrid();
			int g = node.getDistanceFromRoot();
			int h = node.getDistanceFromGoal();
			int f = node.getEvalValue();
			fileWriter.saveAStarNodeToFile(fileName, node, f, g, h);
			numOfNodesSearched++;
			System.out.println("Distance Travelled: " + g);
			System.out.println("Distance from goal: " + h);
			System.out.println("Evaluation Value: " + f);
			System.out.println();
			if(node.checkForGoal(goalGrid)){
				fileWriter.saveNodesExpanded(fileName, numOfNodesSearched, frontier.size());
				return numOfNodesSearched;
			}
			
			if(canMoveRight(node)){
				PuzzleGrid newRightNode = this.moveRight(node);
				this.calculateEvalFunc(newRightNode, node.getDistanceFromRoot());
				frontier.add(newRightNode);
			}
			
			if(canMoveLeft(node)){
				PuzzleGrid newLeftNode = this.moveLeft(node);
				this.calculateEvalFunc(newLeftNode, node.getDistanceFromRoot());
				frontier.add(newLeftNode);
			}
			
			if(canMoveUp(node)){
				PuzzleGrid newUpNode = this.moveUp(node);
				this.calculateEvalFunc(newUpNode, node.getDistanceFromRoot());
				frontier.add(newUpNode);
			}
			
			if(canMoveDown(node)){
				PuzzleGrid newDownNode = this.moveDown(node);
				this.calculateEvalFunc(newDownNode, node.getDistanceFromRoot());
				frontier.add(newDownNode);
			}
		}
		
		return numOfNodesSearched;
	}
	
	public boolean canMoveRight(PuzzleGrid node){
		Point agentPos = node.getAgentPos();
		if((agentPos.getXPos() + 1) < gridSize){
			return true;
		}else{
			return false;
		}
	}
	
	private PuzzleGrid moveRight(PuzzleGrid parentNode){
		PuzzleGrid newNode = new PuzzleGrid(gridSize);
		newNode.copyGrid(parentNode);
		Point agentPos = parentNode.getAgentPos();
		
		char tile = newNode.getTile(agentPos.getXPos() + 1, agentPos.getYPos());
		newNode.setAgentPos(agentPos.getXPos() + 1, agentPos.getYPos());
		newNode.setTile(tile, agentPos.getXPos(), agentPos.getYPos());
		
		return newNode;
	}
	
	public boolean canMoveLeft(PuzzleGrid node){
		Point agentPos = node.getAgentPos();
		if((agentPos.getXPos() - 1) >= 0){
			return true;
		}else{
			return false;
		}
	}
	
	private PuzzleGrid moveLeft(PuzzleGrid parentNode){
		PuzzleGrid newNode = new PuzzleGrid(gridSize);
		newNode.copyGrid(parentNode);
		Point agentPos = parentNode.getAgentPos();
		
		char tile = newNode.getTile(agentPos.getXPos() - 1, agentPos.getYPos());
		newNode.setAgentPos(agentPos.getXPos() - 1, agentPos.getYPos());
		newNode.setTile(tile, agentPos.getXPos(), agentPos.getYPos());
		
		return newNode;
	}
	
	public boolean canMoveUp(PuzzleGrid node){
		Point agentPos = node.getAgentPos();
		if((agentPos.getYPos() - 1) >= 0){
			return true;
		}else{
			return false;
		}
	}
	
	private PuzzleGrid moveUp(PuzzleGrid parentNode){
		PuzzleGrid newNode = new PuzzleGrid(gridSize);
		newNode.copyGrid(parentNode);
		Point agentPos = parentNode.getAgentPos();
		
		char tile = newNode.getTile(agentPos.getXPos(), agentPos.getYPos() - 1);
		newNode.setAgentPos(agentPos.getXPos(), agentPos.getYPos() - 1);
		newNode.setTile(tile, agentPos.getXPos(), agentPos.getYPos());
		
		return newNode;
	}
	
	public boolean canMoveDown(PuzzleGrid node){
		Point agentPos = node.getAgentPos();
		if((agentPos.getYPos() + 1) < gridSize){
			return true;
		}else{
			return false;
		}
	}
	
	private PuzzleGrid moveDown(PuzzleGrid parentNode){
		PuzzleGrid newNode = new PuzzleGrid(gridSize);
		newNode.copyGrid(parentNode);
		Point agentPos = parentNode.getAgentPos();
		
		char tile = newNode.getTile(agentPos.getXPos(), agentPos.getYPos() + 1);
		newNode.setAgentPos(agentPos.getXPos(), agentPos.getYPos() + 1);
		newNode.setTile(tile, agentPos.getXPos(), agentPos.getYPos());
		
		return newNode;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void getGoalPositions(PuzzleGrid grid){
		char tileList[][] = grid.getTileList();
		goalPositions = new ArrayList<Point>();
		
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				if((tileList[j][i] != '@')&&(tileList[j][i] != '*')){
					goalPositions.add(new Point(tileList[j][i],j,i));
				}
			}
		}
	}
	
	private int distanceFromGoal(PuzzleGrid grid){
		char tileList[][] = grid.getTileList();
		int distance = 0;
		
		for(int i=0; i<gridSize; i++){
			for(int j=0; j<gridSize; j++){
				if((tileList[j][i] != '@')&&(tileList[j][i] != '*')){
					for(int k=0; k < goalPositions.size(); k++){
						if(goalPositions.get(k).getSymbol() == tileList[j][i]){
							Point goalPoint = goalPositions.get(k);
							distance = distance + (Math.abs(goalPoint.getXPos() - j) + Math.abs(goalPoint.getYPos() - i));
						}
					}
				}
			}
		}
		
		return distance;
	}
	
	private void calculateEvalFunc(PuzzleGrid grid, int g){
		int distanceFromRoot = g + 1;
		int distanceFromGoal = distanceFromGoal(grid);
		int evalValue = distanceFromRoot + distanceFromGoal;
		
		grid.setDistanceFromRoot(distanceFromRoot);
		grid.setDistanceFromGoal(distanceFromGoal);
		grid.setEvalValue(evalValue);
	}
}


