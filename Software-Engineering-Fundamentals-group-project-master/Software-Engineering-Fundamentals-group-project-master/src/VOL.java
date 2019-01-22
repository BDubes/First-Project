import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VOL {
  public String name = "name";
  public Time eventTime;
  private ArrayList<String> interest = new ArrayList<String>();
  public String desc = "Description of the event."; // Event Description
  public String address = "address";
  public String zipCode = "zip code";
  public JButton shortListing = new JButton();
  // If an organization needs to edit their VOL, we need to know which one is
  // theirs
  public String creator = "Jim";
  public ArrayList<String> users = new ArrayList<String>();
  
  public JPanel listing = new JPanel();
  public JTextField nameField;
  public JTextField dateField;
  public JTextField interestField;
  public JTextField addressField;
  public JTextField zipCodeField;
  public JTextArea descField;
  public JButton joinButton;
  
  public VOL() {
    eventTime = new Time();
  }
  
  // method for calling premade VOLs (testing only)
  public VOL(String name, Time eventTime, ArrayList<String> interest, String desc, String address,
      String zipCode) {
    this.name = name;
    this.eventTime = eventTime;
    for (int i = 0; i < interest.size(); i++) {
      this.interest.add(interest.get(i));
    }
    this.desc = desc;
    this.address = address;
    this.zipCode = zipCode;
    shortListing = new JButton(name);
  }
  
  public JPanel getListing() {
    return listing;
  }
  
  public JButton getShortListing(JPanel target) {
    shortListing.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        target.removeAll();
        target.repaint();
        target.add(getVOL().getListing());
        target.validate();
      }
      
    });
    return shortListing;
  }
  
  public void setName(String name) {
    this.name = name;
    shortListing = new JButton(name);
  }
  
  public void setTime(int day, int month, int year, int hour, int minute) {
    eventTime.SetDay(day);
    eventTime.SetMonth(month);
    eventTime.SetYear(year);
    eventTime.SetHour(hour);
    eventTime.SetMinute(minute);
  }
  
  public void setInterest(ArrayList<String> interest) {
    for (int i = 0; i < interest.size(); i++) {
      this.interest.add(interest.get(i));
    }
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
  
  public void setDescription(String desc) {
    this.desc = desc;
  }
  
  public void setCreator(String creator) {
    this.creator = creator;
  }
  
  public VOL getVOL() {
    return this;
  }
  
  public Time getTime() {
    return eventTime;
  }
  
  public String getZip() {
    return zipCode;
  }
  
  public ArrayList<String> getInterests() {
    return interest;
  }
}
