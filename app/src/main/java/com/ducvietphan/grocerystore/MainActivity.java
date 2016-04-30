package com.ducvietphan.grocerystore;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.populateItemList();
    }

    public void populateItemList(){
        final DbManagement db = new DbManagement("items", getApplicationContext());
        final ItemsList itemsList = db.getItemsList();
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
                    Intent i = new Intent(MainActivity.this, AddProduct.class);
                    i.putExtra("barcode", item.getBarcode());
                    startActivity(i);
                }
            });


            ImageView deleteBtn = (ImageView) injecterLayout.findViewById(R.id.minusBtn);

            deleteBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //itemsList.delete(item.getBarcode());
                                    db.deleteItem(item.getBarcode());
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
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

            case R.id.calculatePage:
                Intent searchIntent = new Intent(this, CalculateActivity.class);
                this.startActivity(searchIntent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
