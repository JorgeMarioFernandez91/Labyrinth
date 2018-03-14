import java.util.Iterator;

public class LinkedStack<Node> implements StackADT<Node>{

LinearNode<Node> top;
int count;

	public LinkedStack()
	{
		this.top = null;
		this.count = 0;
	}
	
	public void push (Node nodes) 
	{
		LinearNode<Node> newNode = new LinearNode<Node>();
		
		newNode.setElement(nodes);
		
		
		if (isEmpty())
		{
			this.top = newNode;
		}
		else
		{
			newNode.setNext(this.top);
			this.top = newNode;
			
			
		}
		this.count++;
	}
	  
	
	public Node pop() throws EmptyCollectionException
	{
		if (isEmpty())
		{
			throw new EmptyCollectionException();
		}
		
		Node result = (Node) this.top.getElement();
			
		this.top = this.top.getNext();
			
			
		this.count--;
		
		return result;
	}
	
	
	public Node peek() 
	{
		return (Node) this.top.getElement();
	}
	
	  	
	public int size() 
	{
		return this.count;
	  
	}
	
	public boolean isEmpty()
	{
		if (size() == 0)
		{
			return true;
		}
		return false;
	}

	public Iterator<Node> iterator() {


		return null;
	}


}
