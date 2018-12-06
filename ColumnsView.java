import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import com.opencsv.CSVReader;
import net.miginfocom.swing.MigLayout;



public class ColumnsView extends BaseView{
	
	/**
	 * 
	 */
	
	private JPanel jpl;
	private JTable tblView;
	private JGradientButton btnClose;
	private JGradientButton btnSelect;
	private JGradientButton btnPrint;
	private JGradientButton btnHisto;
	private ArrayList<ArrayList<Integer>> stiles;
	private JLabel lblFile;
	private JLabel lblSumColumns;
	private String filePath;
	
	
	public ColumnsView() {
		
		jpl = new JPanel();
		tblView = new JTable();
		btnClose = new JGradientButton(80, 35, 13,"Close", "Closes this window",156,142,175);
		btnSelect = new JGradientButton(80, 35, 13,"Select", "Selects a column file",156,142,175);
		btnPrint = new JGradientButton(80, 35, 13,"Print", "Prints a column file",156,142,175);
		btnHisto = new JGradientButton(80, 35, 13,"Histo", "Shows a histogramm of number frequency",156,142,175);
		lblFile = new JLabel("<html><b> File : </b><html> (None)");
		lblSumColumns = new JLabel("<html><b> Columns : </b><html>");
		
		jpl.setLayout(new MigLayout());
		
		stiles = new ArrayList<ArrayList<Integer>>();
		
		
		   add(jpl);
		
		   JScrollPane js = new JScrollPane(tblView,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		   js.setPreferredSize(new Dimension(310,350));
		   jpl.add(js,"pos 20 20");
		   jpl.add(btnClose, "pos 340 200");
		   jpl.add(btnHisto, "pos 340 150");
		   jpl.add(btnSelect, "pos 340 50");
		   jpl.add(btnPrint, "pos 340 100");
		   jpl.add(lblFile, "pos 100 380");
		   jpl.add(lblSumColumns, "pos 100 400");
		   
		   btnPrint.setEnabled(false);
		   btnHisto.setEnabled(false);
		   
		   String[] columns = {"Id","Column"};
	   	
	    	DefaultTableModel tableModel = new DefaultTableModel(columns, 0){  
	    	      /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column){  
	    	         return false;  
	    	       }  
	    	     };  
	    	
	    	 	
	    	     tblView.setModel(tableModel);
	    	     tblView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	     tblView.getColumnModel().getColumn(0).setMinWidth(40);
	    	     tblView.getColumnModel().getColumn(1).setMinWidth(220);
	    	  

	    	    
	    	     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    	     centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	    	     for(int x=0;x<2;x++){
	    	         tblView.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	    	        }
	    	     
	    	     TableCellRenderer rendererFromHeader = tblView.getTableHeader().getDefaultRenderer();
			     	JLabel headerLabel = (JLabel) rendererFromHeader;
			     	headerLabel.setHorizontalAlignment(JLabel.CENTER);
		   
			        for(int i=0; i<jpl.getComponentCount(); i++) {
						Component co = jpl.getComponent(i);
							addPointerHand(co);
					}

btnClose.addActionListener(new ActionListener() {
	
    public void actionPerformed(ActionEvent e){
    	
    	dispose();
    	
    }
	
	
});

btnHisto.addActionListener(new ActionListener() {
	
    public void actionPerformed(ActionEvent e){
    	
 
           ArrayList<Integer> num = new  ArrayList<Integer>(); 
           ArrayList<Integer> asOne = new  ArrayList<Integer>();
           
           // Getting numbers in ppg file
           
           for(int i=0; i<stiles.size(); i++) {
        	   for(int j=0; j<8; j++) {
        		   
        		   asOne.add(stiles.get(i).get(j));
        		   
        		   if(!num.contains(stiles.get(i).get(j)))
        		      num.add(stiles.get(i).get(j));
        		   
        	   }
           }
           
           
           Collections.sort(num);
           
           ArrayList<Integer> freq = new  ArrayList<Integer>();
           
           //Getting frequency of numbers
           
           for(int i=0; i<num.size(); i++) {
        	  
        	   int sel = num.get(i);
        	   
        	   int fr = Collections.frequency(asOne, sel);
        	   
        	   freq.add(fr);
        	   
           }
           
           // Making a new frame for the StatisticsPanel
        
           String title = lblFile.getText().substring(28, lblFile.getText().length());
           
           int[] values = new int[num.size()];
           String[] labels = new String[num.size()];
           
           for(int i=0; i<num.size(); i++) {
           	labels[i] = "" + num.get(i);
           	values[i] = freq.get(i);
           }
           
           Color color = new Color(0,0,255);

           StatisticsPanel bc = new StatisticsPanel(values, labels, color, title);

           JFrame frame = new JFrame("Histogramm");
           frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           frame.setSize(430, 250);
           frame.setLocation(400, 300);
           
           frame.add(bc);
           frame.setVisible(true);
           frame.setResizable(false);
    	
    	
    	
    	
    }
	
	
});


btnPrint.addActionListener(new ActionListener() {
	
    public void actionPerformed(ActionEvent e){
    	

    	
    	int coupons = calculateCouponsNr(stiles); // Calculating number of coupons needed for printing
    	

        PrintCoupon[] printCoupons = stilesToDeltio(coupons, stiles); // Transforming coupons into printable coupons
 
		
		String message = "Coupons " + coupons + 
				         "  , columns  " + stiles.size() +
				         "  , cost  " + stiles.size() * 0.25 + ". \n" +
				         " Coupons will be sent to default printer. \n" +
				         " Continue?" ;
	
		int result = JOptionPane.showConfirmDialog((Component) null, message,
		        "Printing", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.YES_OPTION) {
		
			  JOptionPane.showMessageDialog(null, "Coupons were sent to printer.","Success",JOptionPane.INFORMATION_MESSAGE);
			
		             for(int d = 0; d < printCoupons.length; d++)
		            	 printCoupons[d].sendToPrinter();
			                              	
		             
	                     	}
		
		else
			
			JOptionPane.showMessageDialog(null, "Printing was aborted.","Cancel",JOptionPane.INFORMATION_MESSAGE);	

	   
   
    	
    }
	
	
});


btnSelect.addActionListener(new ActionListener() {
	
    public void actionPerformed(ActionEvent e){
    	
    	
    	DefaultTableModel tableModel = (DefaultTableModel) tblView.getModel();     
	   
    	JFileChooser fc = new JFileChooser();
    	
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Propogoal files", "ppg");
    	fc.setFileFilter(filter);
    	   
    	   int returnVal = fc.showOpenDialog(getParent());
    	  
    	   
    	   if(returnVal == JFileChooser.APPROVE_OPTION) {
    		   
    		   
    		   
    		   filePath = (fc.getSelectedFile().getAbsolutePath());
    		   
    		       if(filePath.toLowerCase().endsWith(".ppg")) {
    			   
    		  
    			   
    		             if(lblFile.getText().length() > 10 ) {

        	                 tableModel.setRowCount(0);
    		                }

    			
    		            lblFile.setText(lblFile.getText().substring(0, 28) + fc.getSelectedFile().getName());
    			
    	            	stiles = readCsvToArrayList(filePath);
    		
    		   

    		
		           for(int i=0; i<stiles.size(); i++) {
		        	   
		        	   int len = stiles.get(i).toString().length();
		        	   
		        	   String b = stiles.get(i).toString().substring(1, len - 1);
		        	   
		        	   Object[] row = {(i+1),b};
		        	   
		        	   tableModel.addRow(row);
		        	   
		           }
				
		

    			
    			 lblSumColumns.setText(lblSumColumns.getText().substring(0,30) + stiles.size());
    			 
    			 btnPrint.setEnabled(true);
    			 
    			 btnHisto.setEnabled(true);
		
    			
    

    		
    	   }
    		   
    		   else
    			   JOptionPane.showMessageDialog(null, "Please select only .ppg files");  
    			   
    }
    
    
	   
   }
	
	
});
			     	
			     	
	}
	
public ColumnsView(String arxeio) {
	

	
	jpl = new JPanel();
	tblView = new JTable();
	btnClose = new JGradientButton(70, 35, 13,"Close", "Closes this view",156,142,175);
	lblSumColumns = new JLabel("<html><b> Columns : </b><html>");

	jpl.setLayout(new MigLayout());
	
	 
	
	
	    add(jpl);
	
	   JScrollPane js = new JScrollPane(tblView);
	   js.setPreferredSize(new Dimension(300,350));
	   jpl.add(js,"pos 20 20");
	   jpl.add(btnClose, "pos 250 380");
	   jpl.add(lblSumColumns, "pos 50 390");
	
	
	   
	   String[] columns = {"Id","Column"};
   	
    	DefaultTableModel tableModel = new DefaultTableModel(columns, 0){  
    	      /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column){  
    	         return false;  
    	       }  
    	     };  
    	
    	 	
    	     tblView.setModel(tableModel);
    	     tblView.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	     tblView.getColumnModel().getColumn(0).setMaxWidth(50);
    	     tblView.getColumnModel().getColumn(1).setMinWidth(250);
    	  
    	     
    	  
    	    
    	     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    	     centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
    	     for(int x=0;x<2;x++){
    	         tblView.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
    	        }
    	     
    	     TableCellRenderer rendererFromHeader = tblView.getTableHeader().getDefaultRenderer();
		     	JLabel headerLabel = (JLabel) rendererFromHeader;
		     	headerLabel.setHorizontalAlignment(JLabel.CENTER);
		     	
		        
				
				String[] row = null ;
				int sum = 0;

				CSVReader csvReader = null;
				try {
					csvReader = new CSVReader(new FileReader(arxeio));
				} catch (FileNotFoundException e2) {
					
					e2.printStackTrace();
				}
				try {
					   
					int j = 0;
					
					while((row = csvReader.readNext()) != null) {
						
						sum = sum + 1 ; 
						
						ArrayList<Integer> csv_line = new ArrayList<Integer>();
						
						// ArrayList για αποθήκευση κάθε γραμμής του csv
					   	
					  
					   	
					   	for(int i=0; i<8; i++){
					   		csv_line.add(Integer.parseInt(row[i]));
					   	}
					   	
					   	j = j + 1;
					   	
					   	int strnum = csv_line.toString().length() - 1;
					   	
				 		String b = csv_line.toString().substring(1, strnum );
				 		
				 		Object[] row2 = {j,b};
		    		 	tableModel.addRow(row2);
		    		 	
					  
					   		
					   }
					
					 lblSumColumns.setText(lblSumColumns.getText().substring(0,30) + sum);
			
					
				} catch (IOException e1) {
				
					e1.printStackTrace();
				}
				  
				   try {
					csvReader.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	   
	

btnClose.addActionListener(new ActionListener() {

public void actionPerformed(ActionEvent e){
	
	dispose();
	
}


});


	
}

public int calculateCouponsNr(ArrayList<ArrayList<Integer>> my_array){
	   
	   int coupons = 0;
	   
		if(my_array.size() % 8 == 0)
			coupons = my_array.size() / 8  ;
 	else
 		coupons = (my_array.size() / 8) + 1 ;
		
		return coupons;
	   
}

public PrintCoupon[] stilesToDeltio(int coupons, ArrayList<ArrayList<Integer>> my_array) {
	
	   PrintCoupon[] printCoupons = new PrintCoupon[coupons] ;
	

    for(int i=0; i<coupons; i++){
			
    	       printCoupons[i] = new PrintCoupon(i);
			
			   int remainder = my_array.size() % 8;
			 
			   int couponcolumns ;
			
			   if( (remainder == 0) | ( (remainder > 0) & (i+1<coupons) ) )
				  couponcolumns =  8 ;
			   else
				  couponcolumns = remainder;
			
			
		              	for(int j=0; j< couponcolumns ; j++){
				
		
					         int strnum = my_array.get(i * 8 + j).toString().length() - 1; 
					         String col = my_array.get(i * 8 + j).toString().substring(1, strnum ); 
				
					         printCoupons[i].addColumn(col) ;
					
					
				        }
		
		
	}
	return printCoupons;
}


}
