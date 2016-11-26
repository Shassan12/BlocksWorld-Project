import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class SearchMethods {
	private int gridSize;
	//private String goalState;
	private PuzzleGrid goalGrid;
	private ArrayList<Point> goalPositions;
	
	public SearchMethods(int gridSize){
		this.gridSize = gridSize;
		this.getGoalState();
	}
	
	private void getGoalState(){
		Scanner scanner = new Scanner(System.in);
		String goalState = "";
		
		while(true){
			System.out.println("Input goal state config as a string (e.g. aaaa for grid size 2):");
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
	
	public int startBreadthFirstSearch(PuzzleGrid rootNode){ //can be parallelized
		int numOfNodesSearched = 0;
		PuzzleGrid node = rootNode;
		Queue<PuzzleGrid> frontier = new LinkedList<PuzzleGrid>();
		frontier.add(rootNode);
		
		while(!frontier.isEmpty()){
			node = frontier.poll();
			node.outputGrid();
			numOfNodesSearched += 1;
			
			if(node.checkForGoal(goalGrid)){
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
		PuzzleGrid node = rootNode;
		Queue<PuzzleGrid> frontier = new LinkedList<PuzzleGrid>();
		frontier.add(rootNode);
		Random rand = new Random();
		
		while(!frontier.isEmpty()){
			node = frontier.poll();
			node.outputGrid();
			numOfNodesSearched += 1;
			boolean childMade = false;
			
			if(node.checkForGoal(goalGrid)){
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
	
	int nodesSearched = 0;
	public int startDepthLimitedSearch(PuzzleGrid rootNode){
		//int numOfNodesSearched = 0;
		PuzzleGrid node = rootNode;
		int limit = 1;
		boolean found = false;
		while(!found){	
			found = depthLimitedSearch(node, limit);
			limit++;
		}
		
		return nodesSearched;
	}
	
	public boolean depthLimitedSearch(PuzzleGrid node, int limit){
		nodesSearched++;
		node.outputGrid();
		boolean result = false;

		if(node.checkForGoal(goalGrid)){
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
		getGoalPositions(goalGrid);
		PriorityQueue<PuzzleGrid> open = new PriorityQueue<PuzzleGrid>();
		PuzzleGrid node = rootNode;
		
		calculateEvalFunc(rootNode, -1);
		open.add(rootNode);
		
		while(!open.isEmpty()){
			node = open.poll();
			node.outputGrid();
			numOfNodesSearched++;
			//System.out.println(node.getDistanceFromRoot());
			//System.out.println(node.getDistanceFromGoal());
			//System.out.println(node.getEvalValue());
			
			if(node.checkForGoal(goalGrid)){
				return numOfNodesSearched;
			}
			
			if(canMoveRight(node)){
				PuzzleGrid newRightNode = this.moveRight(node);
				this.calculateEvalFunc(newRightNode, node.getDistanceFromRoot());
				open.add(newRightNode);
			}
			
			if(canMoveLeft(node)){
				PuzzleGrid newLeftNode = this.moveLeft(node);
				this.calculateEvalFunc(newLeftNode, node.getDistanceFromRoot());
				open.add(newLeftNode);
			}
			
			if(canMoveUp(node)){
				PuzzleGrid newUpNode = this.moveUp(node);
				this.calculateEvalFunc(newUpNode, node.getDistanceFromRoot());
				open.add(newUpNode);
			}
			
			if(canMoveDown(node)){
				PuzzleGrid newDownNode = this.moveDown(node);
				this.calculateEvalFunc(newDownNode, node.getDistanceFromRoot());
				open.add(newDownNode);
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
					for(int k=0; k <goalPositions.size(); k++){
						if(goalPositions.get(k).getSymobol() == tileList[j][i]){
							Point goalPoint = goalPositions.get(k);
							distance = distance + (Math.abs(goalPoint.getXPos() - j)+Math.abs(goalPoint.getYPos()+i));
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


