package com.ducvietphan.grocerystore;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.populateItemList();
    }

    public void populateItemList(){
        DbManagement db = new DbManagement("items", getApplicationContext());
        ItemsList itemsList = db.getItemsList();
        LinearLayout ll = (LinearLayout) findViewById(R.id.scrollLinear);
        ll.removeAllViews();
        Node current = itemsList.getHead();
        while (current != null){
            View injecterLayout = getLayoutInflater().inflate(R.layout.itemlist, ll, false);
            final Item item = current.getItem();
            ll.addView(injecterLayout);

            TextView productName = (TextView) injecterLayout.findViewById(R.id.productName);
            productName.setText("Product Name: " + item.getProductName());
            TextView barcode = (TextView) injecterLayout.findViewById(R.id.barcode);
            barcode.setText("Barcode: " + item.getBarcode());


            ImageView editBtn = (ImageView) injecterLayout.findViewById(R.id.editBtn);
            editBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Barcode: " + item.getBarcode(), Toast.LENGTH_SHORT).show();
                }
            });

            current = current.next;
        }
        TextView debug = (TextView) findViewById(R.id.debug);
        debug.setText(itemsList.toString());
    }

    @Override
    public void onResume() {
    // After a pause OR at startup
        super.onResume();
        this.populateItemList();
        //Refresh your stuff here
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNewProduct:
                Intent intent = new Intent(this, AddProduct.class);
                this.startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
