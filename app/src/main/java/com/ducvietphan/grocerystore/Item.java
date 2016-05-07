package com.ducvietphan.grocerystore;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by macbook on 4/23/16.
 */
public class Item {
    private int barcode;
    private String productName;
    private int productPrice;
    private String desc;
    private int total = 1;//quantity default of an item is 1

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }




    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Item(){

    }
    public Item(int barcode, String productName, int productPrice, String desc){
        this(barcode, productName, productPrice, desc, 0);
    }

    public Item(int barcode, String productName, int productPrice, String desc, int total){
        this.barcode = barcode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.desc = desc;
        if(total != 0){
            this.total = total;
        }

    }

    public String toString(){
        return this.toJSONObject().toString();
    }

    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        try {
            json.put("barcode", this.barcode);
            json.put("productName", this.productName);
            json.put("productPrice", this.productPrice);
            json.put("desc", this.desc);
            json.put("total", this.getTotal());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
