package com.example.bicycleshop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bicycleshop.dao.PartDAO;
import com.example.bicycleshop.dao.ProductDAO;
import com.example.bicycleshop.entities.Part;
import com.example.bicycleshop.entities.Product;

@Database(entities = {Product.class, Part.class}, version = 4,exportSchema = false)
public abstract class BicycleDatabaseBuilder extends RoomDatabase {
    public abstract ProductDAO productDAO();
    public abstract PartDAO partDAO();
    private static volatile BicycleDatabaseBuilder INSTANCE;

    static BicycleDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (BicycleDatabaseBuilder.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),BicycleDatabaseBuilder.class, "MyBicycleDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
