package io.klustr.utils;

import org.apache.tika.Tika;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Helpful functions for basic images useful for avatars and resizing
 * for profiles and icons.
 */
public class ImageUtils {

    private static final Tika tika = new Tika();

    /**
     * Returns the mimeType for the specified image bytes.
     * @param imageBytes The image bytes to try to return
     * @return THe mimetype such as image/png, image/webp etc.
     */
    public static String getMimeType(byte[] imageBytes) {
        return tika.detect(imageBytes);
    }

    /**
     * Standardized output as a PNG for all images
     * @param image The image that is a PNG.
     */
    public static record Png(byte[] image) {
        public static final String mimeType = "image/png";
        public static final String extension = "png";
    }

    /**
     * Standardizes the image in various formats (jpg, gif, png, webp) to a PNG with the specified dimensions
     * resized center, great for user profile uploads.
     * @param imageBytes The image bytes of the image which could be any image type.
     * @param maxHeightOrWidth The max height or width of the image to use.
     * @return Returns the standardized PNG resized if needed.
     * @throws Exception On any issues with bytes.
     */
    public static Png standardize(byte[] imageBytes,  int maxHeightOrWidth) throws Exception {
        BufferedImage original = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (original == null) throw new RuntimeException("Unsupported or corrupted image");

        int width = original.getWidth();
        int height = original.getHeight();

        // Scale so the smaller dimension fills 256 (we’ll crop excess)
        double scale = Math.max((double) maxHeightOrWidth / width, (double) maxHeightOrWidth / height);
        int newWidth = (int) Math.round(width * scale);
        int newHeight = (int) Math.round(height * scale);

        // Resize image
        Image scaled = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();

        // Crop from center
        int x = (newWidth - maxHeightOrWidth) / 2;
        int y = (newHeight - maxHeightOrWidth) / 2;
        BufferedImage cropped = resized.getSubimage(x, y, maxHeightOrWidth, maxHeightOrWidth);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(cropped, "png", baos);
        return new Png(baos.toByteArray());
    }
}
