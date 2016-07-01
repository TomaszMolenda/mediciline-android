package local.tomo.medi.ormlite.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;


public class MedicamentDb implements Serializable {

    @DatabaseField(id = true)
    private int packageID;
    @DatabaseField
    private int idServer;
    @DatabaseField
    private String activeSubstance;
    @DatabaseField
    private int distributorID;
    @DatabaseField
    private String dosage;
    @DatabaseField
    private int driving;
    @DatabaseField
    private String drivingInfo;
    @DatabaseField
    private int drugCardLimit;
    @DatabaseField
    private int drugPromoID;
    @DatabaseField
    private String ean;
    @DatabaseField
    private int finalSort;
    @DatabaseField
    private String form;
    @DatabaseField
    private int isAlco;
    @DatabaseField
    private String isAlcoInfo;
    @DatabaseField
    private int isNarcPsych;
    @DatabaseField
    private String isNarcPsychInfo;
    @DatabaseField
    private int isReimbursed;
    @DatabaseField
    private int lactatio;
    @DatabaseField
    private String lactatioInfo;
    @DatabaseField
    private String pack;
    @DatabaseField
    private int pregnancy;
    @DatabaseField
    private String pregnancyInfo;
    @DatabaseField
    private int prescriptionID;
    @DatabaseField
    private String prescriptionName;
    @DatabaseField
    private String prescriptionShortName;
    @DatabaseField
    private double price;
    @DatabaseField
    private String producer;
    @DatabaseField
    private int producerID;
    @DatabaseField
    private int productID;
    @DatabaseField
    private int productLineID;
    @DatabaseField
    private String productLineName;
    @DatabaseField
    private String productName;
    @DatabaseField
    private int productTypeID;
    @DatabaseField
    private String productTypeName;
    @DatabaseField
    private String productTypeShortName;
    @DatabaseField
    private String regNo;
    @DatabaseField
    private int sponsorID;
    @DatabaseField
    private String therapeuticClass;
    @DatabaseField
    private int trimester1;
    @DatabaseField
    private String trimester1Info;
    @DatabaseField
    private int trimester2;
    @DatabaseField
    private String trimester2Info;
    @DatabaseField
    private int trimester3;
    @DatabaseField
    private String trimester3Info;
    @DatabaseField
    private int isFavorite;
    @DatabaseField
    private int oi;



    public MedicamentDb() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof MedicamentDb))return false;
        MedicamentDb medicamentDb = (MedicamentDb)obj;
        if(this.hashCode() == medicamentDb.hashCode()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        int multipler = 31;
        //int
        result = multipler * result + packageID;
        return result;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public String getActiveSubstance() {
        return activeSubstance;
    }

    public void setActiveSubstance(String activeSubstance) {
        this.activeSubstance = activeSubstance;
    }

    public int getDistributorID() {
        return distributorID;
    }

    public void setDistributorID(int distributorID) {
        this.distributorID = distributorID;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getDriving() {
        return driving;
    }

    public void setDriving(int driving) {
        this.driving = driving;
    }

    public String getDrivingInfo() {
        return drivingInfo;
    }

    public void setDrivingInfo(String drivingInfo) {
        this.drivingInfo = drivingInfo;
    }

    public int getDrugCardLimit() {
        return drugCardLimit;
    }

    public void setDrugCardLimit(int drugCardLimit) {
        this.drugCardLimit = drugCardLimit;
    }

    public int getDrugPromoID() {
        return drugPromoID;
    }

    public void setDrugPromoID(int drugPromoID) {
        this.drugPromoID = drugPromoID;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public int getFinalSort() {
        return finalSort;
    }

    public void setFinalSort(int finalSort) {
        this.finalSort = finalSort;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public int getIsAlco() {
        return isAlco;
    }

    public void setIsAlco(int isAlco) {
        this.isAlco = isAlco;
    }

    public String getIsAlcoInfo() {
        return isAlcoInfo;
    }

    public void setIsAlcoInfo(String isAlcoInfo) {
        this.isAlcoInfo = isAlcoInfo;
    }

    public int getIsNarcPsych() {
        return isNarcPsych;
    }

    public void setIsNarcPsych(int isNarcPsych) {
        this.isNarcPsych = isNarcPsych;
    }

    public String getIsNarcPsychInfo() {
        return isNarcPsychInfo;
    }

    public void setIsNarcPsychInfo(String isNarcPsychInfo) {
        this.isNarcPsychInfo = isNarcPsychInfo;
    }

    public int getIsReimbursed() {
        return isReimbursed;
    }

    public void setIsReimbursed(int isReimbursed) {
        this.isReimbursed = isReimbursed;
    }

    public int getLactatio() {
        return lactatio;
    }

    public void setLactatio(int lactatio) {
        this.lactatio = lactatio;
    }

    public String getLactatioInfo() {
        return lactatioInfo;
    }

    public void setLactatioInfo(String lactatioInfo) {
        this.lactatioInfo = lactatioInfo;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public int getPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(int pregnancy) {
        this.pregnancy = pregnancy;
    }

    public String getPregnancyInfo() {
        return pregnancyInfo;
    }

    public void setPregnancyInfo(String pregnancyInfo) {
        this.pregnancyInfo = pregnancyInfo;
    }

    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public String getPrescriptionShortName() {
        return prescriptionShortName;
    }

    public void setPrescriptionShortName(String prescriptionShortName) {
        this.prescriptionShortName = prescriptionShortName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getProducerID() {
        return producerID;
    }

    public void setProducerID(int producerID) {
        this.producerID = producerID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductLineID() {
        return productLineID;
    }

    public void setProductLineID(int productLineID) {
        this.productLineID = productLineID;
    }

    public String getProductLineName() {
        return productLineName;
    }

    public void setProductLineName(String productLineName) {
        this.productLineName = productLineName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(int productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductTypeShortName() {
        return productTypeShortName;
    }

    public void setProductTypeShortName(String productTypeShortName) {
        this.productTypeShortName = productTypeShortName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public int getSponsorID() {
        return sponsorID;
    }

    public void setSponsorID(int sponsorID) {
        this.sponsorID = sponsorID;
    }

    public String getTherapeuticClass() {
        return therapeuticClass;
    }

    public void setTherapeuticClass(String therapeuticClass) {
        this.therapeuticClass = therapeuticClass;
    }

    public int getTrimester1() {
        return trimester1;
    }

    public void setTrimester1(int trimester1) {
        this.trimester1 = trimester1;
    }

    public String getTrimester1Info() {
        return trimester1Info;
    }

    public void setTrimester1Info(String trimester1Info) {
        this.trimester1Info = trimester1Info;
    }

    public int getTrimester2() {
        return trimester2;
    }

    public void setTrimester2(int trimester2) {
        this.trimester2 = trimester2;
    }

    public String getTrimester2Info() {
        return trimester2Info;
    }

    public void setTrimester2Info(String trimester2Info) {
        this.trimester2Info = trimester2Info;
    }

    public int getTrimester3() {
        return trimester3;
    }

    public void setTrimester3(int trimester3) {
        this.trimester3 = trimester3;
    }

    public String getTrimester3Info() {
        return trimester3Info;
    }

    public void setTrimester3Info(String trimester3Info) {
        this.trimester3Info = trimester3Info;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public int getOi() {
        return oi;
    }

    public void setOi(int oi) {
        this.oi = oi;
    }

    @Override
    public String toString() {
        return "MedicamentDb{" +
                "packageID=" + packageID +
                ", idServer=" + idServer +
                ", activeSubstance='" + activeSubstance + '\'' +
                ", distributorID=" + distributorID +
                ", dosage='" + dosage + '\'' +
                ", driving=" + driving +
                ", drivingInfo='" + drivingInfo + '\'' +
                ", drugCardLimit=" + drugCardLimit +
                ", drugPromoID=" + drugPromoID +
                ", ean='" + ean + '\'' +
                ", finalSort=" + finalSort +
                ", form='" + form + '\'' +
                ", isAlco=" + isAlco +
                ", isAlcoInfo='" + isAlcoInfo + '\'' +
                ", isNarcPsych=" + isNarcPsych +
                ", isNarcPsychInfo='" + isNarcPsychInfo + '\'' +
                ", isReimbursed=" + isReimbursed +
                ", lactatio=" + lactatio +
                ", lactatioInfo='" + lactatioInfo + '\'' +
                ", pack='" + pack + '\'' +
                ", pregnancy=" + pregnancy +
                ", pregnancyInfo='" + pregnancyInfo + '\'' +
                ", prescriptionID=" + prescriptionID +
                ", prescriptionName='" + prescriptionName + '\'' +
                ", prescriptionShortName='" + prescriptionShortName + '\'' +
                ", price=" + price +
                ", producer='" + producer + '\'' +
                ", producerID=" + producerID +
                ", productID=" + productID +
                ", productLineID=" + productLineID +
                ", productLineName='" + productLineName + '\'' +
                ", productName='" + productName + '\'' +
                ", productTypeID=" + productTypeID +
                ", productTypeName='" + productTypeName + '\'' +
                ", productTypeShortName='" + productTypeShortName + '\'' +
                ", regNo='" + regNo + '\'' +
                ", sponsorID=" + sponsorID +
                ", therapeuticClass='" + therapeuticClass + '\'' +
                ", trimester1=" + trimester1 +
                ", trimester1Info='" + trimester1Info + '\'' +
                ", trimester2=" + trimester2 +
                ", trimester2Info='" + trimester2Info + '\'' +
                ", trimester3=" + trimester3 +
                ", trimester3Info='" + trimester3Info + '\'' +
                ", isFavorite=" + isFavorite +
                ", oi=" + oi +
                '}';
    }
}
