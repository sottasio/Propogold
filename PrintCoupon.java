
import java.awt.*;

import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import java.awt.print.*;
import java.util.ArrayList;


public class PrintCoupon implements Printable {
	
	public int deltionr ;
	public ArrayList<String> columns = new ArrayList<String>() ;
	

public int getDeltionr() {
		return deltionr;
	}

public void setDeltionr(int deltionr) {
		this.deltionr = deltionr;
	}

public void addColumn(String col) {
	
	this.columns.add(col) ;
}

public ArrayList<String> getColumns() {
    return columns;
  }

public PrintCoupon(int deltionr){
	
	this.deltionr = deltionr; 
	
}

public void sendToPrinter() {
		
		 HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
         attr.add((Attribute) new MediaPrintableArea(82, 1, 105, 203, MediaPrintableArea.MM));
        
         
         PrinterJob job = PrinterJob.getPrinterJob();
       
         
         job.setPrintable(this);
         
     
             try {
                  job.print(attr);
             } 
             
             catch (PrinterException ex) {
             
            	 Message m = new Message("Error","Printing was not finished", "Error");
            	 m.show();
             }
  
		
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page) {

    if (page > 0) { /* We have only one page, and 'page' is zero-based */
        return Printable.NO_SUCH_PAGE;
    }

   
	
	Graphics2D g2d = (Graphics2D)g;
    
    pf.setOrientation(PageFormat.PORTRAIT);
    
    g2d.translate(pf.getImageableX(), pf.getImageableY());
    
    g2d.setFont(new Font("Arial",Font.PLAIN,11));
    
    
    
    /* Rendering */
    
    String[] str = new String[8];
    
	for(int i = 0; i < this.getColumns().size(); i++) {
		
			str = this.getColumns().get(i).split(",");
	
	    	for(int j = 0; j < 8; j++){
	    		
 
    		
    		int num = Integer.parseInt(str[j].trim());
    		
    		g2d.drawString("●", i * 12 + 87, (int)((num-1) * 11.72) + 56);
    	
    								}
	
	                 }
    
    return Printable.PAGE_EXISTS;
	}
	
}
