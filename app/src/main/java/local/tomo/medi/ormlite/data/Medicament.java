package local.tomo.medi.ormlite.data;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Medicament {

    @ForeignCollectionField
    private ForeignCollection<Medicament_Disease> medicament_diseases;

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
    private String pack;
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

    private boolean isChecked;


    public Medicament() {
    }

    public Medicament(DbMedicament dbMedicament) {
        this.name = dbMedicament.getProductName();
        this.producent = dbMedicament.getProducer();
        this.price = dbMedicament.getPrice();
        this.pack = dbMedicament.getPack();
        this.packageID = dbMedicament.getPackageID();
        this.kind = dbMedicament.getForm();
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
        result = multipler * result + (pack == null ? 0 : pack.hashCode());
        //double
        long priceBits = Double.doubleToLongBits(price);
        result = multipler * result + (int)(priceBits ^ (priceBits >>> 32));

        result = multipler * result + (int)(priceBits ^ (priceBits >>> 32));

        return result;
    }

}
