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

    public Patient() {
    }

    public Patient(String name, Date birthday) {
        this.name = name;
        this.birthdayLong = birthday.getTime();
    }

    //private Set<Disease> diseases;

}
