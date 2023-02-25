package org.sfedueye.crudwebappsfedueye.api.qrcode;


import lombok.Data;

@Data
public class RequestFormByQRCode {

    private long userId;
    private int dataHashCode;    // HashCode of user's data for security

}
