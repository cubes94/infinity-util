package com.infinity.training.basis.algorithm.tree;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/09/07 20:39
 */
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    public TreeNode(Integer... arr) {
        TreeNode[] nodes = new TreeNode[arr.length];
        nodes[0] = new TreeNode(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != null) {
                nodes[i] = new TreeNode(arr[i]);
                TreeNode parent = nodes[(i - 1) / 2];
                if (i % 2 == 0) {
                    parent.right = nodes[i];
                } else {
                    parent.left = nodes[i];
                }
            }
        }
        this.val = nodes[0].val;
        this.left = nodes[0].left;
        this.right = nodes[0].right;
    }
}
