import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/* Class for flood filling algorithm*/
public class NodeNetwork {

	/* Set the value of the node number */
	private int noOfNodes;
	/* Set the packet hop count */
	private int packetHopCount;
	/* Set the number of iterations. One iteration is movement from one node to another */
	private int maxCount;

	/* Set the number of duplicate packets generated from cloning a duplicate is 
	 * any packet that has been cloned, moved and had its destination changed
	 * */
	private int noOfDuplicates = 0;
	
	/* Values for viewing the network at a period*/
	private int startTime = 0;
	private int endTime = 0;

	/* Node storages */
	private ArrayList<node> nodeArray = new ArrayList<node>();

	/*
	 * Create a new node network
	 * 
	 * @param noOfNodes A number of nodes
	 * @param hopCount A packet's hop count
	 * @param maxCount A maximum time iteractions
	 */
	public NodeNetwork(int noOfNodes, int hopCount, int maxCount) {
		this.noOfNodes = noOfNodes;
		this.packetHopCount = hopCount;
		this.maxCount = maxCount;
	}
	
	/*
	 * Set the max viewing window from 0 to end time
	 * 
	 * @param startTime start of count
	 * @param endTime end of count
	 */
	public void setViewWindow(int startTime, int endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/* 
	 * Run the Flood Filling Algorithm Program
	 */
	public void run() throws CloneNotSupportedException {

		/*
		 * Node edge Generation. Connections made by checking the connecting
		 * node with itself and makes a generation only if there isnt a
		 * connecting path.
		 */
		this.addPaths();

		/* Display Paths */
		this.displayPaths();

		/*
		 * Add first packet to source
		 */
		nodeArray.get(noOfNodes).addBuffer(new packet(nodeArray.get(noOfNodes).getNodeNumber(), packetHopCount,
				noOfNodes + 1, nodeArray.get(noOfNodes).getPathIndex(0)));

		/*
		 * Initiate the hopCount. The code will first check for moveable packets
		 * in the first buffer of each node and add it to an arrayList. It will
		 * also tick off a list of available path taken. Then the move
		 * generation will be applied, moving all the packets in the list to the
		 * start of the buffer. The packet is then removed from the end of the
		 * buffer. When destination is reached, add to packet memory the node it has
		 * been on and iterate process
		 */
		for (int hopCount = 0; hopCount < maxCount; hopCount++) {

			/*
			 * Print out the viewing window
			 */
			this.printCount(hopCount);

			/*
			 * Append checklist of packets for moving from end buffer
			 */
			ArrayList<packet> movePackets = new ArrayList<packet>();
			this.checkPacketHop(movePackets);
			/*
			 * Apply the checklist 
			 */
			this.applyPacketHop(movePackets);
		}

	}


	private void addPaths() {
		// List of available nodes for edge creation
		ArrayList<Integer> availableNodes = new ArrayList<Integer>();
		/*
		 * Add nodes to array
		 */
		for (int i = 0; i < noOfNodes; i++) {
			nodeArray.add(new node(i));
		}

		/*
		 * Apply paths to each node
		 */
		for (int i = 0; i < noOfNodes; i++) {
			// add all nodes
			for (int a = 0; a < noOfNodes; a++) {
				availableNodes.add(a);
			}
			// remove the node connected to itself
			availableNodes.remove(i);
			// Add connections if node connection less than 2 edges
			if (nodeArray.get(i).getPathLength() < 2) {
				// Then remove the nodes currently connected to it
				// bumping the value after every deletion
				int bump = 0;
				if (i != 0)
					for (int b = 0; b < nodeArray.get(i).getPathLength(); b++) {
						// System.out.println("Deleting " +
						// nodeArray.get(i).getPathIndex(b));
						availableNodes.remove(nodeArray.get(i).getPathIndex(b) - bump);
						bump++;
					}
				// At least one connection will always be made
				int totalNodes = availableNodes.size();
				for (int j = 0; j < totalNodes; j++) {
					// There is a available node choices / noOfNodes chance of
					// it taking the node
					boolean val = new Random().nextInt((totalNodes + 1) - availableNodes.size()) == 0;
					int k = new Random().nextInt((totalNodes) - j);
					// If by random chance it happens, make a connection
					if (val == true) {
						nodeArray.get(i).addPath(availableNodes.get(k));
						nodeArray.get(availableNodes.get(k)).addPath(i);
					}
					availableNodes.remove(k);
				}
			}
			availableNodes.clear();
		}

		// Add 2 client nodes
		// Source
		nodeArray.add(new node(noOfNodes));
		/* Apply random connection to Source*/
		int k = new Random().nextInt(noOfNodes);
		nodeArray.get(noOfNodes).addPath(k);
		nodeArray.get(k).addPath(noOfNodes);

		// Destination
		nodeArray.add(new node(noOfNodes + 1));
		/* Apply random connection to Destination*/
		k = new Random().nextInt(noOfNodes);
		nodeArray.get(noOfNodes + 1).addPath(k);
		nodeArray.get(k).addPath(noOfNodes + 1);
	}

	/* Display Path connections on console*/
	private void displayPaths() {
		System.out.println("Path Connections");
		for (int index = 0; index < noOfNodes; index++) {
			System.out.print("Node " + nodeArray.get(index).getNodeNumber() + ": " + nodeArray.get(index).getPaths() + " ");
		}
		System.out.print("Source Node: " + nodeArray.get(noOfNodes).getPaths());
		System.out.println(" Destination Node: " + nodeArray.get(noOfNodes + 1).getPaths());
	}
	
	/* Print the network layout at current hop count*/
	private void printCount(int hopCount) {
		if ((startTime <= hopCount) && startTime < endTime) {
			System.out.println();
			System.out.println("Packet Positions Count " + startTime + ":");
			System.out.println("Number of Duplicates:" + noOfDuplicates);
			for (int index = 0; index < noOfNodes; index++) {
				System.out.print("Node " + nodeArray.get(index).getNodeNumber() + ": Congestion Size: "
						+ nodeArray.get(index).getBufferLength() + ": ");
				nodeArray.get(index).printBufferMemory();
				System.out.println();
			}

			System.out.print("Node Source: ");
			nodeArray.get(noOfNodes).printBufferMemory();
			System.out.println();

			System.out.print("Node Destination: Size: " + nodeArray.get(noOfNodes + 1).getBufferLength() + ": ");
			nodeArray.get(noOfNodes + 1).printBufferMemory();
			System.out.println();

			startTime++;
		}
	}
	
	private void checkPacketHop(ArrayList<packet> movePackets) { 
		// If path is taken, check list to true for every count
		ArrayList<Boolean> pathTaken = new ArrayList<Boolean>();
		for (int index = 0; index < nodeArray.size(); index++)
			pathTaken.add(false);

		// Check Procedure
		// System.out.println("Array Size: " + nodeArray.size());
		// Check every node except the last for available paths
		for (int i = 0; i < nodeArray.size() - 1; i++) {
			// If there isnt anything in the buffer, skip
			if (nodeArray.get(i).getBufferLength() != 0) {
				packet tempPacket = nodeArray.get(i).getPacket(0);
				// If the path is not taken, to next node
				if (pathTaken.get(tempPacket.getNextDest()) == false) {
					pathTaken.set(tempPacket.getNextDest(), true);
					movePackets.add(tempPacket);
				}
			}
		}
	}
	
	private void applyPacketHop(ArrayList<packet> movePackets) throws CloneNotSupportedException {
		/* Check each packet in moveable packets for cloning/movement to new destination */
		for (int i = movePackets.size() - 1; i > -1; i--) {
			/* Get values of previous, current and next destination */
			int nextDest = movePackets.get(i).getNextDest();
			int originDest = movePackets.get(i).getCurrentNode();
			/* Previous destination used, mainly used for non memory check */
			int prevDest = movePackets.get(i).getPrevNode();

			for (int j = 0; j < nodeArray.get(nextDest).getPathLength(); j++) {
				int nextDestPath = nodeArray.get(nextDest).getPathIndex(j);
			
				/*
				 * For memory packets, will check every path packet has visited
				 */
				if (((movePackets.get(i).checkPaths(nextDestPath) == false) || (nextDest == noOfNodes + 1))
						&& movePackets.get(i).getHops() > 0) {
					
				/* 
				 * For non memory packets, will only check for previous path route
				 */
				//if(prevDest != nextDest && nextDestPath != noOfNodes
			    //&& movePackets.get(i).getHops() > 0){
					/*
					 * Clone Packet
					 */
					ArrayList<Integer> tempList = new ArrayList<Integer>(movePackets.get(i).getMemory());
					// Shallow copy arrayList
					packet tempPacket = movePackets.get(i).clone();
					// Add to memory of nodes visited and current node
					tempPacket.setMemory(tempList);
					tempPacket.setMemory(nextDest);
					tempPacket.setCurrentNode(nextDest);
					tempPacket.setPrevNode(originDest);
					//Increment duplicates
					noOfDuplicates++;
					// setup packets next path
					if (nextDest == (noOfNodes + 1) && originDest != noOfNodes + 1) {
						// use -1 if destination reached
						tempPacket.setNextDest(-1);
					} else {
						tempPacket.setNextDest(nextDestPath);
					}
					// Decrement hop counter
					tempPacket.decrementHops();

					// Add the new packet
					nodeArray.get(nextDest).addBuffer(tempPacket);
				}
			}
			// Remove old packet from 0
			nodeArray.get(originDest).removeBuffer(0);

		}
	}
	

}
