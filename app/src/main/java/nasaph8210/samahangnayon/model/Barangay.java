package nasaph8210.samahangnayon.model;

public class Barangay {
    private String code;
    private String name;
    private String oldName;
    private boolean subMunicipalityCode;
    private boolean cityCode;
    private String municipalityCode;
    private boolean districtCode;
    private String provinceCode;
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

    public boolean isSubMunicipalityCode() {
        return subMunicipalityCode;
    }

    public void setSubMunicipalityCode(boolean subMunicipalityCode) {
        this.subMunicipalityCode = subMunicipalityCode;
    }

    public boolean isCityCode() {
        return cityCode;
    }

    public void setCityCode(boolean cityCode) {
        this.cityCode = cityCode;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public boolean isDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(boolean districtCode) {
        this.districtCode = districtCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
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
