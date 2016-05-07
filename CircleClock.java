import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.ArrayList;

import javax.swing.Timer;

public class CircleClock extends Applet {
	
	//This constant is for the radius and positioning of the circles
	private final int size = 8;
	
	private Image backbuffer;
	private Graphics2D g2d;
	
	//This lets you access the date and time of the computer you are on
	private LocalDateTime ltd;
	
	//These will store the information on the current date and time
	private int tenths;
	private int seconds;
	private int minutes;
	private int hours;
	private int days;
	private int months;
	private int years;
	
	//Stores the number of days in a month
	private int daysInMonth;
	
	//Stores a boolean variable to tell whether the circle should be filled in or left as an outline
	private boolean[] tenthCircles;
	private boolean[] secondCircles;
	private boolean[] minuteCircles;
	private boolean[] hourCircles;
	private ArrayList<Boolean> dayCircles; //Days in the month change so an ArrayList is used
	private boolean[] monthCircles;
	
	public void init() {
		
		//Sets the size of the Applet to 400 by 400 pixels
		this.setSize(400,400);

		backbuffer = createImage(getSize().width, getSize().height);
		g2d = (Graphics2D)backbuffer.getGraphics();
		
		this.setBackground(Color.black);
		
		ActionListener taskPerformer = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				repaint();
			}
		};
		
		//If you want to use hundereths or thousandths of a second, decrease this number from 100 to something less
		//1000 milliseonds == 1 second
		new Timer(100, taskPerformer).start();
		
		//Set current color to black
		g2d.setColor(Color.black);
		//Draw a rectangle the size of the screen
		g2d.fillRect(0, 0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		//This makes the circles look more circle like
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
	}
	
	public void update(Graphics g) {
		paint(g); 
	}
	
	//Initialize all fields
	public CircleClock() {
		
		ltd = LocalDateTime.now();
		
		tenths = this.getTenthOfSecond();
		seconds = ltd.getSecond();
		minutes = ltd.getMinute();
		hours = ltd.getHour();
		days = ltd.getDayOfYear();
		months = ltd.getMonthValue();
		years = ltd.getYear();
		
		daysInMonth = this.daysInCurrentMonth();
		
		tenthCircles = new boolean[10];
		secondCircles = new boolean[60];
		minuteCircles = new boolean[60];
		hourCircles = new boolean[24];
		dayCircles = new ArrayList<Boolean>();
		monthCircles = new boolean[12];
		for(int i = 0; i < daysInMonth; i++) {
			dayCircles.add(false);
		}

	}
	
	public void paint(Graphics graphics) {
		
		backbuffer = createImage(getSize().width, getSize().height);
		g2d = (Graphics2D)backbuffer.getGraphics();
		
		//Sets the LocalDateTime ltd to the current time to maintain continuity through the code
		ltd = LocalDateTime.now();
		
		//Sets the color to a purple
		g2d.setColor(new Color(127,0,255)); 
		//Draws the Date and Time in the center of the current window
		drawDateTime();
		
		
		
		//Sets tenthes to the current tenth of a second
		tenths = this.getTenthOfSecond();
		if(tenths==0) {
			for(int i = 0; i < 10; i++) {
				//Reset all of the variables in the array to false
				tenthCircles[i] = false;
			}
		}
		for(int i = 0; i <= tenths; i++) {
			//Sets the variable for the current tenth of a second and all previous ones true
			tenthCircles[i] = true;
		}
		for(int i = 0; i < 10; i++) {
			int num = size; //Uses size as is
			if(tenthCircles[i]) {
				//Uses basic trigonometry to position the circles
				//Fills the circle
				g2d.fillOval((int)(Math.cos(i/5.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2.0-num/2.0), (int)(Math.sin(i/5.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			} else {
				//Outlines the circle
				g2d.drawOval((int)(Math.cos(i/5.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2.0-num/2.0), (int)(Math.sin(i/5.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			}
		}
		
		
		//Sets the color to blue
		g2d.setColor(Color.blue);
		//Sets seconds to the current second
		seconds = ltd.getSecond();
		if(seconds==0) {
			for(int i = 0; i < 60; i++) {
				//Reset all of the variables in the array to false
				secondCircles[i] = false;
			}
		}
		for(int i = 0; i <= seconds; i++) {
			//Sets the variable for the current second and all previous ones true
			secondCircles[i] = true;
		}
		for(int i = 0; i < 60; i++) {
			int num = size + 2; //Increases distance from center as well as the size of the circle
			if(secondCircles[i]) {
				//Uses basic trigonometry to position the circles
				//Fills the circle
				g2d.fillOval((int)(Math.cos(i/30.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2.0-num/2.0), (int)(Math.sin(i/30.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			} else {
				//Outlines the circle
				g2d.drawOval((int)(Math.cos(i/30.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2.0-num/2.0), (int)(Math.sin(i/30.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			}
		}
		
		
		//Set color to green
		g2d.setColor(Color.green);
		//Sets minutes to the current minute
		minutes = ltd.getMinute();
		if(minutes==0) {
			for(int i = 0; i < 60; i++) {
				//Reset all of the variables in the array to false
				minuteCircles[i] = false;
			}
		}
		for(int i = 0; i <= minutes; i++) {
			//Sets the variable for the current minute and all previous ones true
			minuteCircles[i] = true;
		}
		for(int i = 0; i < 60; i++) {
			int num = size + 4; //Increases distance from center as well as the size of the circle
			if(minuteCircles[i]) {
				//Uses basic trigonometry to position the circles
				//Fills the circle
				g2d.fillOval((int)(Math.cos(i/30.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2.0-num/2.0), (int)(Math.sin(i/30.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			} else {
				//Outlines the circle
				g2d.drawOval((int)(Math.cos(i/30.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2-num/2.0), (int)(Math.sin(i/30.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			}
		}
		
		
		//Set color to yellow
		g2d.setColor(Color.yellow);
		//Sets hour to the current hour
		hours = ltd.getHour();
		if(hours==0) {
			for(int i = 0; i < 24; i++) {
				//Reset all of the variables in the array to false
				hourCircles[i] = false;
			}
		}
		for(int i = 0; i <= hours; i++) {
			//Sets the variable for the hour and all previous ones true
			hourCircles[i] = true;
		}	
		for(int i = 0; i < 24; i++) {
			int num = size + 6; //Increases distance from center as well as the size of the circle
			if(hourCircles[i]) {
				//Uses basic trigonometry to position the circles
				//Fills the circle
				g2d.fillOval((int)(Math.cos(i/12.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2-num/2.0), (int)(Math.sin(i/12.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			} else {
				g2d.drawOval((int)(Math.cos(i/12.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2-num/2.0), (int)(Math.sin(i/12.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			}
		}
		
		
		//Set color to orange
		g2d.setColor(Color.orange);
		//Determine the number of days in the current month
		daysInMonth = this.daysInCurrentMonth();
		//Sets days to the current day of the month
		days = ltd.getDayOfMonth();
		if(days==1) {
			dayCircles.clear();
			for(int i = 0; i < daysInMonth; i++) {
				//Reset all of the variables in the ArrayList to false
				dayCircles.add(false);
			}
		}
		for(int i = 1; i <= days; i++) {
			//Sets the variable for the current day and all previous ones true
			dayCircles.set(i-1, true);
		}	
		for(int i = 0; i < daysInMonth; i++) {
			int num = size + 8; //Increases distance from center as well as the size of the circle
			if(dayCircles.get(i)) {
				//Uses basic trigonometry to position the circles
				//Fills the circle
				g2d.fillOval((int)(Math.cos((2.0*i)/daysInMonth*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2-num/2.0), (int)(Math.sin((2.0*i)/daysInMonth*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			} else {
				//Outlines the circle
				g2d.drawOval((int)(Math.cos((2.0*i)/daysInMonth*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2-num/2.0), (int)(Math.sin((2.0*i)/daysInMonth*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			}
		}
		
		
		
		//Set the current color to red
		g2d.setColor(Color.red);
		//Sets months to the current month
		months = ltd.getMonthValue();
		if(months==1) {
			for(int i = 0; i < 12; i++) {
				//Reset all of the variables in the array to false
				monthCircles[i] = false;
			}
		}
		for(int i = 1; i <= months; i++) {
			//Sets the variable for the current month and all previous ones true
			monthCircles[i-1] = true; 
		}	
		for(int i = 0; i < 12; i++) {
			int num = size + 10; //Increases distance from center as well as the size of the circle
			if(monthCircles[i]) {
				//Uses basic trigonometry to position the circles
				//Fills the circle
				g2d.fillOval((int)(Math.cos(i/6.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2.0-num/2.0), (int)(Math.sin(i/6.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			} else {
				//Outlines the circle
				g2d.drawOval((int)(Math.cos(i/6.0*Math.PI-(Math.PI/2))*num*10+this.getWidth()/2.0-num/2.0), (int)(Math.sin(i/6.0*Math.PI-(Math.PI/2))*num*10+this.getHeight()/2.0-num/2.0), num, num);
			}
		}
		
		
		graphics.drawImage(backbuffer, 0, 0, null);
		
		
	}
	
	
	
	
	/**
	 * 
	 * @return tenthes of a second [0-9]
	 */
	public int getTenthOfSecond() {
		return this.ltd.getNano() / 100000000;
	}
	
	/**
	 * 
	 * @return hundredths of a second [0-9]
	 */
	public int getHundrethOfSecond() {
		return ( this.ltd.getNano() / 10000000 ) % 10;
	}
	
	/**
	 * 
	 * @return true if the current year is a leap year, false otherwise
	 */
	public boolean currentlyLeapYear() {
		return (this.years % 4 == 0 && this.years % 100 != 0) || (this.years % 400 == 0);
	}
	
	/**
	 * 
	 * @return the number of days in the current month
	 */
	public int daysInCurrentMonth() {
		switch(this.months) {
			//Removing months with 31 days and making that the default case is slightly more efficient
			//case 1: return 31;
			case 2: if(this.currentlyLeapYear()) return 29;
					return 28;//case 3: return 31;
			case 4: return 30;//case 5: return 31;
			case 6: return 30;//case 7: return 31;//case 8: return 31;
			case 9: return 30; //case 10: return 31;
			case 11: return 30;
			default: return 31;
		}
	}
	
	/**
	 * 
	 * @return the name of the current month
	 */
	public String monthText() {
		switch(this.months){
			case 1: return "January";
			case 2: return "February";
			case 3: return "March";
			case 4: return "April";
			case 5: return "May";
			case 6: return "June";
			case 7: return "July";
			case 8: return "August";
			case 9: return "September";
			case 10: return "October";
			case 11: return "November";
			default: return "December";
		}
	}
	
	/**
	 * 
	 * @return the date in Month D, YYY format
	 */
	public String formatDate() {
		String year = "" + this.ltd.getYear(); //Current year
		String month = "" + this.monthText(); //Current month spelled out
		String day = "" + this.ltd.getDayOfMonth(); //Current day of the month
		
		return month + " " + day + ", " + year;
	}
	
	/**
	 * 
	 * @return the time H:M:S.T (In 24 Time)
	 */
	public String formatTime() {
		String hour = "" + this.ltd.getHour(); //Current hour, add %12 for 12-hour time
		String minute = "" + this.ltd.getMinute(); //Current minute
		String second = "" + this.ltd.getSecond(); //Current second
		String tenth = "" + this.getTenthOfSecond(); //Current tenth of a second
		
		return hour + ":" + minute + ":" + second + "." + tenth;
	}
	
	/**
	 * Displays the date and time centered to the window
	 */
	public void drawDateTime() {
		
	    FontMetrics metrics = this.g2d.getFontMetrics(this.getFont());
	    
	    int x1 = (int) ((this.getWidth() - metrics.stringWidth(this.formatDate())) / 2.0);
	    int x2 = (int) ((this.getWidth() - metrics.stringWidth(this.formatTime())) / 2.0);
	    int y = (int) ((this.getHeight() - metrics.getHeight()) / 2.0) + metrics.getAscent();
	    
	    this.g2d.drawString(this.formatDate(), x1, (int) (y-(metrics.getAscent()/2.0)));
	    this.g2d.drawString(this.formatTime(), x2, (int) (y+(metrics.getAscent()/2.0)));
	    
	}
	

}
