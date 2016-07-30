package local.tomo.medi.ormlite.data;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Disease {

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "patient_id")
    private Patient patient;

    @ForeignCollectionField
    private ForeignCollection<Medicament_Disease> medicament_diseases;

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int idServer;
    @DatabaseField
    private String name;
    @DatabaseField
    private String description;
    @DatabaseField
    private Date start;
    @DatabaseField
    private Date stop;
    @DatabaseField
    private long startLong;
    @DatabaseField
    private long stopLong;
    @DatabaseField
    private boolean archive;

    private boolean hidden;

    public Disease() {}


    public Disease(String name, Date time, String description) {
        this.name = name;
        this.startLong = time.getTime();
        this.description = description;
    }
}
