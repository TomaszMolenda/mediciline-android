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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Medicament))return false;
        Medicament medicament = (Medicament)obj;
        if(this.hashCode() == medicament.hashCode()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        int multipler = 31;
        //int
        result = multipler * result + idServer;
        result = multipler * result + productLineID;
        result = multipler * result + packageID;
        //String
        result = multipler * result + (name == null ? 0 : name.hashCode());
        result = multipler * result + (producent == null ? 0 : producent.hashCode());
        result = multipler * result + (dateExpiration == null ? 0 : dateExpiration.hashCode());
        result = multipler * result + (kind == null ? 0 : kind.hashCode());
        //double
        long priceBits = Double.doubleToLongBits(price);
        result = multipler * result + (int)(priceBits ^ (priceBits >>> 32));

        return result;
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

    @Override
    public String toString() {
        return "Medicament{" +
                "idServer=" + idServer +
                ", name='" + name + '\'' +
                ", producent='" + producent + '\'' +
                ", price=" + price +
                ", kind='" + kind + '\'' +
                ", dateExpiration='" + dateExpiration + '\'' +
                ", productLineID=" + productLineID +
                ", packageID=" + packageID +
                '}';
    }
}
