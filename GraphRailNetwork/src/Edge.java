
/**
 * Class representing an edge of a graph
 *
 * @author Bill Keller, modified by Charlotte Robinson
 * @version 2021
 */
public class Edge {
    private Vertex u, v; // need to store the two Vertices that make up our Edge (u and v)
    private String n; // need to store the name of our edge- for example "Victoria_Embankment"
    private String label = null; // need to store the edge label so we can mark edges as visited or cross edges, etc
    // label edges that have been visited as 'traversed', cross edges as 'crossedge' (note that
    // by this convention un-visited edges are simply null
    private final String TRVSD = "traversed";
    private final String XEDGE = "crossedge";

    /**
     * Construct a new edge with end vertices u and v and name n.
     * 
     * @param u vertex
     * @param v vertex
     * @param n name
     */
    public Edge(Vertex u, Vertex v, String n) {
        this.u = u;
        this.v = v;
        this.n = n;
    }
    
    /**
     * Get endpoint vertex u.
     * 
     * @return Vertex
     */
    public Vertex getVertex_u() {
        return u;
    }
    
    /**
     * Get endpoint vertex v.
     * 
     * @return Vertex
     */
    public Vertex getVertex_v() {
        return v;
    }
    
    /**
     * Get name of edge.
     * 
     * @return String
     */
    public String getName() {
        return n;
    }
    
    /**
     * Set name of edge.
     * 
     * @param n name of edge
     */
    public void setName(String n) {
        this.n = n;
    }
    
    /**
     * Get the position of the edge in the the incidence sequence associated with v. Returns -1
     * if the edge is not incident to v.
     * @param v - a vertex
     * @return int
     */
    public int getIndex(Vertex v) {
        return v.getIncidentEdges().indexOf(this);
    }
    
    /**
     * Mark an edge as 'traversed'. This is used to keep track of edges visited
     * during graph traversal.
     */
    protected void labelAsTraversed() {
        label = TRVSD;
    }
    
    /**
     * Mark an edge as a 'cross edge'. This is used to keep track of edges
     * visited during graph traversal.
     */
    protected void labelAsCrossEdge() {
        label = XEDGE;
    }
    
    /**
     * Check whether an edge has been visited or not. Visited means that the edge
     * has either been traversed or marked as a cross-edge. Used during graph 
     * traversal.
     * 
     * @return true or false
     */
    protected boolean isVisited() {
        return label != null;
    }
    
    /**
     * Check whether an edge has been traversed. Used during graph traversal.
     * 
     * @return true or false
     */
    protected boolean isTraversed() {
        return TRVSD.equals(label);
    }
    
    /**
     * Check whether an edge has been marked as a cross-edge. Used during graph
     * traversal.
     * 
     * @ return true or false
     */
    protected boolean isCrossEdge() {
        return XEDGE.equals(label);
    }

    /**
     * 'Unvisit' an edge. Reset the edge label to 'unvisited'. Used before graph
     * traversal to reset a graph.
     */
    protected void unVisit() {
        label = null;
    }
    
}
