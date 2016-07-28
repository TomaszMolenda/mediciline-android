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

    private String confirmPassword;

    @DatabaseField
    private String uniqueID;

    @DatabaseField
    private String email;

    private String confirmEmail;

    @DatabaseField
    private String auth;

    public User() {}


    public User(String name, String email, String confirmEmail, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.confirmEmail = confirmEmail;
        this.password = password;
        this.confirmPassword = password;
    }


}
