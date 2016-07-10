package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class Medicament {



    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int idServer;
    @DatabaseField
    private String name;
    @DatabaseField
    private String producent;
    @DatabaseField
    private double price;
    @DatabaseField
    private String kind;
    @DatabaseField
    private Date dateExpiration;
    @DatabaseField
    private long date;
    @DatabaseField
    private int productLineID;
    @DatabaseField
    private int packageID;
    @DatabaseField
    private boolean archive;


    public Medicament() {
    }

    public Medicament(DbMedicament dbMedicament) {
        this.name = dbMedicament.getProductName();
        this.producent = dbMedicament.getProducer();
        this.price = dbMedicament.getPrice();
        this.kind = dbMedicament.getPack();
        this.packageID = dbMedicament.getPackageID();
    }

    public static Medicament containsId(Collection<Medicament> c, int id) {
        for(Medicament m : c) {
            if(m != null && m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    public static List<Medicament> compareIdServer(List<Medicament> remoteMedicaments, List<Medicament> localMedicamentsSended) {
        List<Medicament> returnList = new ArrayList<Medicament>(remoteMedicaments);
        for (Medicament remoteMedicament : remoteMedicaments) {
            for (Medicament localMedicament : localMedicamentsSended) {
                if(localMedicament.getIdServer() == remoteMedicament.getIdServer()) {
                    returnList.remove(remoteMedicament);
                    break;
                }

            }

        }
        return returnList;
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
        result = multipler * result + (kind == null ? 0 : kind.hashCode());
        //double
        long priceBits = Double.doubleToLongBits(price);
        result = multipler * result + (int)(priceBits ^ (priceBits >>> 32));

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

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", idServer=" + idServer +
                ", name='" + name + '\'' +
                ", producent='" + producent + '\'' +
                ", price=" + price +
                ", kind='" + kind + '\'' +
                ", dateExpiration=" + dateExpiration +
                ", date=" + date +
                ", productLineID=" + productLineID +
                ", packageID=" + packageID +
                ", archive=" + archive +
                '}';
    }
}
