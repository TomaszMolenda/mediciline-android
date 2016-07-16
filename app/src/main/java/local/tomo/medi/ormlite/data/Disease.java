package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Disease {

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "patient_id")
    private Patient patient;

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

    public Disease() {}


}
