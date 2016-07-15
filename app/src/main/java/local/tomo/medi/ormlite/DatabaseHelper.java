package local.tomo.medi.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.MedicamentAdditional;
import local.tomo.medi.ormlite.data.Patient;
import local.tomo.medi.ormlite.data.User;


/**
 * Created by tomo on 2016-06-22.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "medis13.db";
    private static final int DATABASE_VERSION = 3;

    private Dao<User, Integer> userDao;
    private Dao<Medicament, Integer> medicamentDao;
    private Dao<Patient, Integer> patientDao;
    private Dao<DbMedicament, Integer> medicamentDbDao;
    private Dao<MedicamentAdditional, Integer> medicamentAdditionalsDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Medicament.class);
            TableUtils.createTable(connectionSource, Patient.class);
            //TableUtils.createTable(connectionSource, DbMedicament.class);
            //TableUtils.createTable(connectionSource, MedicamentAdditional.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Medicament.class, true);
            TableUtils.dropTable(connectionSource, Patient.class, true);
            //TableUtils.dropTable(connectionSource, DbMedicament.class, true);
            //TableUtils.dropTable(connectionSource, MedicamentAdditional.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<Medicament, Integer> getMedicamentDao() throws SQLException {
        if(medicamentDao == null) {
            medicamentDao = getDao(Medicament.class);
        }
        return medicamentDao;
    }

    public Dao<Patient, Integer> getPatientDao() throws SQLException {
        if(patientDao == null) {
            patientDao = getDao(Patient.class);
        }
        return patientDao;
    }

    public Dao<DbMedicament, Integer> getMedicamentDbDao() throws SQLException {
        if(medicamentDbDao == null) {
            medicamentDbDao = getDao(DbMedicament.class);
        }
        return medicamentDbDao;
    }


    public Dao<MedicamentAdditional, Integer> getMedicamentAdditionalsDao() throws SQLException {
        if(medicamentAdditionalsDao == null) {
            medicamentAdditionalsDao = getDao(MedicamentAdditional.class);
        }
        return medicamentAdditionalsDao;
    }
}
