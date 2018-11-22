
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class VariableView extends BaseView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JGradientButton btnCalc;
	private JGradientButton btnSave;
	private JGradientButton btnCurrentColumns;
	private JPanel radioPanel;
	private ButtonGroup bg;
	private JRadioButton rbFive;
	private JRadioButton rbSix;
	private JRadioButton rbSeven;
	private JProgressBar jb;
	private ArrayList<ArrayList<Integer>> variablecolumns;
	
	
	public VariableView(){
		
		super();
		
		
		btnCalc = new JGradientButton(100, 35, 13,"Calculate", "Calculates sum of the columns",156,142,175);
		btnSave = new JGradientButton(100, 35, 13,"Save", "Saves columns into a file",156,142,175);
		btnCurrentColumns = new JGradientButton(100, 35, 13,"Current", "Selects current columns",156,142,175);
		
		rbFive = new JRadioButton("For 100% 5 right",false);
		rbSix = new JRadioButton("For 100% 6 right",false);
		rbSeven = new JRadioButton("For 100% 7 right",true);
		
		radioPanel = new JPanel();
		radioPanel.setLayout(new MigLayout());
		radioPanel.setPreferredSize(new Dimension(150,120));
		
		Border lowered = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		TitledBorder border = BorderFactory.createTitledBorder(lowered,"Variable");

		radioPanel.setBorder(border);
		
		radioPanel.add(rbSeven, "pos 10 10");
		radioPanel.add(rbSix, "pos 10 40");
		radioPanel.add(rbFive,"pos 10 70");
		
		
		super.getJpl().add(radioPanel,"pos 50 50");
		
		bg = new ButtonGroup();
		bg.add(rbSeven);
		bg.add(rbSix);
		bg.add(rbFive);
		
		jb = new JProgressBar(0,100);
		jb.setValue(0);    
		jb.setStringPainted(true);  
		
		super.getJpl().add(super.getBtnClose(), "pos 230 210");
		super.getJpl().add(btnCurrentColumns, "pos 230 90");
		super.getJpl().add(btnCalc, "pos 230 130");
		super.getJpl().add(btnSave, "pos 230 170");
		super.getJpl().add(super.getBtnSelect(), "pos 230 50");
		
		
		super.getJpl().add(super.getLblFile(), "pos 60 280");
		super.getJpl().add(super.getLblSumColumns(), "pos 60 300");
		
		super.getJpl().add(jb, "pos 50 230");
		jb.setVisible(false);
		
		btnCalc.setEnabled(false);
		btnSave.setEnabled(false);
		
		for(int i=0; i<super.getJpl().getComponentCount(); i++) {
			Component co = super.getJpl().getComponent(i);
				addPointerHand(co);
		}
		   
		
		


btnSave.addActionListener(new ActionListener() {

	@Override
	public void actionPerformed(ActionEvent ae) {
		
    	
    	JFileChooser fileChooser = new JFileChooser();
    	
    	if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
    	  
    	     File file = fileChooser.getSelectedFile();
    	     
	    	  String path = file.getAbsolutePath() + ".ppg";
	    	  
	    	   file = new File(path);
	    	   
	    	   if(!file.exists()) {
	    		   
                   saveMetavlito(path,variablecolumns);
	    	   
	    	       Message m = new Message("Success", "Variable columns succesfully saved", "Info");
	    	       
	    	       m.show();
	    	   }
	    	   
	    	   else
	    	  
	          if(file.exists()) {
	    	
	    	      int dialogResult = JOptionPane.showConfirmDialog (null, "File already exists and will be deleted. Procceed?","Warning", JOptionPane.YES_NO_OPTION);
	    	
	    	            if(dialogResult == JOptionPane.YES_OPTION) {
	    	            	
	    	            file.delete();
	    	  
	    	            saveMetavlito(path,variablecolumns);
	    		    	   
	  	    	         Message m = new Message("Success", "Variable columns succesfully saved", "Info");
	  	    	       
	  	    	         m.show();
    	  
    	}
	    	            
	    	 
	    	         
	    	         
	    	            	
	    	            	
	    }
	}
		
	}
	
});

super.getBtnSelect().addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent arg0) {
		
		fileSelect();
		
		if(isFileLoaded())
			btnCalc.setEnabled(true);
	
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
     
       if(isFileLoaded())
    	   btnCalc.setEnabled(true);

		
	}

});


btnCalc.addActionListener(new ActionListener() {

	
	public void actionPerformed(ActionEvent arg0) {
		
		
		Runnable runner = new Runnable()
	    {
	        public void run() {
	        	
	            calculate();
	        	
	        }
	    };
	    Thread t = new Thread(runner, "Code Executer");
	    t.start();
		
		
		
	}
	
	
});

		
		
	}
	
public ArrayList<ArrayList<Integer>> calcVariable(ArrayList<ArrayList<Integer>> csv_array) {
	

	 int right = 7;
	   
	   if(rbSix.isSelected())
		   right = 6;
		   else
			   if(rbFive.isSelected())
				   right = 5;
	   
	   ArrayList<ArrayList<Integer>> variable = greedyAlgorithm(csv_array , right);
	   
	   return variable;
	
}
	


	
// Here runs a greedy algorithm, that will make a variable system. The problem is similar to the "vertex cover problem".
// First, an arraylist of arraylist will be created. Rows, are all columns to be examined.
// All columns will be in pairs compared
// If two columns, have at least so many common matches that we are searching for, then this column is added
// at the first columns summation.
// Example : Let's say, we have 4 columns,
// a.1,2,3,4,5,6,7,8
// b.1,2,3,4,5,6,7,9
// c.1,2,3,4,5,6,11,12
// d.11,12,13,14,15,16,17,18
// and we search for minimum 6 common
// Comparing columns
// Column a, has 7 common with b, has 6 common with c and 0 common with d . So, column a can represent 2 columns
// Column b, has 7 common with a, has 6 common with c and  0 common with d. So, column b can represent 2 columns
// Column c, has 6 common with a, has 6 common with b and  0 common with d. So, column c can represent 2 columns
// Column d has no common with the other 3 columns.
// So, a=2, b=2, c =2, d=0.
// Since we have 3 equal with 2 columns each, algorithm takes the first,here a, then deletes all the columns that can be represented by that
// column and procceds till the array has no columns.
// Here, the solution would be a,d.
// Of course, a greedy algorithm is not the best (can be, but not always), but it is a lot quickier than
// checking all possible solutions and select the best

public ArrayList<ArrayList<Integer>> greedyAlgorithm (ArrayList<ArrayList<Integer>> csv_array, int zit) {


		   ArrayList<Integer> currColumn = new ArrayList<>();
		   ArrayList<Integer> represent = new ArrayList<>();
		   ArrayList<ArrayList<Integer>> variableColumns = new ArrayList<ArrayList<Integer>>();
		   ArrayList<ArrayList<Integer>> my_array = new ArrayList<ArrayList<Integer>>();
		   ArrayList<Integer> cardinal = new ArrayList<Integer>();
		   ArrayList<String> deleted = new ArrayList<String>();
		   
		  
		   int asked = zit ; 
		   

		   
		   for(int i=0; i<csv_array.size(); i++){
			   
			   ArrayList<Integer> temp = new ArrayList<>();
			 
			   
			   
			   for(int k=0; k<csv_array.size(); k++) {
				   
				   
			   
				   represent = new ArrayList<Integer>(csv_array.get(i));
		    	   currColumn = new ArrayList<Integer>(csv_array.get(k));
			       int right = 0;
			   
			   
			   
		        for(int j=0; j<8; j++){
				   
				   if(represent.contains(currColumn.get(j)))
					   right = right + 1;
				   
			   }
			  
			   
			
		        	
		        	 if(right >= asked){
		        		 temp.add(k);
		  		           }	
		        
	
			
			   } 
			   
			   my_array.add(temp);
			   
			   
		   }
		   
		   
		   for(int i=0; i<my_array.size(); i++) {
			   cardinal.add(my_array.get(i).size());
			   deleted.add("false");
			  
		   }
		   
	
		   
		   boolean empty = true;
		   
		      for(int c=0; c<my_array.size(); c++) {
		    	  if(my_array.get(c).size() > 0) {
		    		  empty = false;
		    		  break;
		            }
		         }
		      
		
		 
		    int del = 0;  
		      
		   while(!empty) {
			   
					   int max = 0;
					   int pos = 0;
					   
					
					   
			          for(int j=0; j<deleted.size(); j++) {
				           if(deleted.get(j).equals("false")) {
				        	   max = cardinal.get(j);
				        	   pos = j;
				        	   break;
				        	   
				           }
					          
			               }
			          
					  
					 
					   for(int i=0; i<deleted.size(); i++) {
						   if(cardinal.get(i) > max && deleted.get(i).equals("false")) {
							   max = cardinal.get(i);
							   pos = i;
							   
						   }
						   
						  
							  
					   }
					   

				
						   
						   
				
					   
					   represent = new ArrayList<Integer>(csv_array.get(pos));
					  
					   variableColumns.add(represent);
					   
					   ArrayList<Integer> represent2 = new ArrayList<Integer>(my_array.get(pos));
					   
					   represent2.add(pos);
					   
					   Collections.sort(represent2);
					   
					   
			 for(int m=0; m < represent2.size() ; m++) {
							 
							
							 
							 int val = represent2.get(m);
								    
									
						     my_array.get(val).clear();
						     
						     deleted.set(val, "true");
							
						     del = del + 1;
						     
						     jb.setValue(del*100/deleted.size());

						 }
					   
	   
					   
			

					 
		 for(int m=0; m < represent2.size() ; m++) {
							 
							 
							 
							 for(int m2=0; m2< my_array.size(); m2++) {
								 
								  
								    
								    if(my_array.get(m2).size() > 0)
									
								    if(my_array.get(m2).contains(represent2.get(m))){
			
									    my_array.get(m2).remove(Integer.valueOf(represent2.get(m)));
										 
								
									 }
									
								 
							 }
							 
							
							
							
							
							 

						 }
					   
					   
					    
		
			    
				
				
			
					 
					 cardinal.clear();
					 
					 for(int i=0; i<my_array.size(); i++) {
						   cardinal.add(my_array.get(i).size());
						   
					   } 
				
			  
					 empty = true;
					 
					 for(int c=0; c<my_array.size(); c++) {
				    	  if(deleted.get(c).equals("false")) {
				    		  empty = false;
				    		  break;
				      }
				      }
				
					 
				
	        
	         

	        	 
		   }
			   

		
		return variableColumns;
			

	}

	

public void calculate() {

	String file = getFilePath();
	
	jb.setVisible(true);
	jb.setValue(0);
   
   ArrayList<ArrayList<Integer>> csv_array = readCsvToArrayList(file);
   variablecolumns = calcVariable(csv_array);
   
   Thread t1 = new Thread();
   try {
	t1.sleep(1000);
} catch (InterruptedException e) {
	
	e.printStackTrace();
}
   
   double economy = 100-(100*(double)variablecolumns.size()/(double)csv_array.size());
   String res = String.format("%.2f", economy);
   
   String str = "<html>Variable columns : <b><font size=5 color=blue>" + Integer.toString(variablecolumns.size()) + "</font></b>"
   		+ "<br> Economy : " + res + " % </br></html>";
   

   jb.setVisible(false);
   
   Message m = new Message("Info", str, "Info");
   m.show();

   btnSave.setEnabled(true);



}

public void saveMetavlito(String path, ArrayList<ArrayList<Integer>> array) {
	

	  FileWriter writer = null;
	try {
		writer = new FileWriter(path, true);
	} catch (IOException e2) {
		
		e2.printStackTrace();
	}

BufferedWriter bw = new BufferedWriter(writer);


for(int i=0; i<array.size(); i++) {
	
	try {
		
		String str = "";
		
		for(int j=0; j<8; j++) {
			
		   if(j < 7)
			 str = str + array.get(i).get(j) + ",";
		   else
			 str = str + array.get(i).get(j) + "\n";
			
		}
		
		
		bw.write(str);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
}


try {
	bw.close();
} catch (IOException e) {
	
	e.printStackTrace();
}
	
	
}


}
