package local.tomo.login.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tomo on 2016-05-02.
 */
public class Medicament {

    DateExpirationYearMonth dateExpirationYearMonth = new DateExpirationYearMonth();

    private int id;
    private int idServer;
    private String name;
    private String producent;
    private double price;
    private String kind;
    private String dateExpiration;
    private Date dateFormatExpiration;
    private int productLineID;
    private int packageID;


    public Medicament() {
    }

    public Medicament(MedicamentDb medicamentDb) {
        this.name = medicamentDb.getProductName();
        this.producent = medicamentDb.getProducer();
        this.price = medicamentDb.getPrice();
        this.kind = medicamentDb.getPack();
        this.productLineID = medicamentDb.getProductLineID();
        this.packageID = medicamentDb.getPackageID();
    }

    public static class List extends ArrayList<Medicament> {

    }

    public void createDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(getDateExpiration()));
        calendar.add(Calendar.DAY_OF_MONTH,1);
        dateFormatExpiration = calendar.getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducent() {
        return producent;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public int getProductLineID() {
        return productLineID;
    }

    public void setProductLineID(int productLineID) {
        this.productLineID = productLineID;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public Date getDateFormatExpiration() {
        return dateFormatExpiration;
    }

    public void setDateFormatExpiration(Date dateFormatExpiration) {
        this.dateFormatExpiration = dateFormatExpiration;
    }

    public DateExpirationYearMonth getDateExpirationYearMonth() {
        return dateExpirationYearMonth;
    }

    public void setDateExpirationYearMonth(DateExpirationYearMonth dateExpirationYearMonth) {
        this.dateExpirationYearMonth = dateExpirationYearMonth;
    }
}
