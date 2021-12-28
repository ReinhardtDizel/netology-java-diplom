package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextGraphicsConverterImpl implements TextGraphicsConverter {

    private TextColorSchema schema;
    private double maxRatio = 0;
    private int maxHeight = 0;
    private int maxWidth = 0;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));

        int newWidth = img.getWidth();
        int newHeight = img.getHeight();
        double ratio = newWidth / newHeight;
        if ((maxHeight != 0 || maxWidth != 0) && (newWidth > maxHeight || newWidth > maxWidth)) {
            newHeight = maxHeight;
            newWidth = maxWidth;
        }
        if (maxRatio != 0 && ratio > maxRatio) {
            throw new BadImageSizeException(ratio, maxRatio);
        }
        char[][] symbolsImage = new char[newHeight][newWidth * 2];

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0, otherX = 0; x < newWidth; x++, otherX++) {
                int color = bwRaster.getPixel(x, y, new int[3])[0];
                char c = schema.convert(color);
                symbolsImage[y][otherX] = c;
                otherX++;
                symbolsImage[y][otherX] = c;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (char[] row : symbolsImage) {
            stringBuilder.append(row).append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
