package connect4;

public class List<Type extends Comparable<Type>>{
	Node root;
	
	private class Node{
		Type head;
		Node tail;
		public Node(Type head,Node tail){
			this.tail=tail;
			this.head = head;
		}
	}
	
	
	/**
	 * set a list with head and tail
	 * @param head the head of the list
	 * @param tail the tail of the list
	 */
	public List(){
		root=null;
	}
	public boolean isEmpty(){
		
		if (root==null)return true;
		else return false;
	}
	
	/**
	 * push the value into the list
	 * @param x the value to be put in list
	 */
	public void push(Type x){
		Node item =new Node(x,root);
		root=item;
			
	}
	/**
	 * check is the value exist in list
	 * @param x the value to be checked
	 * @return true if exist; false if not exist
	 */
	public boolean contains(Type x){
		return contains(root,x);
	}
	private boolean contains(Node n, Type x){
		if(n==null){return false;}
		
		if(n.head.compareTo(x)==0){
			return true;
		}else if (n.tail==null){
			return false;
		}else{
			return contains(n.tail,x);
		}
	}
}
