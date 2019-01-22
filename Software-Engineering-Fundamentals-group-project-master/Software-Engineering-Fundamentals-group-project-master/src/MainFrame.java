import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainFrame extends JFrame {
  
  AdminAccount adminMain;
  LoginScreen loginScreen;
  MainScreen mainScreen;
  VOLManager volManager;
  AccountCreation makeAccount;
  EditProfile editProfile;
  VOLFrame volMaker;
  VOLViewer volViewer;
  VOLEditor volEditor;
  JPanel cards;
  User user;
  ArrayList<VOL> VOLlist = new ArrayList<VOL>();
  
  Timer timer = null;
  PrintWriter writer = null;
  
  public MainFrame() {
    super("Come Help Me!");
    
    readVOLs();
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }
    catch (ClassNotFoundException e) {
      System.out.println("An error has occured.");
      e.printStackTrace();
    }
    catch (InstantiationException e) {
      System.out.println("An error has occured.");
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      System.out.println("An error has occured.");
      e.printStackTrace();
    }
    catch (UnsupportedLookAndFeelException e) {
      System.out.println("An error has occured.");
      e.printStackTrace();
    }
    
    user = null;
    cards = new JPanel(new CardLayout());
    
    loginScreen = new LoginScreen(this);
    
    ImagePanel loginImage = new ImagePanel("Volunteer-Handshake.jpg");
    loginImage.add(loginScreen);
    loginImage.setLayout(new GridBagLayout());
    
    adminMain = new AdminAccount(this);
    
    ImagePanel adminImage = new ImagePanel("Judge.jpg");
    adminImage.add(adminMain);
    adminImage.setLayout(new GridBagLayout());
    
    volManager = new VOLManager(this);
    mainScreen = new MainScreen(this);
    makeAccount = new AccountCreation(this);
    editProfile = new EditProfile(this);
    volMaker = new VOLFrame(this);
    volViewer = new VOLViewer(this);
    volEditor = new VOLEditor(this);
    
    cards.add(loginImage, "Login");
    cards.add(mainScreen, "Main");
    cards.add(volManager, "Manager");
    cards.add(makeAccount, "CreateAccount");
    cards.add(editProfile, "EditProfile");
    cards.add(volMaker, "MakeVOL");
    cards.add(volViewer, "ViewVOL");
    cards.add(volEditor, "EditVOL");
    cards.add(adminImage, "Admin");
    add(cards);
    
    CardLayout cl = (CardLayout) (cards.getLayout());
    cl.show(cards, "Login");
    
    int delay = 1000;
    TimerHandler timeHandler = new TimerHandler();
    timer = new Timer(delay, timeHandler);
    timer.start();
  }
  
  
  private class ImagePanel extends JPanel
  {
    Image backgroundImage;
    ImagePanel(String imagePath)
    {
      try
      {
        backgroundImage = javax.imageio.ImageIO.read(new File(imagePath));
        
      }
      catch(Exception e)
      {
      
      }
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      int x = (this.getWidth() - backgroundImage.getWidth(null)) / 2;
      int y = (this.getHeight() - backgroundImage.getHeight(null)) / 2;
      try
      {
        g.drawImage(backgroundImage, x, y, null);
      }
      catch(Exception e)
      {
      
      }
    }
  }
  
  
  private class TimerHandler implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (VOLlist.size() != 0) {
        try {
          writer = new PrintWriter("VOLFile.txt", "UTF-8");
        }
        catch (FileNotFoundException e1) {
          System.err.println("Error, VOL File not found.");
        }
        catch (UnsupportedEncodingException e1) {
          System.err.println("Error, Encoding is unsupported");
        }
        
        Calendar currentDate = Calendar.getInstance();
        for (int i = 0; i < VOLlist.size(); i++) {
          // Add currentVOL's data to a file
          
          writeVOL(i);
          if (VOLlist.get(i).getTime().GetYear() < currentDate.get(Calendar.YEAR)) {
            VOLlist.remove(i);
            i--;
            continue;
          }
          if (VOLlist.get(i).getTime().GetYear() > (currentDate.get(Calendar.YEAR))) {
            continue;
          }
          if (VOLlist.get(i).getTime().GetMonth() < currentDate.get(Calendar.MONTH)) {
            VOLlist.remove(i);
            i--;
            continue;
          }
          if (VOLlist.get(i).getTime().GetMonth() > currentDate.get(Calendar.MONTH)) {
            continue;
          }
          if (VOLlist.get(i).getTime().GetDay() < currentDate.get(Calendar.DATE)) {
            VOLlist.remove(i);
            i--;
            continue;
          }
          if (VOLlist.get(i).getTime().GetDay() > currentDate.get(Calendar.DATE)) {
            continue;
          }
          if (VOLlist.get(i).getTime().GetHour() < currentDate.get(Calendar.HOUR_OF_DAY)) {
            VOLlist.remove(i);
            i--;
            continue;
          }
          if (VOLlist.get(i).getTime().GetHour() > currentDate.get(Calendar.HOUR_OF_DAY)) {
            continue;
          }
          if (VOLlist.get(i).getTime().GetMinute() < currentDate.get(Calendar.MINUTE)) {
            VOLlist.remove(i);
            i--;
            continue;
          }
          if (VOLlist.get(i).getTime().GetMinute() > currentDate.get(Calendar.MINUTE)) {
            continue;
          }
        }
        writer.close();
      }
    }
    
  }
  
  private void writeVOL(int i) {
    /*
     * to write to the File: print to the file each individual value on it's own
     * line, in a specific and non-random order then print a special character
     * to denote the end of the VOL
     */
    writer.println(VOLlist.get(i).name);
    writer.println(VOLlist.get(i).creator);
    writer.println(VOLlist.get(i).address);
    writer.println(VOLlist.get(i).zipCode);
    writer.println(VOLlist.get(i).desc);
    writer.println(VOLlist.get(i).getTime().GetDay());
    writer.println(VOLlist.get(i).getTime().GetMonth());
    writer.println(VOLlist.get(i).getTime().GetYear());
    writer.println(VOLlist.get(i).getTime().GetHour());
    writer.println(VOLlist.get(i).getTime().GetMinute());
    
    int numInterests = VOLlist.get(i).getInterests().size();
    writer.println(numInterests);
    
    for (int j = 0; j < numInterests; j++) {
      writer.println(VOLlist.get(i).getInterests().get(j));
    }
    
    int numUsers = VOLlist.get(i).users.size();
    writer.println(numUsers);
    
    for (int j = 0; j < numUsers; j++) {
      writer.println(VOLlist.get(i).users.get(j));
    }
  }
  
  void readVOLs() {
    /*
     * To read from the file: While there is a next line, and it's not a special
     * character meant to denote an end of a VOL, take that line, and take the
     * value, convert it if necessary, and give the value to the vol, if the
     * line is a number(infinite chars, preceded by a special char, read that
     * number of values past this into the correct arraylist)
     */
    Scanner in = null;
    File file = null;
    
    try {
      file = new File("VOLFile.txt");
      in = new Scanner(file);
    }
    catch (FileNotFoundException e) {
      try
      {
        file.createNewFile();
        in = new Scanner(file);
      }
      catch(IOException e1)
      {
        System.err.println("An error has occured. Terminating");
        System.exit(1);
      }
    }
    String line = null;
    
    try {
      while ((line = in.nextLine()) != null) {
        VOL VOLtoAdd = new VOL();
        VOLtoAdd.name = line;
        line = in.nextLine();
        VOLtoAdd.creator = line;
        line = in.nextLine();
        VOLtoAdd.address = line;
        line = in.nextLine();
        VOLtoAdd.zipCode = line;
        line = in.nextLine();
        VOLtoAdd.desc = line;
        line = in.nextLine();
        int day = Integer.parseInt(line);
        line = in.nextLine();
        int month = Integer.parseInt(line);
        line = in.nextLine();
        int year = Integer.parseInt(line);
        line = in.nextLine();
        int hour = Integer.parseInt(line);
        line = in.nextLine();
        int minute = Integer.parseInt(line);
        VOLtoAdd.setTime(day, month, year, hour, minute);
        line = in.nextLine();
        int numInterests = Integer.parseInt(line);
        
        for (int i = 0; i < numInterests; i++) {
          line = in.nextLine();
          VOLtoAdd.getInterests().add(line);
        }
        
        line = in.nextLine();
        int numUsers = Integer.parseInt(line);
        
        for (int i = 0; i < numUsers; i++) {
          line = in.nextLine();
          VOLtoAdd.users.add(line);
        }
        VOLlist.add(VOLtoAdd);
        
      }
    }
    catch (NoSuchElementException e1) {
      in.close();
    }
  }
}