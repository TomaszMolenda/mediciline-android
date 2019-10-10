package local.tomo.medi.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import local.tomo.medi.ormlite.data.Drug;
import local.tomo.medi.ormlite.data.UserDrug;
import lombok.SneakyThrows;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "medisss.db";
    private static final int DATABASE_VERSION = 6;

    private Dao<Drug, Integer> drugs;
    private Dao<UserDrug, Integer> userDrugs;
    private DrugQuery drugQuery;
    private UserDrugQuery userDrugQuery;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    @SneakyThrows
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        TableUtils.createTableIfNotExists(connectionSource, Drug.class);
        TableUtils.createTableIfNotExists(connectionSource, UserDrug.class);
    }

    @SneakyThrows
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        TableUtils.dropTable(connectionSource, UserDrug.class, true);
        TableUtils.createTableIfNotExists(connectionSource, UserDrug.class);
    }

    @SneakyThrows
    Dao<Drug, Integer> getDrugsDataAccess() {
        if(drugs == null) {
            drugs = getDao(Drug.class);
        }
        return drugs;
    }

    @SneakyThrows
    Dao<UserDrug, Integer> getUserDrugsDataAccess() {
        if(userDrugs == null) {
            userDrugs = getDao(UserDrug.class);
        }
        return userDrugs;
    }

    @SneakyThrows
    public DrugQuery getDrugQuery() {
        if(drugQuery == null) {
            drugQuery = new DrugQuery(this);
        }
        return drugQuery;
    }

    @SneakyThrows
    public UserDrugQuery getUserDrugQuery() {
        if(userDrugQuery == null) {
            userDrugQuery = new UserDrugQuery(this);
        }

        return userDrugQuery;
    }
}
