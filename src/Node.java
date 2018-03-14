/*This class represents a node which stores the names of nodes in the the adjacency matrix*/
public class Node {
	
	private int name;
	private boolean mark;
	
	/*
	 * this constructor creates a node with an int name
	 */
	public Node(int name)
	{
		this.name = name;
	}
	/*
	 * sets the mark, true or false, of a node it if has been visited or not
	 */
	public void setMark(boolean mark)
	{
		this.mark = mark;
	}
	/*
	 * returns the mark corresponding to the node
	 */
	public boolean getMark()
	{
		return this.mark;
	}
	/*
	 * returns the name of the node
	 */
	public int getName()
	{
		return this.name;
	}

}
