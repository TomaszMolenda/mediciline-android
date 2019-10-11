package local.tomo.medi.ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;

import local.tomo.medi.ormlite.data.Drug;
import local.tomo.medi.ormlite.data.UserDrug;
import lombok.SneakyThrows;

import static local.tomo.medi.ormlite.data.Drug.D_NAME;
import static local.tomo.medi.ormlite.data.UserDrug.D_ARCHIVE;

public class UserDrugQuery {

    private final DatabaseHelper databaseHelper;

    UserDrugQuery(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public List<UserDrug> listActiveByName(String searchText) {

        return listByNameAndArchive(searchText, false);
    }

    public List<UserDrug> listAllActive() {

        return listAllByArchive(false);
    }

    @SneakyThrows
    public void save(UserDrug userDrug) {

        databaseHelper.getUserDrugsDataAccess().createOrUpdate(userDrug);
    }

    public List<UserDrug> listAllArchive() {

        return listAllByArchive(true);
    }

    public List<UserDrug> listArchiveByName(String searchText) {

        return listByNameAndArchive(searchText, false);
    }

    @SneakyThrows
    private List<UserDrug> listByNameAndArchive(String searchText, boolean archive) {

        Dao<Drug, Integer> drugsDataAccess = databaseHelper.getDrugsDataAccess();
        Dao<UserDrug, Integer> userDrugsDataAccess = databaseHelper.getUserDrugsDataAccess();

        QueryBuilder<Drug, Integer> drugsQueryBuilder = drugsDataAccess.queryBuilder();
        QueryBuilder<UserDrug, Integer> userDrugsQueryBuilder = userDrugsDataAccess.queryBuilder();

        drugsQueryBuilder
                .where()
                .like(D_NAME, "%"+ searchText +"%");

        userDrugsQueryBuilder
                .where()
                .eq(D_ARCHIVE, archive);

        userDrugsQueryBuilder.join(drugsQueryBuilder);

        PreparedQuery<UserDrug> prepare = userDrugsQueryBuilder.prepare();

        return userDrugsDataAccess.query(prepare);
    }

    @SneakyThrows
    private List<UserDrug> listAllByArchive(boolean archive) {

        Dao<UserDrug, Integer> userDrugsDataAccess = databaseHelper.getUserDrugsDataAccess();

        PreparedQuery<UserDrug> prepare = userDrugsDataAccess.queryBuilder()
                .where()
                .eq(D_ARCHIVE, archive)
                .prepare();

        return userDrugsDataAccess.query(prepare);
    }
}
