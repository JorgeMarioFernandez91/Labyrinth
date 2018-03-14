/**

 *  Defines the interface to a stack data structure.
 */

public interface StackADT<Node>
{
 
  public void push (Node element);
  
 
  public Node pop() throws EmptyCollectionException;


  public Node peek();
  
 
  public boolean isEmpty();

  
  public int size();

 
  public String toString();
}
