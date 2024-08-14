package com.example.bicycleshop.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bicycleshop.R;
import com.example.bicycleshop.database.Repository;
import com.example.bicycleshop.entities.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductDetails extends AppCompatActivity {
    String name;
    double price;
    int productID;
    EditText editName;
    EditText editPrice;


    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        editName=findViewById(R.id.titletext);
        editPrice=findViewById(R.id.pricetext);
        productID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0.0);
        editName.setText(name);
        editPrice.setText(Double.toString(price));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductDetails.this, PartDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.partrecyclerview);
        repository = new Repository(getApplication());
        final PartAdapter partAdapter = new PartAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        partAdapter.setParts(repository.getmAllParts());
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()== R.id.productsave){
            Product product;
            if (productID==-1){
                if (repository.getmAllProducts().size() == 0)productID = 1;
                else productID = repository.getmAllProducts().get(repository.getmAllProducts().size() -1).getProductID() +1;
                product=
            }
        }
    }
}