import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.opencsv.CSVReader;

import net.miginfocom.swing.MigLayout;

public class BaseView extends JFrame{
	
	/**
	 * 
	 */
	
	private JPanel jpl;
	private JGradientButton btnClose;
	private JGradientButton btnSelect;
	private JLabel lblFile;
	private JLabel lblSumColumns;
	private String filePath;
	private boolean isFileLoaded;
	
	public BaseView(){
		
		
		jpl = new JPanel();
		btnClose = new JGradientButton(100, 35, 13,"Close", "Closes this view",156,142,175);
		btnSelect = new JGradientButton(100, 35, 13,"Select", "Selects a columns' file",156,142,175);
		lblFile = new JLabel("<html><b> File : </b><html> (None)");
		lblSumColumns = new JLabel("<html><b> Columns : </b><html>");
		isFileLoaded = new Boolean(false);
		
		jpl.setLayout(new MigLayout());
		add(jpl);
		
btnClose.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				    	
				   dispose();
				    	
				    }
					
					
				});


	}



	public JPanel getJpl() {
		return jpl;
	}



	public void setJpl(JPanel jpl) {
		this.jpl = jpl;
	}



	public JGradientButton getBtnClose() {
		return btnClose;
	}



	public void setBtnClose(JGradientButton btnClose) {
		this.btnClose = btnClose;
	}





	public JGradientButton getBtnSelect() {
		return btnSelect;
	}





	public void setBtnSelect(JGradientButton btnSelect) {
		this.btnSelect = btnSelect;
	}





	public JLabel getLblFile() {
		return lblFile;
	}





	public void setLblFile(JLabel lblFile) {
		this.lblFile = lblFile;
	}





	public JLabel getLblSumColumns() {
		return lblSumColumns;
	}





	public void setLblSumColumns(JLabel lblSumColumns) {
		this.lblSumColumns = lblSumColumns;
	}





	public String getFilePath() {
		return filePath;
	}





	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}





	public boolean isFileLoaded() {
		return isFileLoaded;
	}





	public void setFileLoaded(boolean isFileLoaded) {
		this.isFileLoaded = isFileLoaded;
	}


	
public void selectCurrent(){
		

	       String filePath =  "stiles_kataskevastikou.ppg";
	       
	       int cols = fileColumnCount(filePath);
	       
	       if(cols > 0) {
	   
			       setFilePath(filePath);
		
				   getLblFile().setText(getLblFile().getText().substring(0, 28) + filePath);
		
		           getLblSumColumns().setText(getLblSumColumns().getText().substring(0,30) + cols);
				   
		           setFileLoaded(true);
        
	       }
        
          else {
     	   
     	   Message m = new Message("Error", "No columns to view.", "Error");
            m.show();
            setFileLoaded(false);
        }
	}
	
public void fileSelect(){
		
         
	
		JFileChooser fc = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Propogoal files", "ppg");

		fc.setFileFilter(filter);
		   
		   int returnVal = fc.showOpenDialog(getParent());
		  
		   
		   if(returnVal == JFileChooser.APPROVE_OPTION) {
			   
			   String filePath =  (fc.getSelectedFile().getAbsolutePath());
			   
			        if(filePath.toLowerCase().endsWith(".ppg")) {
			        	
			        	
						   
						   setFilePath(filePath);
				   
						   getLblFile().setText(getLblFile().getText().substring(0, 28) + fc.getSelectedFile().getName());
						   
						   filePath = fc.getSelectedFile().getAbsolutePath();
						  
				           int stiles = fileColumnCount(filePath);
						   
				           getLblSumColumns().setText(getLblSumColumns().getText().substring(0,30) + stiles);
						  
				           setFileLoaded(true);
				  
				   
			   }
			   
			   else
			   {
				   Message m = new Message("Error", "Please select only .ppg files", "Error");
				   m.show();
				   
				   setFileLoaded(false);
				   
			   }
				   
				   
				  
				   
		}

             
				
			}


	
public int fileColumnCount(String file) {

	
    int sum = 0;
	
	
	CSVReader csvReader = null;
	
try {
	
	
	csvReader = new CSVReader(new FileReader(file));
} catch (FileNotFoundException e1) {
	
	e1.printStackTrace();
}
   


String[] row = null;
   

   try {
	while((row = csvReader.readNext()) != null) {
	
		
		sum = sum + 1; 
	   		
	   }
	

	
} catch (IOException e1) {
	
	e1.printStackTrace();
}
   
   try {
	csvReader.close();
} catch (IOException e1) {
	
	e1.printStackTrace();
}
   
	return sum;	

}

public void addPointerHand(Component co) {



	co.addMouseListener(new MouseListener() {
		
		Color col;
		
    public void mouseClicked(MouseEvent e) {
    	

    
    }

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
     
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		

		Object obj = e.getSource();
		
		if(obj instanceof JButton) {
           JButton btn = (JButton) e.getSource();
           
           col = btn.getForeground();

           if(btn.isEnabled()) {
        	  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		      btn.setForeground(new Color(32,178,170));
           }
		}
		
		if(obj instanceof JCheckBox) {
			  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		Object obj = e.getSource();
		
		if(obj instanceof JButton) {
			
           JButton btn = (JButton) e.getSource();
		           btn.setForeground(col);
          
		}
		
	
		
	}



}

		);

		 



}

public ArrayList<ArrayList<Integer>> readCsvToArrayList(String file) {
	

	
	ArrayList<ArrayList<Integer>> csv_array = new ArrayList<ArrayList<Integer>>();
	
	CSVReader csvReader = null;
	try {
		
		csvReader = new CSVReader(new FileReader(file));
		
	} catch (FileNotFoundException e1) {
		
		e1.printStackTrace();
	}
   


String[] row = null;
   

   try {
	while((row = csvReader.readNext()) != null) {
	
		
		// ArrayList για αποθήκευση κάθε γραμμής του csv
	   	
	   	ArrayList<Integer> csv_line = new ArrayList<Integer>();
	   	
	   	for(int i=0; i<8; i++){
	   		csv_line.add(Integer.parseInt(row[i]));
	   	}
	   	csv_array.add(csv_line);
	   		
	   }
	

	
} catch (IOException e1) {
	
	Message m = new Message("Error", "Not a proper file - cannot be loaded!","Error");
	m.show();
	
}
   
   try {
	csvReader.close();
} catch (IOException e1) {
	
	e1.printStackTrace();
}
   

	return csv_array;
		

	
}

}
