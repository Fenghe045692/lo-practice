package stu.jzhang.algorithm.graph;

import stu.jzhang.algorithm.model.Bag;

/**
 * Created by hellen on 8/17/17.
 */
public class EdgeWeightedGraph {
        private final int V;    // number of vertices
        private int E;    // number of edges
        private Bag<Edge>[] adj;    // adjacency lists

        public EdgeWeightedGraph(int V) {
            this.V = V;
            E = 0;
            adj = (Bag<Edge>[]) new Bag[this.V];

            for(int v = 0; v < V; v++){
                adj[v] = new Bag<>();
            }
        }

        public int getV() {
            return V;
        }

        public int getE() {
            return E;
        }

        public void addEdge(Edge e){
            int v = e.either();
            int w = e.other(v);

            adj[v].add(e);
            adj[w].add(e);
            E++;
        }

        public Iterable<Edge> adj(int v){
            return adj(v);
        }

        /**
         * Returns all edges in this edge-weighted graph.
         * To iterate over the edges in this edge-weighted graph, use foreach notation:
         * {@code for (Edge e : G.edges())}.
         *
         * @return all edges in this edge-weighted graph, as an iterable
         */
        public Iterable<Edge> edges() {
            Bag<Edge> list = new Bag<>();
            for (int v = 0; v < V; v++) {
                int selfLoops = 0;
                for (Edge e : adj(v)) {
                    if (e.other(v) > v) {
                        list.add(e);
                    }
                    // only add one copy of each self loop (self loops will be consecutive)
                    else if (e.other(v) == v) {
                        if (selfLoops % 2 == 0) list.add(e);
                        selfLoops++;
                    }
                }
            }
            return list;
        }

        /**
         * Returns a string representation of the edge-weighted graph.
         * This method takes time proportional to <em>E</em> + <em>V</em>.
         *
         * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
         *         followed by the <em>V</em> adjacency lists of edges
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(V + " " + E + "\n");
            for (int v = 0; v < V; v++) {
                s.append(v + ": ");
                for (Edge e : adj[v]) {
                    s.append(e + "  ");
                }
                s.append("\n");
            }
            return s.toString();
        }
}
