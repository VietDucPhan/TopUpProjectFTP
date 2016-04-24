package com.ducvietphan.grocerystore;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Button saveBtn = (Button) findViewById(R.id.saveBtn);

        if (saveBtn != null) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DbManagement itemModel = new DbManagement("items",getApplicationContext());
                    TextView productName = (TextView) findViewById(R.id.productName);
                    TextView barcode = (TextView) findViewById(R.id.barcode);
                    TextView productPrice = (TextView) findViewById(R.id.productPrice);
                    TextView desc = (TextView) findViewById(R.id.desc);


                    String productNameSt = productName.getText().toString();
                    String barcodeSt = barcode.getText().toString();
                    String productPriceSt = productPrice.getText().toString();
                    String descSt = desc.getText().toString();

                    boolean flag = false;
                    if(productNameSt.isEmpty()){
                        flag = true;
                        productName.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    }
                    if(barcodeSt.isEmpty()){
                        flag = true;
                        barcode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    }
                    if(productPriceSt.isEmpty()){
                        flag = true;
                        productPrice.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    }

                    if(!flag){
                        int barCodeInt = Integer.parseInt(barcodeSt);
                        int productPriceInt = Integer.parseInt(productPriceSt);
                        if(itemModel.insertItem(barCodeInt, productNameSt, productPriceInt, descSt)){
                            productName.setText("");
                            barcode.setText("");
                            barcode.setHint("Barcode*");
                            productPrice.setText("");
                            desc.setText("");

                            productName.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            barcode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            productPrice.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));

                            Toast.makeText(getApplicationContext(), "Product saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Product not saved ", Toast.LENGTH_SHORT).show();
                            barcode.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            barcode.setText("");
                            barcode.setHint("Duplicate barcode. Please enter again!");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please input all red fields", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }


}
