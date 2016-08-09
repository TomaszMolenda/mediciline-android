package local.tomo.medi.ormlite;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import local.tomo.medi.R;
import local.tomo.medi.ormlite.data.DbMedicament;
import local.tomo.medi.ormlite.data.Disease;
import local.tomo.medi.ormlite.data.Dosage;
import local.tomo.medi.ormlite.data.File;
import local.tomo.medi.ormlite.data.Medicament;
import local.tomo.medi.ormlite.data.MedicamentAdditional;
import local.tomo.medi.ormlite.data.Medicament_Disease;
import local.tomo.medi.ormlite.data.Patient;
import local.tomo.medi.ormlite.data.User;
import local.tomo.medi.utills.Utill;
import lombok.SneakyThrows;


/**
 * Created by tomo on 2016-06-22.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "medis1.db";
    private static final int DATABASE_VERSION = 2;

    private Dao<User, Integer> userDao;
    private Dao<Medicament, Integer> medicamentDao;
    private Dao<Patient, Integer> patientDao;
    private Dao<Disease, Integer> diseaseDao;
    private Dao<Medicament_Disease, Integer> medicament_diseaseDao;
    private Dao<DbMedicament, Integer> medicamentDbDao;
    private Dao<MedicamentAdditional, Integer> medicamentAdditionalsDao;
    private Dao<Dosage, Integer> dosageDao;
    private Dao<File, Integer> fileDao;

    private ProgressDialog progressDialog;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    @SneakyThrows
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        TableUtils.createTable(connectionSource, User.class);
        TableUtils.createTable(connectionSource, Medicament.class);
        TableUtils.createTable(connectionSource, Patient.class);
        TableUtils.createTable(connectionSource, Disease.class);
        TableUtils.createTable(connectionSource, Medicament_Disease.class);
        TableUtils.createTable(connectionSource, Dosage.class);
        TableUtils.createTable(connectionSource, File.class);
        TableUtils.createTableIfNotExists(connectionSource, DbMedicament.class);
        TableUtils.createTableIfNotExists(connectionSource, MedicamentAdditional.class);
    }

    @Override
    @SneakyThrows
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        TableUtils.dropTable(connectionSource, User.class, true);
        TableUtils.dropTable(connectionSource, Medicament.class, true);
        TableUtils.dropTable(connectionSource, Patient.class, true);
        TableUtils.dropTable(connectionSource, Disease.class, true);
        TableUtils.dropTable(connectionSource, Medicament_Disease.class, true);
        TableUtils.dropTable(connectionSource, Dosage.class, true);
        TableUtils.dropTable(connectionSource, File.class, true);
        onCreate(database, connectionSource);
    }

    @SneakyThrows
    public void dropMedicamentsDb() {
        Log.d(Utill.TAG, "dropMedicamentsDb: drop medi");
        TableUtils.dropTable(connectionSource, DbMedicament.class, true);
        TableUtils.dropTable(connectionSource, MedicamentAdditional.class, true);
        TableUtils.createTable(connectionSource, DbMedicament.class);
        TableUtils.createTable(connectionSource, MedicamentAdditional.class);

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

    public Dao<Disease, Integer> getDiseaseDao() throws SQLException {
        if(diseaseDao == null) {
            diseaseDao = getDao(Disease.class);
        }
        return diseaseDao;
    }

    public Dao<Medicament_Disease, Integer> getMedicament_DiseaseDao() throws SQLException {
        if(medicament_diseaseDao == null) {
            medicament_diseaseDao = getDao(Medicament_Disease.class);
        }
        return medicament_diseaseDao;
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

    public Dao<Dosage, Integer> getDosageDao() throws SQLException {
        if(dosageDao == null) {
            dosageDao = getDao(Dosage.class);
        }
        return dosageDao;
    }

    public Dao<File, Integer> getFileDao() throws SQLException {
        if(fileDao == null) {
            fileDao = getDao(File.class);
        }
        return fileDao;
    }


}
