import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileWriter {
	int gridSize;
	
	public FileWriter(int gridSize){
		this.gridSize = gridSize;
	}
	
	//deletes the specified file
	public void deleteFile(String fileName){
		File file = new File(fileName);
		
		if(file.exists()){
			file.delete();
		}
	}
	
	//saves the configuration of a nodes grid to file
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
	
	//saves the configuration of a nodes grid to file along with the values of the nodes evaluation function
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

	//saves the number of expanded nodes and the number of nodes stored in memory after a search is completed to the end of a file
	public void saveNodesExpanded(String fileName, int numOfNodesSearched, int frontierSize){
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "utf-8"))) {
			writer.write("Number of Nodes Expanded: " + numOfNodesSearched);
			writer.newLine();
			writer.write("Number of Nodes stored: " + frontierSize);
		}catch(Exception e){}
	}
}
