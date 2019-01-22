
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class EditProfile extends JPanel {
  private final JLabel passwordLabel;
  private final JPasswordField passwordField1;
  private final JLabel confirmPasswordLabel;
  private final JPasswordField passwordField2;
  // private final JTextField textField3;
  // private final JLabel usernameLabel;
  private final JButton confirmButton;
  private static String oldUsername;
  private static String firstName;
  private static String lastName;
  private static String organization;
  
  private User user;
  private FileInputStream fstream;
  private DataInputStream in;
  private BufferedReader br;
  private JPanel newPass = new JPanel();
  private JPanel newPassConfirm = new JPanel();
  private JPanel buttonPanel = new JPanel();
  private JButton returnButton;
  private MainFrame mainFrame;
  
  public EditProfile(User user, MainFrame mainFrame) {
    // super("Edit Profile");
    // setLayout();
    this.user = user;
    this.mainFrame = mainFrame;
    /*
     * usernameLabel = new JLabel("Enter new Username"); add(usernameLabel);
     * textField3 = new JTextField(25); add(textField3);
     */
    setOpaque(false);
    JPanel mainPanel = new JPanel(new GridLayout(3, 1));
    mainPanel.setOpaque(false);
    JPanel newPasswordPanel = new JPanel();
    newPasswordPanel.setOpaque(false);
    passwordLabel = new JLabel("Enter new password");
    newPass.add(passwordLabel);
    newPasswordPanel.add(newPass);
    passwordField1 = new JPasswordField(15);
    newPasswordPanel.add(passwordField1);
    
    mainPanel.add(newPasswordPanel);
    
    JPanel confirmPasswordPanel = new JPanel();
    confirmPasswordPanel.setOpaque(false);
    confirmPasswordLabel = new JLabel("Confirm new password");
    newPassConfirm.add(confirmPasswordLabel);
    confirmPasswordPanel.add(newPassConfirm);
    passwordField2 = new JPasswordField(15);
    confirmPasswordPanel.add(passwordField2);
    
    mainPanel.add(confirmPasswordPanel);
    
    confirmButton = new JButton("Confirm changes");
    returnButton = new JButton("Return");
    buttonPanel.setLayout(new GridLayout(1, 2));
    buttonPanel.add(confirmButton);
    buttonPanel.add(returnButton);
    buttonPanel.setOpaque(false);
    mainPanel.add(buttonPanel);
    
    add(mainPanel);
    
    EditProfileHandler handler = new EditProfileHandler();
    confirmButton.addActionListener(handler);
    returnButton.addActionListener(handler);
  }
  
  private class EditProfileHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
      if (event.getSource() == returnButton) {
        CardLayout cl1 = (CardLayout) mainFrame.cards.getLayout();
        cl1.show(mainFrame.cards, "Main");
        mainFrame.mainScreen.setupMainScreen();
      }
      
      String string = "";
      if (event.getSource() == confirmButton) {
        try {
          fstream = new FileInputStream("UserDatabase.txt");
          // Get the object of DataInputStream
          in = new DataInputStream(fstream);
          br = new BufferedReader(new InputStreamReader(in));
          String line;
          String lineToRemove = null;
          while ((line = br.readLine()) != null) {
            
            String[] first = line.split("!199!");
            if (first.length > 0) {
              oldUsername = LoginScreen.userName();
              firstName = LoginScreen.firstName();
              lastName = LoginScreen.lastName();
              organization = LoginScreen.organization();
              if (oldUsername.equals(first[0])) {
                lineToRemove = line;
              }
              
              File inputFile = new File("UserDatabase.txt");
              File tempFile = new File("Newnames.txt");
              
              BufferedReader reader = new BufferedReader(new FileReader(inputFile));
              BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
              
              String currentLine;
              String trimmedLine;
              
              String newUsername = user.getUserName();
              String newPassword = String.valueOf(passwordField1.getPassword());
              String confirmPassword = String.valueOf(passwordField2.getPassword());
              
              /*
               * if (newUsername.equals("")) {
               * JOptionPane.showMessageDialog(confirmButton,
               * "Must Enter a UserName"); } else
               */if (newPassword.equals("")) {
                JOptionPane.showMessageDialog(confirmButton, "Must Enter a Password");
              } else if (confirmPassword.equals("")) {
                JOptionPane.showMessageDialog(confirmButton, "Must Confirm Password");
              } else if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(confirmButton, "Password must be the Same");
              } else {
                while ((currentLine = reader.readLine()) != null) {
                  trimmedLine = currentLine.trim();
                  if (trimmedLine.equals(lineToRemove))
                    continue;
                  else
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                boolean successful = tempFile.renameTo(inputFile);
                String All;
                if (user.getUserType().equals("Individual")) {
                  All = ("\n" + newUsername + "!199!" + user.getUserType() + "!199!" + newPassword
                      + "!199!" + firstName + "!199!" + lastName + "!199!");
                } else if (user.getUserType().equals("Organization")) {
                  All = ("\n" + newUsername + "!199!" + user.getUserType() + "!199!" + newPassword
                      + "!199!" + user.getOrganizationName() + "!199!");
                } else {
                  All = "";
                }
                try {
                  // Specify the file name and path here
                  File file = new File("Newnames.txt");
                  
                  /*
                   * This logic is to create the file if the file is not already
                   * present
                   */
                  
                  // Here true is to append the content to file
                  FileWriter fw = new FileWriter(file, true);
                  // BufferedWriter writer give better performance
                  BufferedWriter bw = new BufferedWriter(fw);
                  bw.write(All);
                  // Closing BufferedWriter Stream
                  bw.close();
                }
                catch (IOException e) {
                  System.out.println("Issue removing user");
                  e.printStackTrace();
                }
              }
            }
          }
        }
        catch (Exception e) {
          System.out.println("Error editprofile");
        }
      }
    }
  }
}
