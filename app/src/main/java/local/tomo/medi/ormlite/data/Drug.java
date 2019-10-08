package local.tomo.medi.ormlite.data;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class Drug {

    public static final String D_PACKAGE_ID = "package_id";
    public static final String D_NAME = "name";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @SerializedName("package_id")
    @DatabaseField(columnName = D_PACKAGE_ID, unique = true)
    private int packageID;

    @SerializedName("product_name")
    @DatabaseField(columnName = D_NAME)
    private String name;

    @SerializedName("producer")
    @DatabaseField(columnName = "producer")
    private String producer;

    @SerializedName("pack")
    @DatabaseField(columnName = "pack")
    private String pack;

    @SerializedName("form")
    @DatabaseField(columnName = "form")
    private String form;

    @SerializedName("dosage")
    @DatabaseField(columnName = "dosage")
    private String dosage;

    @SerializedName("ean")
    @DatabaseField(columnName = "ean")
    private String ean;

    public Drug() {
    }

    public Drug(int packageID, String name, String producer, String pack, String form, String ean, String dosage) {
        this.packageID = packageID;
        this.name = name;
        this.producer = producer;
        this.pack = pack;
        this.form = form;
        this.ean = ean;
        this.dosage = dosage;
    }
}
