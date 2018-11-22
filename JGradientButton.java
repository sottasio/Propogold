import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JButton;


public class JGradientButton extends JButton{
	
	

	private static final long serialVersionUID = 1L;

		public JGradientButton(String text) {
	    	super(text);
	        setContentAreaFilled(false);
	        setFocusPainted(false);
	        setBorder(BorderFactory.createBevelBorder(0));
	        setRolloverEnabled(true);
	       
	     
	}
	    public JGradientButton(){
	    	
	    }
	    
	    public JGradientButton (int minx, int miny, int textHeight, String str, String tooltip, int r, int g, int b) {
	    	
	    	super(str);
	    	
            setContentAreaFilled(false);
	        
	        setFocusPainted(false);
	        
	        setBorder(BorderFactory.createBevelBorder(0));
	        
	        setRolloverEnabled(true);
	    	
	    	this.setBackground(new Color(r,g,b));
	    	
	    	this.setMinimumSize(new Dimension(minx, miny));
	    	
	    	this.setFont(new Font("Arial", Font.BOLD, textHeight));
	    	
	    	this.setToolTipText(tooltip);
	    	
	        

	    }


		@Override
	    protected void paintComponent(Graphics g){
	        Graphics2D g2 = (Graphics2D)g.create();
	        g2.setPaint(new GradientPaint(
	                new Point(0, 0), 
	                getBackground(), 
	                new Point(0, getHeight()/3), 
	                Color.WHITE));
	        g2.fillRect(0, 0, getWidth(), getHeight()/3);
	        g2.setPaint(new GradientPaint(
	                new Point(0, getHeight()/3), 
	                Color.WHITE, 
	                new Point(0, getHeight()), 
	                getBackground()));
	       
	        
	        g2.fillRect(0, getHeight()/3, getWidth(), getHeight());
	        g2.dispose();

	        super.paintComponent(g);
	    }
		
 public void newProperties(int minx, int miny, int textHeight, String tooltip){
	 
 	this.setBackground(new Color(156,142,175));
 	
 	this.setMinimumSize(new Dimension(minx, miny));
 	
 	this.setFont(new Font("Arial", Font.BOLD, textHeight));
 	
 	this.setToolTipText(tooltip);
 }

}
