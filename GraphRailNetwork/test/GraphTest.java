import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class GraphTest {
    /**
     * Test the program.Graph constructors
     */
    @Test
    public void constructor() {
        Random random = new Random();
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        //Create a load of random vertices
        for (int i = 0; i < 20; i++) vertices.add(new Vertex(randomString(10)));
        //Create a load of random edges between the vertices
        for (int i = 0; i < 50; i++) {
            Vertex vertex = vertices.get(random.nextInt(vertices.size()));
            Vertex vertex1 = vertices.get(random.nextInt(vertices.size()));
            edges.add(new Edge(vertex, vertex1, randomString(10)));
        }

        //Create the graph with the edges and vertices
        Graph graph = new Graph(vertices, edges);
        //Check that the edges and vertices lists match
        Assert.assertTrue(edges.size() == graph.edges().size() && edges.containsAll(graph.edges()) && graph.edges().containsAll(edges));
        Assert.assertTrue(vertices.size() == graph.vertices().size() && vertices.containsAll(graph.vertices()) && graph.vertices().containsAll(vertices));
    }

    /**
     * Test the program.Graph.insertVertex and program.Graph.removeVertex methods
     */
    @Test
    public void insertRemoveVertex() {
        //Create a new graph
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        for (int i = 0; i < 50; i++) {
            //Create a random vertex
            String name = randomString(10);
            Vertex vertex = graph.insertVertex(name);
            //Check that the vertex is added to the list
            Assert.assertEquals(vertex, graph.vertices().get(0));
            //Check that the correct name is returned
            String removed = graph.removeVertex(vertex);
            Assert.assertEquals(name, removed);
            //Check that the vertex is removed from the list
            Assert.assertEquals(0, graph.vertices().size());
        }
    }

    /**
     * Test the program.Graph.insertEdge and program.Graph.removeEdge methods
     */
    @Test
    public void insertRemoveEdge() {
        for (int i = 0; i < 50; i++) {
            ArrayList<Vertex> vertices = new ArrayList<>();
            ArrayList<Edge> edges = new ArrayList<>();
            Graph graph = new Graph(vertices,edges);

            //Create an edge between two random vertices
            Vertex vertex = graph.insertVertex(randomString(10));
            Vertex vertex1 = graph.insertVertex(randomString(10));
            String name = randomString(10);
            Edge edge = graph.insertEdge(vertex, vertex1, name);
            //Check that the edge has the right name
            Assert.assertEquals(name, edge.getName());
            //Check that the edge and vertices are added to the lists
            Assert.assertEquals(2, graph.vertices().size());
            Assert.assertEquals(1, graph.edges().size());
            //Check that the correct name is returned
            String removed = graph.removeEdge(edge);
            Assert.assertEquals(name, removed);
            //Check that only the edge is removed from the graph
            Assert.assertEquals(2, graph.vertices().size());
            Assert.assertEquals(0, graph.edges().size());
        }
    }

    /**
     * Test the program.Graph.opposite method
     */
    @Test
    public void opposite() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        for (int i = 0; i < 50; i++) {
            //Create three random vertices
            Vertex vertex = graph.insertVertex(randomString(10));
            Vertex vertex1 = graph.insertVertex(randomString(10));
            //Create an edge between two vertices
            Edge edge = graph.insertEdge(vertex, vertex1, randomString(10));
            //Check that the method returns the correct values
            Assert.assertFalse(graph.opposite(edge, vertex) == null);
            Assert.assertEquals(vertex1, graph.opposite(edge, vertex));
            Assert.assertEquals(vertex, graph.opposite(edge, vertex1));
        }
    }

    /**
     * Test the program.Graph.vertices method
     */
    @Test
    public void vertices() {
        final int VERTEX_COUNT = 50;
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        for (int i = 0; i < VERTEX_COUNT; i++) {
            //Add a new vertex to the graph
            vertices.add(graph.insertVertex(randomString(15)));
            //Check that the vertex is added to the list
            Assert.assertTrue(vertices.size() == graph.vertices().size() && vertices.containsAll(graph.vertices()) && graph.vertices().containsAll(vertices));
        }

        for (int i = 0; i < VERTEX_COUNT; i++) {
            //Remove a new vertex from the graph
            graph.removeVertex(vertices.remove(0));
            //Check that the vertex is removed from the list
            Assert.assertTrue(vertices.size() == graph.vertices().size() && vertices.containsAll(graph.vertices()) && graph.vertices().containsAll(vertices));
        }
    }

    /**
     * Test the program.Graph.edges method
     */
    @Test
    public void edges() {
        final int VERTEX_COUNT = 5;

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        for (int i = 0; i < VERTEX_COUNT; i++) {
            //Add a new edge to the graph
            Vertex vertex = graph.insertVertex(randomString(15));
            Vertex vertex1 = graph.insertVertex(randomString(15));
            edges.add(graph.insertEdge(vertex, vertex1, randomString(15)));
            //Check that the vertex is added to the list
            Assert.assertTrue(edges.size() == graph.edges().size() && edges.containsAll(graph.edges()) && graph.edges().containsAll(edges));
        }
        for (int i = 0; i < VERTEX_COUNT; i++) {
            //Remove an edge form the graph
            graph.removeEdge(edges.remove(0));
            //Check that the edge is removed from the list
            Assert.assertTrue(edges.size() == graph.edges().size() && edges.containsAll(graph.edges()) && graph.edges().containsAll(edges));
        }
    }

    /**
     * Test the program.Graph.areAdjacent method
     */
    @Test
    public void areAdjacent() {
        for (int i = 0; i < 50; i++) {
            ArrayList<Vertex> vertices = new ArrayList<>();
            ArrayList<Edge> edges = new ArrayList<>();
            Graph graph = new Graph(vertices,edges);

            //Add two vertices to the graph
            Vertex vertex = graph.insertVertex(randomString(15));
            Vertex vertex1 = graph.insertVertex(randomString(15));
            //Check that the vertices are not adjacent
            Assert.assertFalse(graph.areAdjacent(vertex, vertex1));
            //Connect the two vertices with an edge
            graph.insertEdge(vertex, vertex1, randomString(15));
            //Check that the vertices are adjacent
            Assert.assertTrue(graph.areAdjacent(vertex, vertex1));
        }
    }

    /**
     * Test the program.Graph.areAdjacent and the program.Graph.getEdge methods
     */
    @Test
    public void incidentEdges() {
        Random random = new Random();

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        //Store the vertices in an adjacency list
        HashMap<Vertex, ArrayList<Edge>> adjacent = new HashMap<>();
        //Add a load of vertices to the graph
        for (int i = 0; i < 30; i++)
            adjacent.put(graph.insertVertex(randomString(10)), new ArrayList<>());
        //Get the list of vertices
        vertices = graph.vertices();

        for (int i = 0; i < 50; i++) {
            //Connect two random vertices with an edge
            Vertex vertex, vertex1;
            do {
                //Pick two random vertices which haven't already been connected
                vertex = vertices.get(random.nextInt(vertices.size()));
                vertex1 = vertices.get(random.nextInt(vertices.size()));
            } while (graph.areAdjacent(vertex, vertex1) || vertex == vertex1);
            Edge edge = graph.insertEdge(vertex, vertex1, randomString(10));
            //Add the edge to the adjacency list
            adjacent.get(vertex).add(edge);
            adjacent.get(vertex1).add(edge);
        }

        //Check that the incidentEdges result matches the adjacency list
        for (Vertex vertex : vertices){
            Assert.assertEquals(adjacent.get(vertex), graph.incidentEdges(vertex));
        }
    }

    /**
     * Test the program.Graph.renameVertex method
     */
    @Test
    public void renameVertex() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        for (int i = 0; i < 50; i++) {
            //Make two random names
            String name = randomString(10);
            String name1 = randomString(10);
            //Make a vertex with the first name
            Vertex vertex = graph.insertVertex(name);
            //Check the name is set correctly
            Assert.assertEquals(name, vertex.getName());
            //Rename the vertex
            graph.rename(vertex, name1);
            //Check the new name is correct
            Assert.assertEquals(name1, vertex.getName());
        }
    }

    /**
     * Test the program.Graph.renameEdge method
     */
    @Test
    public void renameEdge() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        for (int i = 0; i < 50; i++) {
            //Make two random names
            String name = randomString(10);
            String name1 = randomString(10);
            //Make two random vertices
            Vertex vertex = graph.insertVertex(randomString(10));
            Vertex vertex1 = graph.insertVertex(randomString(10));
            //Make an edge with the first name
            Edge edge = graph.insertEdge(vertex, vertex1, name);
            //Check the name is set correctly
            Assert.assertEquals(name, edge.getName());
            //Rename the edge
            graph.rename(edge, name1);
            //Check the new name is correct
            Assert.assertEquals(name1, edge.getName());
        }
    }

    /**
     * Test the program.Graph.allReachable method
     */
    @Test
    public void allReachable() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        ArrayList<Vertex> localVertices = new ArrayList<>();
        //Add a vertex to the graph
        Vertex vertex = graph.insertVertex(randomString(15));
        localVertices.add(vertex);

        for (int i = 0; i < 20; i++) {
            //Set the next vertex to the last added vertex
            Vertex vertex1 = vertex;
            //Add a new vertex
            vertex = graph.insertVertex(randomString(15));
            localVertices.add(vertex);
            //Connect the two vertices with an edge
            graph.insertEdge(vertex, vertex1, randomString(10));
            //Check that all the vertices in the graph are reachable
            ArrayList<Vertex> reachable = graph.allReachable(vertex);
            localVertices.remove(vertex);
            for (Vertex v : localVertices) {
                Assert.assertTrue(reachable.contains(v));
            }
        }
    }

    /**
     * Test the program.Graph.allConnected method
     */
    @Test
    public void allConnected() {
        Random random = new Random();
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        //Add a vertex to the graph
        Vertex vertex = graph.insertVertex(randomString(15));
        for (int i = 0; i < 20; i++) {
            //Set the next vertex to the last added vertex
            Vertex vertex1 = vertex;
            //Add a new vertex
            vertex = graph.insertVertex(randomString(15));
            //Connect the two vertices with an edge
            graph.insertEdge(vertex, vertex1, randomString(10));
            //Check that the graph is all connected
            Assert.assertTrue(graph.allConnected());
        }

        //Remove a random edge
        int x = random.nextInt(graph.edges().size());
        Edge a = graph.edges().get(x);
        graph.removeEdge(a);

        //Check that the graph is no longer connected
        Assert.assertFalse(graph.allConnected());
    }

    /**
     * Test the program.Graph.mostDirectRoute method
     */
    @Test
    public void mostDirectRoute() {

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        //Add vertices to the graph
        Vertex a = graph.insertVertex("a");
        Vertex b = graph.insertVertex("b");
        Vertex c = graph.insertVertex("c");
        Vertex d = graph.insertVertex("d");
        Vertex e = graph.insertVertex("e");
        Vertex f = graph.insertVertex("f");

        //Add the edges to the graph
        Edge fd = graph.insertEdge(f, d, "fd");
        Edge de = graph.insertEdge(d, e, "de");
        Edge dc = graph.insertEdge(d, c, "dc");
        Edge cb = graph.insertEdge(c, b, "cb");
        Edge eb = graph.insertEdge(e, b, "eb");
        Edge ea = graph.insertEdge(e, a, "ea");
        Edge ab = graph.insertEdge(a, b, "ab");

        //Check that the correct path is produced for the route f->a
        ArrayList<Edge> expected = new ArrayList<>();
        expected.add(fd);
        expected.add(de);
        expected.add(ea);
        ArrayList<Edge> actual = graph.mostDirectRoute(f, a);
        Assert.assertEquals(expected, actual);

        //Check that the correct path is produced for the route a->c
        expected = new ArrayList<>();
        expected.add(ab);
        expected.add(cb);
        Assert.assertEquals(expected, graph.mostDirectRoute(a, c));

        //Check that the correct path is produced for the route e->b
        expected = new ArrayList<>();
        expected.add(eb);
        Assert.assertEquals(expected, graph.mostDirectRoute(e, b));
    }

    /**
     * Test the program.Graph.mostDirectRoute method
     */
    @Test
    public void mostDirectRouteLondon() {

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        //Add vertices to the graph
        Vertex bondStreet = graph.insertVertex("Bond Street");
        Vertex oxfordCircus = graph.insertVertex("Oxford Circus");
        Vertex greenPark = graph.insertVertex("Green Park");
        Vertex victoria = graph.insertVertex("Victoria");
        Vertex westminster = graph.insertVertex("Westminster");
        Vertex piccadillyCircus = graph.insertVertex("Piccadilly Circus");
        Vertex warrenStreet = graph.insertVertex("Warren Street");
        Vertex goodgeStreet = graph.insertVertex("Goodge Street");
        Vertex tottenhamCourtRoad = graph.insertVertex("Tottenham Court Road");
        Vertex leicesterSquare = graph.insertVertex("Leicester Square");
        Vertex charingCross = graph.insertVertex("Charing Cross");
        Vertex embankment = graph.insertVertex("Embankment");

        //Add the edges to the graph
        Edge edge1 = graph.insertEdge(bondStreet, oxfordCircus, "edge1");
        Edge edge2 = graph.insertEdge(bondStreet, greenPark, "edge2");
        Edge edge3 = graph.insertEdge(oxfordCircus, warrenStreet, "edge3");
        Edge edge4 = graph.insertEdge(oxfordCircus, tottenhamCourtRoad, "edge4");
        Edge edge5 = graph.insertEdge(oxfordCircus, piccadillyCircus, "edge5");
        Edge edge6 = graph.insertEdge(oxfordCircus, greenPark, "edge6");
        Edge edge7 = graph.insertEdge(greenPark, piccadillyCircus, "edge7");
        Edge edge8 = graph.insertEdge(greenPark, westminster, "edge8");
        Edge edge9 = graph.insertEdge(greenPark, victoria, "edge9");
        Edge edge10 = graph.insertEdge(victoria, westminster, "edge10");
        Edge edge11 = graph.insertEdge(westminster, embankment, "edge11");
        Edge edge12 = graph.insertEdge(embankment, charingCross, "edge12");
        Edge edge13 = graph.insertEdge(charingCross, piccadillyCircus, "edge13");
        Edge edge14 = graph.insertEdge(charingCross, leicesterSquare, "edge14");
        Edge edge15 = graph.insertEdge(piccadillyCircus, leicesterSquare, "edge15");
        Edge edge16 = graph.insertEdge(leicesterSquare, tottenhamCourtRoad, "edge16");
        Edge edge17 = graph.insertEdge(tottenhamCourtRoad, goodgeStreet, "edge17");
        Edge edge18 = graph.insertEdge(goodgeStreet, warrenStreet, "edge18");

        //Check that the correct path is produced for the route bondStreet to embankment
        ArrayList<Edge> expected = new ArrayList<>();
        expected.add(edge2);
        expected.add(edge8);
        expected.add(edge11);
        ArrayList<Edge> actual = graph.mostDirectRoute(bondStreet, embankment);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void traverse() {
        //program.Graph based off this image from Wikipedia:
        // https://commons.wikimedia.org/wiki/File:6n-graf.png
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        //Add vertices to the graph
        Vertex i = graph.insertVertex("i");
        Vertex h = graph.insertVertex("h");
        Vertex a = graph.insertVertex("a");
        Vertex b = graph.insertVertex("b");
        Vertex c = graph.insertVertex("c");
        Vertex j = graph.insertVertex("j");
        Vertex d = graph.insertVertex("d");
        Vertex e = graph.insertVertex("e");
        Vertex f = graph.insertVertex("f");
        Vertex g = graph.insertVertex("g");

        //Add the edges to the graph
        //Disconnect gh and ij from the rest of the graph
        graph.insertEdge(e, a, "ea");
        graph.insertEdge(f, d, "fd");
        graph.insertEdge(d, e, "de");
        graph.insertEdge(d, c, "dc");
        graph.insertEdge(i, j, "ij");
        graph.insertEdge(c, b, "cb");
        graph.insertEdge(e, b, "eb");
        graph.insertEdge(a, b, "ab");
        graph.insertEdge(g, h, "gh");

        // checking bfTraverse()
        // it should traverse all 10 vertices including Disconnect components (gh and ij)
        ArrayList<Vertex> arr = graph.bfTraverse();
        Assert.assertEquals(graph.vertices().size(), arr.size());
        for (Vertex v : vertices){
            Assert.assertTrue(arr.contains(v));
        }

    }

    @Test
    public void traverse_v() {
        //program.Graph based off this image from Wikipedia:
        // https://commons.wikimedia.org/wiki/File:6n-graf.png
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(vertices,edges);

        //Add vertices to the graph
        Vertex i = graph.insertVertex("i");
        Vertex h = graph.insertVertex("h");
        Vertex a = graph.insertVertex("a");
        Vertex b = graph.insertVertex("b");
        Vertex c = graph.insertVertex("c");
        Vertex j = graph.insertVertex("j");
        Vertex d = graph.insertVertex("d");
        Vertex e = graph.insertVertex("e");
        Vertex f = graph.insertVertex("f");
        Vertex g = graph.insertVertex("g");

        //Add the edges to the graph
        //Disconnect gh and ij from the rest of the graph
        graph.insertEdge(e, a, "ea");
        graph.insertEdge(f, d, "fd");
        graph.insertEdge(d, e, "de");
        graph.insertEdge(d, c, "dc");
        graph.insertEdge(i, j, "ij");
        graph.insertEdge(c, b, "cb");
        graph.insertEdge(e, b, "eb");
        graph.insertEdge(a, b, "ab");
        graph.insertEdge(g, h, "gh");

        // checking bfTraverse(Vertex v)
        ArrayList<Vertex> arr = graph.bfTraverse(f);
        Assert.assertEquals(6, arr.size());
        Assert.assertTrue(arr.indexOf(f) < arr.indexOf(d));
        Assert.assertTrue(arr.indexOf(d) < arr.indexOf(c));
        Assert.assertTrue(arr.indexOf(d) < arr.indexOf(e));
        Assert.assertTrue(arr.indexOf(e) < arr.indexOf(b));

    }


    /**
     * Generate a random string of a given length
     *
     * @param length    The length of the string to generate
     * @return          The random string
     */
    private String randomString(final int length) {
        //The alphabet to use
        final String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
        final int alphabetLength = alphabet.length();
        Random random = new Random();

        //Create a string of the correct length using random characters
        StringBuilder string = new StringBuilder();
        for (int j = 0; j < length; j++) string.append(alphabet.charAt(random.nextInt(alphabetLength)));

        //Return the string
        return string.toString();
    }
}
