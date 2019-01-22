import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;

/*
 * LoginScreen
 * 
 * Extends JPanel
 * Creates a GUI Panel that allows user's to login to the program
 */
public class LoginScreen extends JPanel {
  // Labels instructing the user what to put in the text fields
  private final JLabel usernameLabel;
  private final JLabel passwordLabel;
  // Text fields used to except user input
  private final JTextField usernameField;
  private final JPasswordField passwordField;
  // Panels containing the various GUI elements
  // For example, the usernamePanel contains the usernameLabel
  // and corresponding text field
  private final JPanel usernamePanel = new JPanel();
  private final JPanel passwordPanel = new JPanel();
  private final JPanel buttonPanel = new JPanel();
  // logInButton will take the user to the program's main screen
  private final JButton logInButton;
  // createProfileButton will take the user to the AccountCreation screen
  private final JButton createProfileButton;
  private MainFrame mainFrame;
  
  private FileInputStream fstream;
  private DataInputStream in;
  private BufferedReader br;
  
  private static String oldUsername;
  private static String firstName;
  private static String lastName;
  private static String organization;
  
  public LoginScreen(MainFrame mainFrame) {
    
    this.mainFrame = mainFrame;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setSize(265, 130);
  
    ImageIcon image = new ImageIcon("comehelpmeresized.png");
    JLabel label = new JLabel("", image, JLabel.CENTER);
    add( label, BorderLayout.CENTER );
    setOpaque(false);
    usernameLabel = new JLabel("Username:");
    usernamePanel.add(usernameLabel);
    
    usernameField = new JTextField(15);
    usernamePanel.add(usernameField);
    
    add(usernamePanel);
    
    passwordLabel = new JLabel("Password:");
    passwordPanel.add(passwordLabel);
    
    passwordField = new JPasswordField(15);
    passwordPanel.add(passwordField);
    
    add(passwordPanel);
    
    logInButton = new JButton("Log-in");
    buttonPanel.add(logInButton);
    
    createProfileButton = new JButton("Create profile");
    buttonPanel.add(createProfileButton);
    
    add(buttonPanel);
    
    TextFieldHandler handler = new TextFieldHandler();
    usernameField.addActionListener(handler);
    passwordField.addActionListener(handler);
    logInButton.addActionListener(handler);
    createProfileButton.addActionListener(handler);
    
  }
  
  public static String userName() {
    return oldUsername;
  }
  
  public static String firstName() {
    return firstName;
  }
  
  public static String organization() {
    return lastName;
  }
  
  public static String lastName() {
    return organization;
  }
  
  private class TextFieldHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
      String userAccess = null;
      String passAccess = null;
      String usernameString = usernameField.getText();
      String passwordString = new String(passwordField.getPassword());
      
      if (event.getSource() == logInButton) {
        if (usernameString.equals("CaveMan") && passwordString.equals("SpaghettiMan")) {
          CardLayout cl1 = (CardLayout) (mainFrame.cards.getLayout());
          cl1.show(mainFrame.cards, "Admin");
        } else {
          try {
            fstream = new FileInputStream("UserDatabase.txt");
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
              
              String[] first = line.split("!199!");
              if (first.length > 0) {
                String username = first[0];
                String password = first[2];
                
                if (username.equals(usernameString) && password.equals(passwordString)) {
                  // Here is where the magic happens
                  oldUsername = username;
                  userName();
                  
                  firstName = first[2];
                  firstName();
                  
                  lastName = first[3];
                  lastName();
                  
                  organization = first[4];
                  organization();
                  System.out.println(organization);
                  
                  if (first[1].equals("Individual")) {
                    
                    mainFrame.user = new IndividualUser(first[0], first[2], first[3], first[4]);
                    mainFrame.mainScreen.setupMainScreen();
                  } else {
                    
                    mainFrame.user = new OrganizationUser(first[0], first[2], first[3]);
                    mainFrame.mainScreen.setupMainScreen();
                  }
                  
                  // mainFrame.user = username;
                  passAccess = password;
                }
              }
            }
          }
          catch (IOException e) {
            System.out.println("Issue removing user");
            e.printStackTrace();
          }
          
          if (userAccess == null && passAccess == null) {
            JOptionPane.showMessageDialog(logInButton, "Invaild Login");
            
          } else {
            CardLayout cl1 = (CardLayout) (mainFrame.cards.getLayout());
            cl1.show(mainFrame.cards, "Main");
            mainFrame.mainScreen.setupMainScreen();
          }
        }
        
      } else if (event.getSource() == createProfileButton) {
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "CreateAccount");
      }
    }
  }
}