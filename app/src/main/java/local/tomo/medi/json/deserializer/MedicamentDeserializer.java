package local.tomo.medi.json.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import local.tomo.medi.ormlite.data.Medicament;

/**
 * Created by tomo on 2016-06-13.
 */
public class MedicamentDeserializer implements JsonDeserializer<Medicament> {
    @Override
    public Medicament deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement id = json.getAsJsonObject().get("id");
        JsonElement name = json.getAsJsonObject().get("name");
        JsonElement producent = json.getAsJsonObject().get("producent");
        JsonElement price = json.getAsJsonObject().get("price");
        JsonElement kind = json.getAsJsonObject().get("kind");
        JsonElement productLineID = json.getAsJsonObject().get("productLineID");
        JsonElement packageID = json.getAsJsonObject().get("packageID");
        JsonElement dateExpiration = json.getAsJsonObject().get("dateExpiration");
        Medicament medicament = new Medicament();
        medicament.setId(id.getAsInt());
        medicament.setName(name.getAsString());
        medicament.setProducent(producent.getAsString());
        medicament.setPrice(price.getAsDouble());
        medicament.setKind(kind.getAsString());
        medicament.setProductLineID(productLineID.getAsInt());
        medicament.setPackageID(packageID.getAsInt());
        Date date = new Date(dateExpiration.getAsLong());
        medicament.setDateExpiration(date);

        return medicament;
    }
}
