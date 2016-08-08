package local.tomo.medi.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

//http://stackoverflow.com/a/4803346
public class MedicamentExclusion implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getName().equals("dateExpiration"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
