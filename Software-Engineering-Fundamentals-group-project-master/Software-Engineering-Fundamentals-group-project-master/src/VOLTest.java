import javax.swing.JFrame;

public class VOLTest {
  public static void main(String[] args) {
    /*
     * VOLManager volManager = new VOLManager(); for(int i = 0; i < 3; i++) {
     * volManager.CreateVOL(); }
     * 
     * volManager.DisplayListings();
     */
    MainFrame frame = new MainFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700, 500);
    frame.setResizable(false);
    frame.setVisible(true);
    
  }
}
