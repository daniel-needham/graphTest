import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class Graph provides an implementation of the GraphADT interface class.
 * drn22
 */
public class Graph implements GraphADT {
    // INSERT PRIVATE CLASS VARIABLES FOR YOUR GRAPH CLASS HERE
    // (think about what you need to store- vertices, edges, anything else?)
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;
    ArrayList<ArrayList<Vertex>> adjacencyList;
    Hashtable<String, Integer> vertexNameToIndex;
    int adjacencyListIndex;


    /**
     * Constructs a new graph object. The constructor takes a collection of vertices and
     * a collection of edges and initialises the graph. In particular, it instantiates the incidence
     * sequences associated with the vertices.
     *
     * @param vertices a list of vertices
     * @param edges    a list of edges
     */
    public Graph(ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
        // INSERT YOUR CODE HERE
        this.vertices = vertices;
        this.edges = edges;
        adjacencyList = new ArrayList<>();
        vertexNameToIndex = new Hashtable<>();
        adjacencyListIndex = 0;
        for (Edge e : edges) {
            Vertex u = e.getVertex_u();
            if (!u.getIncidentEdges().contains(e)) {
                u.addIncidentEdge(e);
            }
            Vertex v = e.getVertex_v();
            if (!v.getIncidentEdges().contains(v)) {
                v.addIncidentEdge(e);
            }
        }
        for (Vertex v : vertices) {
            addToAdjacencyList(v);
        }
    }

    /**
     * Constructs a new "blank" graph object.
     * This constructor should just initialise the internal storage of edges and vertices
     * for the Graph. (Note that creating a blank Graph object might be useful
     * if there is no vertices or edges to add to it yet).
     */
    public Graph() {
        // INSERT YOUR CODE HERE
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        adjacencyList = new ArrayList<>();
        vertexNameToIndex = new Hashtable<>();
        adjacencyListIndex = 0;
    }

    private void addToAdjacencyList(Vertex v) {
        vertexNameToIndex.put(v.getName(), adjacencyListIndex);
        ArrayList<Vertex> connected = new ArrayList<>();
        try {
            v.getIncidentEdges().forEach((edge -> {
                if (edge.getVertex_u().equals(v)) {
                    connected.add(edge.getVertex_v());
                } else {
                    connected.add(edge.getVertex_u());
                }
            }));
        } catch (Exception e) {
            System.err.println("no edges on vertex" + e);
        }
        adjacencyList.add(adjacencyListIndex, connected);
        adjacencyListIndex++;
    }

    /**
     * Insert a vertex with name n into the graph.
     *
     * @param n String
     * @return v Vertex that was added
     */
    public Vertex insertVertex(String n) {
        // INSERT YOUR CODE HERE
        Vertex v = new Vertex(n);
        vertices.add(v);
        addToAdjacencyList(v);
        return v;

    }

    /**
     * Remove the given vertex from the graph. The name of the removed vertex is returned, or
     * null if the graph does not contain the vertex.
     *
     * @param v Vertex
     * @return n String representing the name of the removed Vertex
     */
    public String removeVertex(Vertex v) {
        // INSERT YOUR CODE HERE
        if (!vertices.contains(v)) {
            return null;
        } else {
            try {
                adjacencyList.get(vertexNameToIndex.get(v.getName())).forEach(vertex -> {
                    adjacencyList.get(vertexNameToIndex.get(vertex.getName())).remove(v);
                });
            } catch (Exception e) {
                System.err.print("error deleting connected vertices" + e);
            }
            adjacencyList.set(vertexNameToIndex.get(v.getName()), new ArrayList<>());
            vertices.remove(v);

            ArrayList<Edge> incidentEdges = v.getIncidentEdges();
            try {
                for (Edge e: incidentEdges) {
                    removeEdge(e);
                }
            } catch (Exception e) {
                System.err.println("remove edge error" + e);
            }
            return v.getName();
        }

    }

    /**
     * Build a new edge with end vertices u and v and insert into the graph.
     * NB: This implementation assumes the vertices u and v must already be in the graph
     * for simplicity sake, so you do not have to do error handling.
     *
     * @param u Vertex
     * @param v Vertex
     * @param n the name of the Edge
     * @return e the Edge that was inserted into the Graph
     */
    public Edge insertEdge(Vertex u, Vertex v, String n) {
        // INSERT YOUR CODE HERE
        Edge e = new Edge(u, v, n);
        edges.add(e);
        /**/
        int index = vertexNameToIndex.get(u.getName());
        adjacencyList.get(index).add(v);
        u.addIncidentEdge(e);
        /**/
        adjacencyList.get(vertexNameToIndex.get(v.getName())).add(u);
        v.addIncidentEdge(e);

        return e;

    }

    /**
     * Remove the edge e from the graph. The name of the old edge is returned (or null if the edge is not
     * in the graph).
     *
     * @param e the edge to be removed
     * @return String name of the edge that was removed
     */
    public String removeEdge(Edge e) {
        // INSERT YOUR CODE HERE
        if (edges.contains(e)) {
            e.getVertex_u().removeIncidentEdge(e);
            e.getVertex_v().removeIncidentEdge(e);
            edges.remove(e);

            adjacencyList.get(vertexNameToIndex.get(e.getVertex_u().getName())).remove(e.getVertex_v());
            adjacencyList.get(vertexNameToIndex.get(e.getVertex_v().getName())).remove(e.getVertex_u());

            return e.getName();
        } else {
            return null;
        }
    }

    /**
     * Find and return the vertex opposite v in edge e.
     *
     * @param e an edge
     * @param w a vertex
     * @return Vertex that is opposite of the given vertex along edge e
     */
    public Vertex opposite(Edge e, Vertex w) {
        // INSERT YOUR CODE HERE
        Vertex result = (Vertex) null;
        if (e.getVertex_u().equals(w)) {
            result = e.getVertex_v();
        } else if (e.getVertex_v().equals(w)) {
            result = e.getVertex_u();
        }
        return result;
    }

    /**
     * Return an iterable collection containing of all of the vertices in the graph.
     *
     * @return ArrayList<Vertex>
     */
    public ArrayList<Vertex> vertices() {
        // INSERT YOUR CODE HERE
        return vertices;
    }

    /**
     * Return an iterable collection of all of the edges in the graph.
     *
     * @return ArrayList<Edge>
     */
    public ArrayList<Edge> edges() {
        // INSERT YOUR CODE HERE
        return edges;
    }

    /**
     * Checks whether two vertices are adjacent (i.e. joined by a single edge) or not.
     *
     * @param v a vertex
     * @param w a vertex
     * @return boolean, true if v and w are adjacent, false otherwise
     */
    public boolean areAdjacent(Vertex v, Vertex w) {
        // INSERT YOUR CODE HERE
        return adjacencyList.get(vertexNameToIndex.get(v.getName())).contains(w);
    }

    /**
     * Finds and returns the set of edges that are incident to a given vertex.
     *
     * @param v the vertex
     * @return ArrayList<Edge> a list of all edges incident on the given vertex
     */
    public ArrayList<Edge> incidentEdges(Vertex v) {
        // INSERT YOUR CODE HERE
        return v.getIncidentEdges();
    }

    /**
     * Rename vertex v as n
     *
     * @param v a vertex
     * @param n the new name
     * @return String name of Vertex that was over-written
     */
    public String rename(Vertex v, String n) {
        // INSERT YOUR CODE HERE
        String oldName = v.getName();
        v.setName(n);
        return oldName;
    }

    /**
     * Rename edge e as n
     *
     * @param e an edge
     * @param n the new name
     * @return String name of Edge that was over-written
     */
    public String rename(Edge e, String n) {
        // INSERT YOUR CODE HERE
        String oldName = e.getName();
        e.setName(n);
        return oldName;
    }

    /**
     * Perform a breadth-first search traversal of the graph (i.e, of the entire rail network).
     * This will work with either connected or unconnected graphs. It works by iterating through
     * the graph vertices and carrying out a breadth-first traversal for each unvisited vertex.
     *
     * @return a list of Vertices in order visited (so v will always be at index 0)
     */
    public ArrayList<Vertex> bfTraverse() {
        // INSERT YOUR CODE HERE
        ArrayList<Vertex> traversedList = new ArrayList<>();
        Queue<Vertex> traversalQueue = new LinkedList<>();
        for (Vertex v: vertices) {
            traversalQueue.add(v);
        }
        while (!traversalQueue.isEmpty()) {
            Vertex temp = traversalQueue.remove();
            adjacencyList.get(vertexNameToIndex.get(temp.getName())).forEach(vertex -> {
                if (!vertex.isVisited()) {
                    vertex.labelAsVisited();
                    traversalQueue.add(vertex);
                }
            });
            traversedList.add(temp);
        }
        labelAllNotVisited();
        return traversedList;
    }

    /**
     * Perform a breadth-first search traversal of the connected component of the graph that
     * starts at the given vertex v.
     *
     * @param v a vertex to start from
     * @return a list of Vertices in order visited (so v will always be at index 0)
     */
    public ArrayList<Vertex> bfTraverse(Vertex v) {
        // INSERT YOUR CODE HERE
        if (!vertices.contains(v)) { throw new NoSuchElementException("Element not found");}


        ArrayList<Vertex> traversedList = new ArrayList<>();
        Queue<Vertex> traversalQueue = new LinkedList<>();

        traversalQueue.add(v);
        v.labelAsVisited();
        while (!traversalQueue.isEmpty()) {
            Vertex temp = traversalQueue.remove();
            adjacencyList.get(vertexNameToIndex.get(temp.getName())).forEach(vertex -> {
                if (!vertex.isVisited()) {
                    vertex.labelAsVisited();
                    traversalQueue.add(vertex);
                }
            });
            traversedList.add(temp);
        }
        labelAllNotVisited();
        return traversedList;

    }

    /**
     * Return a list of all of the vertices reachable from the given 'start' vertex. This works
     * by simply performing a breadth-first search of the graph to label vertices. Any 'visited'
     * vertices have by definition been reached and are returned in a list.
     * (In other words,  return a list of all of the stations that can be reached
     * by rail when starting from v.
     *
     * @param v Vertex
     * @return a list of vertices that have been reached
     */
    public ArrayList<Vertex> allReachable(Vertex v) {
        // INSERT YOUR CODE HERE
        return bfTraverse(v);
    }

    /**
     * Determine whether the graph is connected, or not.
     * Note that an empty graph is by definition connected.
     * (in other words, return true if all the stations in the entire rail network
     * can be reached from one another, and false if not.)
     *
     * @return true if the graph is connected; false otherwise.
     */
    public boolean allConnected() {
        // INSERT YOUR CODE HERE
        if (vertices.isEmpty()) {return true;}

        AtomicBoolean result = new AtomicBoolean(true);
        ArrayList<Vertex> graph = bfTraverse(vertices.get(0));
        vertices.forEach(vertex -> {
            if (!graph.contains(vertex)) {
                result.set(false);
            }
        });
        return result.get();
    }

    /**
     * Use breadth-first search traversal to find a 'most direct route' between u and v.
     * In other words, given two stations u and v, return the path with the least amount of
     * stations between them; or return null to indicate that the two stations cannot
     * be reached from one another.
     * The path itself is represented as an ArrayList of Edge objects.
     *
     * @param u start vertex
     * @param v end vertex
     * @return an ArrayList of edges
     */
    public ArrayList<Edge> mostDirectRoute(Vertex u, Vertex v) {
        // INSERT YOUR CODE HERE
        if (!vertices.contains(v)) { throw new NoSuchElementException("Element not found");}


        ArrayList<Edge> traversedEdgeList = new ArrayList<>();
        Queue<Vertex> traversalQueue = new LinkedList<>();

        traversalQueue.add(u);
        u.labelAsVisited();
        boolean found = false;

        while (!traversalQueue.isEmpty() && !found) {
            Vertex temp = traversalQueue.remove();
            for (Vertex vertex : adjacencyList.get(vertexNameToIndex.get(temp.getName()))) {
                if (!vertex.isVisited()) {
                    vertex.labelAsVisited();
                    /*set followed edge*/
                    Edge followedEdge = null;
                    for (Edge edge : temp.getIncidentEdges()) {
                        if (edge.getVertex_u().equals(vertex) || edge.getVertex_v().equals(vertex)) {
                            followedEdge = edge;
                            System.out.println(followedEdge);
                        }
                    }
                    vertex.setFollowed(followedEdge);
                    traversalQueue.add(vertex);
                    if (vertex.equals(v)) {
                        found = true;
                    }
                }
            }
            ;


        }

        if (v.getFollowed() == null) {
            return null;
        } else {
            Edge iterE = v.getFollowed();
            Vertex iterV = v;
            while (iterE != null) {
                traversedEdgeList.add(iterE);
                iterV = v.equals(iterE.getVertex_u()) ? iterE.getVertex_v() : iterE.getVertex_u();
                iterE = iterV.getFollowed();
            }
            Collections.reverse(traversedEdgeList);
            labelAllNotVisited();
            return traversedEdgeList;
        }

    }

    /*
    / INSERT ALL PRIVATE METHODS USED BELOW THIS POINT
    / You are welcome to use as many private methods as necessary, but do not edit the types
    / or method header of any provided public methods.
    */

    public void printAdjacencyList() {
        for (Vertex v : vertices) {
            System.out.print(v.getName() + ": ");
            try {
                ArrayList<Vertex> arr = adjacencyList.get(vertexNameToIndex.get(v.getName()));
                arr.forEach(vertex -> {
                    System.out.print(vertex.getName() + ",");
                });
            } catch (Exception e) {
                System.err.print(e);
            }
            System.out.println();
        }
    }

    private void labelAllNotVisited() {
        vertices.forEach(vertex -> {
            vertex.unVisit();
        });
    }
}

