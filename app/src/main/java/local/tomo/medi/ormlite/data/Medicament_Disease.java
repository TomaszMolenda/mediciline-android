package local.tomo.medi.ormlite.data;


import com.j256.ormlite.field.DatabaseField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Medicament_Disease {

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "medicament_id")
    private Medicament medicament;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "disease_id")
    private Disease disease;

    @DatabaseField(generatedId = true)
    private int id;

    public Medicament_Disease() {}

    public Medicament_Disease(Medicament medicament, Disease disease) {
        this.medicament = medicament;
        this.disease = disease;
    }
}
