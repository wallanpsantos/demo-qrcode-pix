package com.br.demoqrcodepix.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static java.lang.System.out;

public class QrCodeUtil {

    private static final String DIRECTORY = "./src/main/resources/qrcode";
    private static String uuid = String.valueOf(UUID.randomUUID());
    private static final String FORMAT_IMAGE = "png";

    private static final Path PATH_IMAGE = new File(DIRECTORY.concat("/").concat(uuid).concat(".").concat(FORMAT_IMAGE)).toPath();

    private static void writeQRCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);

        MatrixToImageWriter.writeToPath(bitMatrix, FORMAT_IMAGE, PATH_IMAGE);
    }

    private static String readQRCode(File file) throws IOException, ChecksumException, NotFoundException, FormatException {
        QRCodeReader qrCodeReader = new QRCodeReader();

        Result decode = qrCodeReader.decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file)))));

        return decode.getText();
    }

    public static void main(String[] args) throws IOException, WriterException, ChecksumException, NotFoundException, FormatException {
        String text = "Here is my message to generate the QR Code!";

        QrCodeUtil.writeQRCode(text);

        out.println(QrCodeUtil.readQRCode(new File(PATH_IMAGE.toString())));
    }
}
