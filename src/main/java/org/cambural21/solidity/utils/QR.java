package org.cambural21.solidity.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class QR {

    private QR(){}

    public BufferedImage generate(int width, int height, String message, ErrorCorrectionLevel error) throws WriterException {
        BufferedImage image = null;
        try{
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
            hashMap.put(EncodeHintType.ERROR_CORRECTION, error);
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(message.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), BarcodeFormat.QR_CODE, width, height);
            image = MatrixToImageWriter.toBufferedImage(matrix);
        }catch (Exception e){
            e.printStackTrace();
            image = null;
        }
        return image;
    }

}
