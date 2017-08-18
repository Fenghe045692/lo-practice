package stu.jzhang.algorithm.graph;

import stu.jzhang.algorithm.model.MinPQ;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by hellen on 8/17/17.
 */
public class KruskalMST {
    private Queue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new LinkedList<>();
        MinPQ<Edge> pq = new MinPQ<>();
        for(Edge e : G.edges()){
            pq.insert(e);
        }

        UF uf = new UF(G.getV());
        while(!pq.isEmpty() && mst.size() < G.getV() - 1){
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if(!uf.connected(v, w)){
                mst.add(e);
                uf.union(v, w);
            }
        }
    }

    public Iterable<Edge> edges(){
        return mst;
    }
}
