package nasaph8210.samahangnayon.model;

public class Region {
    private String code;
    private String name;
    private String regionName;
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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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
