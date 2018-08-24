package requesdt.volley.com.handlerequestlib;

/**
 * Created by baby on 2017/5/26.
 */

public class ResponceData {
 private  String resultcode;
    private  String reason;
    private int error_code;
    private Result result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    @Override
    public String toString() {
        return "ResponceData{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", error_code=" + error_code +
                ", result=" + result +
                '}';
    }

}
