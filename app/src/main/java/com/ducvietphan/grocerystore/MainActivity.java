package com.ducvietphan.grocerystore;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbManagement db = new DbManagement("items", getApplicationContext());
        ItemsList itemsList = db.getItemsList();

        LinearLayout ll = (LinearLayout) findViewById(R.id.scrollLinear);

        Node current = itemsList.getHead();
        while (current != null){
            TextView tv = new TextView(getApplicationContext());
            Item item = current.getItem();
            tv.setText(item.getProductName());
            ll.addView(tv);

            current = current.next;
        }

    }

    @Override
    public void onResume() {
    // After a pause OR at startup
        super.onResume();
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
