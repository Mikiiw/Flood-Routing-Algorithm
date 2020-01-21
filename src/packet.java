import java.util.ArrayList;

/* Packet class for transversing across the node network*/
public class packet implements Cloneable {

	/* Memory of previous locations the packet has been to*/
	ArrayList<Integer> memory = new ArrayList<Integer>();;
	
	/* Default value of previous node location -1 as it has not visited a previous node*/
	private int previousNode = -1;
	
	/* Current position of node */
	private int currentNode;
	
	/* Next destination for movement */
	private int nextDestination;
	
	/* Life of packet. Every move from node to node decrements the count by 1*/
	private int hopCount;
	
	/* Target Destination */
	private int finalDestination;
	
	/*
	 * Instantiate a packet
	 * 
	 * @param currentNode current node location
	 * @param destination target destination
	 * @param next destination next destination after one hop
	 */
	public packet(int currentNode, int hopCount, int destination, int nextDestination) {
		this.currentNode = currentNode;
		this.nextDestination = nextDestination;
		this.hopCount = hopCount;
		this.finalDestination = destination;

		/* Add to memory current node it is on for future pathing */
		memory.add(currentNode);
	}
	
	/* Set current node location */
	public void setCurrentNode(int node) {
		this.currentNode = node;
	}

	/* Get current node location */
	public int getCurrentNode() {
		return currentNode;
	}

	/* Copy memory of old packet array list for packet cloning*/
	public void setMemory(ArrayList<Integer> oldList) {
		this.memory = oldList;
	}
	
	/* Add to memory of node index */
	public void setMemory(int nodeLocation) {
		memory.add(nodeLocation);
	}

	/* Return arraylist of memory */
	public ArrayList<Integer> getMemory() {
		return memory;
	}

	/* Return index of memory value */
	public int getMemory(int index) {
		return memory.get(index);
	}

	/* Return size */
	public int getMemorySize() {
		return memory.size();
	}

	/* Search if packet has been on node return true */
	public boolean checkPaths(int index) {
		for (int i = 0; i < memory.size(); i++) {
			if (memory.get(i) == index)
				return true;
		}
		return false;
	}

	/* Return target destination */
	public int getDestination() {
		return finalDestination;
	}

	/* Return next hop destination */
	public int getNextDest() {
		return nextDestination;
	}

	/* Set next destination*/
	public void setNextDest(int nextDest) {
		this.nextDestination = nextDest;
	}

	/* Clone Packet*/
	public packet clone() throws CloneNotSupportedException {
		return (packet) super.clone();
	}

	/* Return hop count */
	public int getHops() {
		return hopCount;
	}

	/* Decrement the hop by 1 */
	public void decrementHops() {
		this.hopCount--;
	}

	/* Set previous node location */
	public void setPrevNode(int node) {
		this.previousNode = node;
	}
	
	/* get previous node location */
	public int getPrevNode() {
		return this.previousNode;
	}

}
