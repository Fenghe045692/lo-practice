package stu.jzhang.algorithm.graph;

import stu.jzhang.algorithm.model.MinPQ;

import java.util.Queue;

/**
 * Created by hellen on 8/17/17.
 */
public class LazyPrimMST {
    private Queue<Edge> mst;    // MST edges
    private boolean[] marked;   // MST vertices
    private MinPQ<Edge> pq;     // crossing(and ineligible) edges

    public LazyPrimMST(EdgeWeightedGraph G) {
        marked = new boolean[G.getV()];
        visit(G, 0);
        while(!pq.isEmpty()){
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if(marked[v] && marked[w]) continue;
            mst.add(e);
            if(!marked[v]) pq.insert(e);
            if(!marked[w]) pq.insert(e);

        }

    }

    private void visit(EdgeWeightedGraph G, int v){
        marked[v] = true;
        for(Edge e : G.adj(v)){
            if(!marked[e.other(v)]){
                pq.insert(e);
            }
        }
    }

    public Iterable<Edge> edges(){
        return mst;
    }
}
