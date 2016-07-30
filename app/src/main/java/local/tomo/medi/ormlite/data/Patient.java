package local.tomo.medi.ormlite.data;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Patient {

    @ForeignCollectionField
    private ForeignCollection<Disease> diseases;

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
    private boolean lastUse;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] photo;

    private boolean hidden;

    public Patient() {
    }

    public Patient(String name, Date birthday, byte[] photo) {
        this.name = name;
        this.birthdayLong = birthday.getTime();
        this.photo = photo;
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


}
