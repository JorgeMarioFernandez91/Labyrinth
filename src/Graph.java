/**
 * CS-2210 Assignment 5
 * Graph.java
 * The purpose of this class is to create an adjacency matrix which will store edges if two nodes are adjacent. 
 * This class is integral to the Labyrinth.java class
 * 
 * @author Jorge Fernandez
 * @version 1.0 2017-12-06
 */

import java.util.*;

public class Graph<T> implements GraphADT{
	
	private T[][] matrix;
	
	private T[] vert;
	
	private int nodeCount;
	
	@SuppressWarnings("unchecked")
	public Graph(int n)
	{
		int i;
		int j;
		
		//keeps track of the number of nodes in the graph
		this.nodeCount = n;
		//creates a 2d array which will represent the adjacency matrixm edges are stored here
		this.matrix = (T[][])new Object[n][n];
		//creates an array to store the nodes of the array
		this.vert = (T[]) new Object[n];
		//inserts nodes into vert array
		for (i = 0 ; i < n; i++)
		{
			this.vert[i] = (T) new Node(i);
		}
		//initializes all indexes of the matrix to null before anything is inserted
		for (i = 0; i < n ; i++)
		{
			for (j = 0; j < n; j++)
			{
				this.matrix[i][j] = null;
			}
		}
	}
	
	/* 
	 * Adds to the graph an edge connecting the given vertices. The type of the edge is as indicated. The label of the 
	 * edge is set to the empty String. Throws a GraphException if either node does not exist or if the edge is already in the graph.
	 */
	@SuppressWarnings("unchecked")
	public void insertEdge(Node nodeu, Node nodev, String edgeType) throws GraphException
    {
		//if nodeu or nodev does not exist then throw exception
		if (getNode(nodeu.getName()) == null || getNode(nodev.getName()) == null)
		{
			throw new GraphException();
		}
		//if edge already exists in matrix throw exception
		if (this.matrix[nodeu.getName()][nodev.getName()] != null)
		{
			throw new GraphException();
		}
		//create edges to represent the link between node u and node v, and nodev and nodeu. This ensures
		//the graph is properly populated
		Edge edgeA = new Edge(nodeu, nodev, edgeType);
		Edge edgeB = new Edge(nodev, nodeu, edgeType);
		//store the edges into the graph in the proper index
		this.matrix[nodeu.getName()][nodev.getName()] = (T) edgeA;
		this.matrix[nodev.getName()][nodeu.getName()] = (T) edgeB;
    }
	
	/* 
	 * Returns the node with the specified name. Throws a GraphException if the node does not exist. 
	 */
	public Node getNode(int name) throws GraphException
	{
		
		if (name >= this.nodeCount)
		{
			throw new GraphException();
		}
		
		return (Node) this.vert[name];
	}
	
	/* 
	 * Returns a Java Iterator storing all the edges incident on the specified node. It returns null if the node does
	 * not have any edges incident on it. Throws a GraphException if the node does not exist. 
	 */
	@SuppressWarnings("unchecked")
	public Iterator<Edge> incidentEdges(Node u) throws GraphException
	{
		int i = 0; 
		int j = 0;
		int count = 0;
		//if node does not exist throw exception
		if (getNode(u.getName()) == null)
		{
			throw new GraphException();
		}
		//creates a temporary array to store the edges that are to be iterated by the iterator
		T[] temp = (T[]) new Object [this.nodeCount];

		Vector<Edge> v = new Vector<Edge>();
		Iterator<Edge> iter = null;
		
		j = 0;	
		count = 0;
		//store the edges in the temp array 
		for (i = 0; i < this.nodeCount; i++)
		{
			if (i != u.getName())
			{
				Node nodeu = getNode(u.getName());
				Node nodev = getNode(i );
				//if a node exists in the matrix then add it to the temporary array to be used by the iterator
				if (this.matrix[u.getName()][i] != null)
				{
					Edge str = getEdge(nodeu, nodev);
					
					temp[j] = (T) str;
					
					v.add((Edge) temp[j]);
					
					j++;
					
					count++;
				}
			}
		}
		iter = v.iterator();
		//return the iterator
		return iter;
	}
	
	/* 
	 * Returns the edge connecting the given vertices. Throws a GraphException if there is no edge connecting the given 
	 * vertices or if u or v do not exist. 
	 */
	@SuppressWarnings("unchecked")
	public Edge getEdge(Node u, Node v) throws GraphException
	{
		//if u or v does not exist throw exception
		if (getNode(u.getName()) == null || getNode(v.getName()) == null)
		{
			throw new GraphException();
		}
		//if edge does not exist throw exception
		if (this.matrix[u.getName()][v.getName()] == null )
		{
			throw new GraphException();
		}
		
		return (Edge) this.matrix[u.getName()][v.getName()];
	}
	
	/* 
	 * Returns true is u and v are adjacent, and false otherwise. 
	 * It throws a GraphException if either vertex does not exist. 
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException
	{
		//if either node or both do not exist throw exception
		if (this.vert[u.getName()] == null || this.vert[v.getName()] == null)
		{
			throw new GraphException();
		}
		//if nodes are adjacent return true
		if (this.matrix[u.getName()][v.getName()] != null)
		{
			return true;
		}
		
		return false;
	}
}
