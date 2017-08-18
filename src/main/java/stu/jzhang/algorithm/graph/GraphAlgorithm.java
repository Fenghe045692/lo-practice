package stu.jzhang.algorithm.graph;

import java.util.*;

/**
 * Created by hellen on 8/14/17.
 */
public class GraphAlgorithm {
    public static void main(String[] args){
        GraphAlgorithm test = new GraphAlgorithm();
//        System.out.println(test.countComponents(5, new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}}));
//        for(List<String> item : test.findLadders("magic", "pearl",
//                new ArrayList<>(Arrays.asList(
//                        "flail","halon","lexus","joint","pears","slabs","lorie","lapse","wroth","yalow","swear",
//                        "cavil","piety","yogis","dhaka","laxer","tatum","provo","truss","tends","deana","dried","hutch",
//                        "basho","flyby","miler","fries","floes","lingo","wider","scary","marks","perry","igloo","melts",
//                        "lanny","satan","foamy","perks","denim","plugs","cloak","cyril","women","issue","rocky","marry",
//                        "trash","merry","topic","hicks","dicky","prado","casio","lapel","diane","serer","paige","parry",
//                        "elope","balds","dated","copra","earth","marty","slake","balms","daryl","loves","civet","sweat",
//                        "daley","touch","maria","dacca","muggy","chore","felix","ogled","acids","terse","cults","darla",
//                        "snubs","boats","recta","cohan","purse","joist","grosz","sheri","steam","manic","luisa","gluts",
//                        "spits","boxer","abner","cooke","scowl","kenya","hasps","roger","edwin","black","terns","folks",
//                        "demur","dingo","party","brian","numbs","forgo","gunny","waled","bucks","titan","ruffs","pizza",
//                        "ravel","poole","suits","stoic","segre","white","lemur","belts","scums","parks","gusts","ozark",
//                        "umped","heard","lorna","emile","orbit","onset","cruet","amiss","fumed","gelds","italy","rakes",
//                        "loxed","kilts","mania","tombs","gaped","merge","molar","smith","tangs","misty","wefts","yawns",
//                        "smile","scuff","width","paris","coded","sodom","shits","benny","pudgy","mayer","peary","curve",
//                        "tulsa","ramos","thick","dogie","gourd","strop","ahmad","clove","tract","calyx","maris","wants",
//                        "lipid","pearl","maybe","banjo","south","blend","diana","lanai","waged","shari","magic","duchy",
//                        "decca","wried","maine","nutty","turns","satyr","holds","finks","twits","peaks","teems","peace",
//                        "melon","czars","robby","tabby","shove","minty","marta","dregs","lacks","casts","aruba","stall",
//                        "nurse","jewry","knuth")))){
//            Utilies.printArrayList(item);

//        }
    }

    public int countComponents(int n, int[][] edges) {
        if(n < 1){
            return 0;
        }

        List<Integer>[] graph = generateGraph(edges, n);
        int count = 0;
        boolean[] mark = new boolean[n];
        for(int v = 0; v < n; v++){
            if(!mark[v]){
                count++;
                dfs(graph, v, mark);
            }
        }
        return count;
    }

    private void dfs(List<Integer>[] graph, int v, boolean[] mark){
        mark[v] = true;
        for(int s : graph[v]){
            if(!mark[s]){
                dfs(graph, s, mark);
            }
        }
    }

    private List<Integer>[] generateGraph(int[][] edges, int n){
        List<Integer>[] graph = new List[n];
        for(int[] edge : edges){
            if(graph[edge[0]] == null){
                graph[edge[0]] = new ArrayList<>();
            }

            if(graph[edge[1]] == null){
                graph[edge[1]] = new ArrayList<>();
            }

            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        return graph;
    }

    // classical two-end search BFS
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Map<String, List<String>> map = new HashMap<>();
        Set<String> beginSet = new HashSet<>(), endSet = new HashSet<>(), words = new HashSet<>(wordList);
        List<List<String>> rst = new ArrayList<>();

        if(!words.contains(endWord)){
            return rst;
        }
        beginSet.add(beginWord);
        endSet.add(endWord);

        boolean isEnd = false;
        boolean flip = false;
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            Set<String> temp = new HashSet<>();

            // avoid the cycle search
            words.removeAll(beginSet);
            words.removeAll(endSet);
            for (String word : beginSet) {
                char[] chs = word.toCharArray();
                for (int i = 0; i < chs.length; i++) {
                    char old = chs[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        String target = String.valueOf(chs);

                        // we use two-end way to construct the map
                        String key = flip ? target : word;
                        String val = flip ? word : target;
                        List<String> list = map.containsKey(key) ? map.get(key) : new ArrayList<>();

                        if (endSet.contains(target)) {
                            isEnd = true;

                            list.add(val);
                            map.put(key, list);
                            continue;
                        }

                        if (words.contains(target)) {
                            temp.add(target);

                            list.add(val);
                            map.put(key, list);
                        }
                        chs[i] = old;
                    }
                }
            }
            if(isEnd) break;
            beginSet = temp;
            flip = !flip;
            // we need switch to the other end to construct the map
            Set<String> set = beginSet;
            beginSet = endSet;
            endSet = set;
        }
        List<String> path = new ArrayList<>();
        path.add(beginWord);
        getSolution(rst, path, beginWord, endWord, map);
        return rst;
    }

    private void getSolution(List<List<String>> rst, List<String> path, String start, String end, Map<String, List<String>> map){
        if(start.equals(end)){
            rst.add(new ArrayList<>(path));
            return;
        }

        if(!map.containsKey(start)){
            return;
        }

        for(String word : map.get(start)){
            path.add(word);
            getSolution(rst, path, word, end, map);
            path.remove(path.size() -1);
        }
    }
    /**

    [       ["magic","manic","mania","maria","maris","marks","parks","perks","peaks","pears","pearl"]
            ["magic","manic","mania","maria","maris","paris","parks","perks","peaks","pears","pearl"],
            ["magic","manic","mania","maria","marta","marty","party","parry","perry","peary","pearl"],
            ["magic","manic","mania","maria","marta","marty","marry","merry","perry","peary","pearl"],
            ["magic","manic","mania","maria","marta","marty","marry","parry","perry","peary","pearl"],
           ]

            [["magic","manic","mania","maria","maris","paris","parks","perks","peaks","pears","pearl"],
            ["magic","manic","mania","maria","marta","marty","marry","parry","perry","peary","pearl"],
            ["magic","manic","mania","maria","marta","marty","marry","merry","perry","peary","pearl"],
            "magic","manic","mania","maria","marta","marty","party","parry","perry","peary","pearl"]]
     **/
}
