import jdk.swing.interop.SwingInterOpUtils;

public class Graph1 {
    public static void main(String[] args) {
        Graph graph = new Graph();

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

        graph.printAdjacencyList();

        System.out.println(graph.removeVertex(bondStreet));
        System.out.println();
        graph.printAdjacencyList();
        graph.bfTraverse(victoria).forEach( vertex -> {
            System.out.print(vertex.getName() + ", ");
        });

        System.out.println(graph.mostDirectRoute(warrenStreet, tottenhamCourtRoad));

    }
}
