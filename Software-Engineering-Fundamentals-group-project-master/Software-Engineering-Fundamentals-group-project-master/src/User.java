import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public abstract class User {
  private static String username;
  private String password;
  private boolean banned;
  
  User(String user, String pass) {
    username = user;
    password = pass;
    
  }
  
  public User loadUser(String username) {
    // code to search file for username and load user.
    return null;
  }
  
  public String getUserName() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public static void login(User user) {
    String[] userInfo = null;
    Scanner input = null;
    // open UserDatabase.txt and read username as well as userType
    // take each word and store into userInfo
    // use string tokenizer and remember to use delimiter
    try {
      input = new Scanner(new File("UserDatabase.txt"));
    }
    catch (FileNotFoundException e) {
      System.out.println("Error opening file..");
      System.exit(1);
    }
    
    String line;
    try {
      while ((line = input.nextLine()) != null) {
        StringTokenizer st = new StringTokenizer(line, "ɮɸ\n");
        while (st.hasMoreTokens()) {
          int counter = 0;
          String word = st.nextToken();
          userInfo[counter] = word;
          counter++;
        }
      }
    }
    catch (NoSuchElementException e) {
      // no more lines in the file
      // no handler is necessary
    }
    
    String userType = userInfo[1];
    switch (userType) {
      case "admin":
        // create the admin
        
      case "individual":
        // create the individual
        // assuming order is
        // (usernameɮɸindividualɮɸfirstNameɮɸlastNameɮɸgenderɮɸpassword)
        String userName = userInfo[0];
        String first = userInfo[2];
        String last = userInfo[3];
        String gender = userInfo[4];
        String passWord = userInfo[5];
        user = new IndividualUser(first, last, userName, passWord);
      case "organization":
        // create the organization
        // assuming order is
        // (usernameɮɸorganizationɮɸorganizationNameɮɸpassword)
        String username = userInfo[0];
        String organization = userInfo[2];
        String password = userInfo[3];
        user = new OrganizationUser(organization, username, password);
    }
    
  }
  
  public abstract String getOrganizationName();
  public abstract String getUserType();
}