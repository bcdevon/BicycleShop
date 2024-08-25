package com.example.bicycleshop.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.Manifest;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bicycleshop.R;
import com.example.bicycleshop.database.Repository;
import com.example.bicycleshop.entities.Part;
import com.example.bicycleshop.entities.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PartDetails extends AppCompatActivity {
    private EditText editPartName, editPartPrice, editPartNote, editPartDate;
    private int partID, productID;
    private Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_details);
        repository = new Repository(getApplication());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        editPartName = findViewById(R.id.editPartName);
        editPartPrice = findViewById(R.id.editPartPrice);
        editPartNote = findViewById(R.id.editPartNote);
        editPartDate = findViewById(R.id.editPartDate);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editPartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info=editPartDate.getText().toString();
                if(info.equals(""))info="08/10/24";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(PartDetails.this,
                        startDate,
                        myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
        Spinner spinner=findViewById(R.id.spinner);
        ArrayList<Product> productArrayList=new

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

    private void updateLabelStart(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editPartDate.setText(sdf.format(myCalendarStart.getTime()));
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
            if (partID == -1){
                if (repository.getmAllParts().size() == 0)
                    partID = 1;
                else
                    partID = repository.getmAllParts().get(repository.getmAllParts().size() -1).getPartID() +1;
                part = new Part(partID, editPartName.getText().toString(), Double.parseDouble(editPartPrice.getText().toString()), productID);
                repository.insert(part);
            } else {
            part =new Part(partID, editPartName.getText().toString(), Double.parseDouble(editPartPrice.getText().toString()), productID);
            repository.update(part);
            }
            return true;
        }
        if (item.getItemId() == R.id.sharenote) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editPartNote.getText().toString() + "EXTRA_TEXT");
            sendIntent.putExtra(Intent.EXTRA_TITLE, editPartNote.getText().toString()+ "EXTRA_TITLE");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId() == R.id.notify) {
            String dateFromScreen = editPartDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new  SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            }catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(PartDetails.this, MyReceiver.class);
            intent.putExtra("key", "Message I want to see");
            PendingIntent sender = PendingIntent.getBroadcast(PartDetails.this,++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
        }
}