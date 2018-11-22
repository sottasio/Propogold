import javax.swing.JOptionPane;

public class Message {

	String title;
	String message;
	String type;
	
	public Message(String title, String message, String type){
		
		this.title = title;
		this.message = message;
		this.type = type;


	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void show(){
		
		if(this.type.equals("Error"))
			JOptionPane.showMessageDialog(null, this.getMessage(), this.getTitle(), JOptionPane.ERROR_MESSAGE);
		else
			if(this.type.equals("Info"))
				JOptionPane.showMessageDialog(null, this.getMessage(), this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
			else
				if(this.type.equals("Warning"))
					JOptionPane.showMessageDialog(null, this.getMessage(), this.getTitle(), JOptionPane.WARNING_MESSAGE);
				
	}
}
