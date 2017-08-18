package stu.jzhang.algorithm.graph;

/**
 * Created by hellen on 8/17/17.
 */
public class PrimMST {
    private Edge[] edgeTo;
    private double distTo[];
    private boolean marked[];
    private IndexMinPQ<Double> pq;

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.getV()];
        distTo = new double[G.getV()];
        marked = new boolean[G.getV()];

        for(int v = 0; v < G.getV(); v++){
            distTo[v] = Double.MAX_VALUE;
        }
        pq = new IndexMinPQ<>(G.getV());
        visit(G, 0);
        while (!pq.isEmpty()){
            visit(G, pq.delMin());
        }
    }

    private void visit(EdgeWeightedGraph G, int v){
        marked[v] = true;
        for(Edge e : G.adj(v)){
            int w = e.other(v);
            if(marked[w]) continue;
            if(e.getWeight() < distTo[w]){
                edgeTo[w] = e;
                distTo[w] = e.getWeight();
                if(pq.contains(w)){
                    pq.changeKey(w, e.getWeight());
                }else{
                    pq.insert(w, e.getWeight());
                }
            }
        }
    }
}
