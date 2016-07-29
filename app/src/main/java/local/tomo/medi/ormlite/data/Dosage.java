package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dosage {

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "medicament_disease_id")
    private Medicament_Disease medicament_disease;

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int idServer;
    @DatabaseField
    private Date takeTime;
    @DatabaseField
    private int wholePackage;
    @DatabaseField
    private String unit;
    @DatabaseField
    private int dose;
    @DatabaseField
    private boolean sended;
}
