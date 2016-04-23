package com.ducvietphan.grocerystore;

import android.content.Context;

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
                String json = "{}";
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


    public boolean insertItem(int barcode, String productName, int productPrice, String desc){
        String st = this.toString();
        String insertId = new Integer(barcode).toString();

        Item insertItem = new Item(barcode, productName, productPrice, desc);

        try {
            JSONObject jsonObject = new JSONObject(st);
            jsonObject.put(insertId, insertItem.toJSONObject());
            this.writeToFile(jsonObject.toString());

        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
