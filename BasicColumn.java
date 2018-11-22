import java.util.ArrayList;

public class BasicColumn {

	private ArrayList<Integer> basicColumn;
	private ArrayList<Integer> limits;
	
	
	public BasicColumn(){
		
		this.basicColumn = new ArrayList<Integer>();
		this.limits = new ArrayList<Integer>();
	}
	
	


	public BasicColumn(ArrayList<Integer> bc, ArrayList<Integer> limit) {
		super();
		this.basicColumn = bc;
		this.limits = limit;
	}



	
	

	public ArrayList<Integer> getBasicColumn() {
		return basicColumn;
	}




	public void setBasicColumn(ArrayList<Integer> basicColumn) {
		this.basicColumn = basicColumn;
	}




	public ArrayList<Integer> getLimits() {
		return limits;
	}




	public void setLimits(ArrayList<Integer> limits) {
		this.limits = limits;
	}




	
	
	
}
