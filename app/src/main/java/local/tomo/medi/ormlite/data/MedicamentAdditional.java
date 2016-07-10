package local.tomo.medi.ormlite.data;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomo on 2016-07-04.
 */
public class MedicamentAdditional {

    @ForeignCollectionField
    private ForeignCollection<DbMedicament> medicamentSet;

    @DatabaseField(id = true)
    private int productLineID;

    @DatabaseField
    private String composition;

    @DatabaseField
    private String effects;

    @DatabaseField
    private String indications;

    @DatabaseField
    private String contraindications;

    @DatabaseField
    private String precaution;

    @DatabaseField
    private String pregnancy;

    @DatabaseField
    private String sideeffects;

    @DatabaseField
    private String interactions;

    @DatabaseField
    private String dosage;

    @DatabaseField
    private String remark;

    public MedicamentAdditional() {
    }

    public MedicamentAdditional(int productLineID, String composition, String effects, String indications, String contraindications, String precaution, String pregnancy, String sideeffects, String interactions, String dosage, String remark) {
        this.productLineID = productLineID;
        this.composition = composition;
        this.effects = effects;
        this.indications = indications;
        this.contraindications = contraindications;
        this.precaution = precaution;
        this.pregnancy = pregnancy;
        this.sideeffects = sideeffects;
        this.interactions = interactions;
        this.dosage = dosage;
        this.remark = remark;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof MedicamentAdditional))return false;
        MedicamentAdditional medicamentAdditional = (MedicamentAdditional)obj;
        if(this.hashCode() == medicamentAdditional.hashCode()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        int multipler = 31;
        //int
        result = multipler * result + productLineID;
        return result;
    }

    public ForeignCollection<DbMedicament> getMedicamentSet() {
        return medicamentSet;
    }

    public void setMedicamentSet(ForeignCollection<DbMedicament> medicamentSet) {
        this.medicamentSet = medicamentSet;
    }

    public int getProductLineID() {
        return productLineID;
    }

    public void setProductLineID(int productLineID) {
        this.productLineID = productLineID;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public String getPrecaution() {
        return precaution;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
    }

    public String getPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(String pregnancy) {
        this.pregnancy = pregnancy;
    }

    public String getSideeffects() {
        return sideeffects;
    }

    public void setSideeffects(String sideeffects) {
        this.sideeffects = sideeffects;
    }

    public String getInteractions() {
        return interactions;
    }

    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MedicamentAdditional{" +
                "medicamentSet=" + medicamentSet +
                ", productLineID=" + productLineID +
                ", composition='" + composition + '\'' +
                ", effects='" + effects + '\'' +
                ", indications='" + indications + '\'' +
                ", contraindications='" + contraindications + '\'' +
                ", precaution='" + precaution + '\'' +
                ", pregnancy='" + pregnancy + '\'' +
                ", sideeffects='" + sideeffects + '\'' +
                ", interactions='" + interactions + '\'' +
                ", dosage='" + dosage + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
