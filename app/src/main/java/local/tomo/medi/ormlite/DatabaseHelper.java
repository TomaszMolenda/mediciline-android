package local.tomo.medi.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.MedicamentDb;
import local.tomo.medi.ormlite.data.User;


/**
 * Created by tomo on 2016-06-22.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "medi.db";
    private static final int DATABASE_VERSION = 4;

    private Dao<User, Integer> userDao;
    private Dao<MedicamentDb, Integer> medicamentDbDao;
    private Dao<Medicament, Integer> medicamentDao;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            //TableUtils.createTable(connectionSource, MedicamentDb.class);
            TableUtils.createTable(connectionSource, Medicament.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            //TableUtils.dropTable(connectionSource, MedicamentDb.class, true);
            TableUtils.dropTable(connectionSource, Medicament.class, true);
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

    public Dao<MedicamentDb, Integer> getMedicamentDbDao() throws SQLException {
        if(medicamentDbDao == null) {
            medicamentDbDao = getDao(MedicamentDb.class);
        }
        return medicamentDbDao;
    }

    public Dao<Medicament, Integer> getMedicamentDao() throws SQLException {
        if(medicamentDao == null) {
            medicamentDao = getDao(Medicament.class);
        }
        return medicamentDao;
    }
}
