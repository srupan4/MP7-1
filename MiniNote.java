import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.awt.print.PrinterException;

public class MiniNote extends JFrame implements ActionListener  {
 
   JMenuBar menuBar = new JMenuBar();     
   JMenu file   = new JMenu("File(F)"),
        edit   = new JMenu("Edit(E)"),
        format = new JMenu("Format(O)"),
        view  = new JMenu("View(V)"),  
        help   = new JMenu("Help(H)");
   //menu
 
   JMenuItem[] menuItem = {                  //sub-menu
    new JMenuItem("New(N)"),
    new JMenuItem("Open(O)"),
    new JMenuItem("Save(S)"),
    new JMenuItem("Print(P)"),
    new JMenuItem("ChooseAll(A)"),
    new JMenuItem("Copy(C)"),
    new JMenuItem("Cut(T)"),
    new JMenuItem("Paste(P)"),
    new JMenuItem("Wrap(W)"),
    new JMenuItem("Font(F)"),
    new JMenuItem("Status(S)"),
    new JMenuItem("HelpTopic(H)"),
    new JMenuItem("About(A)"),
    new JMenuItem("Page Setup(U)"),
    new JMenuItem("Exit(X)"),
    new JMenuItem("Find(F)"),
    new JMenuItem("Find Next(N)"),
    new JMenuItem("Replace(R)")
    };
   
   JPopupMenu popupMenu = new JPopupMenu(); ;//right click menu   
   JMenuItem [] menuItem1 = {
	 new JMenuItem("Undo(Z)"),
	 new JMenuItem("Cut(X)"),
	 new JMenuItem("Paste(C)"),
     new JMenuItem("Copy(V)"),
     new JMenuItem("Delete(D)"),
     new JMenuItem("SelectAll(A)"),	   
   };

   private JTextArea textArea ;               //textArea
   private JScrollPane js ;	               //showBar
   private JPanel jp ;                          
   private FileDialog openFileDialog ;        //openFileDialog 
   private FileDialog saveFileDialog ;
   private Toolkit toolKit;                   //getTool
   private Clipboard clipboard;               //getClipBoard
   private String fileName;                  //get Default FileName

 /**
  *  MiniEdit  methods definition.
  *
  *  initialize
  *
  **/
 public MiniNote() {
	 
   fileName = "Untitled";                       
   toolKit = Toolkit.getDefaultToolkit();   
   clipboard = toolKit.getSystemClipboard(); 	  
   textArea =new JTextArea();            
   js = new JScrollPane(textArea);	   
   jp = new JPanel();
   openFileDialog =  new FileDialog(this,"Open",FileDialog.LOAD); 
   saveFileDialog =  new FileDialog(this,"Save as",FileDialog.SAVE);
   js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
   jp.setLayout(new GridLayout(1,1));            
   jp.add(js); 
   textArea.setComponentPopupMenu(popupMenu);   //textArea
   textArea.add(popupMenu); 
   add(jp);
   setTitle("MiniNote");
   setFont(new Font("Times New Roman",Font.PLAIN,15));
   setBackground(Color.white);
   setSize(800,600);
   setJMenuBar(menuBar);
   menuBar.add(file);
   menuBar.add(edit);
   menuBar.add(format);
   menuBar.add(view);
   menuBar.add(help);  
   for(int i=0;i<4;i++)
   {
       file.add(menuItem[i]);
       edit.add(menuItem[i+4]);
   }
   for(int i=0;i<3;++i)
   {
	   edit.add(menuItem[i+15]);
   }
   for(int i=0;i<2;++i)
   {
       format.add(menuItem[i+8]);
       help.add(menuItem[i+11]);
   }
       view.add(menuItem[10]);
       file.add(menuItem[14]);
   for(int i=0; i<6;++i)
   {
	   popupMenu.add(menuItem1[i]);
   }

 //addWindowListener
       addWindowListener(new WindowAdapter(){       
    	   public void windowClosing(WindowEvent e){
                       e.getWindow().dispose();
                       System.exit(0);
          }
       });
       //addWindowListener in all menus
      for(int i=0;i<menuItem.length;i++)
      {
            menuItem[i].addActionListener(this);
      }
      for(int i=0;i<menuItem1.length;i++)
      {
            menuItem1[i].addActionListener(this);
      }
 }

 /**
  *  actionPerformed.
  *
  **/
 public void actionPerformed(ActionEvent e) {

     Object eventSource = e.getSource();    
     if(eventSource == menuItem[0]) //newItem
     {
          textArea.setText("");
     }
     else if(eventSource == menuItem[1])//Open
     {
          openFileDialog.setVisible(true);
          fileName = openFileDialog.getDirectory() + openFileDialog.getFile();
          if(fileName != null)
          {
                 openFile(fileName);
          }
     }  
     else if(eventSource ==menuItem[2])//Save
     {
          saveFileDialog.setVisible(true);
          fileName = saveFileDialog.getDirectory()+saveFileDialog.getFile();       
          if(fileName !=null)
          {
                 writeFile(fileName);
           }
     }   
     else if(eventSource==menuItem[14])//Exit
     {
           System.exit(0);
     }  
     else if(eventSource == menuItem[4]||eventSource == menuItem1[5])  //SelectAll
     {
           textArea.selectAll();
     }    
     else if(eventSource == menuItem[5]||eventSource == menuItem1[2])  //Paste
     {
           String text = textArea.getSelectedText();
           StringSelection selection= new StringSelection(text);
           clipboard.setContents(selection,null);      
     }     
     else if(eventSource == menuItem[6]||eventSource == menuItem1[1])//Cut
     {
           String text = textArea.getSelectedText();
           StringSelection selection = new StringSelection(text);
           clipboard.setContents(selection,null);
           textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
     }
    
     else if(eventSource == menuItem[7]||eventSource == menuItem1[3])//Copy
     {
           Transferable contents = clipboard.getContents(this);
           if(contents==null)  return;
           String text;
           text="";
           try
          {
                text = (String)contents.getTransferData(DataFlavor.stringFlavor);
          }
          catch(Exception ex)
          {
        	 
          }
          textArea.replaceRange(text, textArea.getSelectionStart(),textArea.getSelectionEnd()); 
      }
     else if(eventSource == menuItem[8])  //Next
     {
    	 if (textArea.getLineWrap())   textArea.setLineWrap(false);
    	 else    	textArea.setLineWrap(true);

     }   
     else if(eventSource == menuItem[9])   //Format
     {//Font
    	 FontDialog fontdialog = new FontDialog(new JFrame(),"Font",true); 
    	 textArea.setFont(fontdialog.showFontDialog());             //setFont
     }    

     else if(eventSource == menuItem[11])   //Help
     {
    	 try
  	     {
  	         String filePath = "C:/WINDOWS/Help/notepad.hlp";
  	         Runtime.getRuntime().exec("cmd.exe /c "+filePath);
  	     }
  	     catch(Exception ee)
  	     {
  	         JOptionPane.showMessageDialog(this,"Cannot Open the file!","Error Message",JOptionPane.INFORMATION_MESSAGE);
  	     }
     }    
     else if(eventSource == menuItem[12])    //about
     {
    	     String help = "MiniNote";
    	     JOptionPane.showConfirmDialog(null, help, "About the Note",
    	     JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

     }
     else if(eventSource == menuItem[15]||eventSource == menuItem[16])  //find next
     {
    	 search();
    	 
     }
     else if(eventSource == menuItem[17]) //replace
     {
    	 substitude();
     }
 }
 
 /**
  *  openFile 
  *
  *  from txt to MiniNote
  *
  **/
 public void openFile(String fileName){
  
	 try
	 {
		  File file = new File(fileName);
          FileReader readIn = new FileReader(file);
          int size = (int)file.length();
          int charsRead = 0;
          char[] content = new char[size];
          while(readIn.ready())
          {
              charsRead += readIn.read(content,charsRead,size-charsRead);  
          }
          readIn.close();
          textArea.setText(new String(content,0,charsRead));
     }
	 catch(Exception e)
    {
          System.out.println("Error opening file!");
    }
  } 
 
 /**
  *  saveFile.
  *
  **/
 public void writeFile(String fileName){
    try
    {
         File file = new File(fileName);
         FileWriter write = new FileWriter(file);
         write.write(textArea.getText());
         write.close();
    }
    catch(Exception e)
    {
    System.out.println("Error closing file!");
    }
  }

 /**
  *  substitude方法
  *
  *  实现替换功能
  *
  */
 public void substitude() {
	 
	   final JDialog findDialog = new JDialog(this, "Find and Replace", true);
	   Container con = findDialog.getContentPane();
	   con.setLayout(new FlowLayout(FlowLayout.LEFT));
	   JLabel searchContentLabel = new JLabel("Find Content(N) :");
	   JLabel replaceContentLabel = new JLabel("Replace as(P)　 :");
	   final JTextField findText = new JTextField(30);
	   final JTextField replaceText = new JTextField(30);
	   final JCheckBox matchcase = new JCheckBox("Capital sensitive(C)");
	   ButtonGroup bGroup = new ButtonGroup();
	   final JRadioButton up = new JRadioButton("Up(U)");
	   final JRadioButton down = new JRadioButton("Down(D)");
	   down.setSelected(true);  
	   bGroup.add(up);
	   bGroup.add(down);	   
	   JButton searchNext = new JButton("Find next(F)");
	   JButton replace = new JButton("Replace(R)");
	   final JButton replaceAll = new JButton("Replace all(A)");
	   
	   
	   replace.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	         if (replaceText.getText().length() == 0 && textArea.getSelectedText() != null)
	    	     textArea.replaceSelection("");
	         if (replaceText.getText().length() > 0  && textArea.getSelectedText() != null)
	    	    textArea.replaceSelection(replaceText.getText());
	      }
	   });
	   

	   replaceAll.addActionListener(new ActionListener() {
	     public void actionPerformed(ActionEvent e) {
	        textArea.setCaretPosition(0); 
            int a = 0, b = 0, replaceCount = 0;
	        if (findText.getText().length() == 0) 
	        {
	            JOptionPane.showMessageDialog(findDialog, "Content!", "hint",JOptionPane.WARNING_MESSAGE);
	            findText.requestFocus(true);
                return;
            }
	        while (a > -1)
	        {
	            int FindStartPos = textArea.getCaretPosition();//get caret
	            String str1, str2, str3, str4, strA, strB;
	            str1 = textArea.getText();
	            str2 = str1.toLowerCase();
	            str3 = findText.getText();
	            str4 = str3.toLowerCase();
                if (matchcase.isSelected())   //Case
                {   
	               strA = str1;
	               strB = str3;
	            } 
                else 
                {
	               strA = str2;
	               strB = str4;
	            }
                                
	            if (up.isSelected())         //
	            {           
	                if (textArea.getSelectedText() == null)    
	                {
	                    a = strA.lastIndexOf(strB, FindStartPos - 1);
	                } 
	                else 
	                {
	                    a = strA.lastIndexOf(strB, FindStartPos- findText.getText().length() - 1);
	                }
	            }
	            else   //
	            {
	                if (textArea.getSelectedText() == null) 
	                {
	                    a = strA.indexOf(strB, FindStartPos);
	                                    
	                 }
	                else
	                {
	                    a = strA.indexOf(strB, FindStartPos- findText.getText().length() + 1);
	                }
	            }

	            if (a > -1)
	            {
	                if (up.isSelected())
	                {
	    	            textArea.setCaretPosition(a);
	                    b = findText.getText().length();
	                    textArea.select(a, a + b);
	                }
	                else if (down.isSelected()) 
	                {
	    	            textArea.setCaretPosition(a);
	                    b = findText.getText().length();
	                    textArea.select(a, a + b);
	                }
	             }
	             else 
	             {
	                if (replaceCount == 0)
	                {
	             JOptionPane.showMessageDialog(findDialog,"Not Found!", "MiniNote",JOptionPane.INFORMATION_MESSAGE);
	                } 
	               else
	                {
	                     JOptionPane.showMessageDialog(findDialog, "Replace successfully"+ replaceCount + "Not", "Done",JOptionPane.INFORMATION_MESSAGE);
	                }
	             }
	             if (replaceText.getText().length() == 0&& textArea.getSelectedText() != null) 
	             {
	    	         textArea.replaceSelection("");
	                 replaceCount++;
	             }
                 if (replaceText.getText().length() > 0&& textArea.getSelectedText() != null) 
                 {
	    	         textArea.replaceSelection(replaceText.getText());
	                 replaceCount++;
	             }
	         }//end while
	       }
	   }); 


	   searchNext.addActionListener(new ActionListener() {
		   
	    public void actionPerformed(ActionEvent e) {
	     int a = 0, b = 0;
	     int FindStartPos = textArea.getCaretPosition();
	     String str1, str2, str3, str4, strA, strB;
	     str1 = textArea.getText();
	     str2 = str1.toLowerCase();
	     str3 = findText.getText();
	     str4 = str3.toLowerCase();

	  
	     if (matchcase.isSelected())  
	     {
	       strA = str1;
	       strB = str3;
	     }  
	     else     
	     {
	       strA = str2;
	       strB = str4;
	     }
	     if (up.isSelected())  
	     {
	        if (textArea.getSelectedText() == null) 
	        {
	        	a = strA.lastIndexOf(strB, FindStartPos - 1);
	        } 
	        else
	        {
	            a = strA.lastIndexOf(strB, FindStartPos  - findText.getText().length() - 1);
	        }
	     } 
	     else if (down.isSelected())
	     {
	        if (textArea.getSelectedText() == null)
	        {
	           a = strA.indexOf(strB, FindStartPos);
	        } 
	        else 
	        {
	            a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
	        }
	     }
	     if (a > -1) 
	     {
	        if (up.isSelected())
	        {
	    	    textArea.setCaretPosition(a);
	            b = findText.getText().length();
	            textArea.select(a, a + b);
	        }
	        else if (down.isSelected()) 
	        {
	    	   textArea.setCaretPosition(a);
	           b = findText.getText().length();
	           textArea.select(a, a + b);
	        }
	     }
	     else
	     {
	        JOptionPane.showMessageDialog(null, "Not Found!", "Notes",
	        JOptionPane.INFORMATION_MESSAGE);
	     }
	    }
	   });	   
	   
	   //build cancel button interact
	   JButton cancel = new JButton("Cancel");
	   cancel.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	     findDialog.dispose();
	    }
	   });
	   //build find and replace interact
	   JPanel bottomPanel = new JPanel();
	   JPanel centerPanel = new JPanel();
	   JPanel topPanel = new JPanel();
	   JPanel direction = new JPanel();

	   direction.setBorder(BorderFactory.createTitledBorder("Direction"));
	   direction.add(up);
	   direction.add(down);

	   JPanel replacePanel = new JPanel();
	   replacePanel.setLayout(new GridLayout(1, 2));
	   replacePanel.add(searchNext);
	   replacePanel.add(replace);
	   replacePanel.add(replaceAll);
	   replacePanel.add(cancel);
	   
	   topPanel.add(searchContentLabel);
	   topPanel.add(findText);

	   centerPanel.add(replaceContentLabel);
	   centerPanel.add(replaceText);
	   centerPanel.add(replacePanel);

	   bottomPanel.add(matchcase);
	   bottomPanel.add(direction);

	   con.add(replacePanel);
	   con.add(topPanel);
	   con.add(centerPanel);
	   con.add(bottomPanel);	
       
	   //find and replace font.
	   findDialog.setSize(550, 240);
	   findDialog.setResizable(true);
	   findDialog.setLocation(230, 280);
	   findDialog.setVisible(true);
	}//mySearch() End

 /**
  *  search method
  *
  */
 public void search() {
	 
	   final JDialog findDialog = new JDialog(this, "Find Next", true);
	   Container con = findDialog.getContentPane();
	   con.setLayout(new FlowLayout(FlowLayout.LEFT));
	   JLabel searchContentLabel = new JLabel(" Find content (N) :");	   
	   final JTextField findText = new JTextField(17);
	   final JCheckBox matchcase = new JCheckBox("Case-sensitive (C)");
	   ButtonGroup bGroup = new ButtonGroup();
	   final JRadioButton up = new JRadioButton("Up(U)");
	   final JRadioButton down = new JRadioButton("Down(D)");
	   down.setSelected(true);  //default down
	   bGroup.add(up);
	   bGroup.add(down);	   
	   JButton searchNext = new JButton("Find Next(F)");	   

	   //Find next Button
	   searchNext.addActionListener(new ActionListener() {
		   
	    public void actionPerformed(ActionEvent e) {
	     int a = 0, b = 0;
	     int FindStartPos = textArea.getCaretPosition();
	     String str1, str2, str3, str4, strA, strB;
	     str1 = textArea.getText();
	     str2 = str1.toLowerCase();
	     str3 = findText.getText();
	     str4 = str3.toLowerCase();

	     //Case sensitive or not
	     if (matchcase.isSelected())   //no
	     {
	       strA = str1;
	       strB = str3;
	     }  
	     else      //yes
	     {
	       strA = str2;
	       strB = str4;
	     }
	     if (up.isSelected())  //Up
	     {
	        if (textArea.getSelectedText() == null) 
	        {
	        	a = strA.lastIndexOf(strB, FindStartPos - 1);
	        } 
	        else
	        {
	            a = strA.lastIndexOf(strB, FindStartPos  - findText.getText().length() - 1);
	        }
	     } 
	     else if (down.isSelected())
	     {
	        if (textArea.getSelectedText() == null)
	        {
	           a = strA.indexOf(strB, FindStartPos);
	        } 
	        else 
	        {
	            a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
	        }
	     }
	     if (a > -1) 
	     {
	        if (up.isSelected())
	        {
	    	    textArea.setCaretPosition(a);
	            b = findText.getText().length();
	            textArea.select(a, a + b);
	        }
	        else if (down.isSelected()) 
	        {
	    	   textArea.setCaretPosition(a);
	           b = findText.getText().length();
	           textArea.select(a, a + b);
	        }
	     }
	     else
	     {
	        JOptionPane.showMessageDialog(null, "Error!Not Found!", "NotePad",
	        JOptionPane.INFORMATION_MESSAGE);
	     }
	    }
	   });
	  	   
	   //Cancel board
	   JButton cancel = new JButton("Cancel");
	   cancel.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	     findDialog.dispose();
	    }
	   });
	   //Replace board
	   JPanel bottomPanel = new JPanel();
	   JPanel centerPanel = new JPanel();
	   JPanel topPanel = new JPanel();
	   JPanel direction = new JPanel();
	   direction.setBorder(BorderFactory.createTitledBorder("Direction"));
	   direction.add(up);
	   direction.add(down);
	   topPanel.add(searchContentLabel);
	   topPanel.add(findText);
	   topPanel.add(searchNext);
	   bottomPanel.add(matchcase);
	   bottomPanel.add(direction);
	   bottomPanel.add(cancel);
	   con.add(topPanel);
	   con.add(centerPanel);
	   con.add(bottomPanel); 
	   //Replace dialog box to change size (no), position, and visibility
	   findDialog.setSize(425, 200);
	   findDialog.setSize(500, 500);//try
	   findDialog.setResizable(true);
	   findDialog.setLocation(230, 280);
	   findDialog.setVisible(true);
	}


 	/**
 	 * Main function.
 	 * show the miniNote pad
 	 *
 	 **/ 
 	public static void main(String[] args) {
 
 		MiniNote note = new MiniNote();
 		note.setVisible(true);
  
 	}
 
}

