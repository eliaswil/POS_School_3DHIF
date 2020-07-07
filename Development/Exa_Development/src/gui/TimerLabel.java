/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;

/**
 *
 * @author Elias Wilfinger
 */
public class TimerLabel extends JLabel implements Runnable{

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    
    @Override
    public void run() {
        while(!Thread.interrupted()){
            LocalTime time = LocalTime.now();
            this.setText(time.format(DTF));
            try{
                Thread.sleep(250);
            }catch(InterruptedException e){
                //e.printStackTrace();
                break;
            }
        }
        this.setText("00:00:00");
        
    }
    
}
