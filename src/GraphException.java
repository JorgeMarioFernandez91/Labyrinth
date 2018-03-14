/*This class throw a GraphException if it is invoked*/
public class GraphException extends Exception{
	
	public GraphException()
	{
		System.out.println("This is not a node in the graph");
	}
	
}