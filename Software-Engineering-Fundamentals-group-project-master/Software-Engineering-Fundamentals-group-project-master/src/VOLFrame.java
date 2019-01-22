import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VOLFrame extends JPanel {
  private VOL targetVOL;
  
  private JPanel DatePanel = new JPanel();
  private JPanel InterestPanel = new JPanel();
  private JPanel backPanel = new JPanel();
  
  private final JTextField name;
  private final JTextField day;
  private final JTextField month;
  private final JTextField year;
  private final JTextField hour;
  private final JTextField minute;
  private final JTextArea desc;
  private final JTextField address;
  private final JTextField zipCode;
  private final JLabel fSlash = new JLabel("/");
  private final JLabel fSlash2 = new JLabel("/");
  private final JLabel colon = new JLabel(":");
  private final JLabel interestsLabel = new JLabel("What interests does this event appeal to?");
  private JRadioButton animalButton = new JRadioButton("Animals");
  private JRadioButton elderButton = new JRadioButton("Assisting the Elderly");
  private JRadioButton environmentButton = new JRadioButton("Environment");
  private JRadioButton foodButton = new JRadioButton("Food");
  private JButton submit, backButton;
  private MainFrame mainFrame;
  
  public VOLFrame(MainFrame mainFrame) {
    
    setLayout(new GridLayout(8, 1));
    
    this.mainFrame = mainFrame;
    
    submit = new JButton("Submit");
    backButton = new JButton("Cancel");
    
    backPanel.setLayout(new GridLayout());
    backPanel.add(backButton);
    backPanel.add(new JPanel());
    backPanel.add(new JPanel());
    add(backPanel);
    name = new JTextField("Event Name", 15);
    name.setToolTipText("Event Name");
    add(name);
    
    address = new JTextField("Event Address", 15);
    address.setToolTipText("Event Address");
    add(address);
    
    zipCode = new JTextField("Event Zip Code", 10);
    zipCode.setToolTipText("Event ZipCode");
    add(zipCode);
    
    day = new JTextField("DD", 2);
    day.setToolTipText("Day the Event will occur");
    DatePanel.add(day);
    DatePanel.add(fSlash);
    
    month = new JTextField("MM", 2);
    month.setToolTipText("Month the Event will occur");
    DatePanel.add(month);
    DatePanel.add(fSlash2);
    
    year = new JTextField("YYYY", 4);
    year.setToolTipText("Year the Event will occur");
    DatePanel.add(year);
    
    hour = new JTextField("HH", 2);
    hour.setToolTipText("Hour the Event will occur");
    DatePanel.add(hour);
    DatePanel.add(colon);
    
    minute = new JTextField("MM", 2);
    minute.setToolTipText("Minute the Event will occur");
    DatePanel.add(minute);
    
    add(DatePanel);
    
    desc = new JTextArea("Event Description");
    desc.setToolTipText("Event Description");
    desc.setColumns(30);
    desc.setLineWrap(true);
    add(desc);
    
    InterestPanel.add(interestsLabel);
    InterestPanel.add(animalButton);
    InterestPanel.add(elderButton);
    InterestPanel.add(foodButton);
    InterestPanel.add(environmentButton);
    add(InterestPanel);
    
    add(submit);
    
    ButtonHandler bHandler = new ButtonHandler();
    TextHandler tfHandler = new TextHandler();
    submit.addActionListener(bHandler);
    backButton.addActionListener(bHandler);
    day.addMouseListener(tfHandler);
    month.addMouseListener(tfHandler);
    year.addMouseListener(tfHandler);
    hour.addMouseListener(tfHandler);
    minute.addMouseListener(tfHandler);
    
    name.addMouseListener(tfHandler);
    address.addMouseListener(tfHandler);
    zipCode.addMouseListener(tfHandler);
    desc.addMouseListener(tfHandler);
  }
  
  private class ButtonHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
      if (event.getSource() == submit) {
        if (SubmitData()) {
          CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
          cl.show(mainFrame.cards, "Main");
        }
      }
      if (event.getSource() == backButton) {
        clearForm();
        mainFrame.VOLlist.remove(targetVOL);
        CardLayout cl = (CardLayout) (mainFrame.cards.getLayout());
        cl.show(mainFrame.cards, "Main");
      }
    }
  }
  
  private class TextHandler implements MouseListener {
    
    @Override
    public void mouseClicked(MouseEvent e) {
      JTextField clickedField = new JTextField();
      if (e.getSource() != desc) {
        clickedField = (JTextField) (e.getSource());
      }
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
    
  }
  
  public void setVOL(VOL vol) {
    targetVOL = vol;
  }
  
  private void clearForm() {
    name.setText("Event Name");
    address.setText("Event Address");
    zipCode.setText("Event Zipcode");
    desc.setText("Event Description");
    day.setText("DD");
    month.setText("MM");
    year.setText("YYYY");
    hour.setText("HH");
    minute.setText("MM");
    
    animalButton.setSelected(false);
    environmentButton.setSelected(false);
    elderButton.setSelected(false);
    foodButton.setSelected(false);
  }
  
  public boolean SubmitData() {
    ArrayList<String> interestList = new ArrayList<String>();
    
    if (day.getText().matches("[0-9]{1,2}") && month.getText().matches("[0-9]{1,2}")
        && year.getText().matches("[0-9]{1,4}") && hour.getText().matches("[0-9]{1,2}")
        && minute.getText().matches("[0-9]{1,2}")) {
      targetVOL.setTime(Integer.parseInt(day.getText()), Integer.parseInt(month.getText()),
          Integer.parseInt(year.getText()), Integer.parseInt(hour.getText()),
          Integer.parseInt(minute.getText()));
    } else {
      JOptionPane.showMessageDialog(this, "One of the date/time values entered was invalid");
      return false;
    }
    targetVOL.setName(name.getText());
    targetVOL.setAddress(address.getText());
    if (animalButton.isSelected()) {
      interestList.add("Animals");
    }
    if (elderButton.isSelected()) {
      interestList.add("Elderly");
    }
    if (environmentButton.isSelected()) {
      interestList.add("Environment");
    }
    if (foodButton.isSelected()) {
      interestList.add("Food");
    }
    if (interestList.size() == 0) {
      JOptionPane.showMessageDialog(this, "You must select an interest your event appeals to");
      return false;
    }
    targetVOL.setInterest(interestList);
    targetVOL.setZipCode(zipCode.getText());
    targetVOL.setDescription(desc.getText());
    targetVOL.setCreator(mainFrame.user.getUserName());
    clearForm();
    return true;
  }
  
}