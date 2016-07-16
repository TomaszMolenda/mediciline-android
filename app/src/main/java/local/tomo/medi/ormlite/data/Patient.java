package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Patient {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int idServer;
    @DatabaseField
    private String name;
    @DatabaseField
    private Date birthday;
    @DatabaseField
    private long birthdayLong;
    @DatabaseField
    private String photoUrl;
    @DatabaseField
    private boolean lastUse;

    public Patient() {
    }

    public Patient(String name, Date birthday, String path) {
        this.name = name;
        this.birthdayLong = birthday.getTime();
        this.photoUrl = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Patient))return false;
        Patient patient = (Patient)obj;
        if(this.hashCode() == patient.hashCode()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        int multipler = 31;
        //int
        result = multipler * result + id;
        return result;
    }

    //private Set<Disease> diseases;

}
