package local.tomo.medi.ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;

import local.tomo.medi.ormlite.data.Drug;
import lombok.SneakyThrows;

import static local.tomo.medi.ormlite.data.Drug.D_EAN;
import static local.tomo.medi.ormlite.data.Drug.D_NAME;

public class DrugQuery {

    private final DatabaseHelper databaseHelper;

    DrugQuery(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @SneakyThrows
    public List<Drug> listByName(String searchText) {

        Dao<Drug, Integer> drugsDataAccess = databaseHelper.getDrugsDataAccess();
        QueryBuilder<Drug, Integer> queryBuilder = drugsDataAccess.queryBuilder();

        queryBuilder.where()
                .like(D_NAME, "%"+ searchText +"%");

        return drugsDataAccess.query(queryBuilder.prepare());
    }

    @SneakyThrows
    public Drug getById(Integer drugId) {

        return databaseHelper.getDrugsDataAccess()
                .queryForId(drugId);
    }

    @SneakyThrows
    public Drug getEan(String ean) {

        Dao<Drug, Integer> drugsDataAccess = databaseHelper.getDrugsDataAccess();
        QueryBuilder<Drug, Integer> queryBuilder = drugsDataAccess.queryBuilder();

        PreparedQuery<Drug> prepare = queryBuilder.where()
                .eq(D_EAN, ean)
                .prepare();

        return drugsDataAccess.queryForFirst(prepare);
    }
}
