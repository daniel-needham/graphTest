import java.util.ArrayList;

/**
 * Represents graph vertices.
 * 
 * @author Bill Keller, modified by Charlotte Robinson
 * @version 2021
 */
public class Vertex {
    private String name;
    private ArrayList<Edge> incidenceSeq; // for this implementation we will store incident edges in an ArrayList
    private String label = null; // need a place to store if this vertex has been visited or not
    private Edge followed = null;

    /**
     * Construct a new vertex
     * 
     * @param name the name of the vertex
     */
    public Vertex(String name) {
        this.name = name;
        incidenceSeq = new ArrayList<Edge>(); // initialise the list that will hold this vertex's incident edges
    }
    
    /**
     * Get the name of the vertex
     * 
     * @return name of the vertex
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of the vertex
     * 
     * @param n the vertex name
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * Returns a list of edges incident to the vertex
     * @return ArrayList<Edge>
     */
    public ArrayList<Edge> getIncidentEdges() {
        return incidenceSeq;
    }
    
    /**
     * Adds an edge to the incidence sequence.
     * @param e an edge
     */
    public void addIncidentEdge(Edge e) {
        incidenceSeq.add(e);
    }

    /**
     * Removes an edge from the incidence sequence. Returns true if the edge is removed and false if
     * the edge is not incident to the vertex.
     * @param e an edge
     * @return boolean
     */
    public boolean removeIncidentEdge(Edge e)
    /* NB: the remove() operation on ArrayList has O(n) running time.
     * To achieve O(1) runtime for edge removal (which is possible) we could have chosen something
     * like a doubly-linked list to represent incidence sequences. You do NOT need to fix
     * or optimise this, it is just worth noting and understanding this.
     */ {
        return incidenceSeq.remove(e); 
    }
    
    /**
     * Returns the degree of the vertex
     * @return int
     */
    public int degree() {
        return incidenceSeq.size();
    }
    
    /**
     * Mark a vertex as visited. This is used to keep track of visited 
     * vertices during graph traversal.
     */
    protected void labelAsVisited() {
        label = "visited";
    }
    
    /**
     * Check whether a vertex has been visited or not. Used during graph
     * traversal.
     * 
     * @return true or false
     */
    protected boolean isVisited() {
        return label != null;
    }
    
    /**
     * Set the 'followed edge'. This is used to record the edge that
     * was traversed in order to reach the vertex in a BFS traversal.
     * 
     * @param f the edge traversed to reach vertex in a BFS traversal.
     */
    protected void setFollowed(Edge f) {
        followed = f;
    }
    
    /** Get the 'followed edge'. 
     * 
     * @return Edge
     */
    protected Edge getFollowed() {
        return followed;
    }
    
    /**
     * 'Unvisit' a vertex. Used to reset the vertex to unvisited before carrying
     * out graph traversal.
     */
    protected void unVisit() {
        label = null;
        followed = null;
    }


}
