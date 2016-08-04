package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class File {

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "disease_id")
    private Disease disease;

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int idServer;
    @DatabaseField
    private String name;
    @DatabaseField
    private String description;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] file;

    public File() {}
}
