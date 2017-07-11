package stu.jzhang.algorithm.model;

/**
 *
 * It is a search tree.
 * Created by hellen on 7/10/17.
 */
public class Trie {

    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public boolean search(String word){
        if(word == null || word.length() == 0){
            return true;
        }
        TrieNode p = root;
        for(int i = 0; i < word.length(); i++){
            int idx = word.charAt(i) - 'a';
            if(p.children[idx] != null){
                p = p.children[idx];
            }else{
                return false;
            }
        }

        return p.isWord;
    }

    public boolean insert(String word){
        if(search(word)){
            return false;
        }

        TrieNode p = root;
        for(int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if(p.children[idx] == null){
                p.children[idx] = new TrieNode();

            }
            p.children[idx].numWords++;
            p = p.children[idx];
        }

        p.isWord = true;
        return true;
    }

    public boolean delete(String word){
        if(!search(word)){
            return false;
        }

        TrieNode p = root;
        for(int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if(p.children[idx].numWords == 1){
                p.children[idx] = null;
                return true;
            }
            p.children[idx].numWords--;
            p = p.children[idx];
        }

        p.isWord = false;

        return true;
    }

    public static class TrieNode{
        TrieNode[] children;
        boolean isWord;
        int numWords;

        public TrieNode() {
            children = new TrieNode[26];
        }
    }
}

