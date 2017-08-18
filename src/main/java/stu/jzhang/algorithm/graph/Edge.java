package stu.jzhang.algorithm.graph;

/**
 * Created by hellen on 8/17/17.
 */
public class Edge implements Comparable<Edge> {
    private final int v;    // one vertex
    private final int w;    // the other vertex
    private final double weight;    // edge weight

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (v == vertex) {
            return w;
        } else if (w == vertex) {
            return v;
        } else {
            throw new RuntimeException("Inconsistent edge");
        }
    }

    public int compareTo(Edge other) {
        if (weight > other.weight) {
            return 1;
        } else if (weight < other.weight) {
            return -1;
        } else {
            return 0;
        }
    }
}
