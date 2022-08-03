package graph;
import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/** 
 * <P>The MazeGraph is an extension of WeightedGraph.  
 * The constructor converts a Maze into a graph.</P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/* STUDENTS:  SEE THE PROJECT DESCRIPTION FOR A MUCH
	 * MORE DETAILED EXPLANATION ABOUT HOW TO WRITE
	 * THIS CONSTRUCTOR
	 */
	
	/** 
	 * <P>Construct the MazeGraph using the "maze" contained
	 * in the parameter to specify the vertices (Junctures)
	 * and weighted edges.</P>
	 * 
	 * <P>The Maze is a rectangular grid of "junctures", each
	 * defined by its X and Y coordinates, using the usual
	 * convention of (0, 0) being the upper left corner.</P>
	 * 
	 * <P>Each juncture in the maze should be added as a
	 * vertex to this graph.</P>
	 * 
	 * <P>For every pair of adjacent junctures (A and B) which
	 * are not blocked by a wall, two edges should be added:  
	 * One from A to B, and another from B to A.  The weight
	 * to be used for these edges is provided by the Maze. 
	 * (The Maze methods getMazeWidth and getMazeHeight can
	 * be used to determine the number of Junctures in the
	 * maze. The Maze methods called "isWallAbove", "isWallToRight",
	 * etc. can be used to detect whether or not there
	 * is a wall between any two adjacent junctures.  The 
	 * Maze methods called "getWeightAbove", "getWeightToRight",
	 * etc. should be used to obtain the weights.)</P>
	 * 
	 * @param maze to be used as the source of information for
	 * adding vertices and edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {
		super();
		//initializes vertices at all of junctures on the map
		for (int i = 0; i < maze.getMazeWidth(); i++) {
			for (int j = 0; j < maze.getMazeHeight(); j++) {

				Juncture vertex = new Juncture(i, j);
				this.addVertex(vertex);
			}
		}
		
		//iterates through the whole maze at every juncture and adds edges when necessary
		for (int i = 0; i < maze.getMazeWidth(); i++) {
			for (int j = 0; j < maze.getMazeHeight(); j++) {
				
				//creates new vertex
				Juncture vertex = new Juncture(i, j);

				//makes edges on all values above the current vertex
				if (j > 0) {
					Juncture above = new Juncture(i, j - 1);
					//checks if wall is above vertex, and if not makes an edge
					if (!maze.isWallAbove(vertex)) {
						super.addEdge(above, vertex, maze.getWeightBelow(above));
						super.addEdge(vertex, above, maze.getWeightAbove(vertex));
					}
				}
				
				//makes edges on all values below the current vertex
				if (j < maze.getMazeHeight()-1) {
					Juncture below = new Juncture(i, j + 1);
					//checks if wall is below vertex, and if not makes an edge
					if (!maze.isWallBelow(vertex)) {
						super.addEdge(below, vertex, maze.getWeightAbove(below));
						super.addEdge(vertex, below, maze.getWeightBelow(vertex));
					}
				}
				
				//makes edges on all values to the right of the current vertex
				if (i < maze.getMazeWidth() - 1) {
					Juncture right = new Juncture(i + 1, j);
					//checks if wall is to the right of the vertex, and if not makes an edge
					if (!maze.isWallToRight(vertex)) {
						super.addEdge(right, vertex, maze.getWeightToLeft(right));
						super.addEdge(vertex, right, maze.getWeightToRight(vertex));
					}
				}
				
				//makes edges on all values to the left of the current vertex
				if (i > 0) {
					Juncture left = new Juncture(i - 1, j);
					//checks if wall is to the left of the vertex, and if not makes an edge
					if (!maze.isWallToLeft(vertex)) {
						super.addEdge(left, vertex, maze.getWeightToRight(left));
						super.addEdge(vertex, left, maze.getWeightToLeft(vertex));
					}
				}

				
			}
		}
		
		
	}
}
