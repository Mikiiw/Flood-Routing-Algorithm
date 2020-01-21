
import java.util.ArrayList;

/* Node class for  */
public class node {
	/* List of available path connections */
	ArrayList<Integer> paths;

	/* Node number index */
	int nodeNumber;

	/* Store for queueing packets */
	ArrayList<packet> packetBuffer = new ArrayList<packet>();

	/*
	 * Initialise node
	 * 
	 * @param index node number index
	 */
	public node(int index) {
		this.nodeNumber = index;
		paths = new ArrayList<Integer>();
	}

	/* Add path connection to array list */
	public void addPath(int index) {
		paths.add(index);
	}

	/* Get the node index number */
	public int getNodeNumber() {
		return nodeNumber;
	}

	/* Return the whole array list of paths */
	public ArrayList<Integer> getPaths() {
		return paths;
	}

	/* Return number of edge connections */
	public int getPathLength() {
		return paths.size();
	}

	/* Return node number the index is connected to */
	public int getPathIndex(int index) {
		return paths.get(index);
	}

	/* Set a new packet to end of packet buffer */
	public void addBuffer(packet newPacket) {
		this.packetBuffer.add(newPacket);
	}

	/* Remove packet at index of the buffer */
	public void removeBuffer(int index) {
		this.packetBuffer.remove(index);
	}

	/* Return length of buffer size */
	public int getBufferLength() {
		return packetBuffer.size();
	}

	/* Print out the node packet buffer */
	public void printBufferMemory() {
		for (int i = 0; i < packetBuffer.size(); i++) {
			System.out.print("(nextdest: " + packetBuffer.get(i).getNextDest());
			System.out.print(" hops: " + packetBuffer.get(i).getHops());
			System.out.print(" [");
			for (int j = 0; j < packetBuffer.get(i).getMemorySize() - 1; j++) {
				System.out.print(packetBuffer.get(i).getMemory(j) + ", ");
			}
			System.out.print(packetBuffer.get(i).getMemory(packetBuffer.get(i).getMemorySize() - 1));
			System.out.print("]) ");
		}
	}

	/* Return packet at an index*/
	public packet getPacket(int index) {
		return packetBuffer.get(index);
	}

	/* Return packet's destination at an index*/
	public int getPacketDestination(int index) {
		return packetBuffer.get(index).getDestination();

	}

}
