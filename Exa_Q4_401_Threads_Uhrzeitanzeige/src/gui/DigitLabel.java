/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;

/**
 *
 * @author Elias Wilfinger
 */
public class DigitLabel extends JLabel{
    private int number = 0;
    
    
     public DigitLabel() {
        this.setBackground(Color.DARK_GRAY);
        this.setOpaque(true);
    }

    @Override
    public void paint(Graphics g) {
        
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2 = (Graphics2D) g;
        double scaleX = this.getWidth() / 11.;
        double scaleY = this.getHeight() / 18.;
        AffineTransform atrans = new AffineTransform();
        atrans.scale(scaleX, scaleY);
        g2.transform(atrans);
        
        for(Integer segment : getRequiredSegments(number)){
            g2.setColor(Color.RED);
            g2.fillPolygon(getXCoordinatesForSegment(segment), getYCoordinatesForSegment(segment), 
                    getXCoordinatesForSegment(segment).length);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(0.3f));
            g2.drawPolygon(getXCoordinatesForSegment(segment), getYCoordinatesForSegment(segment), 
                    getXCoordinatesForSegment(segment).length);
        }
        
        
    }

    public void setNumber(int number) {
        this.number = number;
        this.repaint();
    }
    
    private int[] getXCoordinatesForSegment(int segmentNo){
        
        switch(segmentNo){
            case -2:
                return new int[]{4, 7, 7, 4};
            case -1:
                return new int[]{4, 7, 7, 4};
            case 0:
                return new int[]{2, 3, 8, 9, 8, 3};
            case 1:
                return new int[]{9, 10, 10, 9, 8, 8};
            case 2:
                return new int[]{9, 10, 10, 9, 8, 8};
            case 3:
                return new int[]{2, 3, 8, 9, 8, 3};
            case 4:
                return new int[]{2, 3, 3, 2, 1, 1};
            case 5:
                return new int[]{2, 3, 3, 2, 1, 1};
            case 6:
                return new int[]{2, 3, 8, 9, 8, 3};
        }
        
        return null;
    }

    private int[] getYCoordinatesForSegment(int segmentNo){
        switch(segmentNo){
            case -2:
                return new int[]{4, 4, 7, 7};
            case -1:
                return new int[]{11, 11, 14, 14};
            case 0:
                return new int[]{2, 1, 1, 2, 3, 3};
            case 1:
                return new int[]{2, 3, 8, 9, 8, 3};
            case 2:
                return new int[]{9, 10, 15, 16, 15, 10};
            case 3:
                return new int[]{16, 15, 15, 16, 17, 17};
            case 4:
                return new int[]{9, 10, 15, 16, 15, 10};
            case 5:
                return new int[]{2, 3, 8, 9, 8, 3};
            case 6:
                return new int[]{9, 8, 8, 9, 10, 10};
        }
        return null;
    }
    
    private Integer[] getRequiredSegments(int number){
        Map<Integer, Integer[]> segments = new HashMap();
        segments.put(-1, new Integer[]{-1, -2});
        segments.put(0, new Integer[]{0, 1, 2, 3, 4, 5});
        segments.put(1, new Integer[]{1, 2});
        segments.put(2, new Integer[]{0, 1, 6, 4, 3});
        segments.put(3, new Integer[]{0, 1, 6, 2, 3});
        segments.put(4, new Integer[]{5, 6, 1, 2});
        segments.put(5, new Integer[]{0, 5, 6, 2, 3});
        segments.put(6, new Integer[]{0, 5, 6, 4, 3, 2});
        segments.put(7, new Integer[]{0, 1, 2});
        segments.put(8, new Integer[]{0, 1, 2, 3, 4, 5, 6});
        segments.put(9, new Integer[]{0, 1, 2, 3, 5, 6});
        
        
        return segments.get(number);
    }

    
    
    
}
