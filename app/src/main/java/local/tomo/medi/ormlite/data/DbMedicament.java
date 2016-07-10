package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class DbMedicament implements Serializable {

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "productLineID")
    private MedicamentAdditional medicamentAdditional;

    @DatabaseField(id = true)
    private int packageID;
    @DatabaseField
    private int idServer;
    @DatabaseField
    private String activeSubstance;
    @DatabaseField
    private int distributorID;
    @DatabaseField
    private String dosage;
    @DatabaseField
    private int driving;
    @DatabaseField
    private String drivingInfo;
    @DatabaseField
    private int drugCardLimit;
    @DatabaseField
    private int drugPromoID;
    @DatabaseField
    private String ean;
    @DatabaseField
    private int finalSort;
    @DatabaseField
    private String form;
    @DatabaseField
    private int isAlco;
    @DatabaseField
    private String isAlcoInfo;
    @DatabaseField
    private int isNarcPsych;
    @DatabaseField
    private String isNarcPsychInfo;
    @DatabaseField
    private int isReimbursed;
    @DatabaseField
    private int lactatio;
    @DatabaseField
    private String lactatioInfo;
    @DatabaseField
    private String pack;
    @DatabaseField
    private int pregnancy;
    @DatabaseField
    private String pregnancyInfo;
    @DatabaseField
    private int prescriptionID;
    @DatabaseField
    private String prescriptionName;
    @DatabaseField
    private String prescriptionShortName;
    @DatabaseField
    private double price;
    @DatabaseField
    private String producer;
    @DatabaseField
    private int producerID;
    @DatabaseField
    private int productID;
    @DatabaseField
    private String productLineName;
    @DatabaseField
    private String productName;
    @DatabaseField
    private int productTypeID;
    @DatabaseField
    private String productTypeName;
    @DatabaseField
    private String productTypeShortName;
    @DatabaseField
    private String regNo;
    @DatabaseField
    private int sponsorID;
    @DatabaseField
    private String therapeuticClass;
    @DatabaseField
    private int trimester1;
    @DatabaseField
    private String trimester1Info;
    @DatabaseField
    private int trimester2;
    @DatabaseField
    private String trimester2Info;
    @DatabaseField
    private int trimester3;
    @DatabaseField
    private String trimester3Info;
    @DatabaseField
    private int isFavorite;
    @DatabaseField
    private int oi;

    public DbMedicament() {
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof DbMedicament))return false;
        DbMedicament dbMedicament = (DbMedicament)obj;
        if(this.hashCode() == dbMedicament.hashCode()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        int multipler = 31;
        //int
        result = multipler * result + packageID;
        return result;
    }
}
