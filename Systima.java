import java.util.ArrayList;

public class Systima {
	
	private ArrayList<Integer> matches;
	private Condition condition;
	private ArrayList<GroupBC> groups;
	
	public Systima() {
		
		this.matches = new ArrayList<Integer>();
		this.condition = new Condition();
		this.groups = new ArrayList<GroupBC>();
		
	}
	
	
	
	
	public ArrayList<Integer> getMatches() {
		return matches;
	}




	public void setMatches(ArrayList<Integer> matches) {
		this.matches = matches;
	}




	public Condition getCondition() {
		return condition;
	}




	public void setCondition(Condition condition) {
		this.condition = condition;
	}




	public ArrayList<GroupBC> getGroups() {
		return groups;
	}




	public void setGroups(ArrayList<GroupBC> groups) {
		this.groups = groups;
	}




public long calcColumnCombination() { // Calculates sum of combinations of number of matches
		
		int num = this.getMatches().size();
		
		long product = 1;
		
		for(int i=0; i<8; i++) {
			
			product = product * (num - i);
			
			
			
		}
		
		long i = 40320;
		
		return (product / i);
		
	}
	

}
