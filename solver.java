public class solver {
	
	public static void main(String[] args) {
		board cracker = new board();
		
		for (int i = 0; i<5; i++)
	    {
	      System.out.print("=== " + i + " ===");
	      System.out.println();
	      cracker.setTable(i);							// Set start position (i)
	      cracker.printTable();
	      cracker.play();
	      
	      System.out.println();
	      cracker.resetTable();							// Reset table to full (all occupied - to delete a peg later)
	    }
	}
}