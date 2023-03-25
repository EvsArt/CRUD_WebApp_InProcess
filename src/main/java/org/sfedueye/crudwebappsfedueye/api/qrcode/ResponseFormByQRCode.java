package org.sfedueye.crudwebappsfedueye.api.qrcode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseFormByQRCode {

    private boolean access;

    private String message;

}
