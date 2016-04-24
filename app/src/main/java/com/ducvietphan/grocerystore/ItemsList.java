package com.ducvietphan.grocerystore;

/**
 * Created by macbook on 4/23/16.
 */
public class ItemsList {
    public Node tail, head;
    private int length;
    public ItemsList(){
        this.length = 0;
        this.tail = this.head = null;
    }

    public Node getHead(){
        return this.head;
    }

    public boolean isEmpty(){
        return this.head == null;
    }

    public boolean add(Item item){
        if(isEmpty()){
            this.head = this.tail = new Node(item);
            this.length++;
            return true;
        } else {
            Node node = new Node(item);
            this.tail.next = node;
            this.tail = node;
        }
        this.length++;
        return true;
    }

    public int getLength(){
        return this.length;
    }

    public int count(){
        Node p=this.head;
        int i = 0;
        while(p != null){
            p=p.next;
            i++;
        }
        return i;
    }


    public String toString(){
        String result = "";
        if(!isEmpty()){
            Item item = this.head.getItem();
            return item.getProductName();
        }

        return result;
    }


}
