/*This class represents the edges connecting two nodes*/
public class Edge {
	
	private Node u;
	private Node v;
	private String type;
	
	/*
	 * this constructor creates an edge between nodes u and v, and sets it's type
	 */
	public Edge(Node u, Node v, String type)
	{
		this.u = u;
		this.v = v;
		this.type = type;
	}
	/*
	 * returns the first end point of an edge
	 */
	public Node firstEndpoint()
	{
		return this.u;
	}
	/*
	 * returns the second end point of an edge
	 */
	public Node secondEndpoint()
	{
		return this.v;
	}
	/*
	 * returns the type of the edge
	 */
	public String getType()
	{
		return this.type;
	}
	/*
	 * sets the type of the edge
	 */
	public void setType(String type)
	{
		this.type = type;
	}
}
