public class board
{
  
  public boolean debug = false;
  public int []table = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
  public int []testTable = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
  public int count = 15;
  public int testCount = 15;
  public int depthCount = 0;
  public int [][]pathway = new int [14][3];
  
  // 18 possible moves
  public int [][]validMoves = { 
        {0,1,3}, {0,2,5}, {1,3,6}, {1,4,8}, {2,4,7}, {2,5,9}, {3,6,10},{3,7,12}, {4,7,11}, {4,8,13}, {5,8,12}, {5,9,14}, {3,4,5}, {6,7,8},{7,8,9}, {10,11,12}, {11,12,13}, {12,13,14} };

        
  // Bunch o' Debug strings. After all this time I think I found a good way of doing this.
  public void pD(int code, int details)
  {
    if (debug)
    {
      System.out.println();
      switch (code) {
        case 0:
          System.out.print("Initial Empty Spot: " + details);
          System.out.println();
          break;
        case 1:
          System.out.print("Current Table State: ");
          for (int i = 0; i < 15; i++)
          {
            System.out.print(table[i] + " ");
          }
          System.out.println();
          break;
        case 2:
          System.out.print("Modifying Table State");
          break;
        case 3:
          System.out.print("Playing game...");
          System.out.println();
          break;
        case 4:
          System.out.print("FOUND A MATCH");
          System.out.println();
          break;
        case 5:
          System.out.print("Occupied Count: " + details);
          System.out.println();
          break;
        case 6:
          System.out.print("ERROR: PEGS CANNOT BE MOVED");
          System.out.println();
          break;
        case 7:
          System.out.print("Moving peg " + details);
          System.out.println();
          break;
        case 8:
          System.out.print("Valid Move");
          System.out.println();
          break;
        case 9:
          System.out.print("Invalid Move. Attempt: " + details);
          System.out.println();
          break;
        case 10:
          System.out.print("Trying Reverse Jump");
          System.out.println();
          break;
      }
    }
  }

  // Prints out the entire table
  // Yes, there are nicer ways to do this, but I've already written it out.
  // I could refactor but forget that.
  public void printTable()
  {
    System.out.print("    " + table[0] + "    ");
    System.out.println();
    System.out.print("   " + table[1] + " " + table[2] + "   ");
    System.out.println();
    System.out.print("  " + table[3] + " " + table[4] + " " + table[5] + "  ");
    System.out.println();
    System.out.print(" " + table[6] + " " + table[7] + " " + table[8] + " " + table[9] + " ");
    System.out.println();
    System.out.print(table[10] + " " + table[11] + " " + table[12] + " " + table[13] + " " + table[14]);
    System.out.println();
  }

  // Sets given start peg to EMPTY
  public void setTable(int start)
  {
    //System.out.print(start);
    pD(1,start);  // Returns list of pegs
    pD(0,start);  // Initial empty spot
    table[start] = 0;
    testTable[start] = 0;
    count--;
    pD(2,start);  // Modify table state
    pD(1,start);  // Current list of pegs (should have changed)
    
  }
  
  // Sets all pegs to FULL
  public void resetTable()
  {
    for (int i = 0; i < 15; i++)
    {
      table[i] = 1;
      testTable[i] = 1;
    }
    count = 15;
  }
  
  public void movePeg(int from, int over, int to)
  {
    // Verify:
    //        From - FULL
    //        Over - FULL
    //        To   - EMPTY    Uh...yeah, it does that. Should also see if the move if a real move? JIC
    boolean valid = false;

    for (int i = 0; i < 18; i++)
    {
      // run through each move
      //validMoves[i][0] - validMoves[i][2]

      if (valid == false)
      {
        //System.out.print("[" + from + " " + over + " " + to + "]");
        //System.out.println();
        //System.out.print("[" + validMoves[i][0] + " " + validMoves[i][1] + " " + validMoves[i][2] + "]");
        //System.out.println();
        if (((from == validMoves[i][0] && to == validMoves[i][2]) || (from == validMoves[i][2] && to == validMoves[i][0])))
        {

          valid = true;
          pD(8,0);
        }
        else
        {
          pD(9,i);
        }
      }
    }

    if (table[from] == 1 && table[over] == 1 && table[to] == 0 && valid == true)
    {
      // Need to also check to see if it's a valid move.
      pD(1,from);  // Current list of pegs
      pD(7, from);
      
      table[from] = 0;
      table[over] = 0;
      table[to]   = 1;
      pD(1,from);  // Current list of pegs (should have changed)
      count--;
    }
    else if (table[from] == 0 && table[over] == 1 && table[to] == 1 && valid == true)
    {
      // Try the reverse, just in case
      pD(10,0);
      pD(1,from);  // Current list of pegs
      pD(7, from);

      table[from] = 1;
      table[over] = 0;
      table[to]   = 0;
      pD(1,from);  // Current list of pegs (should have changed)
      count--;
    }
    else
    {
      pD(6,0);  // Error Condition
    } 
    
    // Now Verify:
    //        From - EMPTY
    //        Over - EMPTY
    //        To   - FULL
    
    // print board
    printTable();
  }
    
  public int [][] getValidMoves(int[] tableState)
  {
	  int []newTableState = new int [15];
	  int [][]movesList = new int [50][3];
	  int movesCounter = 0;
	  
	  for (int i = 0; i < 15; i++)
	  {
		  newTableState[i] = tableState[i];
	  }
	  
	  for (int i = 0; i < 15; i++)
	    {
	      if (newTableState[i] == 0)
	      {
	        //check for moves in validMoves that contain this node
	        
	        for (int a = 0; a < 18; a++)
	        {
	          if (i == validMoves[a][0] || i == validMoves[a][2])
	          {
	           // pD(4,0); // Found a match
	            
	            if (debug == true)
	            {
	            	// Sum of the pegs MUST EQUAL 2. Otherwise we're trying to jump over an empty spot, and that's not a valid move.
		            //System.out.print("SUM: " + (newTableState[validMoves[a][0]] + newTableState[validMoves[a][1]] + newTableState[validMoves[a][2]]));
		            //System.out.println();
	            }
	            
	            if ((newTableState[validMoves[a][0]] + newTableState[validMoves[a][1]] + newTableState[validMoves[a][2]]) == 2)
	            {
		            if (debug == true)
		            {
		              System.out.print("[" + validMoves[a][0] + " " + validMoves[a][1] + " " + validMoves[a][2] + "]" );
		              System.out.println();
		            }
		            
		            // add to moveslist
		            movesList[movesCounter][0] = validMoves[a][0];
		            movesList[movesCounter][1] = validMoves[a][1];
		            movesList[movesCounter][2] = validMoves[a][2];
		            movesCounter++;
	            }
	          }
	        }
	      }
	    }
	  
	//print moveslist
	   /* if (movesCounter > 0)
	    {
		    for (int i = 0; i < movesCounter; i++)
		    {
		    	System.out.print('[');
		    	System.out.print(movesList[i][0] + " " + movesList[i][1] + " " + movesList[i][2]);
		    	System.out.print(']');
		    	System.out.println();
		    }
	    }
	    else
	    {
	    	System.out.print("No More Valid Moves - Need to go back a step");
	    }*/
	  
	  return movesList;
  }
  
  public int[] testMove(int[] tableState, int from, int over, int to)
  {
	  int []newTableState = new int[15];
	  
	  for (int i = 0; i < 15; i++)
	  {
		  newTableState[i] = tableState[i];
	  }
	  
	  if (newTableState[from] == 1 && newTableState[over] == 1 && newTableState[to] == 0)
	    {
	      // Need to also check to see if it's a valid move.
	      //pD(1,from);  // Current list of pegs
	      //pD(7, from);
	      
	      newTableState[from] = 0;
	      newTableState[over] = 0;
	      newTableState[to]   = 1;
	      //pD(1,from);  // Current list of pegs (should have changed)
	      //count--;
	    }
	    else if (newTableState[from] == 0 && newTableState[over] == 1 && newTableState[to] == 1)
	    {
	      // Try the reverse, just in case
	      pD(10,0);
	      //pD(1,from);  // Current list of pegs
	      //pD(7, from);

	      newTableState[from] = 1;
	      newTableState[over] = 0;
	      newTableState[to]   = 0;
	      //pD(1,from);  // Current list of pegs (should have changed)
	      //count--;
	    }
	    else
	    {
	      pD(6,0);  // Error Condition
	    } 
	  
	  return newTableState;
  }
  
  public boolean generateMovesList(int[] tableState, int depth)
  {
	  int [][]movesList = new int [50][3];
	  int [][]realMovesList = new int [14][3];
	  int []newTableState = new int[15];
	  int movesCounter = 0;
	  int occupiedCounter = 15;
	  
	  for (int i = 0; i < 15; i++)
	  {
		  newTableState[i] = tableState[i];
		  
		  if (newTableState[i] == 0)
		  {
			  occupiedCounter--;
		  }
	  }
	  
	  movesList = getValidMoves(newTableState);
	  
	  
	  // Print Moves List (Not needed)
	  for (int i = 0; i < 18; i++)
	  {
		  if ((movesList[i][0] + movesList[i][1] + movesList[i][2]) != 0)
		  {
			  if (debug == true)
			  {
				System.out.print('[');
			    System.out.print(movesList[i][0] + " " + movesList[i][1] + " " + movesList[i][2]);
			    System.out.print(']');
			    System.out.println();
			  }
			  movesCounter++;
		  }
	  }
	  
	  if (debug == true)
	  {
		  System.out.print("# of Moves Available: " + movesCounter);
		  System.out.println();
		  System.out.print("# of Occupied Nodes: " + occupiedCounter);
		  System.out.println();
	  }
	  
	  if (movesCounter > 0 && occupiedCounter > 1)
	  {
		  for (int i = 0; i < movesCounter; i++)
		  {
			  // test move
			  int []newNewTableState = new int [15];
			  
			  boolean add = false;
			  
			  if (debug == true)
			  {
				  System.out.print("Iteration: " + i + "  Depth: " + depth);
				  System.out.println();
				  System.out.print("==============================");
				  System.out.println();
				  System.out.print("Testing Moveset: [ " + movesList[i][0] + " " + movesList[i][1] + " " + movesList[i][2] + "]");
				  System.out.println();
			  }
			  
			  newNewTableState = testMove(newTableState, movesList[i][0], movesList[i][1], movesList[i][2]);
			  
			  pathway[depth][0] = movesList[i][0];
			  pathway[depth][1] = movesList[i][1];
			  pathway[depth][2] = movesList[i][2];
			  
			  add = generateMovesList(newNewTableState, depth+1);
			  
			  if (add == true)
			  {
				 
				 if (debug == true)
				 {
					 for (int r = 0; r < 14; r++)
					 {
						 System.out.print("[ " + pathway[r][0] + " " + pathway[r][1] + " " + pathway[r][2] + "]");
						 System.out.println();
					 }
				 }
				  return true;
			  }
			  /*else
			  {
				 System.out.print("NOT A GOOD PATH. " + depth);
				 System.out.println();
				 return false;
			  }*/
			  
		  }
	  }
	  else if (movesCounter == 0 && occupiedCounter == 1)
	  {
		  if (debug == true)
		  {
			  System.out.print("WE FOUND A PATH");
			  System.out.println();
			  System.out.print("DEPTH: " + depth);
			  System.out.println();
		  }
		  realMovesList[depth][0] = movesList[1][0];
		  realMovesList[depth][1] = movesList[1][1];
		  realMovesList[depth][2] = movesList[1][2];
		  
		  if (debug == true)
		  {
			  System.out.print("Moves List?: ");
			  System.out.println();
			  for (int r = 0; r < 14; r++)
				 {
					 System.out.print("[ " + realMovesList[r][0] + " " + realMovesList[r][1] + " " + realMovesList[r][2] + "]");
					 System.out.println();
				 }
			  System.out.println("Press Enter to continue");
			  try{System.in.read();}
			  catch(Exception e){}
		  }
		  
		  
		  return true;
	  }
	  else
	  {
		  //return false;
	  }
	  
	  
	  return false;
  }
  
  // Simulate board.
  public void play()
  {
    pD(3,0);

    generateMovesList(table,0);
    
    for (int i = 0; i < 13; i++)
    {
    	movePeg(pathway[i][0],pathway[i][1],pathway[i][2]);
    }
  }

 // Do stuff.
}