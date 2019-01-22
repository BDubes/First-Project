public class OrganizationUser extends User {
  private String organizationName;
  
  public OrganizationUser(String orgName, String username, String password) {
    super(username, password);
    organizationName = orgName;
    
  }
  
  @Override
  public String getUserType() {
    return "Organization";
  }
  
  @Override
  public String getOrganizationName() {
    return organizationName;
  }
}