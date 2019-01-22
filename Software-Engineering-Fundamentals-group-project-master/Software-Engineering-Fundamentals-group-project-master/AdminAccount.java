import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AdminAccount extends JPanel {
  
  private MainFrame mainFrame;
  private JButton banUser;
  private JButton banVol;
  private JButton logOut;
  private JComboBox<String> users = new JComboBox<String>();
  private JComboBox<String> vols = new JComboBox<String>();
  private JComboBox<String> volsToBe = new JComboBox<String>();
  private FileInputStream fstream;
  private DataInputStream in;
  private BufferedReader br;
  private FileInputStream fstream2;
  private DataInputStream in2;
  private BufferedReader br2;
  private JPanel panel = new JPanel();
  private JPanel OutPanel = new JPanel();
  
  public AdminAccount(MainFrame mainFrame) {
    setLayout(new BorderLayout());
    OutPanel.setLayout(new FlowLayout());
    OutPanel.setOpaque(false);
    panel.setLayout(new GridLayout(2, 2));
    panel.setOpaque(false);
    
    this.mainFrame = mainFrame;
    setSize(265, 130);
    
    users.addItem("Users");
    try {
      // Open the file that is the first
      // command line parameter
      fstream = new FileInputStream("UserDatabase.txt");
      // Get the object of DataInputStream
      in = new DataInputStream(fstream);
      br = new BufferedReader(new InputStreamReader(in));
      String strLine;
      // Read File Line By Line
      while ((strLine = br.readLine()) != null) {
        
        String[] first = strLine.split("!199!");
        if (first.length > 0) {
          String name = first[0];
          users.addItem(name);
        }
      }
      // Close the input stream
      in.close();
    }
    catch (Exception e) {// Catch exception if any
      System.err.println("Error2: " + e.getMessage());
    }
    
    vols.addItem("VOLS");
    try {
      // Open the file that is the first
      // command line parameter
      fstream2 = new FileInputStream("VOLFile.txt");
      // Get the object of DataInputStream
      in2 = new DataInputStream(fstream2);
      br2 = new BufferedReader(new InputStreamReader(in2));
      String strLine2;
      // Read File Line By Line
      while ((strLine2 = br2.readLine()) != null) {
        String[] first2 = strLine2.split("!199!");
        if (first2.length > 0) {
          String name2 = first2[0];
          vols.addItem(name2);
        }
      }
      // make the spaces into !&!2
      // Close the input stream
      in2.close();
    }
    catch (FileNotFoundException e) {// Catch exception if any
      System.err.println("dddf");
    }
    catch (IOException e) {
      System.out.println("ddfsad");
    }
    
    logOut = new JButton("Log-Out");
    banUser = new JButton("BAN USER!");
    banVol = new JButton("BAN VOL!");
    
    OutPanel.add(logOut);
    panel.add(banUser);
    panel.add(users);
    panel.add(banVol);
    panel.add(vols);
    
    add(panel);
    add(OutPanel, BorderLayout.NORTH);
    setOpaque(false);
    TextFieldHandler handler = new TextFieldHandler();
    banUser.addActionListener(handler);
    banVol.addActionListener(handler);
    logOut.addActionListener(handler);
  }
  
  private void setLayout(String south) {
    // TODO Auto-generated method stub
    
  }
  
  private class TextFieldHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
      if (event.getSource() == logOut) {
        
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "Login");
      }
      if (event.getSource() == banUser) {
        
        if (users.getSelectedItem() == "Users") {
          // Do Nothing
          
        } else {
          System.out.println("ddd");
          String line;
          String removeUser = (String) users.getSelectedItem();
          System.out.println(removeUser);
          try {
            System.out.println("qqq");
            fstream = new FileInputStream("UserDatabase.txt");
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
              System.out.println("bbb");
              String[] first = line.split("!199!");
              String name = first[0];
              
              if (name.equals(removeUser)) {
                System.out.println("remove");
                // Specify the file name and path here
                File file = new File("BanUserDatabase.txt");
                if (!file.exists()) {
                  file.createNewFile();
                }
                FileWriter fileWritter = new FileWriter(file.getName(), true);
                BufferedWriter bw = new BufferedWriter(fileWritter);
                String banMessage = JOptionPane.showInputDialog("Reason for Banning.", null);
                
                bw.write(line + "!199!" + banMessage + "\n");
                bw.close();
                System.out.println("Done");
                System.out.println("Data successfully appended at the end of file");
              }
            }
          }
          catch (IOException e) {
            System.out.println("Issue removing user");
            e.printStackTrace();
          }
        }
        
      }
      
      if (event.getSource() == banVol)
      
      {
        if (vols.getSelectedItem() == "VOLS") {
          // Do Nothing
        } else {
          String banVol = (String) users.getSelectedItem();
          if (mainFrame.VOLlist.contains(banVol)) {
            mainFrame.VOLlist.remove(banVol);
          }
        }
      }
    }
  }
}

// class Image extends JPanel {
//
// private Image img;
//
// public ImagePanel(String img) {
// this(new ImageIcon(img).getImage());
// }
//
// public ImagePanel(Image img) {
// this.img = img;
// Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
// setPreferredSize(size);
// setMinimumSize(size);
// setMaximumSize(size);
// setSize(size);
// setLayout(null);
// }
//
// @Override
// public void paintComponent(Graphics g) {
// g.drawImage(img, 0, 0, null);
// }
// }