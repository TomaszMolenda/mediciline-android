package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class UserDrug {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "drug_id")
    private Drug drug;

    @DatabaseField(columnName = "overdue_date", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date overdueDate;

    public UserDrug() {
    }

    public UserDrug(Drug drug, Date overdueDate) {
        this.drug = drug;
        this.overdueDate = overdueDate;
    }
}
