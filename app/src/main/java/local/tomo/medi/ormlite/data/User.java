package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String password;

    @DatabaseField
    private String uniqueID;

    @DatabaseField
    private String email;

    public User() {
    }

}
