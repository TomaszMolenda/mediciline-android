package local.tomo.medi.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.Drug;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.MedicamentAdditional;
import lombok.SneakyThrows;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "medis.db";
    private static final int DATABASE_VERSION = 3;

    private Dao<Drug, Integer> drugs;
    private Dao<Medicament, Integer> medicamentDao;
    private Dao<DbMedicament, Integer> medicamentDbDao;
    private Dao<MedicamentAdditional, Integer> medicamentAdditionalsDao;

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
        TableUtils.createTable(connectionSource, Medicament.class);
        TableUtils.createTableIfNotExists(connectionSource, DbMedicament.class);
        TableUtils.createTableIfNotExists(connectionSource, MedicamentAdditional.class);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    @SneakyThrows
    public Dao<Drug, Integer> getDrugsDataAccess() {
        if(drugs == null) {
            drugs = getDao(Drug.class);
        }
        return drugs;
    }
}
