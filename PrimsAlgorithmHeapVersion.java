/**
 *
 * @author Pipjak
 */


import java.util.*;
import java.io.*;
import java.lang.*;


public class PrimsAlgorithmHeapVersion {

    /* fields */
    
    static int numVertices;
    
    // custom comparator used in priority queues
    static Comparator cust = (Comparator<Edge>) (Edge o1, Edge o2) -> {
        Integer a = o1.weight;
        Integer b = o2.weight;
        
        return a.compareTo(b);
    };
    
    // global Node[] with nodes of a graph
    Node[] map;
    
    // global hash map to keep edges from visited to unvisited:
    PriorityQueue<Edge> edge;
    
    // what is the weight of a spanning tree
    long spanTreeWeight;
    
    /* constructor */
    private PrimsAlgorithmHeapVersion(){
    
        this.spanTreeWeight = 0;
            
        this.map = new Node[PrimsAlgorithmHeapVersion.numVertices];
        this.edge = new PriorityQueue(PrimsAlgorithmHeapVersion.numVertices,this.cust);
    }
    
    /* methods */
    
    // search the next edge 
    private Edge searchNextEdge(){
    
        Edge result =null;
        Edge temp;
        
        while(!this.edge.isEmpty()){
        
            temp = this.edge.poll();
            
            if(!temp.A.visited ^ !temp.B.visited){
                
                spanTreeWeight += temp.weight;
                result = temp;
                break;
            }
        }
        
      return result;
    }
    
    // by an assignment the graph is connected
    private void prim(){
        
        Node tempA;
        Edge tempE;
        
        // initial conditions, it does not matter where I start
        tempA = this.map[0];
        this.edge.addAll(tempA.pq);
        tempA.visited();
        
        
        while(true){
            
            // searching the next edge in the global min-heap
            tempE = searchNextEdge();
            
            if(tempE!=null){
                
                if(!tempE.A.visited){
                    
                    this.edge.addAll(tempE.A.pq);
                    tempE.A.visited();
                }
                else{
                    
                    this.edge.addAll(tempE.B.pq);
                    tempE.B.visited();
                }
            }
            else
                break;
            
        }
    }
    
    /* private local clases */
    
    private class Node{
    
        /* private local fields of node */
        
        // every node will held pq of other nodes
        PriorityQueue<Edge> pq;
        
        // visited?
        boolean visited;
        
        /* constructor */
        private Node(){
            
            this.pq = new PriorityQueue(50, PrimsAlgorithmHeapVersion.cust);
            this.visited = false;
        }
        
        /* methods */
        
        private void visited(){
            this.visited = true;
        }
        
        private void addEdge(Edge edo){
        
            this.pq.add(edo);
        }
        
    }
    
    private class Edge{
        
        /* private local fields of edge */
        
        Node A;
        Node B;
        int weight;
        
        /* constructor */
        
        /* methods */
        
        private void putEdge(Node A, Node B, int weight){
        
            this.A = A;
            this.B = B;
            this.weight = weight;
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        // reading the number of vertices
        PrimsAlgorithmHeapVersion.nVert();
        
        // creating the class
        PrimsAlgorithmHeapVersion g = new PrimsAlgorithmHeapVersion();
        g.run();
        g.prim();
        
        System.out.println("The weight of MST is " + g.spanTreeWeight);
    }
    
    /* reading methods */
    
    // read in number of vertices
    public static void nVert() throws IOException {
    
    try{
        File file = new File("edges.txt");
        FileReader fileReader = new FileReader(file);
            
        BufferedReader bufferedReader = new BufferedReader(fileReader);    
        StringBuffer stringBuffer = new StringBuffer();
            
        String line;
        String[] arr;
            
        line = bufferedReader.readLine();
        arr = line.split("\\s+");
        
        // fillint the number of vertices;
        PrimsAlgorithmHeapVersion.numVertices = Integer.parseInt(arr[0]);
        
        // closing the fileReader:
        fileReader.close();
    
    
    }catch (IOException e) {
             e.printStackTrace();
        }
    
    }
    
    
    // the read-in and run methods:
    public void run() throws IOException {
        
    try {
        
        Edge edge;
        Node nodeA, nodeB;
        
        File file = new File("edges.txt");
        FileReader fileReader = new FileReader(file);
            
        BufferedReader bufferedReader = new BufferedReader(fileReader);    
        StringBuffer stringBuffer = new StringBuffer();
            
        String line;
            
        // reading the first line = number of jobs and skipping it since
        // I do not need that information
        
        line = bufferedReader.readLine();
        
        // read a line
        while ((line = bufferedReader.readLine()) != null ) {
                
            int idA,idB,weight;
                
            String[] arr = line.split("\\s+");
                
            // Node A, Node B and weight are
            idA = Integer.parseInt(arr[0])-1;
            idB = Integer.parseInt(arr[1])-1;
            weight = Integer.parseInt(arr[2]);
                    
            // creating Edge and Node instances
            edge = new Edge();
            
            // if not created creating a node instance
            if(this.map[idA]==null)
                this.map[idA] = new Node();
            
            if(this.map[idB]==null)
                this.map[idB] = new Node();
                
            
            // filling up those instances depends whether you saw that id or not
            edge.putEdge(this.map[idA], this.map[idB], weight);
            
            this.map[idA].addEdge(edge);
            this.map[idB].addEdge(edge);
            
            
        }
            
        // closing the fileReader:
        fileReader.close();
            
        }catch (IOException e) {
             e.printStackTrace();
        }
    }
}
