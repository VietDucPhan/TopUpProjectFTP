package com.ducvietphan.grocerystore;

/**
 * Created by macbook on 4/23/16.
 */
public class Node {
    Node next;
    Item item;
    Node(){

    }

    Node(Item item){
        this.item = item;
    }

    public Item getItem(){
        return this.item;
    }
}
