package com.example.bicycleshop.database;

import android.app.Application;

import com.example.bicycleshop.dao.PartDAO;
import com.example.bicycleshop.dao.ProductDAO;
import com.example.bicycleshop.entities.Part;
import com.example.bicycleshop.entities.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private PartDAO mPartDao;
    private ProductDAO mProductDAO;

    private List<Product> mAllProducts;
    private List<Part> mAllParts;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        BicycleDatabaseBuilder db = BicycleDatabaseBuilder.getDatabase(application);
        mPartDao=db.partDAO();
        mProductDAO= db.productDAO();
    }
    public List<Product> getmAllProducts(){
        databaseExecutor.execute(()->{
            mAllProducts=mProductDAO.getAllProducts();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllProducts;
    }

    public void update(Product product){
        databaseExecutor.execute(()->{
            mProductDAO.update(product);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void insert(Product product){
        databaseExecutor.execute(()->{
            mProductDAO.insert(product);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Product product){
        databaseExecutor.execute(()->{
            mProductDAO.delete(product);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Part> getmAllParts(){
        databaseExecutor.execute(()->{
           mAllParts=mPartDao.getAllParts();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllParts;
    }

    public List<Part> getAssociatedParts(int productID){
        databaseExecutor.execute(()->{
            mAllParts=mPartDao.getAssociatedParts(productID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllParts;
    }

    public void update(Part part){
        databaseExecutor.execute(()->{
            mPartDao.update(part);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void insert(Part part){
        databaseExecutor.execute(()->{
            mPartDao.insert(part);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Part part){
        databaseExecutor.execute(()->{
            mPartDao.delete(part);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
