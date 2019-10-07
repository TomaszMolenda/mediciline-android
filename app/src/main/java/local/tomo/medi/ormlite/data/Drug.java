package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Drug {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "package_id")
    private int packageID;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "producer")
    private String producer;

    @DatabaseField(columnName = "pack")
    private String pack;

    @DatabaseField(columnName = "form")
    private String form;

    public Drug() {
    }

    public Drug(int packageID, String name, String producer, String pack, String form) {
        this.packageID = packageID;
        this.name = name;
        this.producer = producer;
        this.pack = pack;
        this.form = form;
    }
}
