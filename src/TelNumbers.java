public class TelNumbers {
    private int telId;
    private String telNum;

    public TelNumbers(int telId, String telNum) {
        this.telId = telId;
        this.telNum = telNum;
    }

    public int getTelId() {
        return telId;
    }

    public void setTelId(int telId) {
        this.telId = telId;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    @Override
    public String toString() {
        return "TelNumbers{" +
                "telId=" + telId +
                ", telNum='" + telNum + '\'' +
                '}';
    }
}
