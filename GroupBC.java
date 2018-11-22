import java.util.ArrayList;

public class GroupBC {
	
	private int id;
	private ArrayList<BasicColumn> basicColumns;
	private ArrayList<Integer> limits;
	
	public GroupBC(int id) {
		
		
		this.id = id;
		this.basicColumns = new ArrayList<BasicColumn>();
		this.limits = new ArrayList<Integer>();
		this.limits.add(0);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<BasicColumn> getBasicColumns() {
		return basicColumns;
	}

	public void setBasicColumns(ArrayList<BasicColumn> basicColumns) {
		this.basicColumns = basicColumns;
	}

	public ArrayList<Integer> getLimits() {
		return limits;
	}

	public void setLimits(ArrayList<Integer> limits) {
		this.limits = limits;
	}

	
	

}
