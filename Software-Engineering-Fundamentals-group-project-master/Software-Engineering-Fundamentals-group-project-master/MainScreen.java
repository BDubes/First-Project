import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainScreen extends JPanel {
  
  private JPanel upperBar, centralUI;
  private JButton signOutButton, profileButton, makeVOLButton, searchButton, editVOLButton,
      viewVOLButton;
  public VOLManager volManager;
  public AccountCreation editProfile;
  private JPanel east = new JPanel();
  private JPanel west = new JPanel();
  private JPanel south = new JPanel();
  private JPanel center = new JPanel();
  private JPanel empty = new JPanel();
  private MainFrame mainFrame;
  private JPanel Blank = new JPanel();
  
  public MainScreen(MainFrame mainFrame) {
    
    this.mainFrame = mainFrame;
    
    setLayout(new BorderLayout(100, 0));
    
    upperBar = new JPanel();
    centralUI = new JPanel();
    signOutButton = new JButton("Sign Out");
    profileButton = new JButton("Edit Profile");
    // Buttons shouldn't reach the end of the screen
    east.setOpaque(false);
    west.setOpaque(false);
    south.setOpaque(false);
    add(east, BorderLayout.EAST);
    add(west, BorderLayout.WEST);
    add(south, BorderLayout.SOUTH);
    
    searchButton = new JButton("Search For Volunteer Opportunities!");
    volManager = mainFrame.volManager;
    editProfile = new AccountCreation(mainFrame);
    
    upperBar.setLayout(new GridLayout(1, 3));
    
    centralUI.setLayout(new GridLayout(3, 1));
    
    upperBar.add(signOutButton);
    
    Blank.setOpaque(false);
    upperBar.add(Blank);
    // Chris Dreiser
    upperBar.add(profileButton);
    upperBar.setOpaque(false);
    
    center.setOpaque(false);
    centralUI.setOpaque(false);
    centralUI.add(center);// This is where the edit/view button should
    // be going
    
    add(upperBar, BorderLayout.NORTH);
    add(centralUI, BorderLayout.CENTER);
    
    ButtonHandler handler = new ButtonHandler();
    signOutButton.addActionListener(handler);
    profileButton.addActionListener(handler);
    setOpaque(false);
  }
  
  private class ButtonHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      Object buttonClicked = e.getSource();
      if (buttonClicked == signOutButton) {
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "Login");
        centralUI.removeAll();
      }
      if (buttonClicked == profileButton) {
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "EditProfile");
      }
      if (buttonClicked == searchButton) {
        mainFrame.volManager.updateListings(mainFrame.VOLlist);
        mainFrame.volManager.DisplayListings();
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "Manager");
      }
      if (buttonClicked == makeVOLButton) {
        volManager.CreateVOL();
      }
      if (buttonClicked == editVOLButton) {
        mainFrame.volEditor.updateListings(mainFrame.VOLlist);
        mainFrame.volEditor.DisplayListings();
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "EditVOL");
      }
      if (buttonClicked == viewVOLButton) {
        mainFrame.volViewer.updateListings(mainFrame.VOLlist);
        mainFrame.volViewer.DisplayListings();
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "ViewVOL");
      }
    }
  }
  
  public void setupMainScreen() {
    ButtonHandler handler = new ButtonHandler();
    centralUI.removeAll();
    centralUI.setOpaque(false);
    centralUI.add(searchButton);
    searchButton.addActionListener(handler);
    
    if (mainFrame.user.getUserType().equals("Organization")) {
      editVOLButton = new JButton("Edit your Listings");
      centralUI.add(editVOLButton);
      editVOLButton.addActionListener(handler);
      makeVOLButton = new JButton("Request Help!");
      centralUI.add(makeVOLButton);
      makeVOLButton.addActionListener(handler);
      // add button to view VOLs that they created
    }
    if (mainFrame.user.getUserType().equals("Individual")) {
      viewVOLButton = new JButton("View your Volunteering opportunities");
      Blank.setOpaque(false);
      centralUI.add(Blank);
      centralUI.add(viewVOLButton);
      viewVOLButton.addActionListener(handler);
    }
    
  }
}