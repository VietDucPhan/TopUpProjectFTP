package com.ducvietphan.grocerystore;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        final DbManagement itemModel = new DbManagement("items",getApplicationContext());
//        ImageView iv = (ImageView) findViewById(R.id.imagePicker);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent pickIntent = new Intent();
//                pickIntent.setType("image/*");
//                pickIntent.setAction(Intent.ACTION_GET_CONTENT);
//
//                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
//                Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
//                chooserIntent.putExtra
//                        (
//                                Intent.EXTRA_INITIAL_INTENTS,
//                                new Intent[] { takePhotoIntent }
//                        );
//
//                startActivityForResult(chooserIntent, RESULT_LOAD_IMAGE);
//
//            }
//        });

        if(getIntent().hasExtra("barcode")){
            int passedBarcode = getIntent().getExtras().getInt("barcode");
            TextView productName = (TextView) findViewById(R.id.productName);
            TextView barcode = (TextView) findViewById(R.id.barcode);
            TextView productPrice = (TextView) findViewById(R.id.productPrice);
            TextView desc = (TextView) findViewById(R.id.desc);

            ItemsList il = itemModel.getItemsList();
            Node node = il.getNodeByBarcode(passedBarcode);

            if(node != null){
                productName.setText(node.getItem().getProductName());
                barcode.setText(""+node.getItem().getBarcode());
                barcode.setEnabled(false);
                barcode.setFocusable(false);
                productPrice.setText(""+node.getItem().getProductPrice());
                desc.setText(node.getItem().getDesc());
            }

        }
        if (saveBtn != null) {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

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
                        if(getIntent().hasExtra("barcode")){
                            Item item = new Item(barCodeInt, productNameSt, productPriceInt, descSt);
                            if(itemModel.editItem(item)){
                                Intent i = new Intent(AddProduct.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "An unknown errors happened please try again", Toast.LENGTH_SHORT).show();
                            }
                        } else {
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
                                barcode.setText("");
                                barcode.setHint("Duplicate barcode. Please enter again!");
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please input all red fields", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK ) {
            Log.d("data", data.getData().toString());
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            ImageView imageView = (ImageView) findViewById(R.id.imagePicker);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }
}
