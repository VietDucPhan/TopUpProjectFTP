package com.ducvietphan.grocerystore;

import android.util.Log;

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

    public boolean clear(){
        this.length = 0;
        this.tail = this.head = null;
        return true;
    }

    public boolean isEmpty(){
        return this.head == null;
    }

    public boolean add(Item item){
        if(!this.isBarcodeExist(item.getBarcode())){
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
        } else {
            return false;
        }
    }

    public boolean addAdditionalItem(Item item){
        if(!this.isBarcodeExist(item.getBarcode())){
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
        } else {
            Item currentItem = this.getNodeByBarcode(item.getBarcode()).getItem();
            int currentTotal = currentItem.getTotal() + 1;

            item.setTotal(currentTotal);

            Node n = new Node(item);


            this.editNode(n);
            return false;
        }
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

    public Node getNodeByBarcode(int barcode){
        Node p = this.head;
        if(this.head.getItem().getBarcode() == barcode){
            return this.head;
        }
        while(p.next != null){
            if(p.next.getItem().getBarcode() == barcode){
                return p.next;
            }
            p=p.next;
        }
        return p;
    }

    public boolean editNode(Node node){
        Node p = this.head;
        if(this.head.getItem().getBarcode() == node.getItem().getBarcode()){
            node.next = this.head.next;
            this.head = node;
            return true;
        }
        while(p.next != null){
            if(p.next.getItem().getBarcode() == node.getItem().getBarcode()){
                node.next = p.next.next;
                p.next = node;
                break;
            }
            p=p.next;
        }
        return true;
    }


    public boolean delete(int barcode){
        Node p = this.head;
        if(this.head.getItem().getBarcode() == barcode){
            this.head = this.head.next;
            return true;
        }
        while(p.next != null){
            if(p.next.getItem().getBarcode() == barcode){
                p.next = p.next.next;
                break;
            }
            p=p.next;
        }


        return true;
    }


    public String toString(){
        String result = "[";
            Node p = this.head;
            while(p != null){
                result += p.getItem().toJSONObject().toString();
                result += ",";
//                result += "{";
//                result += "'barcode':";
//                result += p.getItem().getBarcode()+",";
//                result += "'productName':";
//                result += "'"+p.getItem().getProductName()+"',";
//                result += "'productPrice':";
//                result += p.getItem().getProductPrice()+",";
//                result += "'desc':";
//                if(p.getItem().getDesc().isEmpty()){
//                    result += "'"+p.getItem().getDesc()+"'";
//                } else {
//                    result += "''";
//                }
//
//                result += "},";
                p=p.next;
            }
        result += "]";
        return result;
    }

    public Item searchBarcode(int barcode){
        Node p = this.head;
        boolean found = false;
        while(p != null){

            if(p.getItem().getBarcode() == barcode){
                found = true;
                break;
            }
            p=p.next;
        }

        if(found){
            return p.getItem();
        }
        return null;
    }

    public int totalPrice(){
        Node p = this.head;
        boolean found = false;
        int total = 0;
        while(p != null){
            total += p.getItem().getTotal() * p.getItem().getProductPrice();
            p=p.next;
        }
        return total;
    }

    public boolean isBarcodeExist(int barcode){
        Node p = this.head;
        boolean found = false;
        while(p != null){

            if(p.getItem().getBarcode() == barcode){
                found = true;
                break;
            }
            p=p.next;
        }

        return found;
    }


}
