
public class test {

	/*
	 * Instructions for running the code is seen in the report. Set the view window and initialise a network to run it.
	 * 	Name: Mickey Wang
	 *    ID: 1569294
	 * */
	public static void main(String args[]) throws CloneNotSupportedException{
	
		/*
		 * Setup node size, packet hop count and maximum time count
		 * */
		NodeNetwork testnetwork = new NodeNetwork(5, 10, 1000);
		/*
		 * Set viewing window between start time and end time
		 * */
		testnetwork.setViewWindow(999, 1000);
		/* Run the network */
		testnetwork.run();
	}
}
