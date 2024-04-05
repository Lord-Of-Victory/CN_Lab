import java.util.Arrays;
import java.util.Scanner;

class Graph {
    int[][] adjMatrix;
    int numOfvertices;

    Graph(int[][] mat, int v) {
        this.adjMatrix = mat;
        this.numOfvertices = v;
    }

    void addEdge(int src, int dest, int edgeWeight) {
        adjMatrix[src][dest] = edgeWeight;
        adjMatrix[dest][src] = edgeWeight;
    }
}

public class DijkstrasAlgorithm {
    public static int getClosestVertex(int[] distance, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIdx = -1;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] < min)
                if (visited[i] == false) {
                    min = distance[i];
                    minIdx = i;
                }
        }
        return minIdx;
    }

    public static int[] dijkstrasShortestPath(Graph g, int src) {
        // final shortest distance array
        int[] distance = new int[g.numOfvertices];
        // array to tell whether shortest distance of vertex has been found
        boolean[] visited = new boolean[g.numOfvertices];

        // initializing the arrays
        for (int i = 0; i < g.numOfvertices; i++) {
            distance[i] = Integer.MAX_VALUE;// initial distance is infinite
            visited[i] = false;// shortest distance for any node has not been found yet
        }
        distance[src] = 0;

        for (int i = 0; i < g.numOfvertices; i++) {
            int closestVertex = getClosestVertex(distance, visited);// get the closest node
            // if closest node is infinite distance away, it means that no other node can be
            // reached. So return
            if (closestVertex == Integer.MAX_VALUE)
                return distance;

            visited[closestVertex] = true;
            for (int j = 0; j < g.numOfvertices; j++) {
                if (visited[j] == false)// shortest distance of the node j should not have been finalized
                {
                    if (g.adjMatrix[closestVertex][j] != 0) {
                        int d = distance[closestVertex] + g.adjMatrix[closestVertex][j];
                        if (d < distance[j])// distance via closestVertex is less than the initial distance
                            distance[j] = d;
                    }
                }
            }
        }
        return distance;
    }

    public static void main(String[] args) {
        int numOfVertices = 6;
        int[][] adjMat = new int[6][6];
        Graph graph = new Graph(adjMat, numOfVertices);
        graph.addEdge(0, 1, 15);
        graph.addEdge(1, 2, 9);
        graph.addEdge(2, 3, 12);
        graph.addEdge(3, 4, 8);
        graph.addEdge(4, 0, 17);
        graph.addEdge(2, 4, 14);
        graph.addEdge(5, 2, 11);
        graph.addEdge(1, 5, 13);
        graph.addEdge(3, 5, 16);
        graph.addEdge(0, 5, 20);

        Scanner sc=new Scanner(System.in);


        System.out.println("Available Nodes: 0, 1, 2, 3, 4, 5");
        System.out.print("Enter Source: ");
        int source = sc.nextInt();
        System.out.print("Enter Destination: ");
        int destination = sc.nextInt();
        int[] dist = dijkstrasShortestPath(graph, source);
        System.out.print("Shortest Distance to all Nodes: ");
        System.out.print(Arrays.toString(dist));
        System.out.println();
        System.out.println("Shortest distance to Node "+destination+" from "+source+" is: "+dist[destination]);
        sc.close();
    }
}