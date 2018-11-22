import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	private JPanel matches_jpl;
	private JCheckBox matches_check_checkboxes[];
	private JCheckBox analysis;
	private ArrayList<Integer> matches;
	
	
	public WinningCheckView(){
		
		super();
		
		matches = new ArrayList<Integer>();
		
		matches_jpl = new JPanel();
		matches_check_checkboxes = new JCheckBox[30];
		
		matches_jpl.setLayout(new MigLayout());
		
		matches_jpl.setPreferredSize(new Dimension(220,245));
		matches_jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		btnCurrentColumns = new JGradientButton(100, 35, 13,"Current", "Selects the current columns",156,142,175);
		btnWinCheck = new JGradientButton(100, 35, 13,"Check", "Checks if there is a winning column",156,142,175);

		analysis = new JCheckBox("<html><b>Analysis</html></b>");
		
		
		 JLabel jl = new JLabel("<HTML><U>Select winning column</U></HTML>");
		 jl.setFont(new Font("Arial", Font.BOLD, 13));
		 jl.setForeground(new Color(0,128,0));
		 
		 super.getJpl().add(jl,"pos 60 20");
		 super.getJpl().add(matches_jpl, "pos 20 50");
		
		 
		 
		for(int i=0; i<30; i++){
			   
			   matches_check_checkboxes[i] = new JCheckBox(i+1 + ".");
			   matches_check_checkboxes[i].setFont(new Font("Arial",Font.BOLD, 12));
			   
				  int col = i%5;
				  int seira = i/5 + 1;
				  int f = 10 + col * 40 ;
				  int g = -30 + seira * 40;
				  
				  String cell = "pos " + f + " " + g + "";
				
				  matches_jpl.add(matches_check_checkboxes[i],cell);
				 
			   }
		
		super.getJpl().add(super.getBtnClose(), "pos 280 170");
		super.getJpl().add(btnCurrentColumns, "pos 280 90");
		super.getJpl().add(btnWinCheck, "pos 280 130");
		super.getJpl().add(super.getBtnSelect(), "pos 280 50");
		
		
		super.getJpl().add(super.getLblFile(), "pos 50 310");
		super.getJpl().add(super.getLblSumColumns(), "pos 50 330");
		
		super.getJpl().add(analysis, "pos 280 280");

		btnWinCheck.setEnabled(false);
	    analysis.setEnabled(false);
		
		for(int i=0; i<super.getJpl().getComponentCount(); i++) {
			Component co = super.getJpl().getComponent(i);
				addPointerHand(co);		
		}
				
		
		   
		for(int i=0; i<30; i++) {			
			matches_check_checkboxes[i].addItemListener(this);
			
		}
		
		
		
super.getBtnSelect().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				fileSelect();
				
				if(isFileLoaded()) {
					
					btnWinCheck.setEnabled(true);
				    analysis.setEnabled(true);
				    
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
	
	//DefaultTableModel tableModel3 = (DefaultTableModel) tblDialogi.getModel();
	
	 // Άδειασμα του agondial που είναι το arraylist που αποθηκεύονται οι αριθμοί της Νικήτριας στήλης
	   if(matches.size() > 0)
	   matches.clear();
	   
	   // Αν τo αντίστοιχο checkbox είναι επιλεγμένο, πρόσθεσε τον αγώνα στη νικήτρια στήλη
	   for(int i=0; i<30; i++){
		   if(matches_check_checkboxes[i].isSelected() == true)
			   matches.add(i+1);
	   }
	   
	   ArrayList<ArrayList<Integer>> csv_array = new  ArrayList<ArrayList<Integer>>();
	   csv_array = readCsvToArrayList(getFilePath());
	   
	   if(matches.size() < 9 && matches.size() > 1) {
		   
		int categ1 = 0;
		int categ2 = 0;
		int categ3 = 0;
		
		for(int j=0; j<csv_array.size(); j++){
			int sum_right = 0;
			for(int k=0; k<matches.size(); k++){
				if(csv_array.get(j).contains(matches.get(k)))
						sum_right = sum_right + 1;
			}
			
			if(sum_right == matches.size()-2) {
				
				   if(analysis.isSelected())
				   showAnalysis(j,sum_right,csv_array);
				   
				   categ3 = categ3 + 1;
			}
			
			if(sum_right == matches.size()-1) {
				
				   if(analysis.isSelected())
					   
					   showAnalysis(j,sum_right,csv_array);
					   
				   categ2 = categ2 + 1;
			}
			 
			
			if(sum_right == matches.size()) {
				
				  if(analysis.isSelected())
					  
					 showAnalysis(j,sum_right,csv_array);
			   
				  
				     categ1 = categ1 + 1;
			
			}
	    
	    		
					
		}
		
    	
		String str = "<html>There are <br>" + categ1 + " columns with " + matches.size()   + " right predicted, <br>" +
				                      categ2 + " columns with " + (matches.size()-1) + " right predicted, <br>" +
				                      categ3 + " columns with " + (matches.size()-2) + " right predicted. <html>";
	
	   Message m = new Message("Success", str, "Info");
	   m.show();
	   
		}
	   
	   else
		   
	   {
		   
		   Message m = new Message("Σφάλμα", "Επιλέξτε από 1-8 αριθμούς", "Error");
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
	
		
		// ArrayList για αποθήκευση κάθε γραμμής του csv
	   	
	   	ArrayList<Integer> csv_line = new ArrayList<Integer>();
	   	
	   	for(int i=0; i<8; i++){
	   		csv_line.add(Integer.parseInt(row[i]));
	   	}
	   	csv_array.add(csv_line);
	   		
	   }
	

	
} catch (IOException e1) {
	
	Message m = new Message("Σφάλμα", "Μη κατάλληλο αρχείο - δε μπορεί να διαβαστεί!","Error");
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

public void showAnalysis(int row, int rightChoises, ArrayList<ArrayList<Integer>> arr){
	
	
		String str = "<html>Έχετε " + rightChoises + " σωστά στη στήλη " + (row+1) + " :<br>" + arr.get(row).toString().substring(1, arr.get(row).toString().length()-1) + "</html>";
		
		Message m = new Message("Επιτυχία", str,"Info");
		
		m.show();
		
	
}



	@Override
	public void itemStateChanged(ItemEvent e) {
		
		int index = 0;
		Object source = e.getItemSelectable();
		
		
		for(int i=0; i<30; i++) {
			if(source == matches_check_checkboxes[i]) {
				index = i;
				break;
		 }
		}
		if(e.getStateChange() == ItemEvent.DESELECTED)
			matches_check_checkboxes[index].setForeground(Color.black);
		else
			if(e.getStateChange() == ItemEvent.SELECTED)
				matches_check_checkboxes[index].setForeground(Color.red);
		
		
	}
	
}
