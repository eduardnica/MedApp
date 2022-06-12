package csie.aplicatielicenta.Models;

public class County {
    private String countyId;
    private String countyName;
    private String totalCases;

    public County(String countyId, String countyName, String totalCases) {
        this.countyId = countyId;
        this.countyName = countyName;
        this.totalCases = totalCases;
    }

    public String getCountyId() {
        return countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public String getTotalCases() {
        return totalCases;
    }
}
