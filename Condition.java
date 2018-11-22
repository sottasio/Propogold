import java.util.ArrayList;

public class Condition {
	
	private ArrayList<Integer> odd;
	private ArrayList<Integer> low;
	private ArrayList<Integer> symmetrics;
	private ArrayList<Integer> endings;
	private ArrayList<Integer> neighbours;
	private ArrayList<Integer> transitions;
	private ArrayList<Integer> sum;
	private ArrayList<Integer> errors;
	
	
	public Condition(){
		
		this.odd = new ArrayList<Integer>();
		this.low = new ArrayList<Integer>();
		this.symmetrics = new ArrayList<Integer>();
		this.endings = new ArrayList<Integer>();
		this.neighbours = new ArrayList<Integer>();
		this.transitions = new ArrayList<Integer>();
		this.sum = new ArrayList<Integer>();
		this.errors = new ArrayList<Integer>();
	}


	public ArrayList<Integer> getOdd() {
		return odd;
	}


	public void setOdd(ArrayList<Integer> odd) {
		this.odd = odd;
	}


	public ArrayList<Integer> getLow() {
		return low;
	}


	public void setLow(ArrayList<Integer> low) {
		this.low = low;
	}


	public ArrayList<Integer> getSymmetrics() {
		return symmetrics;
	}


	public void setSymmetrics(ArrayList<Integer> symmetrics) {
		this.symmetrics = symmetrics;
	}


	public ArrayList<Integer> getEndings() {
		return endings;
	}


	public void setEndings(ArrayList<Integer> endings) {
		this.endings = endings;
	}


	public ArrayList<Integer> getNeighbours() {
		return neighbours;
	}


	public void setNeighbours(ArrayList<Integer> neighbours) {
		this.neighbours = neighbours;
	}


	public ArrayList<Integer> getTransitions() {
		return transitions;
	}


	public void setTransitions(ArrayList<Integer> transitions) {
		this.transitions = transitions;
	}


	public ArrayList<Integer> getSum() {
		return sum;
	}


	public void setSum(ArrayList<Integer> sum) {
		this.sum = sum;
	}


	public ArrayList<Integer> getErrors() {
		return errors;
	}


	public void setErrors(ArrayList<Integer> errors) {
		this.errors = errors;
	}


	
	
	

}
