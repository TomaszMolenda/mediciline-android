package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by tomo on 2016-06-22.
 */
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", uniqueID='" + uniqueID + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
