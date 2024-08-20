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

import com.example.bicycleshop.R;
import com.example.bicycleshop.database.Repository;
import com.example.bicycleshop.entities.Part;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PartDetails extends AppCompatActivity {
    String partName;
    String partNote;
    String partDate;
    double partPrice;
    int partID;
    int productID;

    EditText editPartName;
    EditText editPartPrice;
    EditText editPartNote;
    EditText editPartDate;


    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_details);


        editPartName = findViewById(R.id.editPartName);
        editPartPrice = findViewById(R.id.editPartPrice);
        editPartNote = findViewById(R.id.editPartNote);
        editPartDate = findViewById(R.id.editPartDate);

        partID = getIntent().getIntExtra("id", -1);
        productID = getIntent().getIntExtra("prodID", -1);
        partName = getIntent().getStringExtra("name");
        partPrice = getIntent().getDoubleExtra("price", 0.0);
        partNote = getIntent().getStringExtra("note");
        partDate = getIntent().getStringExtra("date");

        editPartName.setText(partName);
        editPartPrice.setText(Double.toString(partPrice));
        editPartNote.setText(partNote);
        editPartDate.setText(partDate);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_part_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.partsave) {
            Part part;
            if (partID == -1) {
                if (repository.getmAllParts().size() == 0) partID = 1;
                else
                    partID = repository.getmAllParts().get(repository.getmAllParts().size() - 1).getPartID() + 1;
                part = new Part(partID, editPartName.getText().toString(), Double.parseDouble(editPartPrice.getText().toString()), productID);
                repository.insert(part);
                this.finish();
            } else {
                part = new Part(partID, editPartName.getText().toString(), Double.parseDouble(editPartPrice.getText().toString()), productID);
                repository.update(part);
                this.finish();
            }
        } else if (item.getItemId() == R.id.productdelete) {
            Part part = new Part(partID, editPartName.getText().toString(), Double.parseDouble(editPartPrice.getText().toString()), productID);
            repository.delete(part);
            this.finish();
        }
        return true;
    }
}