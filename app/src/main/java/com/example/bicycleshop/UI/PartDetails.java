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


public class PartDetails extends AppCompatActivity {
    private EditText editPartName, editPartPrice, editPartNote, editPartDate;
    private int partID, productID;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_part_details);

        editPartName = findViewById(R.id.editPartName);
        editPartPrice = findViewById(R.id.editPartPrice);
        editPartNote = findViewById(R.id.editPartNote);
        editPartDate = findViewById(R.id.editPartDate);

        repository = new Repository(getApplication());

        partID = getIntent().getIntExtra("id", -1);
        productID = getIntent().getIntExtra("prodID", -1);
        String partName = getIntent().getStringExtra("name");
        double partPrice = getIntent().getDoubleExtra("price", 0.0);
        String partNote = getIntent().getStringExtra("note");
        String partDate = getIntent().getStringExtra("date");

        if (partID != -1) {
            editPartName.setText(partName);
            editPartPrice.setText(String.valueOf(partPrice));
            editPartNote.setText(partNote);
            editPartDate.setText(partDate);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_part_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.partsave) {
            savePart();
            return true;
        } else if (item.getItemId() == R.id.sharenote) {
            sharePartNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePart() {
        Part part;
        if (partID == -1) {
            partID = repository.getmAllParts().size() == 0 ? 1 : repository.getmAllParts().get(repository.getmAllParts().size() - 1).getPartID() + 1;
            part = new Part(partID, editPartName.getText().toString(), Double.parseDouble(editPartPrice.getText().toString()), productID);
            repository.insert(part);
        } else {
            part = new Part(partID, editPartName.getText().toString(), Double.parseDouble(editPartPrice.getText().toString()), productID);
            repository.update(part);
        }
        this.finish();
    }

    private void deletePart() {
        Part part = new Part(partID, editPartName.getText().toString(), Double.parseDouble(editPartPrice.getText().toString()), productID);
        repository.delete(part);
        this.finish();
    }

    private void sharePartNote() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, editPartNote.getText().toString());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}