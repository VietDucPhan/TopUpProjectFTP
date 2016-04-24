package com.ducvietphan.grocerystore;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by macbook on 4/23/16.
 */
public class DbManagement {
    private String name = "items";
    private Context context;




    public DbManagement(String name, Context c){
        if(!name.isEmpty()){
            this.name = name;
        }

        this.context = c;

        File file = new File(this.context.getFilesDir() + "/" + name);
            if(!file.exists()){
            try{
                String json = "{'items':[]}";
                FileOutputStream fos = this.context.openFileOutput(this.name, Context.MODE_PRIVATE);
                fos.write(json.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ItemsList getItemsList(){
        String st = this.toString();
        ItemsList il = new ItemsList();
        try {
            JSONObject jsonObject = new JSONObject(st);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray.length() > 0){
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jo = jsonArray.getJSONObject(i);
                    Item item = new Item();
                    item.setBarcode(jo.getInt("barcode"));
                    item.setProductName(jo.getString("productName"));
                    item.setProductPrice(jo.getInt("productPrice"));
                    item.setDesc(jo.getString("desc"));
                    il.add(item);
                }
            }



        } catch (Throwable e) {
            e.printStackTrace();
        }
        return il;
    }


    public boolean insertItemBck(int barcode, String productName, int productPrice, String desc){
        String st = this.toString();
        String insertId = new Integer(barcode).toString();

        Item insertItem = new Item(barcode, productName, productPrice, desc);
        try {
            JSONObject jsonObject = new JSONObject(st);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            jsonArray.put(insertItem.toJSONObject());
            this.writeToFile(jsonObject.toString());

        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertItem(int barcode, String productName, int productPrice, String desc){
        Item insertItem = new Item(barcode, productName, productPrice, desc);
        ItemsList it = this.getItemsList();
        if(it.add(insertItem)){
            this.writeToFile(it.toString());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Funciton to save string to file.
     * @notice Always overide file.
     * @param st String to save to file.
     * @return boolean true on success, false otherwise
     */
    public boolean writeToFile(String st) {
        if(st.isEmpty()){
            return false;
        }


        try {
            FileOutputStream fos = this.context.openFileOutput(this.name, Context.MODE_PRIVATE);
            fos.write(st.getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        FileInputStream fis = null;

        try {
            fis = this.context.openFileInput(this.name);
            int ch;

            while((ch = fis.read()) != -1){
                builder.append((char)ch);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return builder.toString();
    }

}
