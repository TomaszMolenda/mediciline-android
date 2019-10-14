package local.tomo.medi.ormlite.data;

import android.content.Context;
import android.content.res.Resources;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

import local.tomo.medi.activity.drug.Months;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class UserDrug {

    public static final String D_ARCHIVE = "archive";
    public static final String D_OVERDUE_DATE = "overdue_date";

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

    public String getExpirationDate(Context context) {

        Resources resources = context.getResources();

        String month = resources.getString(resources.getIdentifier(Months.valueOf(overdueDate.getMonth()).name(), "string", context.getPackageName()));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

        return String.format("%s %s", month, formatter.format(overdueDate));
    }

    public boolean isOverdue() {

        Date monthPrevious = DateTime.now().minusMonths(1).toDate();

        return overdueDate.before(monthPrevious);
    }

    public void markAsArchive() {
        this.archive = true;
    }
}
