package local.tomo.medi.ormlite.data;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
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
}
