import java.awt.Color;
import java.util.*;

/**
 * The {@code Post} class represents a post with an associated photo, caption, date, and likes.
 * The class provides methods to retrieve and manipulate the post information, 
 * including resizing the photo and generating a formatted string representation of the post.
 */
public class Post {

    /** The desired size for the resized photo. */
    public static final int PHOTO_SIZE = 17;

    private Picture photo;   // The photo associated with the post
    private String filename; // The filename of the photo
    private String caption;  // The caption of the post
    private String date;     // The date of the post
    private int likes;       // The number of likes on the post
    private int retweets;    // The number of times the number of retweets

    /**
     * Constructs a new Post with the specified photo, caption, date, number of likes and retweets!
     *
     * @param photoString  The file path of the photo.
     * @param caption      The caption of the post.
     * @param date         The date of the post.
     * @param likes        The number of likes on the post
     * @param retweets     The number of retweets on the post
     *
     * @throws IllegalArgumentException if retweets is less than 0
     * @throws IllegalArgumentException if either dimension is less than PHOTO_SIZE
     */
    public Post(String photoString, String caption, String date, int likes, int retweets) {
        if (retweets < 0) {
            throw new IllegalArgumentException("Retweets are less than zero: " + retweets);
        }
        // Verify Photo is correct size
        Picture photo = new Picture(photoString);
        Color[][] pixels = photo.getPixels();
        if (pixels.length < PHOTO_SIZE || pixels[0].length < PHOTO_SIZE) {
            throw new IllegalArgumentException("Photo must be at least " + PHOTO_SIZE +
                                               " pixels in each dimension");
        }
        // resize photo to PHOTO_SIZE
        photo = new Picture(PHOTO_SIZE, PHOTO_SIZE);
        photo.setPixels(getResizedPhoto(pixels));
        // set fields
        this.photo = photo;
        this.filename = photoString;
        this.caption = caption;
        this.date = date;
        this.likes = likes;
        this.retweets = retweets;
    }

    /**
     * Constructs a new Post with the specified photo, caption, and date.
     * The post has a random number of likes and a random number of retweets.
     *
     * @param photoString   The file path of the photo.
     * @param caption       The caption of the post.
     * @param date          The date of the post.
     * @throws IllegalArgumentException if either dimension is less than PHOTO_SIZE
     */
    public Post(String photoString, String caption, String date) {
        this(photoString, caption, date,
             new Random().nextInt(100000) - 10000, new Random().nextInt(50000));
    }

    /**
     * Gets the string representation of the resized photo.
     *
     * @return The string representation of the resized photo.
     */
    public String getPhotoString() {
        return photoToString(this.photo.getPixels());
    }

    /**
     * Gets the string filename of the photo.
     *
     * @return The filename of this Post's photo
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Gets the 2D array of Color objects representing the photo.
     *
     * @return The 2D array of Color objects representing the photo.
     */
    public Color[][] getPhotoPixels() {
        return this.photo.getPixels();
    }

    /**
     * Updates the photo of this post to match the given 2D array of Color objects. Does not represent the photo filename!
     *
     * @param pixels  The 2D array of Color objects representing the new photo.
     */
    public void updatePhoto(Color[][] pixels) {
        this.photo.setPixels(pixels);
    }

    /**
     * Gets the caption of the post.
     *
     * @return The caption of the post.
     */
    public String getCaption() {
        return this.caption;
    }

    /**
     * Updates the caption of this post to be the new provided caption.
     *
     * @param caption  The new caption of the post.
     */
    public void updateCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Gets the date of the post.
     *
     * @return The date of the post.
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Gets the number of likes on the post.
     *
     * @return The number of likes on the post.
     */
    public int getLikes() {
        return this.likes;
    }

    /**
     * Gets the number of retweets on the post.
     *
     * @return The number of retweets on the post.
     */
    public int getRetweets() {
        return this.retweets;
    }

    /**
     * Increases the number of likes on the post by 1.
     */
    public void likePost() {
        this.likes++;
    }

    /**
     * Decreases the number of likes on the post by 1.
     */
    public void dislikePost() {
        this.likes--;
    }

    /**
     * Increases the number of retweets on the post by 1.
     */
    public void retweetPost() {
        this.retweets++;
    }

    /**
     * Returns a string representation of the post, including the resized  
     * photo (spaces with background colors), the caption, number of likes,
     * number of retweets, and the date. 
     *
     * @return The string representation of the post.
     */
    public String toString() {
        return this.date + "\n" +
               this.caption + "\n" +
               photoToString(this.photo.getPixels()) +
               "Likes: " + this.likes + "  |  Retweets: " + this.retweets;
    }
    
    /**
     * Compares with Post with the provided Object
     * This Post is equal to the provded Object if the
     * Object is a Post and both Posts have the same
     * photo pixels, caption, date, likes, and retweets.
     *
     * @param other  The Object to check against this Post for equality
     *
     * @return The boolean equality result
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Post) {
            Post otherPost = (Post) other;
            return (
                    this.photo.equals(otherPost.photo) &&
                    this.caption.equals(otherPost.caption) &&
                    this.date.equals(otherPost.date) &&
                    this.likes == otherPost.likes &&
                    this.retweets == otherPost.retweets);
        }
        return false;
    }

    // PRIVATE HELPER METHDOS /////////////////////////////////////////////////////////////////////

    /**
     * Resizes the provided photo for this post to be of PHOTO_SIZE in each dimension.
     * The central sqaure will be selected.
     *
     * @param The 2D color representation of the original photo
     *
     * @return The 2D color representation of the resized photo
     */
    private Color[][] getResizedPhoto(Color[][] pixels) {
        // Get central square of image
        boolean wide = pixels.length < pixels[0].length;
        if (wide) { // make tall or sqaure
            pixels = getTransposePicture(pixels);
        }
        pixels = getPhotoSquare(pixels);
        if (wide) { // make tall or sqaure
            pixels = getTransposePicture(pixels);
        }

        // resize
        int scaleFactor = pixels.length / PHOTO_SIZE;
        Color[][] resizedImage = new Color[PHOTO_SIZE][PHOTO_SIZE];
        // loop through and average colors
        for (int i = 0; i < PHOTO_SIZE; i++) {
            for (int j = 0; j < PHOTO_SIZE; j++) {
                // Calculate average color for each block
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                for (int x = 0; x < scaleFactor; x++) {
                    for (int y = 0; y < scaleFactor; y++) {
                        int row = i * scaleFactor + x;
                        int col = j * scaleFactor + y;

                        Color pixelColor = pixels[row][col];
                        sumRed += pixelColor.getRed();
                        sumGreen += pixelColor.getGreen();
                        sumBlue += pixelColor.getBlue();
                    }
                }

                int avgRed = sumRed / (scaleFactor * scaleFactor);
                int avgGreen = sumGreen / (scaleFactor * scaleFactor);
                int avgBlue = sumBlue / (scaleFactor * scaleFactor);

                resizedImage[i][j] = new Color(avgRed, avgGreen, avgBlue);
            }
        }
        return resizedImage;
    }

    /**
     * Rotates the photo of this post 90 degrees counter clockwise
     *
     * @return The 2D color representation of the resized photo 
     */
    private Color[][] getTransposePicture(Color[][] pixels) {
        Color[][] transpose = new Color[pixels[0].length][pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                transpose[j][i] = pixels[i][j];
            }
        }
        return transpose;
    }

    /**
     * Selects the central square of an image. The image must be at least
     * as tall as it is wide.
     *
     * @param The 2D color representation of the original photo 
     *
     * @return The 2D color representation of the squared photo 
     */
    private Color[][] getPhotoSquare(Color[][] pixels) {
        int length = pixels[0].length;
        Color[][] square = new Color[length][length];
        int start = (pixels.length / 2) - (length / 2); // middle - half length
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++) {
                square[i][j] = pixels[start+i][j];
            }
        }
        return square;
    }

    /**
     * Converts the photo of this post to a String.
     * The returned String will be made of spaces where each pair of spaces represents
     * a pixel and is assigned a ansi color background value.
     *
     * @param The 2D color representation of the original photo 
     *
     * @return The String representation of the photo of this post
     */
    private String photoToString(Color[][] pixels) {
        String pic = "";
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                Color color = pixels[i][j];
                int red = (int) Math.round(color.getRed() / 51.0);
                int green = (int) Math.round(color.getGreen() / 51.0);
                int blue = (int) Math.round(color.getBlue() / 51.0);
                // int ansiCode1 = 16 + 36 * red + 6 * green + blue;
                int ansiCode2 = 16 + (36 * (color.getRed() / 51)) + (6 * (color.getGreen() / 51)) + (color.getBlue() / 51);;

                //int ansiColorCode = rgbToAnsi(color.getRed(), color.getGreen(), color.getBlue());
                pic += "\033[48;5;" + ansiCode2 + "m" + "  " + "\033[m";
            }
            pic += "\n";
        }
        return pic;
    }


}