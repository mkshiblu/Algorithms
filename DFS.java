import java.util.Arrays;

/**
 * Visit nodes with discovery & finished time
 * 
 * @author shiblu
 *
 */
public class DFS {

	private final int WHITE = 0; // White means neither visited nor discovered
	private final int GRAY = 1; // Gray means discovered but not visited
	private final int BLACK = 2; // Black means discovered and then visited

	int[][] graph;
	int[] color;
	int[] predecessor; // holds previous node from which the current node is
						// visited
	int[] d; 			// Discovery time
	int[] f; 			// Finishing time
	String dfsPath;		// holds space separated vertex in the order of visit
	
	int time;

	public DFS(int[][] graph) {

		this.graph = graph;
		color = new int[graph.length];
		predecessor = new int[graph.length];
		d = new int[graph.length];
		f = new int[graph.length];
		dfsPath = "";
	}

	public void dfs() {
		
		// For each vertex set color to white
		for (int i = 0; i < graph.length; i++) {
			color[i] = WHITE;

			// Set predecessor to nil
			predecessor[i] = -1;
		}

		// Reset the global time counter
		time = 0;

		// Do dfs visit for each node which are white
		for (int u = 0; u < graph.length; u++) {

			if (color[u] == WHITE) {
				dfsVisit(u);
			}
		}
		
		// Debug
		System.out.println("dfsPath: " + dfsPath);
		System.out.println("Predecessor : " + Arrays.toString(predecessor));
		System.out.println("Discovery Time: " + Arrays.toString(d));
		System.out.println("Finish Time: " + Arrays.toString(f));
	}

	private void dfsVisit(int u) {

		// Mark node as Discovered
		color[u] = GRAY;

		// Increment time by one
		time++;

		// Store time of discovery
		d[u] = time;

		// Explore other nodes reachable from u
		for (int v = 0; v < graph[u].length; v++) {

			// graph[u] is an array of adjacent nodes of u
			// If u and its neighbor v is connected then the matrix value would
			// be non-negative (1)
			// otherwise 0.
			if (graph[u][v] != 0 && color[v] == WHITE) {

				// v is visited from u, so store u as the previous node
				predecessor[v] = u;

				// Repeat for v
				dfsVisit(v);
			}
		}

		color[u] = BLACK; // Blacken u; it is finished.
		time++;

		// Store the finish time
		f[u] = time;
		
		// Do custom things on node u
		onVisit(u);
	}

	// called when the color the vertex is black i.e. it is visited
	private void onVisit(int u){

		// Append the node to the full dfs visit path
		dfsPath += u + " ";
	}
	

	public static void main(String[] args) {

		// Construct graph
		int[][] g = new int[][]{
			
				//0  1  2  3  4  5
		/*0*/	{ 0, 1, 0, 1, 0, 0},
		/*1*/	{ 0, 0, 0, 0, 1, 0},
		/*2*/	{ 0, 0, 0, 0, 1, 1},
		/*3*/	{ 0, 1, 0, 0, 0, 0},
		/*4*/	{ 0, 0, 0, 1, 0, 0},
		/*5*/	{ 0, 0, 0, 0, 0, 0},
		};
		
		
		DFS cc = new DFS(g);
		cc.dfs();
	}
}
