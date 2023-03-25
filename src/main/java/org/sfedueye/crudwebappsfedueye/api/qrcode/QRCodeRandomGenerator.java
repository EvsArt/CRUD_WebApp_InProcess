package org.sfedueye.crudwebappsfedueye.api.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

@Component
public class QRCodeRandomGenerator implements QRGenerator {

    @Value("${server.port}")
    String port;

    @Value("${qrcode.size}")
    int qrCodeSize;
    @Value("${qrcode.path}")
    String qrCodePath;

    @Value("${qrcode.length}")
    int qrLength;

    ArrayList<NetworkInterface> interfaces;

    {
        try {
            interfaces = Collections.list(
                    NetworkInterface.getNetworkInterfaces());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private final Random random = new SecureRandom();


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
    public String generate() {

        final byte[] bytes = new byte[qrLength];
        random.nextBytes(bytes);

        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) sb.append(String.format("%02x",b));

        String key = sb.toString();

        String charset = "UTF-8";

        String fileName = "demo.png";

        createQR(key, qrCodePath+fileName, charset, qrCodeSize, qrCodeSize);

        return key;
}
}
