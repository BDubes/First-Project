
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/*
 * AccountCreation
 * 
 * Extends JFrame
 * Creates a GuI frame and its elements to allow users
 * to create a Come Help Me profile
 * Profiles include information such as the user's name, username,
 * password and volunteer related interests
 */
public class AccountCreation extends JPanel implements ItemListener {
  // Text fields used to except user input
  private final JTextField firstNameField;
  private final JTextField lastNameField;
  private final JTextField usernameField;
  private final JTextField passwordField;
  private final JTextField orgField;
  // private final JTextField organizationNameField;
  // Labels instructing the user what to input in the text fields
  private final JLabel firstNameLabel;
  private final JLabel lastNameLabel;
  private final JLabel usernameLabel;
  private final JLabel passwordLabel;
  private final JLabel orgLabel;
  // private final JLabel organizationNameLabel;
  // Radio buttons; the user can select if they are an individual volunteer
  // or represent an organization
  private final JRadioButton volunteerButton;
  private final JRadioButton organizationButton;
  // Button group containing the user type radio buttons
  private final ButtonGroup userType = new ButtonGroup();
  
  // Panels containing the various GUI elements
  // For example, the firstNamePanel contains the firstNameLabel
  // and corresponding text field
  private final JPanel firstNamePanel = new JPanel();
  private final JPanel lastNamePanel = new JPanel();
  private final JPanel userTypePanel = new JPanel();
  private final JPanel usernamePanel = new JPanel();
  private final JPanel passwordPanel = new JPanel();
  private final JPanel orgPanel = new JPanel();
  private final JPanel emptyPanel = new JPanel();
  // private final JPanel organizationNamePanel = new JPanel();
  // Button the user will press when they have completed
  // creating their account
  private final JButton signUpButton;
  private final JButton returnButton;
  private MainFrame mainFrame;
  
  private String typeOfUser = null;
  
  /*
   * AccountCreation
   * 
   * Class constructor Adds the various GUI elements such as text fields and
   * radio buttons to the frame
   */
  public AccountCreation(MainFrame mainFrame) {
    // super("Create An Account");
    
    this.mainFrame = mainFrame;
    setLayout(new GridLayout(7, 1));
    
    orgLabel = new JLabel("Organization Name");
    orgPanel.add(orgLabel);
    
    orgField = new JTextField(10);
    orgPanel.add(orgField);
    
    firstNameLabel = new JLabel("First Name");
    firstNamePanel.add(firstNameLabel);
    
    firstNameField = new JTextField(10);
    firstNamePanel.add(firstNameField);
    
    lastNameLabel = new JLabel("Last Name");
    lastNamePanel.add(lastNameLabel);
    
    lastNameField = new JTextField(10);
    lastNamePanel.add(lastNameField);
    
    volunteerButton = new JRadioButton("Individual");
    userTypePanel.add(volunteerButton);
    userType.add(volunteerButton);
    
    organizationButton = new JRadioButton("Organization");
    userTypePanel.add(organizationButton);
    userType.add(organizationButton);
    
    usernameLabel = new JLabel("Please select a username");
    usernamePanel.add(usernameLabel);
    
    usernameField = new JTextField(15);
    usernamePanel.add(usernameField);
    
    passwordLabel = new JLabel("Please choose a password");
    passwordPanel.add(passwordLabel);
    
    passwordField = new JTextField(15);
    passwordPanel.add(passwordField);
    
    signUpButton = new JButton("Sign Up");
    
    returnButton = new JButton("Return to Login");
    
    organizationButton.addActionListener(new Item());
    volunteerButton.addActionListener(new Item());
    ActionHandler handler = new ActionHandler();
    signUpButton.addActionListener(handler);
    returnButton.addActionListener(handler);
    
    add(firstNamePanel);
    add(lastNamePanel);
    add(userTypePanel);
    add(usernamePanel);
    add(passwordPanel);
    add(signUpButton);
    add(returnButton);
    
  }
  
  private class Item implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO Auo-generated method stub
      if (e.getSource() == organizationButton) {
        removeAll();
        add(orgPanel);
        add(userTypePanel);
        add(usernamePanel);
        add(passwordPanel);
        add(emptyPanel);
        add(signUpButton);
        add(returnButton);
        
        validate();
        repaint();
      }
      if (e.getSource() == volunteerButton) {
        removeAll();
        add(firstNamePanel);
        add(lastNamePanel);
        add(userTypePanel);
        add(usernamePanel);
        add(passwordPanel);
        add(signUpButton);
        add(returnButton);
        
        validate();
        repaint();
      }
    }
    
  }
  
  private class ActionHandler implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent event) {
      if (event.getSource() == returnButton) {
        
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "Login");
      }
      
      if (event.getSource() == signUpButton) {
        String firstName;
        String lastName;
        if (volunteerButton.isSelected()) {
          firstName = firstNameField.getText();
          lastName = lastNameField.getText();
        } else {
          firstName = orgField.getText();
          lastName = " ";
        }
        
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        String typeOfUser = null;
        if (volunteerButton.isSelected()) {
          typeOfUser = "Individual";
        }
        if (organizationButton.isSelected()) {
          typeOfUser = "Organization";
        }
        
        if (firstName.equals("")) {
          JOptionPane.showMessageDialog(signUpButton, "Must Enter First Name.");
        } else {
          if (lastName.equals("")) {
            JOptionPane.showMessageDialog(signUpButton, "Must Enter Last Name.");
          } else {
            if (username.equals("")) {
              JOptionPane.showMessageDialog(signUpButton, "Must Enter Username.");
            } else {
              if (password.equals("")) {
                
                JOptionPane.showMessageDialog(signUpButton, "Must Enter Password.");
              } else {
                if (typeOfUser == null) {
                  JOptionPane.showMessageDialog(signUpButton,
                      "Must Select Individual or Organization.");
                } else {
                  String All = ("\n" + username + "!199!" + typeOfUser + "!199!" + password
                      + "!199!" + firstName + "!199!" + lastName + "!199!");
                  try {
                    // Specify the file name and path here
                    File file = new File("UserDatabase.txt");
                    
                    /*
                     * This logic is to create the file if the file is not
                     * already present
                     */
                    if (!file.exists()) {
                      file.createNewFile();
                    }
                    
                    // Here true is to append the content to file
                    FileWriter fw = new FileWriter(file, true);
                    // BufferedWriter writer give better performance
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(All);
                    // Closing BufferedWriter Stream
                    bw.close();
                    
                    System.out.println("Data successfully appended at the end of file");
                    CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
                    cl.show(mainFrame.cards, "Login");
                    
                  }
                  catch (IOException ioe) {
                    System.out.println("Exception occurred:");
                    ioe.printStackTrace();
                  }
                }
              }
            }
          }
        }
        
      }
    }
  }
  
  @Override
  public void itemStateChanged(ItemEvent e) {
    // TODO Auto-generated method stub
    
  }
  
}
