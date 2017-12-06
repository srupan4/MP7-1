import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.FlowLayout;  
import java.awt.GridLayout;  
import java.awt.KeyEventPostProcessor;  
import java.awt.KeyboardFocusManager;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.KeyEvent;  
import java.awt.event.MouseWheelEvent;  
import java.awt.event.MouseWheelListener;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  
import java.util.Calendar;  
  
import javax.swing.ImageIcon;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;  
import javax.swing.border.BevelBorder;  
import javax.swing.border.CompoundBorder;  
  
public class CalendarBoard extends JFrame{  
    /** 
     *  the calendar of one month.
     */  
    private static final long serialVersionUID = 1L;  
    private Calendar calendar;  
    private final static String week[]={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};  
    private JLabel dayLable[][]=new JLabel[6][7],monthLable;  
      
    private void initCalendar() {  
        calendar=Calendar.getInstance();  
        calendar.set(Calendar.DATE,0);  
    }  
       
    private void initFrame() {  
        setIconImage(new ImageIcon("icon\\Calendar.gif").getImage());  
        setSize(500, 300);  
        getContentPane().setLayout(new BorderLayout());  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setLocationRelativeTo(null);  
        addWindowListener(new WindowAdapter() {  
              
            @Override  
            public void windowOpened(WindowEvent e) {  
                requestFocus();  
            }  
              
        });  
    }  
      
    public CalendarBoard() {  
        super("Calendar");  
        initCalendar();  
        initFrame();  
        JPanel northPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));  
        JButton preMonth=new JButton("Last Month");  
        preMonth.addActionListener(new ActionListener() {  
              
            @Override  
            public void actionPerformed(ActionEvent e) {  
                calendarAdd(-1);  
            }  
        });  
        northPanel.add(preMonth);  
        JButton nextMonth=new JButton("Next Month");  
        nextMonth.addActionListener(new ActionListener() {  
              
            @Override  
            public void actionPerformed(ActionEvent e) {  
                calendarAdd(1);  
            }  
        });  
        northPanel.add(nextMonth);  
        getContentPane().add(northPanel,BorderLayout.NORTH);  
          
        JPanel centerPanel=new JPanel(new GridLayout(7, 7,1,1));  
        centerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));  
        for(String s:week)  
        {  
            JButton weekButton=new JButton(s);  
            weekButton.addActionListener(new ActionListener() {  
                  
                @Override  
                public void actionPerformed(ActionEvent e) {  
                    requestFocus();  
                }  
            });  
            centerPanel.add(weekButton);  
        }  
        for(int i=0;i<6;i++)  
        {  
            for(int j=0;j<7;j++)  
            {  
                JPanel tempJpanel=new JPanel();  
                dayLable[i][j]=new JLabel();  
                //dayLable[i][j].enableInputMethods(true);  
                dayLable[i][j].setHorizontalAlignment(JLabel.CENTER);  
                if(j%2==0)tempJpanel.setBackground(Color.white);  
                else tempJpanel.setBackground(Color.lightGray);  
                tempJpanel.add(dayLable[i][j]);  
                centerPanel.add(tempJpanel);  
            }  
        }  
        getContentPane().add(centerPanel, BorderLayout.CENTER);  
          
        JPanel southPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));  
        monthLable=new JLabel();  
        southPanel.add(monthLable);  
        getContentPane().add(southPanel,BorderLayout.SOUTH);  
        updateCalendar();  
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor() {  
  
            private long lastLeftPress=0;  
            private long lastRightPress=0;  
              
            @Override  
            public boolean postProcessKeyEvent(KeyEvent e) {  
                long currentTime = System.currentTimeMillis();  
                if(e.getID() == KeyEvent.KEY_PRESSED)  
                {  
                    if (e.getKeyCode() == KeyEvent.VK_LEFT& currentTime-lastLeftPress>100)   
                    {  
                        lastLeftPress = currentTime;  
                        preMonth.requestFocus();  
                        preMonth.doClick();  
                    }   
                    else if (e.getKeyCode() == KeyEvent.VK_RIGHT& currentTime-lastRightPress>100)   
                    {  
                        lastRightPress = currentTime;  
                        nextMonth.requestFocus();  
                        nextMonth.doClick();  
                    }  
                }  
                return false;  
            }  
        });  
        addMouseWheelListener(new MouseWheelListener() {  
              
            private long lastMove=0;  
            @Override  
            public void mouseWheelMoved(MouseWheelEvent e) {  
                int wheelRotation=e.getWheelRotation();  
                long currentTime=System.currentTimeMillis();  
                if(wheelRotation==-1& currentTime-lastMove>70)  
                {  
                    lastMove=currentTime;  
                    preMonth.requestFocus();  
                    preMonth.doClick();  
                }   
                else if(wheelRotation==1&currentTime-lastMove>70)  
                {  
                    lastMove=currentTime;  
                    nextMonth.requestFocus();  
                    nextMonth.doClick();  
                }   
                  
            }  
        });  
          
        setVisible(true);  
    }  
      
    public String toString(){  
        String s=calendar.get(Calendar.YEAR)+"Äê"+(calendar.get(Calendar.MONTH)+1)+"ÔÂ\n";  
        for(String i:week)  
                s+="  "+i;  
        s+="\n";  
        int day=calendar.get(Calendar.DAY_OF_WEEK)%7;  
        for(int i=0;i<day;i++)s+="    ";  
        int maxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
        for(int i=1;i<=maxDay;i++)  
        {  
            s+=String.format("%4d",i);  
            if(day==6)  
            {  
                s+="\n";  
                day=0;  
            }  
            else day++;  
        }  
        return s;  
    }  
      
    private void updateCalendar() {  
        int i=0,j=calendar.get(Calendar.DAY_OF_WEEK)%7;;  
        int maxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
        //System.out.println(this);  
        for(int k=0;k<j;k++)dayLable[0][k].setText("");  
        for(int k=1;k<=maxDay;k++)  
        {  
            dayLable[i][j].setText(Integer.toString(k));  
            if(j==6)  
            {  
                i++;  
                j=0;  
            }  
            else j++;  
        }  
        while(i<6)  
        {  
            dayLable[i][j].setText("");  
            if(j==6)  
            {  
                i++;  
                j=0;  
            }  
            else j++;  
        }  
        monthLable.setText("Calendar£º"+calendar.get(Calendar.YEAR)+"Year"+(calendar.get(Calendar.MONTH)+1)+"Month");  
    }  
      
    public void calendarAdd(int delta)  
    {  
        calendar.add(Calendar.MONTH,delta);  
        updateCalendar();  
    }  
      
    public static void main(String args[]) {  
        new CalendarBoard();  
    }  
}  
