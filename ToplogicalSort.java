import java.util.Arrays;
import java.util.LinkedList;

/**
 * A topological sort of a DAG (Directed Acyclic Graph) G =(V, E) is a linear ordering of all its
 * vertices such that if G contains an edge (u, v), then u appears before v in
 * the ordering. (If the graph is not acyclic, then no linear ordering is
 * possible.) A topological sort of a graph can be viewed as an ordering of its
 * vertices along a horizontal line so that all directed edges go from left to
 * right. Topological sorting is thus different from the usual kind of "sorting".
 * In other words, topological sort is the sorted order of each vertex's decreasing finish time.
 * ========= Algo ========
 * TopologicalSort (G)
 * 	1. Call DFS to compute finishing times for each vertex
 * 	2. As each vertex is finished, insert it into the front of a linked list.
 * 	3. return the linked list of vertices.
 */
public class ToplogicalSort {

	private final int WHITE = 0; // White means neither visited nor discovered
	private final int GRAY = 1; // Gray means discovered but not visited
	private final int BLACK = 2; // Black means discovered and then visited

	int[][] graph;
	int[] color;
	int[] predecessor; // holds previous node from which the current node is
						// visited
	int[] d; 			// Discovery time
	int[] f; 			// Finishing time
	int time;
	
	String dfsPath;		// holds space separated vertex in the order of visit
	private LinkedList<Integer> topoList; // holds the sorted vertex list in LIFO order of visit
	
	public ToplogicalSort(int[][] graph) {

		this.graph = graph;
		color = new int[graph.length];
		predecessor = new int[graph.length];
		d = new int[graph.length];
		f = new int[graph.length];
		dfsPath = "";
		topoList =  new LinkedList<Integer>();
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

	public LinkedList<Integer> topoSort(){
		
		// Call dfs to find finishing time for each vertex
		dfs();
		
		return topoList;
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
		
		// for topo
		onVisit(u);
	}

	// called when the color the vertex is black i.e. it is visited
	private void onVisit(int u){

		// Append the node to the full dfs visit path
		dfsPath += u + " ";
		
		// Add to the head of the topological answer
		topoList.addFirst(u);
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
		
		
		ToplogicalSort ts = new ToplogicalSort(g);
		LinkedList<Integer> topo = ts.topoSort();
		System.out.println("topo sort :" + topo);
	}

}
