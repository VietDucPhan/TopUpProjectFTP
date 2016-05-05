package com.ducvietphan.grocerystore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        final DbManagement cartDb = new DbManagement("cart", getApplicationContext());
        ItemsList it = cartDb.getItemsList();
        if(it.isEmpty()){
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setEnabled(false);
                    cartDb.checkOut();
                    Snackbar.make(view, "Checkout successfully you will be redirected in 3s", Snackbar.LENGTH_LONG).show();
                    Runnable mRunnable;
                    Handler mHandler = new Handler();

                    mRunnable = new Runnable() {

                        @Override
                        public void run() {
                            Intent mainActivity = new Intent(CartActivity.this, MainActivity.class);
                            startActivity(mainActivity);
                        }
                    };
                    mHandler.postDelayed(mRunnable, 3000);//Execute after 10 Seconds
                }
            });
        }
        TextView tv = (TextView) findViewById(R.id.subTotal);
        tv.setText("$"+it.totalPrice());
        Node current = it.getHead();
        TableLayout tl = (TableLayout) findViewById(R.id.cartContainer);
        View firstView = getLayoutInflater().inflate(R.layout.cart_table, tl, false);
        tl.addView(firstView);
        while(current != null){
            View injecterLayout = getLayoutInflater().inflate(R.layout.cart_table, tl, false);
            final Item item = current.getItem();
            tl.addView(injecterLayout);
            TextView productName = (TextView) injecterLayout.findViewById(R.id.productName);
            TextView productPrice = (TextView) injecterLayout.findViewById(R.id.productPrice);
            TextView productQuantity = (TextView) injecterLayout.findViewById(R.id.productQuantity);
            TextView productTotal = (TextView) injecterLayout.findViewById(R.id.productTotal);
            TextView delete = (TextView) injecterLayout.findViewById(R.id.deleteCartProduct);
            delete.setText("delete");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //itemsList.delete(item.getBarcode());
                                    cartDb.deleteItem(item.getBarcode());
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

            productName.setText("" + item.getProductName());
            productPrice.setText("$" + item.getProductPrice());
            productQuantity.setText("" + item.getTotal());
            productTotal.setText("$" + item.getTotal() * item.getProductPrice());

            current = current.next;
        }
    }

}
