package org.sfedueye.crudwebappsfedueye.api.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Random;

@Component
public class QRCodeRandomGenerator implements QRGenerator {

    @Value("${server.port}")
    String port;

    @Value("${qrcode.size}")
    int qrCodeSize;
    @Value("${qrcode.path}")
    String qrCodePath;

    ArrayList<NetworkInterface> interfaces;

    {
        try {
            interfaces = Collections.list(
                    NetworkInterface.getNetworkInterfaces());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private final Random random = new Random();


    // Function to create the QR code
    private void createQR(String data, String path,
                                String charset,
                                int height, int width)
            throws WriterException, IOException
    {

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToFile(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                new File(path));
    }
    

    @SneakyThrows
    @Override
    public int generate() {
        int key = random.nextInt();

        Enumeration<InetAddress> address = interfaces.get(0).getInetAddresses();
        address.nextElement();
        String baseURL = "http://" + address.nextElement().getHostAddress() + ":" + port;
        String data = baseURL + "?key=" + key;
        String charset = "UTF-8";

        String fileName = "demo.png";

        createQR(data, qrCodePath+fileName, charset, qrCodeSize, qrCodeSize);

        return key;
}
}
