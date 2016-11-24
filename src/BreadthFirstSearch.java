import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BreadthFirstSearch {
	private int gridSize;
	private String goalState;
	private PuzzleGrid goalGrid;
	
	public BreadthFirstSearch(int gridSize){
		this.gridSize = gridSize;
		this.getGoalState();
	}
	
	private void getGoalState(){
		Scanner scanner = new Scanner(System.in);
		goalGrid = new PuzzleGrid(gridSize);
		
		while(true){
			System.out.println("Input goal state config as a string (e.g. aaaa for grid size 2):");
			this.goalState = scanner.next();
			
			if(goalState.length() == gridSize*gridSize){
				break;
			}else{
				System.out.println("invalid goal state. Goal state should have the same number of characters as the gridsize (" + gridSize+").");
			}
		}
		
		goalGrid.initiateGrid();
	}
	
	public int startBreadthFirstSearch(PuzzleGrid rootNode){
		int numOfNodesSearched = 0;
		PuzzleGrid node = rootNode;
		Queue<PuzzleGrid> frontier = new LinkedList<PuzzleGrid>();
		frontier.add(rootNode);
		
		while(!frontier.isEmpty()){
			node = frontier.poll();
			node.outputGrid();
			
			if(node.checkForGoal(goalState)){
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
	
	
}
