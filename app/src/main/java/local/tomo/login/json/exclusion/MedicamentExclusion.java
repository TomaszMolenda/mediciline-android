package local.tomo.login.json.exclusion;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import local.tomo.login.model.DateExpirationYearMonth;

/**
 * Created by tomo on 2016-05-26.
 */

//http://stackoverflow.com/a/4803346
public class MedicamentExclusion implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getDeclaredClass() == DateExpirationYearMonth.class && f.getName().equals("dateExpirationYearMonth"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
