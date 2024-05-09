import java.awt.Color;
import java.util.*;

/**
 * The {@code Tweet} class represents a tweet with an associated photo, caption, date, and likes.
 * The class provides methods to retrieve and manipulate the tweet information, 
 * including resizing the photo and generating a formatted string representation of the tweet.
 */
public class Tweet {

    /** The desired size for the resized photo. You may change this value as you see fit to resize the photo! */
    public static final int PHOTO_SIZE = 17;

    private Picture photo;   // The photo associated with the tweet
    private String filename; // The filename of the tweet
    private String caption;  // The caption of the tweet
    private String date;     // The date of the tweet
    private int likes;       // The number of likes on the tweet
    private int retweets;    // The number of times the number of retweets

    /**
     * Constructs a new {@code Tweet} with specified photo, caption, date, likes, and retweets.
     * <br>
     * <hr>
     * <strong>Example Usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * }
     * <hr>
     * @param photoString  The file path of the photo
     * @param caption      The caption of the tweet
     * @param date         The date of the tweet
     * @param likes        The number of likes on the tweet
     * @param retweets     The number of retweets on the tweet
     * @throws IllegalArgumentException If retweets are less than 0 or photo dimensions are smaller than {@code PHOTO_SIZE}
     */
    public Tweet(String photoString, String caption, String date, int likes, int retweets) {
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
     * Constructs a new {@code Tweet} with a specified photo, caption, and date where it
     * automatically assigns random values for likes and retweets.
     * <br>
     * <hr>
     * <strong>Example Usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * }
     * <hr>
     * @param photoString The file path of the photo
     * @param caption     The caption of the tweet
     * @param date        The date of the tweet
     * @throws IllegalArgumentException If photo dimensions are smaller than {@code PHOTO_SIZE}
     */
    public Tweet(String photoString, String caption, String date) {
        this(photoString, caption, date,
             new Random().nextInt(100000) - 10000, new Random().nextInt(50000));
    }

    /**
     * Gets the string representation of the resized photo.
     * <br>
     * <hr>
     * <strong>Example usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * System.out.println(tweet.getPhotoString()); // Output will be the color string representation of the photo.
     * }
     * <hr>
     * @return The string representation of the resized photo
     */
    public String getPhotoString() {
        return photoToString(this.photo.getPixels());
    }

    /**
     * Gets the filename of the photo associated with this tweet.
     * <br>
     * <hr>
     * <strong>Example usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * System.out.println(tweet.getFilename()); // Output: "gumball/flowers.png"
     * }
     * <hr>
     * @return  The filename of this Tweets's photo
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Gets the 2D array of {@link Color} objects representing the photo pixels.
     * <br>
     * <hr>
     * <strong>Example usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * Color[][] pixels = tweet.getPhotoPixels();
     * }
     * <hr>
     * @return A 2D array of {@link Color} objects representing the photo.
     */
    public Color[][] getPhotoPixels() {
        return this.photo.getPixels();
    }

    /**
     * Updates the photo with a new set of pixels.
     * <br>
     * <hr>
     * <strong>Example usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * Color[][] newPixels = new Color[17][17];
     * tweet.updatePhoto(newPixels);
     * }
     * <hr>
     * @param pixels The new 2D array of {@link Color} objects for the photo
     */
    public void updatePhoto(Color[][] pixels) {
        this.photo.setPixels(pixels);
    }

    /**
     * Gets the caption of this tweet.
     * <br>
     * <hr>
     * <strong>Example usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * System.out.println(tweet.getCaption()); // Output: "Gumball with flowers!"
     * }
     * <hr>
     * @return The caption of the tweet
     */
    public String getCaption() {
        return this.caption;
    }

    /**
     * Updates the caption of this tweet to be the new provided caption.
     * <br>
     * <hr>
     * <strong>Example usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * tweet.updateCaption("CSE 122 rocks!");
     * System.out.println(tweet.getCaption()); // Output: "CSE 122 rocks!"
     * }
     * <hr>
     * @param caption The new caption to set for the tweet.
     */
    public void updateCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Gets the date when this tweet was posted.
     * <br>
     * <hr>
     * <strong>Example usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * System.out.println(tweet.getDate()); // Output: "2024-01-22"
     * }
     * <hr>
     * @return The date of the tweet
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Gets the number of likes for this tweet.
     * <br>
     * <hr>
     * <strong>Example usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * System.out.println(tweet.getLikes()); // Output: 250
     * }
     * <hr>
     * @return The number of likes on the tweet
     */
    public int getLikes() {
        return this.likes;
    }

    /**
     * Gets the number of retweets on the tweet.
     * <br>
     * <hr>
     * <strong>Example Usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * System.out.println("Retweets: " + tweet.getRetweets()); // Output: Retweets: 40
     * }
     * <hr>
     * @return The number of retweets on the tweet
     */
    public int getRetweets() {
        return this.retweets;
    }

    /**
     * Increases the number of likes on this tweet by 1.
     * <br>
     * <hr>
     * <strong>Example Usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * tweet.likeTweet();
     * System.out.println("Likes after liking the tweet: " + tweet.getLikes()); // Output: 251
     * }
     * <hr>
     */
    public void likeTweet() {
        this.likes++;
    }

    /**
     * Decreases the number of likes on this tweet by 1.
     * <br>
     * <hr>
     * <strong>Example Usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * tweet.dislikeTweet();
     * System.out.println("Likes after disliking the tweet: " + tweet.getLikes()); // Output: 249
     * }
     * <hr>
     */
    public void dislikeTweet() {
        this.likes--;
    }

    /**
     * Increases the number of retweets on this tweet by 1.
     * <br>
     * <hr>
     * <strong>Example Usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * tweet.retweetTweet();
     * System.out.println("Retweets after one retweet: " + tweet.getRetweets()); // Output: 41
     * }
     * <hr>
     */
    public void retweetTweet() {
        this.retweets++;
    }

    /**
     * Provides a string representation of this tweet, including its photo (represented as colored spaces),
     * caption, number of likes, retweets, and the date.
     * <br>
     * <hr>
     * <strong>Example Usage:</strong>
     * {@snippet :
     * Tweet tweet = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * System.out.println(tweet.toString());
     * // Output might be:
     * // 2024-01-22
     * // Gumball with flowers!
     * // [Photo representation]
     * // Likes: 250  |  Retweets: 40
     * }
     * <hr>
     * @return The string representation of the tweet
     */
    public String toString() {
        return this.date + "\n" +
               this.caption + "\n" +
               photoToString(this.photo.getPixels()) +
               "Likes: " + this.likes + "  |  Retweets: " + this.retweets;
    }
    
    /**
     * Compares this tweet with another object to determine if they are equal.
     * Two tweets are considered equal if they have the same photo, caption, date, number of likes, and number of retweets. 
     * <br>
     * <hr>
     * <strong>Example Usage:</strong>
     * {@snippet :
     * Tweet tweet1 = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * Tweet tweet2 = new Tweet("gumball/flowers.png", "Gumball with flowers!", "2024-01-22", 250, 40);
     * System.out.println("Are both tweets equal?? " + tweet1.equals(tweet2)); // Output: true if all attributes are identical!
     * }
     * <hr>
     * @param other The object to compare against
     * @return True if the other object is a Tweet with identical photo, caption, date, likes, and retweets
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Tweet) {
            Tweet otherTweet = (Tweet) other;
            return (
                    this.photo.equals(otherTweet.photo) &&
                    this.caption.equals(otherTweet.caption) &&
                    this.date.equals(otherTweet.date) &&
                    this.likes == otherTweet.likes &&
                    this.retweets == otherTweet.retweets);
        }
        return false;
    }

    // PRIVATE HELPER METHDOS /////////////////////////////////////////////////////////////////////

    /**
     * Resizes the provided photo for this tweet to be of PHOTO_SIZE in each dimension.
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
     * Rotates the photo of this tweet 90 degrees counter clockwise
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
     * Converts the photo of this tweet to a String.
     * The returned String will be made of spaces where each pair of spaces represents
     * a pixel and is assigned a ansi color background value.
     *
     * @param The 2D color representation of the original photo 
     *
     * @return The String representation of the photo of this tweet
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