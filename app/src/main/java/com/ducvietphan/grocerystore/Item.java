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
    private Item next;

    public Item(int barcode, String productName, int productPrice, String desc, Item item){
        this.barcode = barcode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.desc = desc;
        this.next = item;
    }

    public Item(int barcode, String productName, int productPrice, String desc){
        this(barcode,productName,productPrice,desc, null);
    }

    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        try {
            json.put("barcode", this.barcode);
            json.put("productName", this.productName);
            json.put("productPrice", this.productPrice);
            json.put("desc", this.desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
