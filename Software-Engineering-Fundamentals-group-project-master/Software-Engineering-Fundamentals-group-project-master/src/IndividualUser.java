public class IndividualUser extends User {
  private String firstName;
  private String lastName;
  
  public IndividualUser(String username, String password, String first, String last)
  {
    super(username, password);
    firstName = first;
    lastName = last;
    
  }
  
  @Override
  public String getUserType()
  {
    return "Individual";
  }
  
  public String getFirstName()
  {
    return firstName;
  }
  
  public String getLastName()
  {
    return lastName;
  }
  
  @Override
  public String getOrganizationName()
  {
    return "";
  }
}