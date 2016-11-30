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
	
	//reads the users configuration of the required goal state and creates a goal state node
	private void getGoalState(){
		Scanner scanner = new Scanner(System.in);
		String goalState = "";
		
		while(true){
			System.out.println("Input goal state config as a string (e.g. **A@ for grid size 2):");
			goalState = scanner.next();
			
			if(goalState.length() == gridSize*gridSize){
				break;
			}else{
				System.out.println("invalid goal state. Goal state should have the same number of characters "
						+ "as the gridsize (" + gridSize+").");
			}
		}
		
		goalGrid = new PuzzleGrid(gridSize);
		goalGrid.setGrid(goalState);;
	}
	
	//carries out a breadth first search starting with the initial node
	public int startBreadthFirstSearch(PuzzleGrid rootNode){
		int numOfNodesSearched = 0;
		String fileName = "bfsResults.txt";
		PuzzleGrid node = rootNode;
		
		//frontier to store nodes that still need to be checked against the goal state
		Queue<PuzzleGrid> frontier = new LinkedList<PuzzleGrid>();
		
		//add root node to frontier
		frontier.add(rootNode);
		
		//delete output file so that result from previous test is erased
		fileWriter.deleteFile(fileName);
		
		//keep searching until we run out of nodes
		while(!frontier.isEmpty()){
			//remove the node at the front of the frontier
			node = frontier.poll();
			
			//output the state of the grid of this node
			node.outputGrid();
			
			//write the state of the grid of this node to the results file
			fileWriter.saveNodeToFile(fileName, node);
			
			//increment the number of nodes searched
			numOfNodesSearched += 1;
			
			//check if nodes grid is the same as the goal states grid
			if(node.checkForGoal(goalGrid)){
				//write the number of nodes expanded to end of results file
				fileWriter.saveNodesExpanded(fileName, numOfNodesSearched, frontier.size());
				
				//return the number of nodes searched
				return numOfNodesSearched;
			}
			
			//check if agent can move right and create/add this node to the frontier
			if(canMoveRight(node)){
				frontier.add(moveRight(node));
			}
			
			//check if agent can move left and create/add this node to the frontier
			if(canMoveLeft(node)){
				frontier.add(moveLeft(node));
			}
			
			//check if agent can move up and create/add this node to the frontier
			if(canMoveUp(node)){
				frontier.add(moveUp(node));
			}
			
			//check if agent can move down and create/add this node to the frontier
			if(canMoveDown(node)){
				frontier.add(moveDown(node));
			}
		}
		
		return numOfNodesSearched;
	}
	
	//carries out a depth first search starting with the inital node
	public int startDepthFirstSearch(PuzzleGrid rootNode){
		int numOfNodesSearched = 0;
		String fileName = "dfsResults.txt";
		Random rand = new Random();
		PuzzleGrid node = rootNode;
		
		//frontier to store nodes that still need to be checked against the goal state
		Queue<PuzzleGrid> frontier = new LinkedList<PuzzleGrid>();
		
		//delete output file so that result from previous test is erased
		fileWriter.deleteFile(fileName);
		
		//add root node to frontier
		frontier.add(rootNode);
		
		//keep searching until we run out of nodes
		while(!frontier.isEmpty()){
			//remove the node at the front of the frontier
			node = frontier.poll();
			
			//output the state of the grid of this node
			node.outputGrid();
		
			//increment the number of nodes searched
			numOfNodesSearched += 1;
			
			//write the state of the grid of this node to the results file
			fileWriter.saveNodeToFile(fileName, node);
			
			//no children made initially
			boolean childMade = false;
			
			//check if nodes grid is the same as the goal states grid
			if(node.checkForGoal(goalGrid)){
				//write the number of nodes expanded to end of results file
				fileWriter.saveNodesExpanded(fileName, numOfNodesSearched, numOfNodesSearched);
				
				//return the number of nodes searched
				return numOfNodesSearched;
			}
			
			//keep trying to make a random child node until one is made
			while(!childMade){
				
				//choose random direction
				int choice = rand.nextInt(4);
				
				if(choice == 0){
					//check if agent can move right and create/add this node to the frontier
					if(canMoveRight(node)){
						frontier.add(moveRight(node));
						childMade = true;
					}
				}
				
				if(choice == 1){
					//check if agent can move left and create/add this node to the frontier
					if(canMoveLeft(node)){
						frontier.add(moveLeft(node));
						childMade = true;
					}
				}
				
				if(choice == 2){
					//check if agent can move up and create/add this node to the frontier
					if(canMoveUp(node)){
						frontier.add(moveUp(node));
						childMade = true;
					}
				}
				
				if(choice == 3){
					//check if agent can move down and create/add this node to the frontier
					if(canMoveDown(node)){
						frontier.add(moveDown(node));
						childMade = true;
					}
				}
			}
		}
		
		return numOfNodesSearched;
	}
	
/////////////////////////////////////////////////Iterative Deepening/////////////////////////////////////////////	
	private int nodesStored = 0;
	private int nodesSearched = 0;
	
	//carries out iterative deepening depth first search starting with the initial node
	public int startIterativeDeepeningSearch(PuzzleGrid rootNode){
		PuzzleGrid node = rootNode;
		String fileName = "IDSResults.txt";
		
		//set initial depth limit to 1
		int limit = 1;
		boolean found = false;
		
		//delete output file so that result from previous test is erased
		fileWriter.deleteFile(fileName);
		
		//keep searching until we run out of nodes
		while(!found){	
			//carry out depth limited search with current depth level
			found = depthLimitedSearch(node, limit);
			
			//increase depth limit by 1
			limit++;
			
			//reset the number of nodes stored on the heap
			nodesStored = 0;
		}
		
		return nodesSearched;
	}
	
	//carries out a depth limited search starting with the initial node and the current depth limit
	public boolean depthLimitedSearch(PuzzleGrid node, int limit){
		String fileName = "IDSResults.txt";
		boolean result = false;
		
		//increment the number of nodes searched
		nodesSearched += 1;
		
		//output the state of the grid of this node
		node.outputGrid();
		
		//write the state of the grid of this node to the results file
		fileWriter.saveNodeToFile(fileName, node);
		
		//increment number of nodes stored 
		nodesStored += 1;
		
		//check if nodes grid is the same as the goal states grid
		if(node.checkForGoal(goalGrid)){
			//write the number of nodes expanded to end of results file
			fileWriter.saveNodesExpanded(fileName, nodesSearched,nodesStored);
			return true;
		}
		
		//if depth limit is reached without finding a solution, return failure
		if(limit <= 0){
			return false;
		}
		
		//check if agent can move right and create/add this node to the frontier
		if(canMoveRight(node)){
			//recursively create children for this node while depth limit is not reached
			result = depthLimitedSearch(moveRight(node), limit - 1);
			
			//stop depth limited search is solution is found
			if(result){
				return true;
			}
		}
		
		//check if agent can move left and create/add this node to the frontier
		if(canMoveLeft(node)){
			//recursively create children for this node while depth limit is not reached
			result = depthLimitedSearch(moveLeft(node), limit - 1);
			
			//stop depth limited search is solution is found
			if(result){
				return true;
			}
		}
		
		//check if agent can move up and create/add this node to the frontier
		if(canMoveUp(node)){
			//recursively create children for this node while depth limit is not reached
			result = depthLimitedSearch(moveUp(node), limit - 1);
			
			//stop depth limited search is solution is found
			if(result){
				return true;
			}
		}

		//check if agent can move down and create/add this node to the frontier
		if(canMoveDown(node)){
			//recursively create children for this node while depth limit is not reached
			result = depthLimitedSearch(moveDown(node), limit - 1);
			
			//stop depth limited search is solution is found
			if(result){
				return true;
			}
		}
		return result;

	}
/////////////////////////////////////////////////End of Iterative Deepening////////////////////////////////
	
	//carries out an A* heuristic search starting with the inital node
	public int startHeuristicASearch(PuzzleGrid rootNode){
		int numOfNodesSearched = 0;
		String fileName = "AResults.txt";
		PuzzleGrid node = rootNode;
		PriorityQueue<PuzzleGrid> frontier = new PriorityQueue<PuzzleGrid>();
		
		//delete output file so that result from previous test is erased
		fileWriter.deleteFile(fileName);
		
		//get a list of the positions of all non blank tiles in goal
		getGoalPositions(goalGrid);
		
		//calculate the evaluation function for the root node
		calculateEvalFunc(rootNode, -1);
		
		//add root node to the frontier
		frontier.add(rootNode);
		
		//keep searching until we run out of nodes
		while(!frontier.isEmpty()){
			//remove the node at the front of the frontier
			node = frontier.poll();
			
			//output the state of the grid of this node
			node.outputGrid();
			
			//get the distance of node from root node
			int g = node.getDistanceFromRoot();
			
			//get the distance from this node to the goal state
			int h = node.getDistanceFromGoal();
			
			//get the evaluation value for this node
			int f = node.getEvalValue();
			
			//write the state of the grid of this node to the results file along with evaluation function
			fileWriter.saveAStarNodeToFile(fileName, node, f, g, h);
			
			//increment the number of nodes searched
			numOfNodesSearched++;
			
			//output evaluation function values
			System.out.println("Distance Travelled: " + g);
			System.out.println("Distance from goal: " + h);
			System.out.println("Evaluation Value: " + f);
			System.out.println();
			
			//check if nodes grid is the same as the goal states grid
			if(node.checkForGoal(goalGrid)){
				//write the number of nodes expanded to end of results file
				fileWriter.saveNodesExpanded(fileName, numOfNodesSearched, frontier.size());
				
				//return the number of nodes searched
				return numOfNodesSearched;
			}
			
			//check if agent can move right and create/add this node to the frontier
			if(canMoveRight(node)){
				PuzzleGrid newRightNode = this.moveRight(node);
				
				//calculate evaluation function for this node
				this.calculateEvalFunc(newRightNode, node.getDistanceFromRoot());
				
				//add node to frontier
				frontier.add(newRightNode);
			}
			
			//check if agent can move left and create/add this node to the frontier
			if(canMoveLeft(node)){
				PuzzleGrid newLeftNode = this.moveLeft(node);
				
				//calculate evaluation function for this node
				this.calculateEvalFunc(newLeftNode, node.getDistanceFromRoot());
				
				//add node to frontier
				frontier.add(newLeftNode);
			}
			
			//check if agent can move up and create/add this node to the frontier
			if(canMoveUp(node)){
				PuzzleGrid newUpNode = this.moveUp(node);
				
				//calculate evaluation function for this node
				this.calculateEvalFunc(newUpNode, node.getDistanceFromRoot());
				
				//add node to frontier
				frontier.add(newUpNode);
			}
			
			//check if agent can move down and create/add this node to the frontier
			if(canMoveDown(node)){
				PuzzleGrid newDownNode = this.moveDown(node);
				
				//calculate evaluation function for this node
				this.calculateEvalFunc(newDownNode, node.getDistanceFromRoot());
				
				//add node to frontier
				frontier.add(newDownNode);
			}
		}
		
		return numOfNodesSearched;
	}
	
	//check if agent can move right
	public boolean canMoveRight(PuzzleGrid node){
		//get position of agent
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


