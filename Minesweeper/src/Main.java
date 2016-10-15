import javax.swing.JFrame;
public class Main 
 {
	public static void main(String[] args) 
	 {
		JFrame myFrame = new JFrame("Minesweeper");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(418, 440);

		GridPanel myPanel= new GridPanel();
		myFrame.add(myPanel);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);
		myMouseAdapter.Mines();
		myFrame.setVisible(true);
	}
 }
