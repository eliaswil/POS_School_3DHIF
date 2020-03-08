/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.JPanel;

/**
 *
 * @author Elias Wilfinger
 */
public class LocationTimePanel extends JPanel implements Runnable { // represents one row
    
    private DigitLabel[] digitLabels;
    private ZoneId zone;

    public LocationTimePanel(ZoneId zone) {
        this.zone = zone;
        GridLayout gridLayout = new GridLayout(1, 8, 0, 0);
        this.setLayout(gridLayout);
        this.setBackground(Color.DARK_GRAY);
        
        digitLabels = new DigitLabel[8];
        
        for (int i = 0; i < digitLabels.length; i++) {
            digitLabels[i] = new DigitLabel(); 
            this.add(digitLabels[i]);
        }
    }

    public void setZone(ZoneId zone) {
        this.zone = zone;
    }
    
    
    private void displayTime(){
        char[] digits = LocalTime.now(zone).format(DateTimeFormatter.ofPattern("HH:mm:ss")).toCharArray();
        for (int i = 0; i < digitLabels.length; i++) {
            digitLabels[i].setNumber(":".equals(digits[i] + "") ? -1 : Integer.parseInt(digits[i] + ""));
        }
    }

     @Override
    public void run() {
        while (!Thread.interrupted()) {
            displayTime();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
    
}
