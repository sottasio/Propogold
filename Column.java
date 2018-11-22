

import java.util.Arrays;

public class Column {
	
	private int[] position = new int[8];
	private int odd;
	private int low;
	private int symmetrics;
	private int endings;
	private int neighbours;
	private int transitions;
	private int sum;
	
	
	public Column(int pos1, int pos2,  int pos3, int pos4, int pos5, int pos6, int pos7, int pos8) {
		
		this.position[0] = pos1;
		this.position[1] = pos2;
		this.position[2] = pos3;
		this.position[3] = pos4;
		this.position[4] = pos5;
		this.position[5] = pos6;
		this.position[6] = pos7;
		this.position[7] = pos8;
		
		
		
		int odd = 0;
		int low = 0;
		int symmetrics = 0;
		int endings = 0;
		int neighbours = 0;
		int transitions = 0;
		int sum = 0;
		
		
		
		for(int i=0; i<8; i++) {
			
			//Calc sum
			
			sum = sum + this.position[i];
			
			//Calc odd;
			
			if(this.position[i] % 2 == 1)
				odd = odd + 1;
			
			//Calc low
			
			 if(this.position[i] < 16)
			    	low = low + 1;
			 
			 //Calc transtions
			 
			 if(i<7) {
			 if(this.position[i] % 2 != this.position[i+1] % 2)
				 transitions = transitions + 1;
			 }
			 
			 // Calc symmetrics and endings
			 
			 for(int j=1; j<8; j++) {
				 
				 if(i<j) {
					 
					
					 
					 if((this.position[i] + this.position[j]) == 31)
						 symmetrics = symmetrics + 1;
					 
					 if(this.position[i] % 10 == this.position[j] % 10)
						 endings = endings + 1;
					 
					 if(j==i+1){
						 
						 if(this.position[i] == this.position[j] - 1)
							 neighbours = neighbours + 1;
					 }
						 
					 
				 }
				 
				
				 
				
				 
				 
			 }
			 
			 
			 
			
		}
		
		this.odd = odd;
		this.low = low;
		this.symmetrics = symmetrics;
		this.endings = endings;
		this.neighbours = neighbours;
		this.transitions = transitions;
		this.sum = sum;
		
		
	}


	public int[] getPosition() {
		return position;
	}


	public void setPosition(int[] position) {
		this.position = position;
	}


	public int getOdd() {
		return odd;
	}


	public void setOdd(int odd) {
		this.odd = odd;
	}


	public int getLow() {
		return low;
	}


	public void setLow(int low) {
		this.low = low;
	}


	public int getSymmetrics() {
		return symmetrics;
	}


	public void setSymmetrics(int symmetrics) {
		this.symmetrics = symmetrics;
	}


	public int getEndings() {
		return endings;
	}


	public void setEndings(int endings) {
		this.endings = endings;
	}


	public int getNeighbours() {
		return neighbours;
	}


	public void setNeighbours(int neighbours) {
		this.neighbours = neighbours;
	}


	public int getTransitions() {
		return transitions;
	}


	public void setTransitions(int transitions) {
		this.transitions = transitions;
	}


	public int getSum() {
		return sum;
	}


	public void setSum(int sum) {
		this.sum = sum;
	}


	@Override
	public String toString() {
		return "stili [position=" + Arrays.toString(position) + ", mona=" + odd + ", mikra=" + low + ", symmetrika="
				+ symmetrics + ", ligontes=" + endings + ", sinexomena=" + neighbours + ", enallagesmz=" + transitions
				+ ", athroisma=" + sum + "]";
	}
	
	
	
	
	
	
	

}
