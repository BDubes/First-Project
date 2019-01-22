import java.awt.*;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
  private final JButton confirmButton;
  private JPanel newPass = new JPanel();
  private JPanel newPassConfirm = new JPanel();
  private JPanel buttonPanel = new JPanel();
  private JButton returnButton;
  private MainFrame mainFrame;
  
  public EditProfile(MainFrame mainFrame) {
    this.mainFrame = mainFrame;

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
        if(!passwordField1.getText().equals(passwordField2.getText()))
        {
          JOptionPane.showMessageDialog(confirmButton, "Passwords do not match.");
          return;
        }
        String username = mainFrame.user.getUserName();
        try
        {
          Path userPath = Paths.get("UserDatabase.txt");
          ArrayList<String> fileContent =
                  new ArrayList<>(Files.readAllLines(userPath, StandardCharsets.UTF_8));
          for (int i = 0; i < fileContent.size(); i++) {
            if(fileContent.get(i).length() > 0)
            {
              String usernameConfirm = fileContent.get(i).substring(0, username.length());
              if(usernameConfirm.equals(username))
              {
                fileContent.set(i, mainFrame.user.getUserName() + "!199!"
                        + mainFrame.user.getUserType() + "!199!" + passwordField1.getText()
                        + "!199!" + mainFrame.user.getName() + "!199!");
                CardLayout c11 = (CardLayout) mainFrame.cards.getLayout();
                c11.show(mainFrame.cards, "Main");
                break;
              }
            }
          }
          Files.write(userPath, fileContent, StandardCharsets.UTF_8);
        }
        catch(IOException e)
        {
          System.err.println("Does this work");
        }
      }
    }
  }
}

