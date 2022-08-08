package server.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ReqErrorResponse {
    private int status;
    private String message;
    private Date timestamp;

    public ReqErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
