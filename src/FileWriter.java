import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileWriter {
	int gridSize;
	
	public FileWriter(int gridSize){
		this.gridSize = gridSize;
	}
	
	/*public void saveToFile(String fileName, int numOfNodes, ArrayList<PuzzleGrid> checkedNodes){
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
			for(PuzzleGrid grid : checkedNodes){
				char tileList[][] = grid.getTileList();
			for(int i=0; i<gridSize; i++){
				for(int j=0; j<gridSize; j++){
					writer.write(tileList[j][i]+" ");
				}
				
				System.out.println();
			}
			}
			
			System.out.println();
		}catch(Exception e){}
	}*/
	
	public void deleteFile(String fileName){
		File file = new File(fileName);
		
		if(file.exists()){
			file.delete();
		}
	}
	
	public void saveNodeToFile(String fileName, PuzzleGrid grid){
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "utf-8"))) {
				char tileList[][] = grid.getTileList();
			for(int i=0; i<gridSize; i++){
				for(int j=0; j<gridSize; j++){
					writer.append(tileList[j][i]+" ");
				}
				
				writer.newLine();
			}
			
			writer.newLine();
		}catch(Exception e){}
	}
	
	public void saveAStarNodeToFile(String fileName, PuzzleGrid grid, int f, int g, int h){
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "utf-8"))) {
				char tileList[][] = grid.getTileList();
			for(int i=0; i<gridSize; i++){
				for(int j=0; j<gridSize; j++){
					writer.append(tileList[j][i]+" ");
				}
				writer.newLine();
			}
			
			writer.append("Distance From Root (g(n)): " + g);
			writer.newLine();
			writer.append("Distance From Goal (h(n)): " + h);
			writer.newLine();
			writer.append("Evaluation Value (f(n))): " + f);
			writer.newLine();
			writer.newLine();
		}catch(Exception e){}
	}

	public void saveNodesExpanded(String fileName, int numOfNodesSearched, int frontierSize){
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "utf-8"))) {
			writer.write("Number of Nodes Expanded: " + numOfNodesSearched);
			writer.newLine();
			writer.write("Number of Nodes stored: " + frontierSize);
		}catch(Exception e){}
	}
}
