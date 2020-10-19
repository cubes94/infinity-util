package com.infinity.training.basis.algorithm.list;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/09/07 17:50
 */
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public ListNode(int x, ListNode next) {
        val = x;
        this.next = next;
    }

    public ListNode(int... arr) {
        ListNode[] nodes = new ListNode[arr.length];
        nodes[arr.length - 1] = new ListNode(arr[arr.length - 1]);
        for (int i = arr.length - 2; i >= 0; i--) {
            nodes[i] = new ListNode(arr[i], nodes[i + 1]);
        }
        this.val = nodes[0].val;
        this.next = nodes[0].next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + (next == null ? "null" : next.toString()) +
                '}';
    }
}
