import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.opencsv.CSVReader;

import net.miginfocom.swing.MigLayout;

public class WinningCheckView extends BaseView implements ItemListener{
	
	/**
	 * 
	 */
	
	private JGradientButton btnWinCheck;
	private JGradientButton btnCurrentColumns;
	private JGradientButton btnSaveWinningColumn;
	private JGradientButton btnLoadWinningColumn;
	private JPanel matches_jpl;
	private JPanel matches_finished_jpl;
	private JCheckBox matches_check_checkboxes[];
	private JCheckBox matches_finished_checkboxes[];
	private JCheckBox analysis;
	private JCheckBox prediction;
	private ArrayList<Integer> matches;
	private ArrayList<Integer> matches_finished;
	
	
	public WinningCheckView(){
		
		super();
		
		matches = new ArrayList<Integer>();
		matches_finished = new ArrayList<Integer>();
		
		
		matches_jpl = new JPanel();
		matches_jpl.setLayout(new MigLayout());
		
		matches_finished_jpl = new JPanel();
		matches_finished_jpl.setLayout(new MigLayout());
		
		matches_check_checkboxes = new JCheckBox[30];
		matches_finished_checkboxes = new JCheckBox[30];
		
		matches_jpl.setPreferredSize(new Dimension(220,245));
		matches_jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		matches_finished_jpl.setPreferredSize(new Dimension(220,245));
		matches_finished_jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		btnCurrentColumns = new JGradientButton(100, 35, 13,"Current", "Selects the current columns",156,142,175);
		btnWinCheck = new JGradientButton(100, 35, 13,"Check", "Checks if there is a winning column",156,142,175);
		btnSaveWinningColumn = new JGradientButton(100, 35, 13,"Save", "Saves winning column and finished matches to disk",156,142,175);
		btnLoadWinningColumn = new JGradientButton(100, 35, 13,"Load", "Loads winning column and finished matches from disk",156,142,175);

		analysis = new JCheckBox("<html><b>Analysis</html></b>");
		prediction = new JCheckBox("<html><b>Prediction</html></b>");
		
		 JLabel jl = new JLabel("<HTML><U>Select winning column</U></HTML>");
		 jl.setFont(new Font("Arial", Font.BOLD, 13));
		 jl.setForeground(new Color(0,128,0));
		 
		 JLabel jl2 = new JLabel("<HTML><U>Select finished matches</U></HTML>");
		 jl2.setFont(new Font("Arial", Font.BOLD, 13));
		 jl2.setForeground(new Color(0,128,0));
		 
		 super.getJpl().add(jl,"pos 60 20");
		 super.getJpl().add(matches_jpl, "pos 20 50");
		
		 super.getJpl().add(jl2,"pos 450 20");
		 super.getJpl().add(matches_finished_jpl, "pos 420 50");
		 
		for(int i=0; i<30; i++){
			   
			   matches_check_checkboxes[i] = new JCheckBox(i+1 + ".");
			   matches_check_checkboxes[i].setFont(new Font("Arial",Font.BOLD, 12));
			   
			   matches_finished_checkboxes[i] = new JCheckBox(i+1 + ".");
			   matches_finished_checkboxes[i].setFont(new Font("Arial",Font.BOLD, 12));
			   
				  int col = i%5;
				  int row = i/5 + 1;
				  int f = 10 + col * 40 ;
				  int g = -30 + row * 40;
				  
				  String cell = "pos " + f + " " + g + "";
				
				  matches_jpl.add(matches_check_checkboxes[i],cell);
				  matches_finished_jpl.add(matches_finished_checkboxes[i],cell);
				 
			   }
		
		super.getJpl().add(super.getBtnClose(), "pos 280 170");
		super.getJpl().add(btnCurrentColumns, "pos 280 90");
		super.getJpl().add(btnWinCheck, "pos 280 130");
		super.getJpl().add(super.getBtnSelect(), "pos 280 50");
		super.getJpl().add(btnSaveWinningColumn, "pos 280 210");
		super.getJpl().add(btnLoadWinningColumn, "pos 280 250");
		
		super.getJpl().add(super.getLblFile(), "pos 50 310");
		super.getJpl().add(super.getLblSumColumns(), "pos 50 330");
		
		super.getJpl().add(analysis, "pos 280 300");
		super.getJpl().add(prediction, "pos 280 320");
		
		btnWinCheck.setEnabled(false);
	    analysis.setEnabled(false);
	    prediction.setEnabled(false);
	    
	    
		for(int i=0; i<super.getJpl().getComponentCount(); i++) {
			Component co = super.getJpl().getComponent(i);
				addPointerHand(co);		
		}
				
		
		   
		for(int i=0; i<30; i++) {			
			matches_check_checkboxes[i].addItemListener(this);
			matches_finished_checkboxes[i].addItemListener(this);
		}
		
		
		
super.getBtnSelect().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				fileSelect();
				
				if(isFileLoaded()) {
					
					btnWinCheck.setEnabled(true);
				    analysis.setEnabled(true);
				    prediction.setEnabled(true);
				    
				}
			
		}
		});

super.getBtnClose().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			
		}
		});

btnCurrentColumns.addActionListener(new ActionListener() {

			
		public void actionPerformed(ActionEvent arg0) {
				

             selectCurrent();
		     
		     if(isFileLoaded()) {
		    	 
		    	 
		        btnWinCheck.setEnabled(true);
		        analysis.setEnabled(true);
		        prediction.setEnabled(true);

		     }
				
			}

		});


btnSaveWinningColumn.addActionListener(new ActionListener() {

	
	public void actionPerformed(ActionEvent arg0) {
			

		String path = "winning_column.txt";		//Name of the file where winning column will be saved			
		File file = new File(path);
		
		if(file.exists()){
			file.delete();
		}
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(path, true);
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}
		
		BufferedWriter bw = new BufferedWriter(writer);
		
		for(int i=0; i<30; i++) {  // Saving winning column matches to file
			String str = "";
			if(matches_check_checkboxes[i].isSelected()) {
				str = "" + i;
				
				try {

					bw.write(str);
					bw.newLine();
					
				} catch (IOException e1) {
					
					
					e1.printStackTrace();
				}
				
				

			}
		}
		
		try {
			bw.write("--");           // The string -- seperates winning column from finished matches
			bw.newLine();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		for(int i=0; i<30; i++) {
			String str = "";
			if(matches_finished_checkboxes[i].isSelected()) {  // Saving finished matches to file
				str = "" + i;
				
				try {

					bw.write(str);
					bw.newLine();
					
				} catch (IOException e1) {
					
					
					e1.printStackTrace();
				}
				
				

			}
		}
		
		try {
			bw.close();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		
		
		Message m = new Message("Success", "Winning column and finished matches succesfully saved to disk", "Info");
        m.show();
		
			
		}

	});

btnLoadWinningColumn.addActionListener(new ActionListener() {

	
	public void actionPerformed(ActionEvent arg0) {
			

		String path = "winning_column.txt";					
		File file = new File(path);
		

		Reader reader = null;
		try {
			reader = new FileReader(path);
			
			BufferedReader br = new BufferedReader(reader);
			
			for(int i=0; i<30; i++) {
				
				matches_check_checkboxes[i].setSelected(false);     //Clears all selected checkboxed for winning column and
				matches_finished_checkboxes[i].setSelected(false);  // finished matches
			}
			
			String line;
			
		    try {
				while ((line = br.readLine()) != null && !(line.equals("--"))) {
				    int num = Integer.parseInt(line);
				    matches_check_checkboxes[num].setSelected(true);
				}
				
				
				while ((line = br.readLine()) != null) {
					if(!line.equals("--")) {
				    int num = Integer.parseInt(line);
				    matches_finished_checkboxes[num].setSelected(true);
					}
				}
					
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			try {
				br.close();
				Message m = new Message("Success", "Winning column and finished matches succesfully loaded.", "Info");
		        m.show();
				
			} catch (IOException e1) {
				
				e1.printStackTrace();
			} 
			
			
		} catch (IOException e2) {
			
			Message m = new Message("Error", "No file found", "Error");
	        m.show();
			
		}
		

			
		}

	});



btnWinCheck.addActionListener(new ActionListener() {

	
	public void actionPerformed(ActionEvent arg0) {
			

		calculateWinnings();

			
		}

	});


	}

	public JGradientButton getBtnCalc() {
		return btnWinCheck;
	}


	public void setBtnCalc(JGradientButton btnCalc) {
		this.btnWinCheck = btnCalc;
	}


	public JPanel getAgonesjpl() {
		return matches_jpl;
	}


	public void setAgonesjpl(JPanel agonesjpl) {
		this.matches_jpl = agonesjpl;
	}


	public JCheckBox[] getAgonesdialogis_checkboxes() {
		return matches_check_checkboxes;
	}


	public void setAgonesdialogis_checkboxes(JCheckBox[] agonesdialogis_checkboxes) {
		this.matches_check_checkboxes = agonesdialogis_checkboxes;
	}


	public JCheckBox getAnalysis() {
		return analysis;
	}


	public void setAnalysis(JCheckBox analysis) {
		this.analysis = analysis;
	}

public void calculateWinnings() {
	
	
	
	 
	   if(matches.size() > 0) {
	      matches.clear();
	      matches_finished.clear();
	   }
	   // if match checkbox is checked, then add it to the winnings 8.
	   for(int i=0; i<30; i++){
		   if(matches_check_checkboxes[i].isSelected() == true)
			   matches.add(i+1);
		   if(matches_finished_checkboxes[i].isSelected() == true)
			   matches_finished.add(i+1);
	   }
	   
	   ArrayList<ArrayList<Integer>> csv_array = new  ArrayList<ArrayList<Integer>>();
	   csv_array = readCsvToArrayList(getFilePath());
	   
	   if(matches.size() < 9 && matches.size() > 1) {
		   
		int categ1 = 0;
		int categ2 = 0;
		int categ3 = 0;
		boolean b;
		
		for(int j=0; j<csv_array.size(); j++){
			 int sum_right = 0;
			 int sum_finished = 0;
			 
			 for(int k=0; k<matches.size(); k++){
				if(csv_array.get(j).contains(matches.get(k)))
						sum_right = sum_right + 1;
				
			    }
			 
			 for(int k=0; k<matches_finished.size(); k++){
					if(csv_array.get(j).contains(matches_finished.get(k)))
						sum_finished = sum_finished + 1;
					
				    }
			
			 if(!prediction.isSelected()) {
				 
				 b = false;
				 
			      if(sum_right == matches.size()-2) {
				
				    if(analysis.isSelected())
				    showAnalysis(j,sum_right,csv_array,b);
				   
				    categ3 = categ3 + 1;
			         }
			
					if(sum_right == matches.size()-1) {
						
						   if(analysis.isSelected())
							   
							   showAnalysis(j,sum_right,csv_array,b);
							   
						   categ2 = categ2 + 1;
					 }
					 
					
					if(sum_right == matches.size()) {
						
						  if(analysis.isSelected())
							  
							 showAnalysis(j,sum_right,csv_array,b);
					   
						  
						     categ1 = categ1 + 1;
					
					 }
	    
			 }
			 
			 else {  // If checkbox 'prediction' is selected
				 
				 b = true;
				 

			      if((sum_right + 8 - sum_finished) == 6 && ((8-matches.size() + sum_right) > 5)) {
				
				    if(analysis.isSelected()) {
				       showAnalysis(j,6,csv_array,b);
				     
				    }
				   
				       categ3 = categ3 + 1;
			         }
			
					if((sum_right + 8 - sum_finished) == 7 && ((8-matches.size() + sum_right) > 6)) {
						
						   if(analysis.isSelected())
							   
							   showAnalysis(j,7,csv_array,b);
							   
						   categ2 = categ2 + 1;
					 }
					 
					
					if((sum_right + 8 - sum_finished) == 8 && ((8-matches.size() + sum_right) > 7)) {
						
						  if(analysis.isSelected())
							  
							 showAnalysis(j,8,csv_array,b);
					   
						  
						     categ1 = categ1 + 1;
					
					 }
	    
			
				 
			 }
				 
				 
					
		}
		
		String str ="";
		
    	if(!prediction.isSelected()) {
		    str = "<html>There are <br>" + categ1 + " columns with " + matches.size()   + " right predicted, <br>" +
				                              categ2 + " columns with " + (matches.size()-1) + " right predicted, <br>" +
				                              categ3 + " columns with " + (matches.size()-2) + " right predicted. <html>";
    	}
    	else   {
    		     str = "<html>Predictions : <br>" + categ1 + " columns with 8 right predicted, <br>" +
                     categ2 + " columns with 7 right predicted, <br>" +
                     categ3 + " columns with 6 right predicted. <html>";
    	}
    	
	   Message m = new Message("Success", str, "Info");
	   m.show();
	   
		}
	   
	   else
		   
	   {
		   
		   Message m = new Message("Error", "Select 1-8 numbers", "Error");
		   m.show();
		   
	   }
	   
	   
	   
	   
	   
	   
	   
	   
}

public ArrayList<ArrayList<Integer>> readCsvToArrayList(String file) {
	

	
	ArrayList<ArrayList<Integer>> csv_array = new ArrayList<ArrayList<Integer>>();
	
	CSVReader csvReader = null;
try {
	
	String arxeio = file ;
	csvReader = new CSVReader(new FileReader(arxeio));
} catch (FileNotFoundException e1) {
	
	e1.printStackTrace();
}
   


String[] row = null;
   

   try {
	while((row = csvReader.readNext()) != null) {
	
		
		// Saving csv lines one by one
	   	
	   	ArrayList<Integer> csv_line = new ArrayList<Integer>();
	   	
	   	for(int i=0; i<8; i++){
	   		csv_line.add(Integer.parseInt(row[i]));
	   	}
	   	csv_array.add(csv_line);
	   		
	   }
	

	
} catch (IOException e1) {
	
	Message m = new Message("Error", "Not a proper file - can't be loaded!","Error");
	m.show();
	
}
   //...
   try {
	csvReader.close();
} catch (IOException e1) {
	
	e1.printStackTrace();
}
   

	return csv_array;
		

	
}

public void showAnalysis(int row, int rightChoises, ArrayList<ArrayList<Integer>> arr, boolean pred){
	
	    String str = "";
	    
	    if(!pred) {
	
		str = "<html>There are " + rightChoises + " right in column " + (row+1) + " :<br>" + arr.get(row).toString().substring(1, arr.get(row).toString().length()-1) + "</html>";
		
	    }
	    
	    else
	    	
			str = "<html>Possible " + rightChoises + " right in column " + (row+1) + " :<br>" + arr.get(row).toString().substring(1, arr.get(row).toString().length()-1) + "</html>";

	    	
		Message m = new Message("Success", str,"Info");
		
		m.show();
		
	
}



	@Override
	public void itemStateChanged(ItemEvent e) {
		
		int index = 0;
		Object source = e.getItemSelectable();
		
		String selected = "";
		
		for(int i=0; i<30; i++) {
			if(source == matches_check_checkboxes[i]) {
				index = i;
				selected = "winning_match";
				break;
		 }
			if(source == matches_finished_checkboxes[i]) {
				index = i;
				selected = "match_finished";
				break;
		 }
		}
		
		
	if(selected.equals("winning_match")) {
		if(e.getStateChange() == ItemEvent.DESELECTED)
			matches_check_checkboxes[index].setForeground(Color.black);
		else
			if(e.getStateChange() == ItemEvent.SELECTED)
				matches_check_checkboxes[index].setForeground(Color.red);
	}
	
	else
		
		if(selected.equals("match_finished")) {
			if(e.getStateChange() == ItemEvent.DESELECTED)
				matches_finished_checkboxes[index].setForeground(Color.black);
			else
				if(e.getStateChange() == ItemEvent.SELECTED)
					matches_finished_checkboxes[index].setForeground(Color.red);
		}
		
	}
	
}
