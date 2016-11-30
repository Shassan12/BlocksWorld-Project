/*class that can store the position of a tile in a grid*/
public class Point{
		private int xPos;
		private int yPos;
		private char symbol;
		
		public Point(int xPos, int yPos){
			this.xPos = xPos;
			this.yPos = yPos;
		}
		
		public Point(char symbol, int xPos, int yPos) {
			this.symbol = symbol;
			this.xPos = xPos;
			this.yPos = yPos;
		}

		//return the row position of this point
		public int getXPos(){
			return this.xPos;
		}
		
		//return the column position of this point
		public int getYPos(){
			return this.yPos;
		}
		
		//return this points symbol
		public char getSymbol(){
			return this.symbol;
		}
	}