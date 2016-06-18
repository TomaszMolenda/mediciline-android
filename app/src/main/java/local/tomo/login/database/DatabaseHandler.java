package local.tomo.login.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import local.tomo.login.model.Medicament;
import local.tomo.login.model.MedicamentDb;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "medicamets.db";
    public static final String TABLE_MEDICAMENTS = "medicaments";
    public static final String TABLE_MEDICAMENTS_COLUMN_ID = "id";
    public static final String TABLE_MEDICAMENTS_COLUMN_ID_SERVER = "id_server";
    public static final String TABLE_MEDICAMENTS_COLUMN_NAME = "name";
    public static final String TABLE_MEDICAMENTS_COLUMN_PRODUCENT = "producent";
    public static final String TABLE_MEDICAMENTS_COLUMN_PRICE = "price";
    public static final String TABLE_MEDICAMENTS_COLUMN_KIND = "kind";
    public static final String TABLE_MEDICAMENTS_COLUMN_DATE = "date";
    public static final String TABLE_MEDICAMENTS_COLUMN_PACKAGE_ID = "package_id";
    public static final String TABLE_MEDICAMENTS_COLUMN_PRODUCT_LINE_ID = "product_line_id";

    public static final String TABLE_MEDICAMENTSDB = "medicamentsdb";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PACKAGEID = "packageID";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_ACTIVESUBSTANCE = "activeSubstance";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_DISTRIBUTORID = "distributorID";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_DOSAGE = "dosage";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_DRIVING = "driving";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_DRIVINGINFO = "drivingInfo";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_DRUGCARDLIMIT = "drugCardLimit";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_DRUGPROMOID = "drugPromoID";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_EAN = "ean";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_FINALSORT = "finalSort";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_FORM = "form";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_ISALCO = "isAlco";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_ISALCOINFO = "isAlcoInfo";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCH = "isNarcPsych";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCHINFO = "isNarcPsychInfo";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_ISREIMBURSED = "isReimbursed";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_LACTATIO = "lactatio";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_LACTATIOINFO = "lactatioInfo";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PACK = "pack";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PREGNANCY = "pregnancy";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PREGNANCYINFO = "pregnancyInfo";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONID = "prescriptionID";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONNAME = "prescriptionName";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONSHORTNAME = "prescriptionShortName";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRICE = "price";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRODUCER = "producer";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRODUCERID = "producerID";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRODUCTID = "productID";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINEID = "productLineID";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINENAME = "productLineName";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRODUCTNAME = "productName";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPEID = "productTypeID";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPENAME = "productTypeName";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPESHORTNAME = "productTypeShortName";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_REGNO = "regNo";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_SPONSORID = "sponsorID";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_THERAPEUTICCLASS = "therapeuticClass";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1 = "trimester1";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1INFO = "trimester1Info";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2 = "trimester2";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2INFO = "trimester2Info";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3 = "trimester3";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3INFO = "trimester3Info";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_ISFAVORITE = "isFavorite";
    public static final String TABLE_MEDICAMENTSDB_COLUMN_OI = "oi";



    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_MEDICAMENTS + "(" +
                TABLE_MEDICAMENTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_MEDICAMENTS_COLUMN_ID_SERVER + " INTEGER, " +
                TABLE_MEDICAMENTS_COLUMN_PACKAGE_ID + " INTEGER, " +
                TABLE_MEDICAMENTS_COLUMN_PRODUCT_LINE_ID + " INTEGER, " +
                TABLE_MEDICAMENTS_COLUMN_NAME + " TEXT, " +
                TABLE_MEDICAMENTS_COLUMN_PRODUCENT + " TEXT, " +
                TABLE_MEDICAMENTS_COLUMN_PRICE + " REAL, " +
                TABLE_MEDICAMENTS_COLUMN_KIND + " TEXT, " +
                TABLE_MEDICAMENTS_COLUMN_DATE + " TEXT" +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABLE_MEDICAMENTSDB + "(" +
                TABLE_MEDICAMENTSDB_COLUMN_PACKAGEID + " INTEGER PRIMARY KEY," +
                TABLE_MEDICAMENTSDB_COLUMN_ACTIVESUBSTANCE + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_DISTRIBUTORID + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_DOSAGE + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_DRIVING + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_DRIVINGINFO + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_DRUGCARDLIMIT + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_DRUGPROMOID + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_EAN + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_FINALSORT + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_FORM + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_ISALCO + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_ISALCOINFO + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCH + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCHINFO + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_ISREIMBURSED + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_LACTATIO + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_LACTATIOINFO + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_PACK + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_PREGNANCY + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_PREGNANCYINFO + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONID + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONNAME + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONSHORTNAME + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_PRICE + " REAL," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCER + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCERID + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTID + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINEID + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINENAME + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTNAME + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPEID + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPENAME + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPESHORTNAME + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_REGNO + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_SPONSORID + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_THERAPEUTICCLASS + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1 + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1INFO + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2 + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2INFO + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3 + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3INFO + " TEXT," +
                TABLE_MEDICAMENTSDB_COLUMN_ISFAVORITE + " INTEGER," +
                TABLE_MEDICAMENTSDB_COLUMN_OI + " INTEGER" +
                ");";
        db.execSQL(query);
    }

    public void removeAllMedicaments() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_MEDICAMENTS + " WHERE 1;";
        db.execSQL(query);
    }

    public boolean deleteRowMedicamentsDb(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_MEDICAMENTSDB, TABLE_MEDICAMENTSDB_COLUMN_PACKAGEID + "=" + id, null) > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAMENTS);
        //db.execSQL("DROP TABLE IF EXISTS" + TABLE_MEDICAMENTSDB);
        onCreate(db);
    }

    public long addMedicament(Medicament medicament){

        ContentValues values = new ContentValues();
        values.put(TABLE_MEDICAMENTS_COLUMN_ID_SERVER, medicament.getIdServer());
        values.put(TABLE_MEDICAMENTS_COLUMN_NAME, medicament.getName());
        values.put(TABLE_MEDICAMENTS_COLUMN_PRODUCENT, medicament.getProducent());
        values.put(TABLE_MEDICAMENTS_COLUMN_PRICE, medicament.getPrice());
        values.put(TABLE_MEDICAMENTS_COLUMN_KIND, medicament.getKind());
        long dateLong = medicament.getDate();
        String dateString = getDate(dateLong);
        values.put(TABLE_MEDICAMENTS_COLUMN_DATE, dateString);
        values.put(TABLE_MEDICAMENTS_COLUMN_PACKAGE_ID, medicament.getPackageID());
        values.put(TABLE_MEDICAMENTS_COLUMN_PRODUCT_LINE_ID, medicament.getProductLineID());


        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TABLE_MEDICAMENTS, null, values);
        db.close();
        Log.d("tomo", "zapisałem lek " +medicament.getName() + " do db, id: "+id);
        return id;


    }

    private String getDate(long dateLong) {
        Date date = new Date(dateLong);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return simpleDateFormat.format(date);
    }


    public boolean updateMedicament(int idServer, Medicament medicament) {
        ContentValues args = new ContentValues();
        args.put(TABLE_MEDICAMENTS_COLUMN_NAME, medicament.getName());
        args.put(TABLE_MEDICAMENTS_COLUMN_PRODUCENT, medicament.getProducent());
        args.put(TABLE_MEDICAMENTS_COLUMN_PRICE, medicament.getPrice());
        args.put(TABLE_MEDICAMENTS_COLUMN_KIND, medicament.getKind());
        long dateLong = medicament.getDate();
        String dateString = getDate(dateLong);
        args.put(TABLE_MEDICAMENTS_COLUMN_DATE, dateString);
        args.put(TABLE_MEDICAMENTS_COLUMN_PACKAGE_ID, medicament.getPackageID());
        args.put(TABLE_MEDICAMENTS_COLUMN_PRODUCT_LINE_ID, medicament.getProductLineID());

        SQLiteDatabase db = getWritableDatabase();
        return db.update(TABLE_MEDICAMENTS, args, TABLE_MEDICAMENTS_COLUMN_ID_SERVER + "=" + idServer, null) > 0;
    }

    public boolean setIdServer(Medicament medicament) {
        ContentValues args = new ContentValues();
        args.put(TABLE_MEDICAMENTS_COLUMN_ID_SERVER, medicament.getIdServer());
        int id = medicament.getId();
        SQLiteDatabase db = getWritableDatabase();
        Log.d("tomo", "ustawiam idserver " + medicament.getIdServer() + ", dla med id: " + id);
        return db.update(TABLE_MEDICAMENTS, args, TABLE_MEDICAMENTS_COLUMN_ID + "=" + id, null) > 0;
    }

    public void setIdServer(List<Medicament> medicaments) {
        for (Medicament medicament : medicaments) {
            setIdServer(medicament);
        }
    }

    public boolean deleteMedicament(String id) {
        SQLiteDatabase db = getWritableDatabase();
        Log.d("tomo", "Usuwam lek z db, id: "+id);
        return db.delete(TABLE_MEDICAMENTS, TABLE_MEDICAMENTS_COLUMN_ID + "=" + id, null) > 0;
    }



    public void addMedicamentDb(MedicamentDb medicamentDb){
        ContentValues values = new ContentValues();
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PACKAGEID, medicamentDb.getPackageID());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_ACTIVESUBSTANCE, medicamentDb.getActiveSubstance());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_DISTRIBUTORID, medicamentDb.getDistributorID());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_DOSAGE, medicamentDb.getDosage());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_DRIVING, medicamentDb.getDriving());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_DRIVINGINFO, medicamentDb.getDrivingInfo());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_DRUGCARDLIMIT, medicamentDb.getDrugCardLimit());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_DRUGPROMOID, medicamentDb.getDrugPromoID());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_EAN, medicamentDb.getEan());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_FINALSORT, medicamentDb.getFinalSort());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_FORM, medicamentDb.getForm());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_ISALCO, medicamentDb.getIsAlco());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_ISALCOINFO, medicamentDb.getIsAlcoInfo());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCH, medicamentDb.getIsNarcPsych());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCHINFO, medicamentDb.getIsNarcPsychInfo());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_ISREIMBURSED, medicamentDb.getIsReimbursed());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_LACTATIO, medicamentDb.getLactatio());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_LACTATIOINFO, medicamentDb.getLactatioInfo());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PACK, medicamentDb.getPack());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PREGNANCY, medicamentDb.getPregnancy());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PREGNANCYINFO, medicamentDb.getPregnancyInfo());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONID, medicamentDb.getPrescriptionID());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONNAME, medicamentDb.getPrescriptionName());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONSHORTNAME, medicamentDb.getPrescriptionShortName());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRICE, medicamentDb.getPrice());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRODUCER, medicamentDb.getProducer());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRODUCERID, medicamentDb.getProducerID());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRODUCTID, medicamentDb.getProductID());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINEID, medicamentDb.getProductLineID());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINENAME, medicamentDb.getProductLineName());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRODUCTNAME, medicamentDb.getProductName());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPEID, medicamentDb.getProductTypeID());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPENAME, medicamentDb.getProductTypeName());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPESHORTNAME, medicamentDb.getProductTypeShortName());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_REGNO, medicamentDb.getRegNo());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_SPONSORID, medicamentDb.getSponsorID());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_THERAPEUTICCLASS, medicamentDb.getTherapeuticClass());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1, medicamentDb.getTrimester1());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1INFO, medicamentDb.getTrimester1Info());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2, medicamentDb.getTrimester2());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2INFO, medicamentDb.getTrimester2Info());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3, medicamentDb.getTrimester3());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3INFO, medicamentDb.getTrimester3Info());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_ISFAVORITE, medicamentDb.getIsFavorite());
        values.put(TABLE_MEDICAMENTSDB_COLUMN_OI, medicamentDb.getOi());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MEDICAMENTSDB, null, values);
        db.close();
    }



    public List<Medicament> getMedicaments() {
        List<Medicament> medicaments = new ArrayList<Medicament>();
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICAMENTS + " ORDER BY " + TABLE_MEDICAMENTS_COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Medicament medicament = new Medicament();
                medicament.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TABLE_MEDICAMENTS_COLUMN_ID))));
                medicament.setName(cursor.getString(cursor.getColumnIndex(TABLE_MEDICAMENTS_COLUMN_NAME)));
                medicament.setProducent(cursor.getString(cursor.getColumnIndex(TABLE_MEDICAMENTS_COLUMN_PRODUCENT)));
                String idServer = cursor.getString(cursor.getColumnIndex(TABLE_MEDICAMENTS_COLUMN_ID_SERVER));
                if(idServer!=null)
                    medicament.setIdServer(Integer.parseInt(idServer));
                medicament.setKind(cursor.getString(cursor.getColumnIndex(TABLE_MEDICAMENTS_COLUMN_KIND)));
                medicament.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(TABLE_MEDICAMENTS_COLUMN_PRICE))));
                String packageId = cursor.getString(cursor.getColumnIndex(TABLE_MEDICAMENTS_COLUMN_PACKAGE_ID));
                if(packageId != null)
                    medicament.setPackageID(Integer.parseInt(packageId));
                String productLineID = cursor.getString(cursor.getColumnIndex(TABLE_MEDICAMENTS_COLUMN_PRODUCT_LINE_ID));
                if(productLineID != null)
                    medicament.setProductLineID(Integer.parseInt(productLineID));
                String date = cursor.getString(cursor.getColumnIndex(TABLE_MEDICAMENTS_COLUMN_DATE));
                try {
                    Date parse = simpleDateFormat.parse(date);
                    medicament.setDateExpiration(parse);
                    long time = parse.getTime();
                    medicament.setDate(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                medicaments.add(medicament);
            } while (cursor.moveToNext());
        }
        Log.d("tomo", "pobieram wszytskie leki z db, rozmiar: "+medicaments.size());
        return medicaments;
    }

    public Medicament getLastMedicament() {
        List<Medicament> medicaments = getMedicaments();
        if(medicaments.size() >= 1)
            return medicaments.get(0);
        return null;
    }

    public List<Medicament> getMedicamentsToSend() {
        List<Medicament> medicamentsToSend = new ArrayList<Medicament>();
        List<Medicament> medicaments = getMedicaments();
        for (Medicament medicament : medicaments) {
            if(medicament.getIdServer() == 0)
                medicamentsToSend.add(medicament);
        }
        return medicamentsToSend;
    }

    public List<Medicament> getSendedMedicaments() {
        List<Medicament> sendedMedicaments = new ArrayList<Medicament>();
        List<Medicament> medicaments = getMedicaments();
        for (Medicament medicament : medicaments) {
            if(medicament.getIdServer() != 0)
                sendedMedicaments.add(medicament);
        }
        return sendedMedicaments;
    }

    public List<MedicamentDb> getMedicamentsDb() {
        List<MedicamentDb> medicamentsDbs = new ArrayList<MedicamentDb>();
        String selectQuery = "SELECT " +
                TABLE_MEDICAMENTSDB_COLUMN_PACKAGEID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ACTIVESUBSTANCE + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DISTRIBUTORID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DOSAGE + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DRIVING + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DRIVINGINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DRUGCARDLIMIT + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DRUGPROMOID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_EAN + "," +
                TABLE_MEDICAMENTSDB_COLUMN_FINALSORT + "," +
                TABLE_MEDICAMENTSDB_COLUMN_FORM + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISALCO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISALCOINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCH + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCHINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISREIMBURSED + "," +
                TABLE_MEDICAMENTSDB_COLUMN_LACTATIO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_LACTATIOINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PACK + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PREGNANCY + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PREGNANCYINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONNAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONSHORTNAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRICE + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCER + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCERID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINEID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINENAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTNAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPEID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPENAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPESHORTNAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_REGNO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_SPONSORID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_THERAPEUTICCLASS + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1 + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1INFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2 + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2INFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3 + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3INFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISFAVORITE + "," +
                TABLE_MEDICAMENTSDB_COLUMN_OI +
                " FROM " + TABLE_MEDICAMENTSDB;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MedicamentDb medicamentDb = new MedicamentDb();
                medicamentDb.setPackageID(cursor.getInt(0));
                medicamentDb.setActiveSubstance(cursor.getString(1));
                medicamentDb.setDistributorID(cursor.getInt(2));
                medicamentDb.setDosage(cursor.getString(3));
                medicamentDb.setDriving(cursor.getInt(4));
                medicamentDb.setDrivingInfo(cursor.getString(5));
                medicamentDb.setDrugCardLimit(cursor.getInt(6));
                medicamentDb.setDrugPromoID(cursor.getInt(7));
                medicamentDb.setEan(cursor.getString(8));
                medicamentDb.setFinalSort(cursor.getInt(9));
                medicamentDb.setForm(cursor.getString(10));
                medicamentDb.setIsAlco(cursor.getInt(11));
                medicamentDb.setIsAlcoInfo(cursor.getString(12));
                medicamentDb.setIsNarcPsych(cursor.getInt(13));
                medicamentDb.setIsNarcPsychInfo(cursor.getString(14));
                medicamentDb.setIsReimbursed(cursor.getInt(15));
                medicamentDb.setLactatio(cursor.getInt(16));
                medicamentDb.setLactatioInfo(cursor.getString(17));
                medicamentDb.setPack(cursor.getString(18));
                medicamentDb.setPregnancy(cursor.getInt(19));
                medicamentDb.setPregnancyInfo(cursor.getString(20));
                medicamentDb.setPrescriptionID(cursor.getInt(21));
                medicamentDb.setPrescriptionName(cursor.getString(22));
                medicamentDb.setPrescriptionShortName(cursor.getString(23));
                medicamentDb.setPrice(cursor.getDouble(24));
                medicamentDb.setProducer(cursor.getString(25));
                medicamentDb.setProducerID(cursor.getInt(26));
                medicamentDb.setProductID(cursor.getInt(27));
                medicamentDb.setProductLineID(cursor.getInt(28));
                medicamentDb.setProductLineName(cursor.getString(29));
                medicamentDb.setProductName(cursor.getString(30));
                medicamentDb.setProductTypeID(cursor.getInt(31));
                medicamentDb.setProductTypeName(cursor.getString(32));
                medicamentDb.setProductTypeShortName(cursor.getString(33));
                medicamentDb.setRegNo(cursor.getString(34));
                medicamentDb.setSponsorID(cursor.getInt(35));
                medicamentDb.setTherapeuticClass(cursor.getString(36));
                medicamentDb.setTrimester1(cursor.getInt(37));
                medicamentDb.setTrimester1Info(cursor.getString(38));
                medicamentDb.setTrimester2(cursor.getInt(39));
                medicamentDb.setTrimester2Info(cursor.getString(40));
                medicamentDb.setTrimester3(cursor.getInt(41));
                medicamentDb.setTrimester3Info(cursor.getString(42));
                medicamentDb.setIsFavorite(cursor.getInt(43));
                medicamentDb.setOi(cursor.getInt(44));
                medicamentsDbs.add(medicamentDb);
            } while (cursor.moveToNext());
        }
        return medicamentsDbs;
    }

    public int getMedicamentsDbCount() {
        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_MEDICAMENTSDB;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int putAllMedicamentsDb(List<MedicamentDb> medicamentDbs) {
        for (MedicamentDb medicamentDb : medicamentDbs) {
            addMedicamentDb(medicamentDb);
        }
        return medicamentDbs.size();
    }

    public ArrayList<MedicamentDb> searchMedicamentsDb(String searchText) {
        ArrayList<MedicamentDb> medicamentsDbs = new ArrayList<MedicamentDb>();
        String selectQuery = "SELECT " +
                TABLE_MEDICAMENTSDB_COLUMN_PACKAGEID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ACTIVESUBSTANCE + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DISTRIBUTORID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DOSAGE + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DRIVING + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DRIVINGINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DRUGCARDLIMIT + "," +
                TABLE_MEDICAMENTSDB_COLUMN_DRUGPROMOID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_EAN + "," +
                TABLE_MEDICAMENTSDB_COLUMN_FINALSORT + "," +
                TABLE_MEDICAMENTSDB_COLUMN_FORM + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISALCO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISALCOINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCH + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISNARCPSYCHINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISREIMBURSED + "," +
                TABLE_MEDICAMENTSDB_COLUMN_LACTATIO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_LACTATIOINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PACK + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PREGNANCY + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PREGNANCYINFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONNAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRESCRIPTIONSHORTNAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRICE + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCER + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCERID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINEID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTLINENAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTNAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPEID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPENAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_PRODUCTTYPESHORTNAME + "," +
                TABLE_MEDICAMENTSDB_COLUMN_REGNO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_SPONSORID + "," +
                TABLE_MEDICAMENTSDB_COLUMN_THERAPEUTICCLASS + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1 + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER1INFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2 + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER2INFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3 + "," +
                TABLE_MEDICAMENTSDB_COLUMN_TRIMESTER3INFO + "," +
                TABLE_MEDICAMENTSDB_COLUMN_ISFAVORITE + "," +
                TABLE_MEDICAMENTSDB_COLUMN_OI +
                " FROM " + TABLE_MEDICAMENTSDB +
                " WHERE " + TABLE_MEDICAMENTSDB_COLUMN_PRODUCTNAME +
                " LIKE '%" + searchText + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MedicamentDb medicamentDb = new MedicamentDb();
                medicamentDb.setPackageID(cursor.getInt(0));
                medicamentDb.setActiveSubstance(cursor.getString(1));
                medicamentDb.setDistributorID(cursor.getInt(2));
                medicamentDb.setDosage(cursor.getString(3));
                medicamentDb.setDriving(cursor.getInt(4));
                medicamentDb.setDrivingInfo(cursor.getString(5));
                medicamentDb.setDrugCardLimit(cursor.getInt(6));
                medicamentDb.setDrugPromoID(cursor.getInt(7));
                medicamentDb.setEan(cursor.getString(8));
                medicamentDb.setFinalSort(cursor.getInt(9));
                medicamentDb.setForm(cursor.getString(10));
                medicamentDb.setIsAlco(cursor.getInt(11));
                medicamentDb.setIsAlcoInfo(cursor.getString(12));
                medicamentDb.setIsNarcPsych(cursor.getInt(13));
                medicamentDb.setIsNarcPsychInfo(cursor.getString(14));
                medicamentDb.setIsReimbursed(cursor.getInt(15));
                medicamentDb.setLactatio(cursor.getInt(16));
                medicamentDb.setLactatioInfo(cursor.getString(17));
                medicamentDb.setPack(cursor.getString(18));
                medicamentDb.setPregnancy(cursor.getInt(19));
                medicamentDb.setPregnancyInfo(cursor.getString(20));
                medicamentDb.setPrescriptionID(cursor.getInt(21));
                medicamentDb.setPrescriptionName(cursor.getString(22));
                medicamentDb.setPrescriptionShortName(cursor.getString(23));
                medicamentDb.setPrice(cursor.getDouble(24));
                medicamentDb.setProducer(cursor.getString(25));
                medicamentDb.setProducerID(cursor.getInt(26));
                medicamentDb.setProductID(cursor.getInt(27));
                medicamentDb.setProductLineID(cursor.getInt(28));
                medicamentDb.setProductLineName(cursor.getString(29));
                medicamentDb.setProductName(cursor.getString(30));
                medicamentDb.setProductTypeID(cursor.getInt(31));
                medicamentDb.setProductTypeName(cursor.getString(32));
                medicamentDb.setProductTypeShortName(cursor.getString(33));
                medicamentDb.setRegNo(cursor.getString(34));
                medicamentDb.setSponsorID(cursor.getInt(35));
                medicamentDb.setTherapeuticClass(cursor.getString(36));
                medicamentDb.setTrimester1(cursor.getInt(37));
                medicamentDb.setTrimester1Info(cursor.getString(38));
                medicamentDb.setTrimester2(cursor.getInt(39));
                medicamentDb.setTrimester2Info(cursor.getString(40));
                medicamentDb.setTrimester3(cursor.getInt(41));
                medicamentDb.setTrimester3Info(cursor.getString(42));
                medicamentDb.setIsFavorite(cursor.getInt(43));
                medicamentDb.setOi(cursor.getInt(44));
                medicamentsDbs.add(medicamentDb);
            } while (cursor.moveToNext());
        }
        return medicamentsDbs;
    }


    public void addMedicaments(List<Medicament> remoteMedicamentsToSave) {
        for (Medicament medicament : remoteMedicamentsToSave) {
            addMedicament(medicament);
        }
    }
}