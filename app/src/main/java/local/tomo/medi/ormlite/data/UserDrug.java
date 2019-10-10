package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class UserDrug {

    public static final String D_ARCHIVE = "archive";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "drug_id")
    private Drug drug;

    @DatabaseField(columnName = "overdue_date", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date overdueDate;

    @DatabaseField(columnName = "archive")
    private boolean archive;

    public UserDrug() {
    }

    public UserDrug(Drug drug, Date overdueDate) {
        this.drug = drug;
        this.overdueDate = overdueDate;
    }

    public String getExpirationDate() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(overdueDate);
    }

    public void markAsArchive() {
        this.archive = true;
    }
}
