import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import PuzzleGrid.Point;

public class BreadthFirstSearch {
	private int gridSize;
	private String goalState;
	
	public BreadthFirstSearch(int gridSize){
		this.gridSize = gridSize;
		this.getGoalState();
	}
	
	private void getGoalState(){
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.println("Input goal state config as a string (e.g. aaaa for grid size 2):");
			this.goalState = scanner.next();
			
			if(goalState.length() == gridSize*gridSize){
				break;
			}else{
				System.out.println("invalid goal state. Goal state should have the same number of characters as the gridsize (" + gridSize+").");
			}
		}
	}
	
	public PuzzleGrid startBreadthFirstSearch(PuzzleGrid rootNode){
		PuzzleGrid node = rootNode;
		Queue<PuzzleGrid> frontier = new LinkedList<PuzzleGrid>();
		frontier.add(rootNode);
		
		while(!frontier.isEmpty()){
			node = frontier.poll();
			node.outputGrid();
			
			if(node.checkForGoal(goalState)){
				return node;
			}
			
			
		}
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
	
	private PuzzleGrid moveRight(PuzzleGrid parentNode){
		PuzzleGrid newNode = new PuzzleGrid(gridSize);
		newNode.copyGrid(parentNode);
		Point agentPos = parentNode.getAgentPos();
		
		char tile = newNode.getTile(agentPos.getXPos() + 1, agentPos.getYPos());
		newNode.setAgentPos(agentPos.getXPos() + 1, agentPos.getYPos());
		newNode.setTile(tile, agentPos.getXPos(), agentPos.getYPos());
		
		return newNode;
	}
}
