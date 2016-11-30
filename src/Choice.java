import java.util.Scanner;

public class Choice{
	public int getChoice(){
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		
		while(true){
			System.out.println("Input your choice:");
			choice = Integer.parseInt(scanner.next());
			
			if((choice >0)&&(choice < 7)){
				break;
			}else{
				System.out.println("invalid choice.");
			}
		}
		
		return choice;
	}
	
	public int getSize(){
		Scanner scanner = new Scanner(System.in);
		int size = 0;
		
		while(true){
			System.out.println("Input grid Width (grid height will be the same as width):");
			size = Integer.parseInt(scanner.next());
			
			if((size >0)&&(size < 7)){
				break;
			}else{
				System.out.println("invalid size.");
			}
		}
		
		return size;
	}
}