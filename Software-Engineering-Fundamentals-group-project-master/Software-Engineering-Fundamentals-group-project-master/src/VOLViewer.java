import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class VOLViewer extends JPanel {
  public ArrayList<VOL> VOLlist = new ArrayList<VOL>();
  
  private JButton backButton;
  private String[] searchTypes = { "Interest", "Location" };
  private JComboBox searchTypeBox;
  private JPanel scrollBox;
  private JPanel displayPanel;
  private JScrollPane searchBox;
  private JPanel options;
  public JButton searchButton;
  private JPanel searchTypePanel;
  private JTextField zipSearch;
  private JCheckBox animals;
  
  private JCheckBox elderly;
  
  private JCheckBox food;
  
  private JCheckBox environment;
  
  private MainFrame mainFrame;
  Timer timer = null;
  
  public VOLViewer(MainFrame mainFrame) {
    
    this.mainFrame = mainFrame;
    
    FlowLayout optionLayout = new FlowLayout();
    options = new JPanel();
    scrollBox = new JPanel();
    setLayout(new BorderLayout(5, 5));
    scrollBox.setLayout(new BoxLayout(scrollBox, BoxLayout.PAGE_AXIS));
    options.setLayout(optionLayout);
    
    backButton = new JButton("BACK");
    searchButton = new JButton("REFRESH");
    searchTypeBox = new JComboBox(searchTypes);
    searchTypePanel = new JPanel();
    searchTypePanel.setLayout(optionLayout);
    animals = new JCheckBox("Animals");
    elderly = new JCheckBox("Elderly");
    food = new JCheckBox("Food");
    environment = new JCheckBox("Environmental");
    searchTypePanel.add(animals);
    searchTypePanel.add(elderly);
    searchTypePanel.add(food);
    searchTypePanel.add(environment);
    
    displayPanel = new JPanel();
    displayPanel.setLayout(new GridLayout(1, 1));
    displayPanel.setBackground(Color.GRAY);
    searchBox = new JScrollPane(scrollBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    searchBox.setPreferredSize(new Dimension(200, 500));
    
    options.add(backButton);
    options.add(searchButton);
    options.add(searchTypeBox);
    options.add(searchTypePanel);
    add(options, BorderLayout.PAGE_START);
    add(searchBox, BorderLayout.LINE_START);
    add(displayPanel, BorderLayout.CENTER);
    
    ButtonHandler handler = new ButtonHandler();
    searchButton.addActionListener(handler);
    backButton.addActionListener(handler);
    SearchTypeHandler stHandle = new SearchTypeHandler();
    searchTypeBox.addActionListener(stHandle);
    
    int delay = 360000; // 360,000 milliseconds, or one hour
    TimerHandler timeHandler = new TimerHandler();
    timer = new Timer(delay, timeHandler);
    timer.start();
  }
  
  private class SearchTypeHandler implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (searchTypeBox.getSelectedItem().equals("Interest")) {
        searchTypePanel.removeAll();
        
        searchTypePanel.add(animals);
        searchTypePanel.add(elderly);
        searchTypePanel.add(food);
        searchTypePanel.add(environment);
        
      }
      if (searchTypeBox.getSelectedItem().equals("Location")) {
        searchTypePanel.removeAll();
        zipSearch = new JTextField("Zipcode to Search", 10);
        TextHandler handler = new TextHandler();
        searchTypePanel.add(zipSearch);
        zipSearch.addMouseListener(handler);
        zipSearch.addKeyListener(handler);
      }
      validate();
    }
    
  }
  
  private class ButtonHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
      if (event.getSource() == searchButton) {
        RemoveListings();
        
        displayPanel.removeAll();
        
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "Main");
        cl.show(mainFrame.cards, "ViewVOL");
        
        DisplayListings();
      }
      if (event.getSource() == backButton) {
        RemoveListings();
        displayPanel.removeAll();
        
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "Main");
      }
    }
  }
  
  private class TextHandler implements MouseListener, KeyListener {
    
    @Override
    public void mouseClicked(MouseEvent e) {
      JTextField clickedField = new JTextField();
      clickedField = (JTextField) (e.getSource());
      clickedField.setText("");
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void keyPressed(KeyEvent arg0) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void keyReleased(KeyEvent arg0) {
      // TODO Auto-generated method stub
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
      switch (e.getKeyChar()) {
        case '0':
          break;
        case '1':
          break;
        case '2':
          break;
        case '3':
          break;
        case '4':
          break;
        case '5':
          break;
        case '6':
          break;
        case '7':
          break;
        case '8':
          break;
        case '9':
          break;
        default:
          e.consume();
      }
      
    }
    
  }
  
  private class TimerHandler implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
      if (VOLlist.size() != 0) {
        Calendar currentDate = Calendar.getInstance();
        for (int i = 0; i < VOLlist.size(); i++) {
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
      } // temp if statement
    }
    
  }
  
  public void CreateVOL() {
    VOL newListing = new VOL();
    VOLlist.add(newListing);
    
    mainFrame.volMaker.setVOL(newListing);
    
    CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
    cl.show(mainFrame.cards, "MakeVOL");
  }
  
  public void RemoveListings() {
    for (int i = 0; i < VOLlist.size(); i++) {
      scrollBox.removeAll();
      ;
    }
  }
  
  public void DisplayListings() {
    
    changeListingType("view");
    
    for (int i = 0; i < VOLlist.size(); i++) {
      VOL currentVOL = VOLlist.get(i);
      
      boolean animalsSelected = animals.isSelected();
      boolean elderlySelected = elderly.isSelected();
      boolean foodSelected = food.isSelected();
      boolean environmentSelected = environment.isSelected();
      boolean notInterested = false;
      
      if (searchTypeBox.getSelectedItem() == "Interest") {
        scrollBox.add(currentVOL.getShortListing(displayPanel));
        for (int j = 0; j < currentVOL.getInterests().size(); j++) {
          String currentInterest = currentVOL.getInterests().get(j);
          notInterested = false;
          if (animalsSelected && !(currentInterest.equals("Animals"))) {
            notInterested = true;
          }
          if (elderlySelected && !(currentInterest.equals("Elderly"))) {
            notInterested = true;
          }
          if (foodSelected && !(currentInterest.equals("Food"))) {
            notInterested = true;
          }
          if (environmentSelected && !(currentInterest.equals("Environment"))) {
            notInterested = true;
          }
          //
          if (currentInterest.equals("Animals")) {
            animalsSelected = false;
          }
          if (currentInterest.equals("Elderly")) {
            elderlySelected = false;
          }
          if (currentInterest.equals("Food")) {
            foodSelected = false;
          }
          if (currentInterest.equals("Environment")) {
            environmentSelected = false;
          }
        }
      }
      if (searchTypeBox.getSelectedItem() == "Location") {
        for (int j = 0; j < currentVOL.users.size(); j++) {
          if (currentVOL.getZip().equals(zipSearch.getText())
              && currentVOL.users.get(j) == mainFrame.user.getUserName()) {
            scrollBox.add(currentVOL.getShortListing(displayPanel));
            validate();
          }
        }
      }
      
      if (!currentVOL.users.contains(mainFrame.user.getUserName()) || notInterested) {
        scrollBox.remove(currentVOL.getShortListing(displayPanel));
        validate();
      }
    }
  }
  
  public void changeListingType(String type) {
    for (int i = 0; i < VOLlist.size(); i++) {
      VOL currentVOL = VOLlist.get(i);
      MakeListing(currentVOL);
    }
  }
  
  public void updateListings(ArrayList<VOL> listings) {
    VOLlist = listings;
    
    for (int i = 0; i < VOLlist.size(); i++) {
      MakeListing(VOLlist.get(i));
      VOLlist.get(i).getShortListing(displayPanel);
    }
  }
  
  public void MakeListing(VOL currentVOL) {
    currentVOL.listing = new JPanel();
    currentVOL.listing.setLayout(new BoxLayout(currentVOL.listing, BoxLayout.PAGE_AXIS));
    
    currentVOL.joinButton = new JButton("Quit");
    currentVOL.joinButton.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        currentVOL.users.remove(mainFrame.user.getUserName());
        JOptionPane.showMessageDialog(currentVOL.getListing(), "You just quit " + currentVOL.name);
        RemoveListings();
        displayPanel.removeAll();
        
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "Main");
        cl.show(mainFrame.cards, "ViewVOL");
        
        DisplayListings();
      }
    });
    
    currentVOL.nameField = new JTextField(currentVOL.name);
    currentVOL.dateField = new JTextField(currentVOL.eventTime.toString());
    currentVOL.interestField = new JTextField(
        "This appeals to people interested in: " + currentVOL.getInterests().toString());
    currentVOL.addressField = new JTextField(currentVOL.address);
    currentVOL.zipCodeField = new JTextField(currentVOL.zipCode);
    currentVOL.descField = new JTextArea(currentVOL.desc);
    currentVOL.descField.setToolTipText("Event Description");
    currentVOL.descField.setColumns(30);
    currentVOL.descField.setLineWrap(true);
    
    currentVOL.nameField.setEditable(false);
    currentVOL.dateField.setEditable(false);
    currentVOL.interestField.setEditable(false);
    currentVOL.addressField.setEditable(false);
    currentVOL.zipCodeField.setEditable(false);
    currentVOL.descField.setEditable(false);
    
    JTextField creatorField = new JTextField();
    creatorField.setText("The organization, " + currentVOL.creator
        + ", is calling for volunteers!\n" + currentVOL.users.size() + " have already joined.");
    creatorField.setEditable(false);
    currentVOL.listing.add(creatorField);
    currentVOL.listing.add(currentVOL.nameField);
    currentVOL.listing.add(currentVOL.dateField);
    currentVOL.listing.add(currentVOL.interestField);
    currentVOL.listing.add(currentVOL.addressField);
    currentVOL.listing.add(currentVOL.zipCodeField);
    currentVOL.listing.add(currentVOL.descField);
    currentVOL.listing.add(currentVOL.joinButton);
    currentVOL.listing.setBackground(Color.gray);
    currentVOL.shortListing = new JButton(currentVOL.name);
  }
}