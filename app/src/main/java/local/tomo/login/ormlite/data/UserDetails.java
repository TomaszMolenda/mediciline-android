package local.tomo.login.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by tomo on 2016-06-22.
 */
public class UserDetails implements Serializable {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "name")
    private String name;

    public UserDetails() {
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
}
