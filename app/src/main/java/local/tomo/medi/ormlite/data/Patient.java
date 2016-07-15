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

    public Patient() {
    }

    public Patient(String name, Date birthday, String path) {
        this.name = name;
        this.birthdayLong = birthday.getTime();
        this.photoUrl = path;
    }

    //private Set<Disease> diseases;

}
