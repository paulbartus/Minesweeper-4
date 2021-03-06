import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JPanel;

public class GridPanel extends JPanel
 {
	static final long serialVersionUID = 1L;
	private static final int GRID_X = 15;
	private static final int GRID_Y = 15;
	private static final int INNER_CELL_SIZE = 40;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 10;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public int counter=0;
	public static MineLocations Mines;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public int NearbyMines[][] = new int[TOTAL_COLUMNS][TOTAL_ROWS];

	public GridPanel() 
	 {   
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) 
		 {	
			 throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		 }

		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) 
		 {	
			 throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		 }

		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) 
		 {	
			 throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		 }

		for (int x = 0; x < TOTAL_COLUMNS; x++) 
		 {   
			for (int y = 0; y < TOTAL_ROWS; y++) 
			 {
				colorArray[x][y] = Color.WHITE;
				NearbyMines[x][y] = 0;
			 }
		 }
	 }

	public void paintComponent(Graphics g) 
	 {
		super.paintComponent(g);

		//Compute interior coordinates

		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background

		g.setColor(Color.BLUE);
		g.fillRect(x1, y1, width + 1, height + 1);

		//By default, the grid will be 9x9 

		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS - 1; y++) 
		{
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}

		for (int x = 0; x <= TOTAL_COLUMNS; x++) 
		{
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)));
		}

		//Paint cell colors

		for (int x = 0; x < TOTAL_COLUMNS; x++) 
		 {
			for (int y = 0; y < TOTAL_ROWS; y++) 
			 {
				if ( (y != TOTAL_ROWS - 1)) 
				 {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				 }
			 }
		 }
		
		for (int x = 0; x < TOTAL_COLUMNS; x++) 
		 {
			for (int y = 0; y < TOTAL_ROWS; y++) 
			 {
				if ( (NearbyMines[x][y] != 0) && colorArray[x][y] != Color.BLACK) 
				 {
					int counter = NearbyMines[x][y];
					
					if (counter == 1)
					 {
						g.setColor(Color.GREEN);
					 }
					
					else if (counter == 2)
					  {
						g.setColor(Color.BLUE);
						
					}
					else if (counter == 3)
					{
						g.setColor(Color.RED);
					}
					
					else g.setColor(Color.ORANGE);
					
					g.drawString(String.valueOf(counter), x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 18, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 25);
				 }
			 }
		 }
	 }

// GETTERS

	public int getGridX(int x, int y) 
	 {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;

		if (x < 0) 
		 {   //To the left of the grid
			return -1;
	 	 }

		if (y < 0) 
		 {   //Above the grid
			return -1;
		 }

		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) 
		{   //Coordinate is at an edge; not inside a cell
			return -1;
		}

		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);

		if (x == 0 && y == TOTAL_ROWS - 1) 
		{    //The lower left extra cell
			return x;
		}

		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) 
		{   //Outside the rest of the grid
			return -1;
		}

		return x;

	}

	public int getGridY(int x, int y) 
	 {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;

		if (x < 0) 
		 {   //To the left of the grid
			 return -1;
		 }

		if (y < 0) 
		 {   //Above the grid
			 return -1;
		 }
 
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) 
		 {   //Coordinate is at an edge; not inside a cell
			return -1;
		 }

		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);

		if (x == 0 && y == TOTAL_ROWS - 1) 
		 {    //The lower left extra cell
			return y;
		}

		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) 
		{   //Outside the rest of the grid
			return -1;
		}
		return y;
	}

	public void NextToBlock_Display(int x, int y)
	 {
		if((x<0)||(y<0) || (x>=9)||(y>=9))
		 {
			return;
		 }
		
		if(MyMouseAdapter.mines.Coordinate_Compare(x,y))
		 {
			return;
		 }
			
	    if(MyMouseAdapter.mines.Nearby_Mines(x, y))
	     {  		 
         	int counter = MyMouseAdapter.mines.Nearby_Mines_Counter(x, y);
         	colorArray[x][y] = Color.GRAY;
      		NearbyMines[x][y] = counter;
       		counter++;
            repaint();
            return;
		 }
			
	    else 
	     {		
			if(colorArray[x][y] == Color.GRAY)
			{
				return;
			}
			
			colorArray[x][y] = Color.GRAY;
			NextToBlock_Display(x-1, y);
			NextToBlock_Display(x+1, y);
			NextToBlock_Display(x, y-1);
			NextToBlock_Display(x, y+1);
			counter++;
		 }
	 }
 }
