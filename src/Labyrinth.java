/*
 * This class creates a labyrinth by using a text file as input. The text file is opened, searched for searched for edges and nodes, 
 * and then finds a path from the start to the end while keeping track of how many bombs are available to use. This determines what path is taken.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

public class Labyrinth<T> {
	
	private Graph graph;
	
	private Node[] nodes;
	
	private Iterator<Node>iter;
	
	private int total;
	
	private Stack<Node> stack = new Stack<Node> ();
	
	private int brickBombs;
	
	private int acidBombs;
	
	private char[] temp;
	
	/*
	 * This constructor creates a labyrinth by reading in a text file and parsing the information, then storing edges into an adjacency matrix
	 */
	@SuppressWarnings("unchecked")
	public Labyrinth (String inputFile)
	{
		BufferedReader in;
		String line;
	    String fileName = inputFile;
	    
	    int size1;
	    int size2;
	    
	    int i = 0;
	    int j = 0;
	    int k = 0;

		try {
	        // Open the input file
		    in = new BufferedReader(new FileReader(fileName));

	        // Read one line of text from the file
		    line = in.readLine();
		  
		    size1 = Integer.parseInt(in.readLine());
		    size2 = Integer.parseInt(in.readLine());
		    //stores the total size of the matrix
		    this.total = size1*size2;
		    //this will set the size of the temporary array which will store the nodes
		    int tempSize = (size1 + (size1-1)) * ((size2 + (size2-1)));
		    //stores the width of the labyrinth
		    int width = size1 + (size1-1);
		    //stores the height of the labyrinth
		    int height = size2 + (size2-1);
		    //create temporary array
		    this.temp=  new char[tempSize];
		    //initialize evey index to "solid rock" type
		    for (i =0; i < tempSize; i++)
		    {
		    	this.temp[i] = ' ';
		    }
		    
		    nodes =  new Node[this.total];
		    
		    Node node ;
		    
		    //stores the nodes in the node array
		    for (j = 0; j < this.total; j++)
		    {
		    	nodes[j] = new Node(j);
		    }
		    //creates a graph object which will store the edges of the labyrinth
		    this.graph = new Graph<Object>(this.total);
		    
		    //keep track of how man brick and acid bombs are available to use
		    this.brickBombs = Integer.parseInt(in.readLine());
		    this.acidBombs = Integer.parseInt(in.readLine());
		    
		    String s = line;
		    i = 0;
		    int tracker = 0;
		    //stores every line of the labyrinth text file into a single array, the contents will be sorted below
		    while (line != null)
		    {
		    	line = in.readLine();
		    	j=0;
		    	for (i = tracker; i < tempSize && j < line.length() ; i++)
		    	{
		    		this.temp[i] = line.charAt(j);
		    		tracker++;
		    		j++;
		    	}
		    }
		    //close the text file after its been used
		    in.close();
		    
		    int nodeCount = 0;
		    
		    String type = "";
		    
		    char c = ' ';

		    //search the temporary array and check if two nodes have an edge between them, then label the type of edge
		    for (i = 0 ; i < tempSize - 1; i++)
		    {
		    	c = this.temp[i];
		    	//if c is a room and i+1 is a corridor and i+2 is another room
		    	if ((c == 'b' || c == 'x' || c == '+' || c == 'o') )
		    	{
		    			if (this.temp[i+2] == '+' || this.temp[i+2] == 'b' || this.temp[i+2] == 'x' || this.temp[i+2] == 'o')
		    			{
		    				if (this.temp[i+1] == '-')
		    				{
		    					type = "corridor";
		    				}
		    				else if (this.temp[i+1] == 'H')
		    				{
		    					type = "thick brick wall";
		    				}
		    				else if (this.temp[i+1] == 'v')
		    				{
		    					type = "brick wall";
		    				}
		    				else if (this.temp[i+1] == 'V')
		    				{
		    					type = "brick wall";
		    				}
		    				else if (this.temp[i+1] == 'h')
		    				{
		    					type = "brick wall";
		    				}
		    				else if (this.temp[i+1] == 'M')
		    				{
		    					type = "metal wall";
		    				}
		    				else if (this.temp[i+1] == 'm')
		    				{
		    					type = "metal wall";
		    				}
		    				else if (this.temp[i+1] == ' ')
		    				{
		    					type = "solid rock";
		    				}
						
			    			try 
			    			{
			    				//store the edge into the graph along with its type
								this.graph.insertEdge(this.graph.getNode(nodeCount), this.graph.getNode(nodeCount+1), type);
							} 
			    			catch (GraphException e) 
			    			{
								e.printStackTrace();
							}
		    			}
						//keeps track of how many nodes or "rooms" have been encountered
		    			nodeCount++;
		    		
		    	}
		    	//if c is not a room, but rather an edge then check if it has nodes at each end
		    	else if (c == '|' || c == 'v' || c == 'V' || c == 'h' || c == 'H' || c == 'M' || c == ' ')
				{
					if (c == '|')
					{
						type = "corridor";
					}
					else if (c == 'v')
					{
						type = "brick wall";
					}
					else if (c == 'V')
					{
						type = "thick brick wall";
					}
					else if (c == 'h')
					{
						type = "brick wall";
					}
					else if (c == 'H')
					{
						type = "thick brick wall";
					}
					else if (c == 'M')
					{
						type = "metal wall";
					}
					else if (c == ' ')
    				{
    					type = "solid rock";
    				}
					try 
					{
						int toproom = i - width;
						int bottomroom = i + width;
						
						int toproomindex = 0;
						int bottomroomindex = 0;
						
						int counter1 = 0;
						
						if ((toproom >= 0 && bottomroom <= tempSize) && (  this.temp[toproom] == 'x' || this.temp[toproom] == 'b' || this.temp[toproom] == '+'|| this.temp[toproom] == 'o') 
							&& (this.temp[bottomroom] == 'x' || this.temp[bottomroom] == 'b' || this.temp[bottomroom] == '+' || this.temp[bottomroom] == 'o'))
						{
							//finds the location of the top room and bottom room in relation to the current edge
							while (toproomindex != tempSize)
							{
								if (((counter1 + width != i) && ((this.temp[counter1] == 'x' || this.temp[counter1] == 'b' || this.temp[counter1] == '+' || this.temp[counter1] == 'o'))))
								{
									toproomindex++;
								}
								else if (counter1 + width == i)
								{
									break;
								}
								counter1++;
							}
							
							bottomroomindex = toproomindex + size1;
							//insert the edge into the graph	
							this.graph.insertEdge(this.nodes[(int) toproomindex], this.nodes[(int) bottomroomindex], type);
						}
					}
					catch (GraphException e) 
					{
						e.printStackTrace();
					}
				}
		    }
		}

		catch (IOException e) 
		{
		    System.out.println("Error reading input file: " + fileName);
		}
	}
	
	/*
	 * This method uses DFS to recursively search for a path between the start and the end node.
	 * It returns true or false depending on the outcome
	 */
	private boolean path(Node u, Node d)
	{
		int j = 0;
		
		//mark u
		this.nodes[u.getName()].setMark(true);
		//push u onto stack
		this.stack.push(this.nodes[u.getName()]);
		//if u equals destination then return true
		if (this.nodes[u.getName()] == this.nodes[d.getName()])
		{
			return true;
		}
		
		try {
			//for each edge incident on u
				for (j = 0; j < this.total; j++)
				{
					//if edge exists
					if (this.graph.areAdjacent(this.nodes[u.getName()], this.nodes[j]))
					{
						//if v is not marked
						if (this.nodes[j].getMark() == false)
						{
							//check if edge is a brick wall
							if (this.graph.getEdge(u, this.nodes[j]).getType() == "brick wall" && this.brickBombs >= 1)
							{
								//use a bomb
								this.brickBombs--;
								//if there is a path between nodes v and d then continue search else refund bombs
								if (path(this.nodes[j], d) == true)
								{
									return true;
								}
								else
								{
									this.brickBombs++;
								}
							}	
							//check if edge is a thick brick wall
							else if (this.graph.getEdge(u, this.nodes[j]).getType() == "thick brick wall" && this.brickBombs >= 2)
							{
								//use a bombs
								this.brickBombs--;
								this.brickBombs--;
								//if there is a path between nodes v and d then continue search else refund bombs
								if (path(this.nodes[j], d) == true)
								{
									return true;
								}
								else
								{
									this.brickBombs++;
									this.brickBombs++;
								}
							}
							//check if edge is a metal wall
							else if (this.graph.getEdge(u, this.nodes[j]).getType() == "metal wall" && this.acidBombs >= 1)
							{
								//use a bombs
								this.acidBombs--;
								//if there is a path between nodes v and d then continue search else refund bombs
								if (path(this.nodes[j], d) == true)
								{
									return true;
								}
								else
								{
									this.acidBombs++;
								}
							}
							//check if edge is a corridor then proceed, no need to use bombs if just "corridor"
							else if (this.graph.getEdge(u, this.nodes[j]).getType() == "corridor")
							{
								if (path(this.nodes[j], d) == true)
								{
									return true;
								}
							}
							
						}
					}
				}
			//to make sure exception is not throw check to see if stack is not empty before popping
			if (!this.stack.isEmpty())
			{
				//pop the node which didn't lead anywhere
				Node backTrack = this.stack.pop();
				//make sure the node is set back to false so we could re-use it if necessary
				backTrack.setMark(false);
			}
			
		} 
		catch (GraphException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * returns the graph if it exists, else return false
	 */
	public Graph getGraph() throws LabyrinthException
	{
		if (this.graph == null)
		{
			throw new LabyrinthException();
		}
		return this.graph;
		
	}
	
	/*
	 * return an iterator if a path is found between the start and end, or else return null
	 */
	Iterator solve()
	{
		int i =0;
		int j =0;
		int counter1 = 0;
		int counter2 = 0;
		int start = 0;
		int end = 0;
		//find the location of the start node
		while (i != this.temp.length)
		{
			if (this.temp[counter1] == 'x' || this.temp[counter1] == '+' || this.temp[counter2] == 'o')
			{
				start++;
			}
			else if (this.temp[counter1] == 'b')
			{
				break;
			}
			counter1++;
		}
		//find the location of the end node
		while (j != this.temp.length)
		{
			if (this.temp[counter2] == 'b' || this.temp[counter2] == '+' || this.temp[counter2] == 'o')
			{
				end++;
			}
			else if (this.temp[counter2] == 'x')
			{
				break;
			}
			counter2++;
		}
		
		try {
			//call the path method and use the start node and end node as parameters
			if (path(this.graph.getNode(start), this.nodes[end]) == true)
			{
				//if there is a path then return an iterator which contain the path from start to finish
				this.iter = stack.iterator();
				
				return this.iter;
			}
		} 
		
		catch (GraphException e) 
		{
			e.printStackTrace();
		}
		//if there is not path then return null
		return null;
	}
	
	
}
