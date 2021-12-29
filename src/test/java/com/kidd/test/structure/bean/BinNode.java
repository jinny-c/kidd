package com.kidd.test.structure.bean;

import java.util.*;

/**
 * @description TODO
 * @auth chaijd
 * @date 2021/12/29
 */
public class BinNode {
    private Object element;
    private BinNode lChild;// 定义指向左子树的指针
    private BinNode rChild;// 定义指向右子树的指针

    public BinNode(Object element, BinNode lChild, BinNode rChild) {
        this.element = element;
        this.lChild = lChild;
        this.rChild = rChild;
    }


    private static List<BinNode> list = new ArrayList<>();

    /**
     * 对该二叉树进行前序遍历 结果存储到list中 前序遍历
     */
    public static void preOrder(BinNode node) {
        list.add(node); // 先将根节点存入list
        // 如果左子树不为空继续往左找，在递归调用方法的时候一直会将子树的根存入list，这就做到了先遍历根节点
        if (node.lChild != null) {
            preOrder(node.lChild);
        }
        // 无论走到哪一层，只要当前节点左子树为空，那么就可以在右子树上遍历，保证了根左右的遍历顺序
        if (node.rChild != null) {
            preOrder(node.rChild);
        }
    }

    /**
     * 对该二叉树进行中序遍历 结果存储到list中
     */
    public static void inOrder(BinNode node) {
        if (node.lChild != null) {
            inOrder(node.lChild);
        }
        list.add(node);
        if (node.rChild != null) {
            inOrder(node.rChild);
        }
    }

    /**
     * 对该二叉树进行后序遍历 结果存储到list中
     * DFS(深度优先搜索)
     */
    public static void postOrder(BinNode node) {
        if (node.lChild != null) {
            postOrder(node.lChild);
        }
        if (node.rChild != null) {
            postOrder(node.rChild);
        }
        list.add(node);
    }
    /**
     * DFS(深度优先搜索)
     */
    public static void dfs(BinNode node) {
        if(node==null){
            return;
        }
        if (node.lChild != null) {
            postOrder(node.lChild);
        }
        if (node.rChild != null) {
            postOrder(node.rChild);
        }
    }
    /**
     * BFS（广度优先搜索）
     */
    public static void bfs(BinNode node) {
        //Queue<BinNode> queue = new LinkedList<>();
        Queue<BinNode> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()){
            BinNode tempNode = queue.poll();
            if (tempNode.lChild != null) {
                queue.add(tempNode.lChild);
            }
            if (tempNode.rChild != null) {
                queue.add(tempNode.rChild);
            }
        }
    }

    /**
     * 层次历遍
     */
    public static List<List<Object>> levelOrder(BinNode root) {
        List<List<Object>> res = new ArrayList<>();
        Queue<BinNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Object> level = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                BinNode node = queue.poll();
                level.add(node.element);
                if (node.lChild != null) {
                    queue.add(node.lChild);
                }
                if (node.rChild != null) {
                    queue.add(node.rChild);
                }
            }
            res.add(level);
        }
        return res;
    }

    private static BinNode root;
    public static void main(String[] args) {
        init();
        //preOrder(root);
        //inOrder(root);
        //postOrder(root);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.print(list.get(i).element + " ");
//        }
        System.out.printf(""+levelOrder(root));
    }

    // 树的初始化:先从叶节点开始,由叶到根

    /*
                    i
            d               l
       c        h       k       n
     a        f       j       m   p
       b    e  g                 o
     */
    public static void init() {
        BinNode b = new BinNode("b", null, null);
        BinNode a = new BinNode("a", null, b);
        BinNode c = new BinNode("c", a, null);

        BinNode e = new BinNode("e", null, null);
        BinNode g = new BinNode("g", null, null);
        BinNode f = new BinNode("f", e, g);
        BinNode h = new BinNode("h", f, null);

        BinNode d = new BinNode("d", c, h);

        BinNode j = new BinNode("j", null, null);
        BinNode k = new BinNode("k", j, null);
        BinNode m = new BinNode("m", null, null);
        BinNode o = new BinNode("o", null, null);
        BinNode p = new BinNode("p", o, null);
        BinNode n = new BinNode("n", m, p);
        BinNode l = new BinNode("l", k, n);

        root = new BinNode("i", d, l);
    }
}
