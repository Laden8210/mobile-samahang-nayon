package nasaph8210.samahangnayon.model;

public class City {
    private String code;
    private String name;
    private String oldName;
    private boolean isCapital;
    private String provinceCode;
    private boolean districtCode;
    private String regionCode;
    private String islandGroupCode;
    private String psgc10DigitCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public boolean isCapital() {
        return isCapital;
    }

    public void setCapital(boolean capital) {
        isCapital = capital;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public boolean isDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(boolean districtCode) {
        this.districtCode = districtCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getIslandGroupCode() {
        return islandGroupCode;
    }

    public void setIslandGroupCode(String islandGroupCode) {
        this.islandGroupCode = islandGroupCode;
    }

    public String getPsgc10DigitCode() {
        return psgc10DigitCode;
    }

    public void setPsgc10DigitCode(String psgc10DigitCode) {
        this.psgc10DigitCode = psgc10DigitCode;
    }
    @Override
    public String toString() {
        return name;
    }
}
