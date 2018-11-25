import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import net.miginfocom.swing.MigLayout;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.opencsv.CSVReader;


public class gui extends JFrame implements Printable, ItemListener{

   JTabbedPane jtb = new JTabbedPane();
   
   private JPanel pnlOperations = new JPanel();
   private JPanel pnlMatches = new JPanel();
   private JPanel pnlStats = new JPanel();
   private JPanel pnlBasicColumns = new JPanel();
   
   private JCheckBox matches_checkboxes[];
   private JCheckBox oddeven_checkboxes[];
   private JCheckBox lowhigh_checkboxes[];
   private JCheckBox symmetry_checkboxes[];
   private JCheckBox endings_checkboxes[];
   private JCheckBox neighbours_checkboxes[];
   private JCheckBox transitions_checkboxes[];
   private JCheckBox agoneskon_checkboxes[];
   private JCheckBox basiccolumns_value_checkboxes[];
   
   private JGradientButton btnLoadCoupon;
   private JGradientButton btnNewSystem;
   private JGradientButton btnAll;
   private JGradientButton btnNone;
   private JGradientButton btnEndOfSelection;
   private JGradientButton btnDummyCoupon;
   
   private JGradientButton all1, all2, all3, all4, all5, all6;

   private JButton btnAddBasicColumn = new JGradientButton("Add");
   private JButton btnDelBasicColumn = new JGradientButton("Delete");

   private JButton btnAddSum = new JGradientButton("Add sum");
   private JButton btnDelSum = new JGradientButton("Delete sum");
   
   private JGradientButton btnGroupUp = new JGradientButton(30, 20, 13,"<html><b><center> + </center></b></html>", "Selects all choises",156,142,175);
   private JGradientButton btnGroupDown = new JGradientButton(30, 20, 13,"<html><b><center> - </center></b></html>", "Selects all choises",156,142,175);
   private JGradientButton btnAddGroupLimits = new JGradientButton(130, 30, 13,"<html><b><center>Add group limits</center></b></html>", "Selects all choises",156,142,175);

   private JTable tblBasicColumns = new JTable();
   private JTable tblMatches = new JTable();
   private JTable tblBasicColumnMatches = new JTable();
   private JTable tblSums = new JTable();
   
   private Systima systima = new Systima();

   private ArrayList<Integer> tempBasicColumn = new ArrayList<Integer>();
   private ArrayList<Integer> tempLimits = new ArrayList<Integer>();
   
   private ArrayList<String> hometeams;
   private ArrayList<String> awayteams;
   
   private JTextField txtMinSum = new JTextField(3);
   private JTextField txtMaxSum = new JTextField(3);
   
   private JLabel lblSelectedMatches = new JLabel();
   private JLabel lblNumOfGroup = new JLabel(" Group 1");
   
   private JLabel lblGroupLimits = new JLabel("Group limits");
   private JLabel lblLimitFrom = new JLabel("Min :");
   private JLabel lblLimitTo = new JLabel("Max :");
   private JTextField txtLimUp = new JTextField("0");
   private JTextField txtLimDown = new JTextField("0");
 
   private JGradientButton btnView ;
   private JGradientButton btnMakeVariable;
   private JGradientButton btnWinCheck ;
   private JGradientButton btnCurrentView ;
   
   private JTextField txtOriaErrorsFrom = new JTextField("0");
   private JTextField txtOriaErrorsTo = new JTextField("0");
   
   
   public gui() {
	   
	   
	   jtb.setBackground(new Color(156,142,175));
	   jtb.setOpaque(true);
	   
	   
	   add(jtb);
	   
	   
	   
	   // adds the panels at jTabbed panel jtb //
	   
	   jtb.add("Matches",pnlMatches);
	   jtb.add("Statistics",pnlStats);
	   jtb.add("Basic columns",pnlBasicColumns);
	   jtb.add("Operations",pnlOperations);

	   jtb.setFont(new Font("Arial", Font.BOLD, 13));
	  
	  jtb.setEnabledAt(1, false);      
	  jtb.setEnabledAt(2, false);     
	 

	   pnlOperations.setLayout(new MigLayout());
	   pnlMatches.setLayout(new MigLayout());
	   pnlStats.setLayout(new MigLayout());
	   pnlBasicColumns.setLayout(new MigLayout());
       

	   matches_checkboxes = new JCheckBox[30];
	   oddeven_checkboxes = new JCheckBox[9];
	   lowhigh_checkboxes = new JCheckBox[9];
	   symmetry_checkboxes = new JCheckBox[5];
	   endings_checkboxes = new JCheckBox[8];
	   neighbours_checkboxes = new JCheckBox[8];
	   transitions_checkboxes = new JCheckBox[8];


	   btnView = new JGradientButton(200, 50, 13,"View - print file", "View or print columns of selected file",156,142,175);
	   pnlOperations.add(btnView, "pos 350 100");
	   
	   btnCurrentView = new JGradientButton(200, 50, 13,"Current columns", "View current columns",156,142,175);
	   pnlOperations.add(btnCurrentView, "pos 600 100");
	   
	   btnWinCheck = new JGradientButton(200, 50, 13,"Winnings check", "Checks if a columns file wins",156,142,175);
	   pnlOperations.add(btnWinCheck, "pos 350 200");
	   
	   
	   btnMakeVariable = new JGradientButton(200, 50, 13,"Variable calc", "Variable based on column file",156,142,175);
	   pnlOperations.add(btnMakeVariable, "pos 100 100");
	   
	   JGradientButton btnConditionsToFile = new JGradientButton(200, 50, 13,"System to doc file", "Saves system details as .doc file",156,142,175);
	   pnlOperations.add(btnConditionsToFile, "pos 100 200");
	   
	   
	   for(int i=0; i<pnlOperations.getComponentCount(); i++) {
			Component co = pnlOperations.getComponent(i);
				addPointerHand(co);
		}

	 // add components panel pnlMatches //
	   
	   addComp1toPanelAgones();
	   addComp2toPanelAgones();
	   addComp3toPanelAgones();
	   addComp4toPanelAgones();
	   
		for(int i=0; i<30; i++) {			
			matches_checkboxes[i].addItemListener(this);
			
		}
		
		 for(int i=0; i<pnlMatches.getComponentCount(); i++) {
			 Component co = pnlMatches.getComponent(i);
			 addPointerHand(co);
		 }
		
		

	   addComp1toPanelStats();
	   addComp2toPanelStats();
	   addComp3toPanelStats();
	   addComp4toPanelStats();
	   addComp5toPanelStats();
	   addComp6toPanelStats();
	   addComp7toPanelStats();
	   
	   for(int i=0; i<9; i++) {			
			oddeven_checkboxes[i].addItemListener(this);
			lowhigh_checkboxes[i].addItemListener(this);
		}
	   
	   for(int i=0; i<5; i++) {			
			symmetry_checkboxes[i].addItemListener(this);
		}
	   
	   for(int i=0; i<8; i++) {			
			endings_checkboxes[i].addItemListener(this);
			neighbours_checkboxes[i].addItemListener(this);
			transitions_checkboxes[i].addItemListener(this);
		}
	   
	   for(int i=0; i<pnlStats.getComponentCount(); i++) {
			 Component co = pnlStats.getComponent(i);
			 addPointerHand(co);
		 }
	   

	   hometeams = new ArrayList<>();   
	   awayteams = new ArrayList<>();
	   

	   
 btnAll.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
			   
		    	
		    	  for(int i=0; i<30; i++){
		    		  
					   matches_checkboxes[i].setSelected(true);
					   
				   }	
		    	
		    	int agones = systima.getMatches().size();
			   
			   lblSelectedMatches.setText("Selected matches : " + agones);
		    	
			   btnEndOfSelection.setEnabled(true);
	   
		   }
	   }); 
	   
  btnNone.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
			   
			   for(int i=0; i<30; i++){
				   matches_checkboxes[i].setSelected(false);
			   }
			   
               
               int agones = systima.getMatches().size();
			   
			   lblSelectedMatches.setText("Selected matches : " + agones);
		    	
			   btnEndOfSelection.setEnabled(false);
			   
		   }
	   }); 
	   
  
  all1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
			  
            
			   for(int i=0; i<9; i++){
				   
				  oddeven_checkboxes[i].setSelected(true);
				
           
			   }
			   
		   }
	   }); 
	   

	   
	   
all2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
			   
			   for(int i=0; i<9; i++){
				   
				  lowhigh_checkboxes[i].setSelected(true);
				  
	
			   }
			   
		   }
	   }); 
	   
	
	   
all3.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
			   
			   for(int i=0; i<5; i++){
				  symmetry_checkboxes[i].setSelected(true);
				  
		
			   }
			   
		   }
	   }); 
	   
	

	   
all4.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
			   
			   for(int i=0; i<8; i++){
				  endings_checkboxes[i].setSelected(true);
				  
		
			   }
			   
		   }
	   }); 
	   

	   
all5.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
			   
			   for(int i=0; i<8; i++){
				  neighbours_checkboxes[i].setSelected(true);
		
			   }
			   
		   }
	   }); 
	   
	
	   
all6.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
			   
			   for(int i=0; i<8; i++){
				  transitions_checkboxes[i].setSelected(true);
				  
			   }
			   
		   }
	   }); 
	   
	
	   
	
	   
 btnEndOfSelection.addActionListener(new ActionListener() {
	 
		    public void actionPerformed(ActionEvent e){
			   
		
		      	
		    	 addComp1toPanelKontres();
		    	 addComp2toPanelKontres();
		    	 addComp3toPanelKontres();
		    	 addComp4toPanelKontres();
		    	 addComp5toPanelKontres();
		    	 
		    	 addListenerToKontres();
		     	
                
		     	jtb.setEnabledAt(1, true);
		     	jtb.setEnabledAt(2, true); 
		     	
		     	 int ag = systima.getMatches().size();
				   
				   int min = 0;
				   for(int i=0; i<8; i++){
					   min = min + systima.getMatches().get(i);
				   }
				   
				   int max = 0;
				   for(int i=ag-1; i>ag-9; i--){
					   max = max + systima.getMatches().get(i);
				   }
				   
				   txtMinSum.setToolTipText("Min :" + min);
				   txtMaxSum.setToolTipText("Max :" + max);
				   
				  
				   
				   saveFullSystemToDisk(); //Save all columns to disk
                   
                   
                    
				   systima.getGroups().add(new GroupBC(1)); // Add a new BC Group with id = 1
				    
				   
				   
				   
				   for(int i = 0; i<30; i++) {
					   matches_checkboxes[i].setEnabled(false);
					   if(!matches_checkboxes[i].isSelected())
						   matches_checkboxes[i].setForeground(Color.gray);
						   
				   }
				   
				   btnLoadCoupon.setEnabled(false);
				 
				   btnAll.setEnabled(false);
				
				   btnNone.setEnabled(false);
				
				   btnEndOfSelection.setEnabled(false);
				
				   btnDummyCoupon.setEnabled(false);
				
				   
				   for(int i=0; i<pnlBasicColumns.getComponentCount(); i++) {
						Component co = pnlBasicColumns.getComponent(i);
							addPointerHand(co);
					}
				   
				   
				   String str = "Columns : " + systima.calcColumnCombination();
				   
				   Message m = new Message("Full system columns", str , "Info");
		            m.show();
			
				    
		   }
	   });
 
 
	   
 btnDummyCoupon.addActionListener(new ActionListener() {
	 
	    public void actionPerformed(ActionEvent e){
	    	
	    	if(hometeams.size() > 0) {
	    	
	    	hometeams.clear();
			awayteams.clear();
			
	    	}
	    	
	    	DefaultTableModel tableModel = (DefaultTableModel) tblMatches.getModel();
	    	tableModel.setRowCount(0); // Empty the table

           for(int i=0; i<30; i++){
        	   
        	    int a = i + 1 ;
		 		
		 		String b = "Team " + (i * 2 + 1);
		 		
		 		String c = "Team " + (i * 2 + 2);
		 	
	
		    	Object[] row2 = {a,b,c};
		    	tableModel.addRow(row2);
		 	
		       hometeams.add(b);
		       awayteams.add(c);
		       
		       
		 	
		 	}
           
           if(countAgones() > 7) {
        	   
        	   btnEndOfSelection.setEnabled(true);
   
           }
           
           
           btnDummyCoupon.setEnabled(false);
	    	
	    }
 });
 
 
 
 
btnGroupUp.addActionListener(new ActionListener() {
	 
	 public void actionPerformed(ActionEvent e){
		 
		 DefaultTableModel tableModel = (DefaultTableModel) tblBasicColumns.getModel();
		 
		 String str = lblNumOfGroup.getText().substring(7);
		 int currentOmilos = Integer.parseInt(str);
		 
		 

		 
		 if( systima.getGroups().size() == currentOmilos && 
		     systima.getGroups().get(currentOmilos - 1).getBasicColumns().size() > 0 ) {
			 
			 systima.getGroups().add(new GroupBC(currentOmilos + 1));
			 tableModel.setRowCount(0);
			 
			 lblNumOfGroup.setText(lblNumOfGroup.getText().substring(0,7) + (currentOmilos + 1));
			 
			 txtLimDown.setText("" + Collections.min(systima.getGroups().get(currentOmilos).getLimits()));
			 txtLimUp.setText("" + Collections.max(systima.getGroups().get(currentOmilos).getLimits()));

		 }
		 
		 else 
			 
			 if( systima.getGroups().size() > currentOmilos )
			 
		 {
			 
			    tableModel.setRowCount(0);
			    
			    txtLimDown.setText("" + Collections.min(systima.getGroups().get(currentOmilos).getLimits()));
				txtLimUp.setText("" + Collections.max(systima.getGroups().get(currentOmilos).getLimits()));
			 
			 
			    lblNumOfGroup.setText(lblNumOfGroup.getText().substring(0,7) + (currentOmilos + 1));
			    
			    str = lblNumOfGroup.getText().substring(7);
				currentOmilos = Integer.parseInt(str);
			    
			 	
			    for(int i=0; i<systima.getGroups().get(currentOmilos - 1).getBasicColumns().size(); i++) {
			    
		 		int a = i + 1;
		 		String b = removeClosures(systima.getGroups().get(currentOmilos - 1).getBasicColumns().get(i).getBasicColumn().toString());
		 		String c = removeClosures(systima.getGroups().get(currentOmilos - 1).getBasicColumns().get(i).getLimits().toString());
		 		
		 		
		    	Object[] row = {a,b,c};
		    	tableModel.addRow(row);
			 
			    }
			 
		 }
		 
		 
	 }
	 
	 
 });
 
 btnGroupDown.addActionListener(new ActionListener() {
	 public void actionPerformed(ActionEvent e){
		 
		 DefaultTableModel tableModel = (DefaultTableModel) tblBasicColumns.getModel();
		 String str = lblNumOfGroup.getText().substring(7);
		 int currentOmilos = Integer.parseInt(str);
		 
		 if(currentOmilos > 1) {
			 
			 currentOmilos = currentOmilos - 1;
		 
		    lblNumOfGroup.setText(lblNumOfGroup.getText().substring(0,7) + (currentOmilos));
		 
		    tableModel.setRowCount(0);
			 
		    
		    int id = systima.getGroups().get(currentOmilos - 1).getId();
		    
		    txtLimDown.setText("" + Collections.min(systima.getGroups().get(currentOmilos - 1).getLimits()));
			txtLimUp.setText("" + Collections.max(systima.getGroups().get(currentOmilos - 1).getLimits()));
			 
		 	
		    for(int i=0; i<systima.getGroups().get(id-1).getBasicColumns().size(); i++) {
		    
		 		int a = i + 1;
		 		String b = removeClosures(systima.getGroups().get(id-1).getBasicColumns().get(i).getBasicColumn().toString());
		 		String c = removeClosures(systima.getGroups().get(id-1).getBasicColumns().get(i).getLimits().toString());
		 		
		 		
		    	Object[] row = {a,b,c};
		    	tableModel.addRow(row);
		 
		    }
		 

		 }
		 
	 }
	 
 });
	   
btnAddBasicColumn.addActionListener(new ActionListener() {
	
    public void actionPerformed(ActionEvent e){
    	
    				String str = lblNumOfGroup.getText().substring(7);
    				int currentOmilos = Integer.parseInt(str) - 1;
					
		    	    ArrayList<Integer> tempKontra = new ArrayList<Integer>(tempBasicColumn);
		    	    ArrayList<Integer> tempOrio = new ArrayList<Integer>(tempLimits);
		    	    
		    	    if(tempKontra.size() > 0 && tempOrio.size() > 0) {
					
							BasicColumn vs = new BasicColumn(tempKontra,tempOrio);
																
							systima.getGroups().get(currentOmilos).getBasicColumns().add(vs);
							
			
						 	     
						 	DefaultTableModel tableModel = (DefaultTableModel) tblBasicColumns.getModel();     
						 	
						
						 	    int kon = (systima.getGroups().get(currentOmilos).getBasicColumns().size() - 1);
						 	
						 		int a = kon + 1;
						 		
						 		
						 		String b = removeClosures(systima.getGroups().get(currentOmilos).getBasicColumns().get(kon).getBasicColumn().toString());
						 		
						 		String c = removeClosures(systima.getGroups().get(currentOmilos).getBasicColumns().get(kon).getLimits().toString());
						 		
						 		
						    	Object[] row = {a,b,c};
						 	    tableModel.addRow(row);
							
						 
						 	
						 	
							
							
							for(int i=0; i<systima.getMatches().size(); i++){
								agoneskon_checkboxes[i].setSelected(false);
							}
							
							for(int i=0; i< 9; i++){
								basiccolumns_value_checkboxes[i].setSelected(false);
								basiccolumns_value_checkboxes[i].setEnabled(false) ;
							}
							
							 
							 btnAddBasicColumn.setEnabled(false);
							 
							 tempBasicColumn.clear();
							 tempLimits.clear();
							
						  
							Message m = new Message("Success", "Basic column added!", "Info");
							m.show();
							 
		    	    }
		    	    
		    	    else
		    	    {
		    	    	Message m = new Message("Failure", "No basic column limits selected", "Error");
					    m.show();
		    	    }
		    	    	
					 
					 if(systima.getGroups().get(currentOmilos).getBasicColumns().size() > 0)
						    btnDelBasicColumn.setEnabled(true);
						 else
							 btnDelBasicColumn.setEnabled(false);
					
					 
	
	
}
    
    
	   }); 
	     
btnDelBasicColumn.addActionListener(new ActionListener() {
	
		    public void actionPerformed(ActionEvent e){
			   
		    try {
		    	DefaultTableModel tableModel = (DefaultTableModel) tblBasicColumns.getModel();
		    	
		    	int row = tblBasicColumns.getSelectedRow();
		    	

		    	tableModel.removeRow(row);
		    	
		    	String str = lblNumOfGroup.getText().substring(7);
				int currentOmilos = Integer.parseInt(str);
				int j;
				
				for(j=0; j<systima.getGroups().size(); j++) {
					if(systima.getGroups().get(j).getId()==currentOmilos)
						break;
				}
				
				systima.getGroups().get(j).getBasicColumns().remove(row);
				
				
		    	
		    	for(int i=0; i<systima.getGroups().get(j).getBasicColumns().size(); i++){
		    		
		    		String val = "" + (i+1);
		    		tableModel.setValueAt(val, i, 0);
		    	}
		    	
		        if(systima.getGroups().get(j).getBasicColumns().size() > 0)
				    btnDelBasicColumn.setEnabled(true);
				 else
					 btnDelBasicColumn.setEnabled(false);
		    	
		    	
		    	}
		    	
		    	catch(Exception ex) {
		    		
                    Message m = new Message("Failure","No basic column selected!", "Error");
                    
		    		m.show();
		    	}

		    	
			   
		   }
	   });

btnAddSum.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e){
		
		
		
		try {
			
			int elax = Integer.parseInt(txtMinSum.getText());
			int meg = Integer.parseInt(txtMaxSum.getText());
			
			if(elax <= meg) {
				
				
			
			
			DefaultTableModel tableModel = (DefaultTableModel) tblSums.getModel();     
		 	

			int nrows = tblSums.getRowCount();
		 	
		 		int a = nrows + 1;
		 		String b = txtMinSum.getText();
		 		String c = txtMaxSum.getText();
		 		
		 		
		 	Object[] row = {a,b,c};
		 	tableModel.addRow(row);
		 	
		 	  for(int k=elax; k<=meg; k++) {
	    		   
		 		  
		 		  
	    		   if(!systima.getCondition().getSum().contains(k))
	    			   systima.getCondition().getSum().add(k);
	    			   
	    	   }
		 	  
		  

		 	
		 	
		}
		
			else {
				
				 Message m = new Message("Warning","Min sum must be less or equal of max sum", "Warning");
            
    		     m.show();
				
	
			}
			
		}
		
		catch(Exception ex){
			
			  Message m = new Message("Failure","Could not insert the sum!", "Error");
              
	    		m.show();
			
			
		}
		
	
	
	
	}
	
	
	
	
});

btnDelSum.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e){
	   
    	try {
    		
    		
    	DefaultTableModel tableModel = (DefaultTableModel) tblSums.getModel();
    	
    	int row = tblSums.getSelectedRow();
    	

    	tableModel.removeRow(row);
    	
    	
    	int nrows = tblSums.getRowCount();
    	
    	
    	
    	  
    	for(int i=0; i<nrows; i++){
    		
    		String val = "" + (i+1);
    		tableModel.setValueAt(val, i, 0);
    	}
    	
    	systima.getCondition().getSum().clear();
    	
    	for(int i=0; i<nrows; i++) {
    	
    	String str1 = (String)tableModel.getValueAt(i, 1);
    	String str2 = (String)tableModel.getValueAt(i, 2);
    	
    	int elax = Integer.parseInt(str1);
    	int meg = Integer.parseInt(str2);
    	
    	
    	
    	   for(int k=elax; k<=meg; k++) {
    		   
    		   if(!systima.getCondition().getSum().contains(k))
    			   systima.getCondition().getSum().add(k);
    			   
    	   }
    	
    	}
    	
    
    
    	}
    	
    	catch(Exception ex) {
    		
    		Message m = new Message("Failure","No sum selected to delete!", "Error");
            
    		m.show();

    		
    	}

    	
	   
   }
});

btnConditionsToFile.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
			   
		    	try {

					    PrintWriter writer = new PrintWriter("system.doc", "UTF-8");
					    writer.println("System conditions");
					    writer.println("---------------");
					    writer.println("Selected matches");
					    writer.println("------------------");
					    
					    int gameid;
					    
					    for(int i=0; i<systima.getMatches().size(); i++){
					    	gameid = systima.getMatches().get(i) - 1;
					    	    writer.println("" + (gameid + 1) + ". " + hometeams.get(gameid) + " - " + awayteams.get(gameid));
					    	  
					    		
					    }
					    
					    writer.println("\n");
					    
		    
					    writer.println(" Basic columns");
					    writer.println("-----------------");
					    
					    
					    ArrayList <Integer> maxOfGroups = new ArrayList<Integer>();

					  
					   
					    
					    for(int i=0; i<systima.getGroups().size(); i++) {
					    	
					    	  for(int t=0; t<systima.getGroups().size(); t++) {
							    	int max = 0;
							    	for(int u=0; u<systima.getGroups().get(t).getBasicColumns().size(); u++) {
							    		if(systima.getGroups().get(t).getBasicColumns().get(u).getBasicColumn().size() > max)
							    			max = systima.getGroups().get(t).getBasicColumns().get(u).getBasicColumn().size();
							    	}
							    	
							    	maxOfGroups.add(max);
							    }
					    	
					    	
					    	
					    	
					    	writer.println(" Group : " + systima.getGroups().get(i).getId());
					    	writer.println("------------");
					    	
					    	
					    	for(int k=0; k<maxOfGroups.get(i); k++) {
					    		
					    		String str = " ";
					    		String str2 = "|";
					    		
					    		    int counter = 0;
					    			
					    			for(int j=0; j<systima.getGroups().get(i).getBasicColumns().size(); j++) {
					    				
					    				counter = systima.getGroups().get(i).getBasicColumns().get(j).getBasicColumn().size();
					    				
					    				
					    			
					    			if(k < counter) {
					    				
					    			  if( systima.getGroups().get(i).getBasicColumns().get(j).getBasicColumn().get(k) < 10)
					    				str = " " + systima.getGroups().get(i).getBasicColumns().get(j).getBasicColumn().get(k);
					    			  else
					    				str = "" + systima.getGroups().get(i).getBasicColumns().get(j).getBasicColumn().get(k);
					    			}
					    				else
					    				str = "  ";
					    				
					    			
					    			
					    		
					    			
					    			str2 = str2 + "  " + str + "  |";
					    		}
					    		
					    	
					    		
					    		writer.println(str2);
					    		
					    	}
					    
					    
					    writer.println("Basic column limit values");
					    writer.println("--------------");
					    
					    maxOfGroups.clear();
					    
					    
					    for(int t=0; t<systima.getGroups().size(); t++) {
					    	int max = 0;
					    	for(int u=0; u<systima.getGroups().get(t).getBasicColumns().size(); u++) {
					    		if(systima.getGroups().get(t).getBasicColumns().get(u).getLimits().size() > max)
					    			max = systima.getGroups().get(t).getBasicColumns().get(u).getLimits().size();
					    	}
					    	
					    	maxOfGroups.add(max);
					    }
					    
					   
					    	for(int k=0; k<maxOfGroups.get(i); k++) {
					    		
					    		String str = " ";
					    		String str2 = "|";
					    		
					    		    int counter = 0;
					    			
					    			for(int j=0; j<systima.getGroups().get(i).getBasicColumns().size(); j++) {
					    				
					    				counter = systima.getGroups().get(i).getBasicColumns().get(j).getLimits().size();
					    				
					    				
					    			
					    			if(k < counter) {
					    				
					    			  if( systima.getGroups().get(i).getBasicColumns().get(j).getLimits().size() < 10)
					    				str = " " + systima.getGroups().get(i).getBasicColumns().get(j).getLimits().get(k);
					    			  else
					    				str = "" + systima.getGroups().get(i).getBasicColumns().get(j).getLimits().get(k);
					    			}
					    				else
					    				str = "  ";
					    				
					    			
					    			
					    		
					    			
					    			str2 = str2 + "  " + str + "  |";
					    		}
					    		
					    		
					    		
					    		writer.println(str2);
					    		
					    	}
					    	
					    	writer.println("\n");
					    	writer.println("Accepted basic column values : From " + Collections.min(systima.getGroups().get(i).getLimits()) + 
					    			" to " + Collections.max(systima.getGroups().get(i).getLimits()));
					    	writer.println("\n");
					    	
					    	maxOfGroups.clear();
					    }
					    
					    writer.println("Odd values : " + systima.getCondition().getOdd().toString()  + "\n");
					    
					    writer.println("Low values : " + systima.getCondition().getLow().toString() + "\n");
					    
					    writer.println("Symmetrics values : " + systima.getCondition().getSymmetrics().toString() + "\n");

					    writer.println("Neighbours values : " + systima.getCondition().getNeighbours().toString() + "\n");
					    
					    writer.println("Odd-even transitions values : " + systima.getCondition().getTransitions().toString() + "\n");

					    writer.println("Endings values : " + systima.getCondition().getEndings().toString() + "\n");

                        

					    writer.close();
					    
					    Message m = new Message("Success", "Information in system.doc saved", "Info");
			            m.show();
					    
		    			
		    	}
		    	
		    	catch(Exception ex) {
		    		
		    		JOptionPane.showMessageDialog(null, "Error in writing .doc file!");
		    	}

		    	
			   
		   }
	   }); 






btnAddGroupLimits.addActionListener(new ActionListener() {

	
	public void actionPerformed(ActionEvent e) {
		
		 String str = lblNumOfGroup.getText().substring(7);
		 int currentOmilos = Integer.parseInt(str) - 1;
		 
		 ArrayList<Integer> oria = new ArrayList<Integer>();
		 
		 int oria_apo;
		 int oria_ws;
		 
		 
		 try {
		 
				 oria_apo = Integer.parseInt(txtLimDown.getText());
				 oria_ws = Integer.parseInt(txtLimUp.getText());
				 
		         int numvasikes = systima.getGroups().get(currentOmilos).getBasicColumns().size();
				 
				 
				 
				 if( oria_apo <= numvasikes && oria_ws <= numvasikes && oria_apo <= oria_ws) {
				 
							 systima.getGroups().get(currentOmilos).getLimits().clear();
							 
							 for(int i=oria_apo; i<=oria_ws; i++) {
								 oria.add(i);
							 }
							 
							 Collections.sort(oria);
							
							 systima.getGroups().get(currentOmilos).setLimits(oria);
							 
							 JOptionPane.showMessageDialog(null, "Group " + systima.getGroups().get(currentOmilos).getId() + " limits : " + 
							 oria_apo + " - " + oria_ws);
							 
					 
				 			}
			
				 else
					{
					 JOptionPane.showMessageDialog(null, "Error in group limits.Try again.");
					 
					 txtLimUp.setText("" + Collections.max(systima.getGroups().get(currentOmilos).getLimits()));
					 txtLimDown.setText("" + Collections.min(systima.getGroups().get(currentOmilos).getLimits()));
		
					}
	
		 
		 
		 
		 }
		 
		 catch(NumberFormatException nfe) {
			 
			 JOptionPane.showMessageDialog(null, "Please insert only integers.");
			 
			 txtLimUp.setText("" + Collections.max(systima.getGroups().get(currentOmilos).getLimits()));
			 txtLimDown.setText("" + Collections.min(systima.getGroups().get(currentOmilos).getLimits()));
			 
		 }
		 
		
	}
	
	
	
});



btnLoadCoupon.addActionListener(new ActionListener() {
   
	public void actionPerformed(ActionEvent e){
    	
    	
    	
    	DefaultTableModel tableModel = (DefaultTableModel) tblMatches.getModel();
    	tableModel.setRowCount(0); // Empty the table
    	
    	
    	
    	String kouponi = JOptionPane.showInputDialog(null,"<html>Please insert coupon id nr. <br> Nr 985 is for 24/11/2018,<br> 986 for 01/12/2018 etc</html>" , "Import coupon",JOptionPane.INFORMATION_MESSAGE);
    	
    	if(kouponi!= null) {
	   
        try {
        	
        	
        	
        	String basicurl = "https://api.opap.gr/program/v1.0/5105/";
        	
        	String url = basicurl + kouponi;
        	
			Document doc = Jsoup.connect(url)
					      .userAgent("Mozilla")
					      .ignoreContentType(true)
					      .get();
			
			String kouponi2 = doc.text().toString() ;
			
			JSONObject agonesJsonObject;
			
			String startdate = "";
        	String enddate = "";
			
			try {
				
				if(hometeams.size() > 0) {
				
				hometeams.clear();
				awayteams.clear();
				
				}
				
				agonesJsonObject = (JSONObject) JSONValue.parseWithException(kouponi2);
				
				JSONArray agonesArray = (JSONArray) agonesJsonObject.get("matchXEvents");
				
                      for(int i=0; i<30; i++){
					
					        JSONObject home = (JSONObject) agonesArray.get(i);
					
				          	String ht = (String) home.get("teamHome");
				         	String at = (String) home.get("teamAway");
					
				        	hometeams.add(ht);
				        	awayteams.add(at);
					
					         }
				
			               
                      
				            startdate = agonesJsonObject.get("visualStartDate").toString();
				            
				            enddate = agonesJsonObject.get("visualEndDate").toString();
				      
				
		
				
				
				
			} catch (ParseException e1) {
				
				e1.printStackTrace();
			}
		
			
			
			if(kouponi2.length() > 100) {  // If successfully downloaded ...
			
				
				
			
			
			tableModel.setRowCount(0); // Empty the table
			
		 	for(int i=0; i<hometeams.size(); i++){
		 		

		 		int a = i + 1;
		 		String b = hometeams.get(i).toString();
		 		String c = awayteams.get(i).toString();
		 	
	
		    	Object[] row = {a,b,c};
		    	tableModel.addRow(row);
		    	
		    	
		
		 	
		 	}
		 	
		 	if(countAgones() > 7)
	    		btnEndOfSelection.setEnabled(true);
	 	
		 	    btnDummyCoupon.setEnabled(true);
		 	
		 	
			JOptionPane.showMessageDialog(null, "Coupon : \n" + startdate + " till \n"  + enddate, "Success",
    		        JOptionPane.INFORMATION_MESSAGE);
		 	
			}
			
         
		 		
			
		} catch (IOException e1) {
			
			JOptionPane.showMessageDialog(null, "No connection with Propogoal page", "Error",
    		        JOptionPane.ERROR_MESSAGE);	
    		
		}	
    	
    }
    	else
    		
    		JOptionPane.showMessageDialog(null, "Please insert a coupon id!", "Error",
    		        JOptionPane.INFORMATION_MESSAGE);	
    		

	   
   }
}); 

btnView.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e){
		
		ColumnsView prv = new ColumnsView();
		prv.setTitle("Columns view");
        prv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        prv.setSize(430, 450);
        prv.setResizable(false);
        prv.setLocation(400, 100);
        prv.setVisible(true);
		
	}
});

btnWinCheck.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e){
		
		WinningCheckView dlg = new WinningCheckView();
		dlg.setTitle("Winnings check");
        dlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dlg.setSize(670,450);
        dlg.setResizable(false);
        dlg.setLocation(400, 100);
        dlg.setVisible(true);
		
	}
});


btnCurrentView.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e){
		
		ColumnsView prv = new ColumnsView("stiles_kataskevastikou.ppg");
		prv.setTitle("View current columns");
        prv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        prv.setSize(345, 460);
        prv.setResizable(false);
        prv.setLocation(400, 100);
        prv.setVisible(true);
		
	}
});

btnMakeVariable.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e){
		
		VariableView prv = new VariableView();
		prv.setTitle("Variable create");
        prv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        prv.setSize(350, 350);
        prv.setResizable(false);
        prv.setLocation(400, 100);
        prv.setVisible(true);
		
	}
});
	

 
   }
   
   

   
public Systima getSystima() {
	return systima;
}



public void setSystima(Systima systima) {
	this.systima = systima;
}



public int countAgones() {
	
	int sum = 0;
	
	 for(int i=0; i<30; i++) {
		 if(matches_checkboxes[i].isSelected())
			 sum = sum + 1;
	 }
	
	 return sum;
}

   



public void addComp1toPanelAgones(){
	
	 JLabel jl = new JLabel("<html><U>Select matches</U></html>");
	 jl.setFont(new Font("Arial", Font.BOLD, 13));
	 jl.setForeground(new Color(0,128,0));
	 this.pnlMatches.add(jl,"pos 60 40");
}

public void addComp2toPanelAgones(){
	
	   JPanel jpl = new JPanel();
	   jpl.setMinimumSize(new Dimension(180,370));
	   jpl.setMaximumSize(new Dimension(180,370));
	   jpl.setLayout(new MigLayout());
	   pnlMatches.add(jpl,"pos 40 70");
	   jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	   
	  
	for(int i=0; i<30; i++){
		   
		   this.matches_checkboxes[i] = new JCheckBox(i+1 + ".");
		   this.matches_checkboxes[i].setFont(new Font("Arial",Font.BOLD, 12));
		   
			  int col = i%3;
			  int seira = (int)(i/3) + 1;
			  int f = 10 + col * 55;
			  int g = -20 + seira * 35;
			  
			  String cell = "pos " + f + " " + g + "";
			
			  jpl.add(this.matches_checkboxes[i],cell);
			 
		   }
	
	
	   
}

public void addComp3toPanelAgones(){
	   
	 pnlMatches.add(lblSelectedMatches,"pos 40 450");
	 lblSelectedMatches.setFont(new Font("Arial", Font.BOLD, 17));
	 lblSelectedMatches.setText("Selected matches : 0");
	 lblSelectedMatches.setForeground(new Color(0,128,0));
	 
	 btnLoadCoupon = new JGradientButton(180,32,13,"Load coupon","Downloads coupon from internet page",156,142,175);
	 pnlMatches.add(btnLoadCoupon,"pos 750 65");
	 
	 btnNewSystem = new JGradientButton(180,32,13,"New System","Starts a new system",156,142,175);
	 pnlMatches.add(btnNewSystem,"pos 750 105");
	 
	 btnAll = new JGradientButton(180,32,13,"Select all","Selects all matches",156,142,175);
	 pnlMatches.add(btnAll, "pos 750 145");
	 
	 btnNone = new JGradientButton(180,32,13,"Deselect all","Deselects all matches",156,142,175);
	 pnlMatches.add(btnNone, "pos 750 185");
	 
	 btnEndOfSelection = new JGradientButton(180,32,13,"End of selection","End of selection - no matches can be selected",156,142,175);
	 pnlMatches.add(btnEndOfSelection, "pos 750 225");
	 
	 btnDummyCoupon = new JGradientButton(180,32,13,"Dummy coupon","Coupon with fake teams",156,142,175);
	 pnlMatches.add(btnDummyCoupon, "pos 750 265");
	 
	 
		
	 btnEndOfSelection.setEnabled(false);
	 

	 
	 JLabel ppgsite = new JLabel("Visit Propogoal official page");
	 ppgsite.setFont(new Font("Arial",Font.BOLD, 15));
	 ppgsite.setForeground(Color.blue);
	 ppgsite.setBackground(Color.GRAY);
	 pnlMatches.add(ppgsite, "pos 730 550");
	 ppgsite.setOpaque(true);
	 addPointerHand(ppgsite);
	 
	 ppgsite.addMouseListener(new MouseListener() {
		    public void mouseClicked(MouseEvent e) {
		    	
		    	Desktop d = Desktop.getDesktop();
				try {
					d.browse(new URI("http://www.opap.gr/el/web/guest/propogoal-draw-results"));
				} catch (IOException e1) {
					
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					
					e1.printStackTrace();
				}
		
		    	
			    	
			    }
			    
			

				@Override
				public void mousePressed(MouseEvent e) {
					
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					
					JLabel lbl = (JLabel) e.getSource();
					lbl.setForeground(Color.red);
					lbl.setOpaque(true);
					
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					
					JLabel lbl = (JLabel) e.getSource();
					lbl.setForeground(Color.blue);
					lbl.setOpaque(true);
					
				}



		 
	 });
	 

	
	 
	 try {
		 ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("propogoal.jpg")));
		 Image scaleImage = icon.getImage().getScaledInstance(180,100,Image.SCALE_DEFAULT);
		 pnlMatches.add(new JLabel(new ImageIcon(scaleImage)), "pos 40 500");
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	 
	 

	 
}

public void addComp4toPanelAgones(){

	   JScrollPane js = new JScrollPane(tblMatches);
	   js.setPreferredSize(new Dimension(452,563));
	   pnlMatches.add(js,"pos 270 40");
	   
	   
	   String[] columns = {"Id","Home","Away"};
	
	DefaultTableModel tableModel = new DefaultTableModel(columns, 0){  
	      /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column){  
	         return false;  
	       }  
	     };  
	
	 	
	   tblMatches.setModel(tableModel);
	   tblMatches.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       tblMatches.getColumnModel().getColumn(0).setMaxWidth(50);
	   tblMatches.getColumnModel().getColumn(1).setMinWidth(200);
	   tblMatches.getColumnModel().getColumn(2).setMinWidth(200);
	     
	 	tblMatches.setBackground(new Color(214,233,235));
		 	tblMatches.setOpaque(true);
	    
	     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	     centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	     for(int x=0;x<3;x++){
	    	tblMatches.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	        }
	     
	     TableCellRenderer rendererFromHeader = tblMatches.getTableHeader().getDefaultRenderer();
		     	JLabel headerLabel = (JLabel) rendererFromHeader;
		     	headerLabel.setHorizontalAlignment(JLabel.CENTER);
}

public void addComp1toPanelStats(){
	
	JLabel jl = new JLabel("<html><U> Odd - Even </U></html>");
	 jl.setFont(new Font("Arial", Font.BOLD, 13));
	 jl.setForeground(new Color(0,128,0));
	 this.pnlStats.add(jl,"pos 25 30");
	 
	   JPanel jpl = new JPanel();
	   jpl.setPreferredSize(new Dimension(80,290));
	   jpl.setLayout(new MigLayout());
	   pnlStats.add(jpl,"pos 30 60");
	   jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	 
	   for(int i=0; i<9; i++){
			  oddeven_checkboxes[i] = new JCheckBox(i + " - " + (8-i));
			  jpl.add(oddeven_checkboxes[i],"pos 10 " + (10 + i*30)) ;
		   }
	  
	   all1 = new JGradientButton(70, 36, 13,"<html><b><center>Select all</center></b></html>", "Selects all choises",156,142,175);
       pnlStats.add(all1,"pos 35 380");

}

public void addComp2toPanelStats(){
	
	JLabel jl = new JLabel("<html><U> Low - High </U></html>");
	 jl.setFont(new Font("Arial", Font.BOLD, 13));
	 jl.setForeground(new Color(0,128,0));
	 this.pnlStats.add(jl,"pos 125 30");
	 
	   JPanel jpl = new JPanel();
	   jpl.setMinimumSize(new Dimension(80,290));
	   jpl.setMaximumSize(new Dimension(80,290));
	   jpl.setLayout(new MigLayout());
	   this.pnlStats.add(jpl,"pos 135 60");
	   jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	 
	   for(int i=0; i<9; i++){
		   lowhigh_checkboxes[i] = new JCheckBox(i + " - " + (8-i));
			  jpl.add(lowhigh_checkboxes[i],"pos 10 " + (10 + i*30)) ;
		   }
	   
	   all2 = new JGradientButton(70, 36, 13,"<html><b><center>Select all</center></b></html>", "Selects all choises",156,142,175);

	   pnlStats.add(all2,"pos 140 380");
	 
	  
	

}

public void addComp3toPanelStats(){
	
	JLabel jl = new JLabel("<html><U> Symmetrics </U></html>");
	jl.setFont(new Font("Arial", Font.BOLD, 13));
	 jl.setForeground(new Color(0,128,0));
	 this.pnlStats.add(jl,"pos 245 30");
	 
	   JPanel jpl = new JPanel();
	   jpl.setMinimumSize(new Dimension(80,290));
	   jpl.setMaximumSize(new Dimension(80,290));
	   jpl.setLayout(new MigLayout());
	   this.pnlStats.add(jpl,"pos 245 60");
	   jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	 
	   for(int i=0; i<5; i++){
		   symmetry_checkboxes[i] = new JCheckBox(""+i);
			  jpl.add(symmetry_checkboxes[i],"pos 18 " + (10 + i*30)) ;
		   }
	   
	   all3 = new JGradientButton(70, 36, 13,"<html><b><center>Select all</center></b></html>", "Selects all choises",156,142,175);

	  
	   pnlStats.add(all3,"pos 250 380");
	  
	  
	
	   

}

public void addComp4toPanelStats(){
	
	JLabel jl = new JLabel("<html><U> Endings </U></html>");
	jl.setFont(new Font("Arial", Font.BOLD, 13));
	 jl.setForeground(new Color(0,128,0));
	 this.pnlStats.add(jl,"pos 355 30");
	 
	   JPanel jpl = new JPanel();
	   jpl.setMinimumSize(new Dimension(80,290));
	   jpl.setMaximumSize(new Dimension(80,290));
	   jpl.setLayout(new MigLayout());
	   this.pnlStats.add(jpl,"pos 345 60");
	   jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	 
	   for(int i=0; i<8; i++){
		   endings_checkboxes[i] = new JCheckBox(""+i);
			  jpl.add(endings_checkboxes[i],"pos 18 " + (10 + i*30)) ;
		   }
	  
	   all4 = new JGradientButton(70, 36, 13,"<html><b><center>Select all</center></b></html>", "Selects all choises",156,142,175);
       pnlStats.add(all4,"pos 350 380");



}

public void addComp5toPanelStats(){
	
	JLabel jl = new JLabel("<html><U> Neighbours </U></html>");
	jl.setFont(new Font("Arial", Font.BOLD, 13));
	 jl.setForeground(new Color(0,128,0));
	 this.pnlStats.add(jl,"pos 455 30");
	 
	   JPanel jpl = new JPanel();
	   jpl.setMinimumSize(new Dimension(80,290));
	   jpl.setMaximumSize(new Dimension(80,290));
	   jpl.setLayout(new MigLayout());
	   this.pnlStats.add(jpl,"pos 455 60");
	   jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	 
	   for(int i=0; i<8; i++){
		   neighbours_checkboxes[i] = new JCheckBox(""+i);
			  jpl.add(neighbours_checkboxes[i],"pos 18 " + (10 + i*30)) ;
		   }
	  
	   all5 = new JGradientButton(70, 36, 13,"<html><b><center>Select all</center></b></html>", "Selects all choises",156,142,175);

	   pnlStats.add(all5,"pos 460 380");
	
	
	

}

public void addComp6toPanelStats(){
	
	JLabel jl = new JLabel("<html><U> Odd-even transitions </U></html>");
	jl.setFont(new Font("Arial", Font.BOLD, 13));
	 jl.setForeground(new Color(0,128,0));
	 this.pnlStats.add(jl,"pos 555 30");
	 
	   JPanel jpl = new JPanel();
	   jpl.setMinimumSize(new Dimension(80,290));
	   jpl.setMaximumSize(new Dimension(80,290));
	   jpl.setLayout(new MigLayout());
	   this.pnlStats.add(jpl,"pos 565 60");
	   jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	 
	   for(int i=0; i<8; i++){
		   transitions_checkboxes[i] = new JCheckBox(""+i);
			  jpl.add(transitions_checkboxes[i],"pos 18 " + (10 + i*30)) ;
		   }
	   
	   all6 = new JGradientButton(70, 36, 13,"<html><b><center>Select all</center></b></html>", "Selects all choises",156,142,175);

	  
	   pnlStats.add(all6,"pos 570 380");

	

}

public void addComp7toPanelStats() {
	
	JLabel jl = new JLabel("<html><u> Sum </u></html>");
	jl.setFont(new Font("Arial", Font.BOLD, 13));
	 jl.setForeground(new Color(0,128,0));
	 this.pnlStats.add(jl,"pos 740 30");
	
	   JPanel jpl = new JPanel();
	   jpl.setMinimumSize(new Dimension(210,290));
	   jpl.setMaximumSize(new Dimension(210,290));
	   jpl.setLayout(new MigLayout());
	   pnlStats.add(jpl,"pos 675 60");
	   jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	   
	   
	   JScrollPane js = new JScrollPane(tblSums);
	   js.setPreferredSize(new Dimension(182,265));
	   jpl.add(js,"pos 10 10");
	   
	   
	   String[] columns = {"Id","From","To"};
	
	DefaultTableModel tableModel = new DefaultTableModel(columns, 0){  
	      public boolean isCellEditable(int row, int column){  
	         return false;  
	       }  
	     };  
	
	 	
	     tblSums.setModel(tableModel);
	     tblSums.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	     tblSums.getColumnModel().getColumn(0).setMaxWidth(30);
	     tblSums.getColumnModel().getColumn(1).setMinWidth(40);
	     tblSums.getColumnModel().getColumn(2).setMinWidth(40);
	     
	     tblSums.setBackground(new Color(214,233,235));
	     tblSums.setOpaque(true);
	    
	     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	     centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	     for(int x=0;x<3;x++){
	    	 tblSums.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	        }
	     
	     TableCellRenderer rendererFromHeader = tblSums.getTableHeader().getDefaultRenderer();
		     	JLabel headerLabel = (JLabel) rendererFromHeader;
		     	headerLabel.setHorizontalAlignment(JLabel.CENTER);
		     	
		   pnlStats.add(btnAddSum,"pos 690 450");
		   btnAddSum.setMinimumSize(new Dimension(180,32));
		   btnAddSum.setBackground(new Color(156,142,175));
		   btnAddSum.setFont(new Font("Arial", Font.BOLD, 12));
		   
		   
		   pnlStats.add(btnDelSum,"pos 690 500");
		   btnDelSum.setMinimumSize(new Dimension(180,32));
		   btnDelSum.setBackground(new Color(156,142,175));
		   btnDelSum.setFont(new Font("Arial", Font.BOLD, 12));
		  
		   
		   JLabel jl1 = new JLabel("Min :");
		   JLabel jl2 = new JLabel("Max :");
		   jl1.setFont(new Font("Arial", Font.BOLD, 12));
		   jl2.setFont(new Font("Arial", Font.BOLD, 12));
		   		   
		   pnlStats.add(jl1,"pos 720 390");
		   pnlStats.add(jl2,"pos 720 420");
		   
		   txtMinSum.setHorizontalAlignment(JTextField.CENTER);
		   txtMaxSum.setHorizontalAlignment(JTextField.CENTER);
		   
		   pnlStats.add(txtMinSum,"pos 760 385");
		   pnlStats.add(txtMaxSum,"pos 760 415");
		   
		   JGradientButton compute = new JGradientButton(200, 50, 13,"Count!", "Calculates the columns", 182,105,105);
		   pnlStats.add(compute, "pos 300 480");
		   
		   
		   JPanel jpl2 = new JPanel();
		   jpl2.setPreferredSize(new Dimension(120,100));
		   jpl2.setLayout(new MigLayout());
		   pnlStats.add(jpl2,"pos 675 60");
		   jpl2.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		   
		   pnlStats.add(jpl2, "pos 30 430");
		   
		   
		   JLabel jl3 = new JLabel("Min :");
		   JLabel jl4 = new JLabel("Max :");
		   JLabel jl5 = new JLabel("<html> <u>Errors in statistics</u></html>");
		   jl3.setFont(new Font("Arial", Font.BOLD, 12));
		   jl4.setFont(new Font("Arial", Font.BOLD, 12));
		   jl5.setFont(new Font("Arial", Font.BOLD, 12));
		   
		   jpl2.add(jl5, "pos 10 10");
		   
		   jpl2.add(jl3,"pos 10 40");
		   jpl2.add(jl4,"pos 10 70");
		   
		   jpl2.add(txtOriaErrorsFrom, "pos 80 30");
		   jpl2.add(txtOriaErrorsTo, "pos 80 60");
		   
		   
  compute.addActionListener(new ActionListener() {
				
			    public void actionPerformed(ActionEvent e){
			    	
			    	calculateColumns();
			    	
			    }
			});
	   
}

public void addComp1toPanelKontres(){
	
	   JScrollPane js = new JScrollPane(tblBasicColumnMatches);
	   js.setPreferredSize(new Dimension(367,570));
	   pnlBasicColumns.add(js,"pos 40 40");
	   
	   
	   String[] columns = {"Id","Home","Away"};
	
	DefaultTableModel tableModel = new DefaultTableModel(columns, 0){  
	      public boolean isCellEditable(int row, int column){  
	         return false;  
	       }  
	     };  
	
	 	
	   tblBasicColumnMatches.setModel(tableModel);
	   tblBasicColumnMatches.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       tblBasicColumnMatches.getColumnModel().getColumn(0).setMaxWidth(47);
	   tblBasicColumnMatches.getColumnModel().getColumn(1).setMinWidth(160);
	   tblBasicColumnMatches.getColumnModel().getColumn(2).setMinWidth(160);
	     
	 	tblBasicColumnMatches.setBackground(new Color(214,233,235));
		tblBasicColumnMatches.setOpaque(true);
	    
	     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	     centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	     for(int x=0;x<3;x++){
	    	tblBasicColumnMatches.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	        }
	     
	     TableCellRenderer rendererFromHeader = tblBasicColumnMatches.getTableHeader().getDefaultRenderer();
		     	JLabel headerLabel = (JLabel) rendererFromHeader;
		     	headerLabel.setHorizontalAlignment(JLabel.CENTER);
		     	
		     	
	
		    	DefaultTableModel tableModel2 = (DefaultTableModel) tblBasicColumnMatches.getModel();
		    	

			 	for(int i=0; i<30; i++){
			 		
			 		 if(matches_checkboxes[i].isSelected()) {
			 	         	int a = i + 1;
			 	         	String b = hometeams.get(i);
			 	        	String c = awayteams.get(i);
			 	
		
			 	Object[] row2 = {a,b,c};
			 	tableModel2.addRow(row2);
			 	
			 	
			 	
			 		 }
			 	}
		     	
	
}

public void addComp2toPanelKontres(){
	
	agoneskon_checkboxes = new JCheckBox[systima.getMatches().size()];
	
    	 for(int i=0; i<systima.getMatches().size(); i++){
 
    		   int start = 65;
    		   int pos = start + i*16;
    		   
    		   String str = Integer.toString(pos);
    		   
      		   agoneskon_checkboxes[i] = new JCheckBox();

      		   pnlBasicColumns.add(agoneskon_checkboxes[i],"pos 415 " + str);
      			  
      			  
      		   }
	
	
}

public void addComp3toPanelKontres() {
	
	   JLabel jl = new JLabel("<html><U> Limits </U></html>");
       jl.setFont(new Font("Arial", Font.BOLD, 13));
	   jl.setForeground(new Color(0,128,0));
	   this.pnlBasicColumns.add(jl,"pos 470 40");
	 
	   JPanel jpl = new JPanel();
	   jpl.setMinimumSize(new Dimension(80,220));
	   jpl.setMaximumSize(new Dimension(80,220));
	   jpl.setLayout(new MigLayout());
	   this.pnlBasicColumns.add(jpl,"pos 450 60");
	   jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	
	
	
	
 	
	 
     	basiccolumns_value_checkboxes = new JCheckBox[9];
    	
     	for(int i=0; i<9 ; i++){
     		
     	   int start = 10;
  		   int pos = start + i*22;
  		   String str = Integer.toString(pos);
     		
     		
      		   basiccolumns_value_checkboxes[i] = new JCheckBox(i + "");
      		   
      			
      		 jpl.add(basiccolumns_value_checkboxes[i],"pos 15 " + str);
      			  
      	
      			  
      		
      		   }
     	
        for(int k = 0; k < 9 ; k++){  // Αρχικοποίηση των checkboxes που είναι τα όρια
                                      // κοντρών . Μη επιλέξιμα στην αρχή.

        basiccolumns_value_checkboxes[k].setEnabled(false) ;

        }
     	

        pnlBasicColumns.add(btnAddBasicColumn,"pos 450 300");
        
        btnAddBasicColumn.setEnabled(false);
        btnAddBasicColumn.setMinimumSize(new Dimension(76,36));
        btnAddBasicColumn.setFont(new Font("Arial", Font.BOLD, 13));
        btnAddBasicColumn.setBackground(new Color(156,142,175));
 	   
     	
     	
     	
}

public void addComp4toPanelKontres() {
	
	   JScrollPane js = new JScrollPane(tblBasicColumns);
	   js.setPreferredSize(new Dimension(300,250));
	   pnlBasicColumns.add(js,"pos 600 40");
 	
 	String[] columns = {"Id","Matches bc","Limits bc"};
	
 	DefaultTableModel tableModel = new DefaultTableModel(columns, 0){  
 	      public boolean isCellEditable(int row, int column){  
 	         return false;  
 	       }  
 	     };  
 	
 	 	
       tblBasicColumns.setModel(tableModel);
       tblBasicColumns.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       tblBasicColumns.getColumnModel().getColumn(0).setMaxWidth(40);
	   tblBasicColumns.getColumnModel().getColumn(1).setMinWidth(180);
	   tblBasicColumns.getColumnModel().getColumn(2).setMinWidth(80);
	     
	 	tblBasicColumns.setBackground(new Color(214,233,235));
		tblBasicColumns.setOpaque(true);
 	TableCellRenderer rendererFromHeader = tblBasicColumns.getTableHeader().getDefaultRenderer();
 	JLabel headerLabel = (JLabel) rendererFromHeader;
 	headerLabel.setHorizontalAlignment(JLabel.CENTER);
 	
 	
 	
 	  btnDelBasicColumn.setMinimumSize(new Dimension(76,36));
      btnDelBasicColumn.setFont(new Font("Arial", Font.BOLD, 13));
      btnDelBasicColumn.setBackground(new Color(156,142,175));
	 
      
      
      
   	
 	
      pnlBasicColumns.add(btnDelBasicColumn, "pos 700 300");
 	  btnDelBasicColumn.setEnabled(false);
 	
 	
 	
 	
 	
 	 try {
		 ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("check.png")));
		 Image scaleImage = icon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
		 pnlBasicColumns.add(new JLabel(new ImageIcon(scaleImage)), "pos 415 43");
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	
}

public void addComp5toPanelKontres() {
	
	JPanel jpl = new JPanel();
	
	pnlBasicColumns.add(jpl, "pos 600 350");
	jpl.setLayout(new MigLayout());
	jpl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	jpl.setPreferredSize(new Dimension(290,230));
	
	lblGroupLimits.setFont(new Font("Arial", Font.BOLD, 17));
	lblNumOfGroup.setFont(new Font("Arial", Font.BOLD, 17));
	lblLimitFrom.setFont(new Font("Arial",Font.BOLD, 13));
	lblLimitTo.setFont(new Font("Arial",Font.BOLD, 13));
	

	txtLimUp.setMinimumSize(new Dimension(30,20));
	txtLimDown.setMinimumSize(new Dimension(30,20));
	
	txtLimUp.setHorizontalAlignment(JTextField.CENTER);
	txtLimDown.setHorizontalAlignment(JTextField.CENTER);

	
 	
 	jpl.add(lblNumOfGroup, "pos 20 40");
 	
 	jpl.add(btnGroupUp, "pos 120 25");
 	jpl.add(btnGroupDown, "pos 120 55");
	jpl.add(lblGroupLimits, "pos 20 120");
	jpl.add(lblLimitFrom, "pos 150 110");
	jpl.add(lblLimitTo, "pos 150 150");
	jpl.add(txtLimUp, "pos 190 140");
    jpl.add(txtLimDown, "pos 190 100");
	jpl.add(btnAddGroupLimits, "pos 90 190");
	
	
	 JGradientButton compute = new JGradientButton(150, 50, 13,"Count!", "Calculates all column combinations", 182, 105, 105);
	 pnlBasicColumns.add(compute, "pos 420 530");
	 
	 
	 for(int i=0; i<jpl.getComponentCount(); i++) {
		 Component co = jpl.getComponent(i);
		 addPointerHand(co);
	 }
	 
	   
compute.addActionListener(new ActionListener() {
			
	    public void actionPerformed(ActionEvent e){
		    	
	    	calculateColumns();
		    	
		    }
		});
	
	
}



public void addPointerHand(Component co){
	

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


public void saveFullSystemToDisk(){
	
	String path = "stiles_plires.ppg";		// All possible combinations will be saved at the disk			
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
   
   
   int a = systima.getMatches().size() - 7;
   
   
   // ------------------------------------ //
   
  // Generating all possible combinations and saving to disk
   
	for(int n1=0; n1<a; n1++){
		for(int n2=1; n2<a+1; n2++){
			if(n2>n1){
				for(int n3=2; n3<a+2; n3++){
					if(n3>n2){
						for(int n4=3; n4<a+3; n4++){
							if(n4>n3){
								for(int n5=4; n5<a+4; n5++){
									if(n5>n4){
										for(int n6=5; n6<a+5; n6++){
											if(n6>n5){
												for(int n7=6; n7<a+6; n7++){
													if(n7>n6){
														for(int n8=7; n8<a+7; n8++){
															if(n8>n7){
																
																
																
														Column s1 = new Column(systima.getMatches().get(n1),systima.getMatches().get(n2),
																               systima.getMatches().get(n3),systima.getMatches().get(n4),
																               systima.getMatches().get(n5),systima.getMatches().get(n6),
																               systima.getMatches().get(n7),systima.getMatches().get(n8));
														
														int arr[] = s1.getPosition();
														

																
																String str = arr[0] + "," + arr[1] + "," + arr[2] + "," + 
																             arr[3] + "," + arr[4] + "," + arr[5] + "," + 
																		     arr[6] + "," + arr[7] + "," +
																		     s1.getOdd() + "," +
																		     s1.getLow() + "," + 
																		     s1.getSymmetrics() + "," +
																		     s1.getEndings() + "," +
																		     s1.getNeighbours() + "," +
																		     s1.getTransitions() + "," + 
																             s1.getSum() ;
																		    

													    
																	try {
																		
																		
																		
																		bw.write(str + "\n");
																		
																	} catch (IOException e1) {
																		
																		
																		e1.printStackTrace();
																	}
													
																
																
																
																
																
																
																
																
																
																
																
																
   
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	// ------------------------------------------------ //
	
	
   

	try {
		bw.close();
	} catch (IOException e1) {
		
		e1.printStackTrace();
	} 
	

	
}

public boolean compareColtoGroup(ArrayList<Integer> column, GroupBC group){
	
	boolean f = true;
	int a = 0;
	int right = 0;
	
	for(int i=0; i<group.getBasicColumns().size(); i++){
		
		a = compareColToBasicCol(column,group.getBasicColumns().get(i).getBasicColumn());
		
		if(group.getBasicColumns().get(i).getLimits().contains(a))
			right = right + 1;
		
		
	       }
	
	
	
	if(group.getLimits().contains(right))
		
	    f = true;
	
	else
		
		f = false;
	
	return f;
}

public String removeClosures(String str) {
	
	int str_len = str.length() - 1;
	
	return(str.substring(1, str_len));
}

public void calculateColumns() {
	
	    systima.getCondition().getErrors().clear();
 
    	
       	for(int i=Integer.parseInt(txtOriaErrorsFrom.getText()); i<=Integer.parseInt(txtOriaErrorsTo.getText()); i++){
	   		systima.getCondition().getErrors().add(i);
	   	}
	   	
    	
    	String path = "stiles_kataskevastikou.ppg";					
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
    	
    	int groups = systima.getGroups().size();
    	
    	
    	
    	

    	int sumColumns = 0;
    	
    	String[] row = null ;
		String arxeio = "stiles_plires.ppg";

		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader(arxeio));
		} catch (FileNotFoundException e2) {
			
			e2.printStackTrace();
		}
			

		try {
			
			ArrayList<Integer> csv_line = new ArrayList<Integer>();
			ArrayList<Integer> csv_stili = new ArrayList<Integer>();
			   
			
			
			while((row = csvReader.readNext()) != null) {
				
				csv_line.clear();
				csv_stili.clear();
				
			   	
				
			   	
			   	for(int i=0; i<15; i++){
			   		csv_line.add(Integer.parseInt(row[i]));
			   	}
			   	
			   	for(int i=0; i<8; i++){
			   		csv_stili.add(csv_line.get(i));
			   	}
			   	
			
				

			   	boolean accepted_group = true ;
			   	

			   	for(int i = 0; i<groups; i++) {
			   		
			   		accepted_group = compareColtoGroup(csv_stili,systima.getGroups().get(i));

		    			
		    			if(accepted_group == false)
		    			   break;

		    		
		    	}
			   	
			   	int rightCondition = 0;
			   	
			  
			   	
			   	if(systima.getCondition().getOdd().contains(csv_line.get(8)))
			   		rightCondition = rightCondition + 1;
			   	if(systima.getCondition().getLow().contains(csv_line.get(9)))
			   		rightCondition = rightCondition + 1;
			 	if(systima.getCondition().getSymmetrics().contains(csv_line.get(10)))
			   		rightCondition = rightCondition + 1;
			   	if(systima.getCondition().getEndings().contains(csv_line.get(11)))
			   		rightCondition = rightCondition + 1;
			 	if(systima.getCondition().getNeighbours().contains(csv_line.get(12)))
			   		rightCondition = rightCondition + 1;
			   	if( systima.getCondition().getTransitions().contains(csv_line.get(13)))
			   		rightCondition = rightCondition + 1;
				if(systima.getCondition().getSum().contains(csv_line.get(14)))
			   		rightCondition = rightCondition + 1;
			   
			  
			   	
			  // If all conditions are true, then 	
			   	
			   	   if(systima.getCondition().getErrors().contains(7-rightCondition) &&
			   		 (accepted_group == true)) {
			   		   

			  
			   		String str = "";

					for(int j=0; j<8; j++) {
						
						if(j<7)
						    str = str + csv_stili.get(j) + ",";
						else
							str = str + csv_stili.get(j);
						
					}
				
					
			   		 
			   	      sumColumns = sumColumns + 1;
			   	      
			   	      // ------------------------------------------// 
			   	      

			   	      
			   	      bw.write(str + "\n");
			   	      
			  
			   	
			   	   }
			   }
			
		
	
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		  
		   try {
			csvReader.close();
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		   
		   try {
    			bw.close();
    			
    		} catch (IOException io2) {
    			
    			io2.printStackTrace();
    		} 
		   
		  
	      
	        String str = "<html>Columns : <b><font size=5 color=blue>" + Integer.toString(sumColumns) + "</font></b></html>";
    		Message m = new Message("System columns", str, "Info");
            m.show();

   

}

public void addListenerToKontres() {
	
	   for(int i=0; i<agoneskon_checkboxes.length; i++) {			
			agoneskon_checkboxes[i].addItemListener(this);
		}
	   
	   for(int i=0; i<9; i++) {			
		   basiccolumns_value_checkboxes[i].addItemListener(this);
		}
	   
}


public int compareColToBasicCol(ArrayList<Integer> col, ArrayList<Integer> basiccol){
	
	int sum = 0;
	
	for(int i=0; i<col.size(); i++) {
		
		for(int j=0; j<basiccol.size(); j++) {
			
			if(col.get(i) == basiccol.get(j))
				
				sum = sum + 1; 
			
		}
	}
	

	return sum;
}


public static void main(String[] args) throws ParseException {
		
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
		}
		
	

		gui ppg = new gui();
		ppg.setTitle("Propogold");
        ppg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ppg.setSize(1000, 700);
        ppg.setResizable(false);
        ppg.setLocation(200, 50);
        ppg.setVisible(true);
      
		
		  
        
      
        
        
	}


	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		
		return 0;
	}




	@Override
public void itemStateChanged(ItemEvent e) {
		

		
		int index = 0;
		
		String selected = "";
		
		
		for(int i=0; i<30; i++) {
			if(e.getSource().equals(matches_checkboxes[i])) {
				index = i;
				selected = "matches";
				break;
		 }
		}
		
		for(int i=0; i<9; i++) {
			if(e.getSource().equals(oddeven_checkboxes[i])) {
				index = i;
				selected = "odd";
				break;
		 }
		}
		
		for(int i=0; i<9; i++) {
			if(e.getSource().equals(lowhigh_checkboxes[i])) {
				index = i;
				selected = "low";
				break;
		 }
		}
		
		for(int i=0; i<5; i++) {
			if(e.getSource().equals(symmetry_checkboxes[i])) {
				index = i;
				selected = "symm";
				break;
		 }
		}
		
		for(int i=0; i<8; i++) {
			
				if(e.getSource().equals(endings_checkboxes[i])) {
					index = i;
					selected = "endings";
					break;
			    }
				
				if(e.getSource().equals(neighbours_checkboxes[i])) {
					index = i;
					selected = "neighbours";
					break;
			 }
				
				if(e.getSource().equals(transitions_checkboxes[i])) {
					index = i;
					selected = "transitions";
					break;
			 }
			
		}
		
		
		if(jtb.isEnabledAt(2)) {  // Αν έχει ενεργοποιηθεί η καρτέλα Κόντρες
			
			for(int i=0; i<agoneskon_checkboxes.length; i++) {
				if(e.getSource().equals(agoneskon_checkboxes[i])) {
					index = i;
					selected = "bc";
					break;
			 }
			}
			
			for(int i=0; i<basiccolumns_value_checkboxes.length; i++) {
				if(e.getSource().equals(basiccolumns_value_checkboxes[i])) {
					index = i;
					selected = "bc_limits";
					break;
			 }
				
			}
			
			
		}
		
		
		
		if(selected.equals("matches")) {
		
					if(e.getStateChange() == ItemEvent.DESELECTED) {
						
						matches_checkboxes[index].setForeground(Color.black);
			
						   for(int j=0; j<systima.getMatches().size(); j++){
			 	    		  if(systima.getMatches().get(j) == (index + 1))
			 	    			systima.getMatches().remove(j);
			 	    	 }
					}
					else
						if(e.getStateChange() == ItemEvent.SELECTED) {
							
							matches_checkboxes[index].setForeground(Color.red);
							
							 if(!systima.getMatches().contains(index + 1))
							 systima.getMatches().add(index + 1);
					
						}
					
					Collections.sort(systima.getMatches());
					
					
					    int sum = systima.getMatches().size();
			
				   	    lblSelectedMatches.setText("Selected matches : " + sum);
				   	    
				   	    if(sum > 7 && hometeams.size() > 0)
				   	    	btnEndOfSelection.setEnabled(true);
				   	    else
				   	    	btnEndOfSelection.setEnabled(false);
					
					}
		
		if(selected.equals("odd")) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				
				oddeven_checkboxes[index].setForeground(Color.black);
				
				   for(int j=0; j<systima.getCondition().getOdd().size(); j++){
	 	    		  if(systima.getCondition().getOdd().get(j) == (index))
	 	    			 systima.getCondition().getOdd().remove(j);
	 	    	 }
			}
			else
				if(e.getStateChange() == ItemEvent.SELECTED) {
					
					oddeven_checkboxes[index].setForeground(Color.red);
					 if(!systima.getCondition().getOdd().contains(index))
						 systima.getCondition().getOdd().add(index);
			
				}
			
			Collections.sort(systima.getCondition().getOdd());
			}	
		
	if(selected.equals("low")) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				
				lowhigh_checkboxes[index].setForeground(Color.black);
				   for(int j=0; j<systima.getCondition().getLow().size(); j++){
	 	    		  if(systima.getCondition().getLow().get(j) == (index))
	 	    			 systima.getCondition().getLow().remove(j);
	 	    	 }
			}
			else
				if(e.getStateChange() == ItemEvent.SELECTED) {
					
					lowhigh_checkboxes[index].setForeground(Color.red);
					 if(!systima.getCondition().getLow().contains(index))
						 systima.getCondition().getLow().add(index);
			
				}
			
			Collections.sort(systima.getCondition().getLow());
			
			}
	
		if(selected.equals("symm")) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				
				symmetry_checkboxes[index].setForeground(Color.black);
				   for(int j=0; j<systima.getCondition().getSymmetrics().size(); j++){
	 	    		  if(systima.getCondition().getSymmetrics().get(j) == (index))
	 	    			 systima.getCondition().getSymmetrics().remove(j);
	 	    	 }
			}
			else
				if(e.getStateChange() == ItemEvent.SELECTED) {
					
					symmetry_checkboxes[index].setForeground(Color.red);
					 if(!systima.getCondition().getSymmetrics().contains(index))
						 systima.getCondition().getSymmetrics().add(index);
			
				}
			
			Collections.sort(systima.getCondition().getSymmetrics());
			
			}
		
		if(selected.equals("endings")) {
				
				if(e.getStateChange() == ItemEvent.DESELECTED) {
					
					endings_checkboxes[index].setForeground(Color.black);
					   for(int j=0; j<systima.getCondition().getEndings().size(); j++){
		 	    		  if(systima.getCondition().getEndings().get(j) == (index))
		 	    			 systima.getCondition().getEndings().remove(j);
		 	    	 }
				}
				else
					if(e.getStateChange() == ItemEvent.SELECTED) {
						
						endings_checkboxes[index].setForeground(Color.red);
						 if(!systima.getCondition().getEndings().contains(index))
							 systima.getCondition().getEndings().add(index);
				
					}
				
				Collections.sort(systima.getCondition().getEndings());
				
				}
		
		if(selected.equals("neighbours")) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				
				neighbours_checkboxes[index].setForeground(Color.black);
				   for(int j=0; j<systima.getCondition().getNeighbours().size(); j++){
	 	    		  if(systima.getCondition().getNeighbours().get(j) == (index))
	 	    			 systima.getCondition().getNeighbours().remove(j);
	 	    	 }
			}
			else
				if(e.getStateChange() == ItemEvent.SELECTED) {
					
					neighbours_checkboxes[index].setForeground(Color.red);
					 if(!systima.getCondition().getNeighbours().contains(index))
						 systima.getCondition().getNeighbours().add(index);
			
				}
			
			Collections.sort(systima.getCondition().getNeighbours());
			
			}
	
		
	if(selected.equals("transitions")) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				
				transitions_checkboxes[index].setForeground(Color.black);
				   for(int j=0; j<systima.getCondition().getTransitions().size(); j++){
	 	    		  if(systima.getCondition().getTransitions().get(j) == (index))
	 	    			 systima.getCondition().getTransitions().remove(j);
	 	    	 }
			}
			else
				if(e.getStateChange() == ItemEvent.SELECTED) {
					
					transitions_checkboxes[index].setForeground(Color.red);
					 if(!systima.getCondition().getTransitions().contains(index))
						 systima.getCondition().getTransitions().add(index);
			
				}
			
			Collections.sort(systima.getCondition().getTransitions());
			
			}
	
	if(selected.equals("bc")) {
		
		int num = tempBasicColumn.size();
		  
				if(e.getStateChange() == ItemEvent.SELECTED) {
					
					num = num + 1; 
					
				    tempBasicColumn.add(systima.getMatches().get(index));
				    
				    for(int i = 0; i< num + 1; i++) {
				    	
				    	if(i < 9)
				    	
				    	basiccolumns_value_checkboxes[i].setEnabled(true);
				    	
				    }
				}
		
				if(e.getStateChange() == ItemEvent.DESELECTED) {
					
					num = num - 1;
					
					 for(int m=0; m<tempBasicColumn.size(); m++){
			    		 if(tempBasicColumn.get(m) == systima.getMatches().get(index))
			    		    tempBasicColumn.remove(m);
					
					 }
					 
					
					 
					 if(num < 8 && num > 0) {
					 
					 basiccolumns_value_checkboxes[num+1].setEnabled(false);
					 basiccolumns_value_checkboxes[num+1].setSelected(false);
					 
					 }
					 
					 else
					 
					 if(num == 0) {
						 
						 basiccolumns_value_checkboxes[0].setEnabled(false);
					     basiccolumns_value_checkboxes[0].setSelected(false);
					     basiccolumns_value_checkboxes[1].setEnabled(false);
					     basiccolumns_value_checkboxes[1].setSelected(false);
					 }
				}
		
		
		
        Collections.sort(tempBasicColumn);
     	
		
		
		
	}
	
	if(selected.equals("bc_limits")) {
		
		if(e.getStateChange() == ItemEvent.SELECTED) {
		
	    
	        tempLimits.add(index);
	        
		}
        
		if(e.getStateChange() == ItemEvent.DESELECTED) {
			
			   if(tempLimits.contains(index)) {
				   int n = tempLimits.indexOf(index);
		           tempLimits.remove(n);
			   }
		
        	}
		
		Collections.sort(tempLimits);
		
         if(tempLimits.size() > 0) 
        	 
        	 btnAddBasicColumn.setEnabled(true);
         else
        	 btnAddBasicColumn.setEnabled(false);
        	
           
		}
		
	}
		    
		
 	    
	}








