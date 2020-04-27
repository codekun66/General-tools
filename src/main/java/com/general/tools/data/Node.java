package com.general.tools.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * Node类 节点模型
 * 1、data 数据域
 * 2、next 指针域
 * */
public class Node {
    private int data;
    private Node next;
    private Node head = this;

    /*
     *有参构造方法,初始化时赋值
     * */
    public Node(int data) {
        this.data = data;
    }

    public Node(int data, Node next) {

        this.data = data;
        this.next = next;
    }


    /*
     *无参的构造方法
     * */
    public Node() {
    }
    /*
     *增加节点操作
     * */
    public void addNode(Node newNode) {

        //临时节点
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
         temp.next =newNode ;
    }

    public static void main(String[] args) {
        Node node = new Node();
        node.addNode(new Node(1,null));
        node.addNode(new Node(2,null));
        System.out.println(node.next.next.data);
    }

}
