package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {

	/* STUDENTS:  You decide what data structure(s) to use to
	 * implement this class.
	 * 
	 * You may use any data structures you like, and any Java 
	 * collections that we learned about this semester.  Remember 
	 * that you are implementing a weighted, directed graph.
	 */
	private HashMap<V, HashMap<V, Integer>> graph;





	/* Collection of observers.  Be sure to initialize this list
	 * in the constructor.  The method "addObserver" will be
	 * called to populate this collection.  Your graph algorithms 
	 * (DFS, BFS, and Dijkstra) will notify these observers to let 
	 * them know how the algorithms are progressing. 
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;
	


	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		observerList = new ArrayList<>();
		graph = new HashMap<>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		if(!observerList.contains(observer)) {
			observerList.add(observer);
		}
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	public void addVertex(V vertex) {
		if (graph.containsKey(vertex)) {
			throw new IllegalArgumentException();
		}
		graph.put(vertex, new HashMap<>());
	}

	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		return graph.containsKey(vertex);
	}

	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if (containsVertex(from) && containsVertex(to) && weight>=0) {
			graph.get(from).put(to, weight);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if (containsVertex(from) && containsVertex(to)) {
			return (!graph.get(from).containsKey(to)) ? null : graph.get(from).get(to);
		} else {
			throw new IllegalArgumentException();
		}
		
	}


	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoBFS(V start, V end) {
		
		HashSet<V> visited = new HashSet<V>();
		LinkedList<V> discovered = new LinkedList<V>();
		
		//checks to see if the graph exists
		if (graph.size() == 0) {
			return;
		}

		//notifies that the search has begun
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyBFSHasBegun();
		}

		discovered.add(start);

		//processing the nodes until the all the nodes in the discovered list have been processed 
		while (!discovered.isEmpty()) {
			//gets and removes the head element
			V node = discovered.remove();
			//if the nodes have not been visited yet, they have to be processed
			if (!visited.contains(node)) {
				//add node to visited list
				visited.add(node);
				//the observers list is notified that the node has been visited
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(node);
				}
				//if the end is found, then the program should end
				if (node.equals(end)) {
					//notify that all of the nodes have been processed
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return;
				}
				/*
				 * check adjacencies to see if they have been visited, and if not add them to the discovered list
				 * to be processed
				 */
				for (V adjacency : graph.get(node).keySet()) {
					if (!visited.contains(adjacency)) {
						discovered.add(adjacency);
					}
				}
			}
			
		}
		
		
		
			
		
	}

	/** 
	 * <P>This method will perform a Depth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyDFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without visiting further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoDFS(V start, V end) {
		Map<V, Boolean> visited = new HashMap<V, Boolean>();
		Stack<V> discovered = new Stack<V>();
		
		//checks if there is a graph
		if (graph.size() == 0) {
			return;
		}
		
		//notify that the search has begun
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDFSHasBegun();
		}
		
		//currently, nothing is visited so all the values in the map should be false
		for(V key : graph.keySet()){
			visited.put(key, false);
		}
		
		discovered.push(start);
		
		//this loop is the heart of the DFS
		while (!discovered.isEmpty()) {
			V node = discovered.pop();
			
			//if node has not been visited, it has to be searched
			if (visited.get(node) == false) {
				//added to the visited map
				visited.put(node, true);
				
				//notifies all the observers that the node has been visited
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(node);
				}
				
				/*
				 * adjacent nodes are checked to see if they have been visited, and if not they are added to the 
				 * discovered Stack so that they can also be processed
				 */
				for (V adjacent : graph.get(node).keySet()) {
					if (!visited.get(adjacent)) {
						discovered.push(adjacent);
					}
				}
				//if the node is the last node, then the program should end
				if (node.equals(end)) {
					//notifying that the search is over
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					return;
				}
			}
		}
		
		
	}

	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through 
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this 
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		
		HashMap<V, V> predecessor = new HashMap<>(); //stores the preceding node that maximizes time
		HashMap<V, Integer> path = new HashMap<>(); //stores shortest path for each node
		Set<V> discovered = new HashSet<>(); //the set of finished nodes
		
		//check if the graph is of appropriate size
		if (graph.size() == 0) {
			return;
		}
		
		//notifies that the algorithm has begun
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraHasBegun();
		}
		
		path.put(start, 0);
		predecessor.put(start, start);
		
		//puts max values in the "path" map since all values in Dijsktra's formula start at infinite (or close to it)
		for (V value : graph.keySet()) {
			if (!value.equals(start)) {
				path.put(value, Integer.MAX_VALUE);
				predecessor.put(value, null);
			} 
			
		}
		
		HashMap<V, Integer> startMap = graph.get(start);
		
		//this is the first iteration of Disktra's, doing the algorithm from the start point
		for (V node : startMap.keySet()) {
			path.put(node, startMap.get(node));
			predecessor.put(node, start);
		}
		
		discovered.add(start);
		
		//notifying the observer list that the start vertex has been processed
		for(GraphAlgorithmObserver<V> observer : observerList){
			observer.notifyDijkstraVertexFinished(start, path.get(start));
		}
		
		//this method processes each vertex until all the vertices have been discovered
		while (discovered.size() < graph.size()) {
			
			//Find the smallest vertex
			V smallestVertex = start;
			int lowestWeight = Integer.MAX_VALUE;
			for(V key : path.keySet()){
				if(!discovered.contains(key) && path.get(key) < lowestWeight){
					lowestWeight = path.get(key);
					smallestVertex = key;
				}
			}

			discovered.add(smallestVertex);
			
			//notifying that the vertex has been properly processed
			for(GraphAlgorithmObserver <V> observer : observerList){
				observer.notifyDijkstraVertexFinished(smallestVertex, path.get(smallestVertex));
			}
			
			Map<V, Integer> vertexMap = graph.get(smallestVertex);

			/*
			 * This checks neighboring vertices to see if the path through that vertex would be shorter than 
			 * the current path
			 */
			for(V neighbor : vertexMap.keySet()){
				if(!discovered.contains(neighbor)){
					int neighborWeight = vertexMap.get(neighbor);
					if(path.get(smallestVertex) + neighborWeight < path.get(neighbor)){
						path.replace(neighbor, path.get(smallestVertex) + neighborWeight);
						predecessor.replace(neighbor, smallestVertex);
					}
				}
			}
		}
		
		//Stores the shortest path from the start to the end
		ArrayList<V> shortestPath = new ArrayList<>();
		
		V curr = end;
		shortestPath.add(end);
		
		//Populates the shortestPath array with the shortest path (in reverse order)
		while(curr != start){
			V prev = predecessor.get(curr);
			shortestPath.add(prev);
			curr = prev;
		}
				
		//reverses the array
		Collections.reverse(shortestPath);
		
		//lets the observers list know that the program is over
		for(GraphAlgorithmObserver<V> observer : observerList){
			observer.notifyDijkstraIsOver(shortestPath);
		}
		
	}
	
}
