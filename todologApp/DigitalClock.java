package todologApp;

import javax.swing.*;
import javax.swing.Timer;

import java.util.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class DigitalClock {
	private final JLabel time = new JLabel();
    private final SimpleDateFormat sdf  = new SimpleDateFormat("hh:mm");
    private int   currentSecond;
    private Calendar calendar;
    
    

    public JLabel getTime(){
    	time.setPreferredSize(new Dimension(700,20));
    	return time;
    }
    private void reset(){
        calendar = Calendar.getInstance();
        currentSecond = calendar.get(Calendar.SECOND);
    }
    public void start(){
        reset();
        Timer timer = new Timer(1000, new ActionListener(){
            public void actionPerformed( ActionEvent e ) {
                    if( currentSecond == 60 ) {
                        reset();
                    }
                    time.setText( String.format("%s:%02d", sdf.format(calendar.getTime()), currentSecond ));
                    currentSecond++;
                }
            });
            timer.start();
        }
}

