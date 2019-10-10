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

    @SneakyThrows
    public List<UserDrug> listActiveByName(String searchText) {

        Dao<Drug, Integer> drugsDataAccess = databaseHelper.getDrugsDataAccess();
        Dao<UserDrug, Integer> userDrugsDataAccess = databaseHelper.getUserDrugsDataAccess();

        QueryBuilder<Drug, Integer> drugsQueryBuilder = drugsDataAccess.queryBuilder();
        QueryBuilder<UserDrug, Integer> userDrugsQueryBuilder = userDrugsDataAccess.queryBuilder();

        drugsQueryBuilder
                .where()
                .like(D_NAME, "%"+ searchText +"%");

        userDrugsQueryBuilder
                .where()
                .eq(D_ARCHIVE, false);

        userDrugsQueryBuilder.join(drugsQueryBuilder);

        PreparedQuery<UserDrug> prepare = userDrugsQueryBuilder.prepare();

        return userDrugsDataAccess.query(prepare);
    }

    @SneakyThrows
    public List<UserDrug> listAllActive() {

        Dao<UserDrug, Integer> userDrugsDataAccess = databaseHelper.getUserDrugsDataAccess();

        PreparedQuery<UserDrug> prepare = userDrugsDataAccess.queryBuilder()
                .where()
                .eq(D_ARCHIVE, false)
                .prepare();

        return userDrugsDataAccess.query(prepare);
    }

    @SneakyThrows
    public void save(UserDrug userDrug) {

        databaseHelper.getUserDrugsDataAccess().createOrUpdate(userDrug);
    }
}
