
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

		public int getXPos(){
			return this.xPos;
		}
		
		public int getYPos(){
			return this.yPos;
		}
		
		public char getSymbol(){
			return this.symbol;
		}
	}