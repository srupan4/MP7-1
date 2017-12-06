/**
 * calendar.
 * @author JIANG Han
 *
 */
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
import java.util.Calendar;
import java.util.Scanner;

public class CalendarPad {
	int year;
	int month;
      public void input(){
    	  System.out.println("Input Year£º");
    	  Scanner sc=new Scanner(System.in);
    	  year = sc.nextInt();
    	  System.out.println("Input Month(1-12)£º");
    	  month = sc.nextInt();
    	  if(month >= 1 && month <= 12) {
    		  calendar1(month);
    	  }else{
    		  System.out.println("Wrong number, try again");
    	      input();
    	   }
      }
      public void calendar1(int month){
    	  Calendar cl=Calendar.getInstance();
    	  cl.set(year, month-1, 1);//set date
    	  int maxday=cl.getActualMaximum(Calendar.DATE);//largest date every month
    	  int sday=cl.get(Calendar.DAY_OF_WEEK);//what day it is in a week
    	  System.out.println("Sun"+"\t"+"Mon"+"\t"+"Tue"+"\t"+"Wed"+"\t"+"Thu"+"\t"+"Fri"+"\t"+"Sat");
    	  for(int j=1;j<sday;j++){
    		  System.out.print(" "+"\t");
    	  }
    	  for(int i=1;i<=maxday;i++){
    		  System.out.print(i+"\t");
    		  if((i+sday-1)%7==0){
    			  System.out.println();
    		  }
    	  }
      }

      public static void main(String[] args) {
    	  CalendarPad cd=new CalendarPad();
    	  cd.input();  
	}
}