package edu.schaf170msu.matchpairs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import static android.R.attr.bitmap;

/**
 * Created by Andrew on 11/14/17.
 */

public class MemoryPiece {
    public String getName() {
        return name;
    }

    /**
     * The image for the actual piece.
     */
    private Bitmap piece;
    private boolean solved = false;

    private boolean visibility = false;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * x location.
     * We use relative x locations in the range 0-1 for the center
     * of the puzzle piece.
     */
    private float x = 0;

    /**
     * y location
     */
    private float y = 0;

    private String name;



    public MemoryPiece(Context context, int id, float X, float Y, String name) {
        this.x = X;
        this.y = Y;
        this.name = name;

        piece = BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * Draw the puzzle piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param boardSize Size we draw the puzzle in pixels
     * @param scaleFactor Amount we scale the puzzle pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int boardSize, float scaleFactor,
                     float posX, float posY) {
        if (visibility) {
            canvas.save();

            // Convert x,y to pixels and add the margin, then draw
            canvas.translate(marginX + (posX * boardSize), marginY + (posY * boardSize));

            // Scale it to the right size
            canvas.scale(scaleFactor/4, scaleFactor/4);

            // This magic code makes the center of the piece at 0, 0
            canvas.translate(-piece.getWidth() / 2, -piece.getHeight() / 2);

            // Draw the bitmap
            canvas.drawBitmap(piece, 0, 0, null);
            canvas.restore();
        }

    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean getSolved() {
        return solved;
    }

    public boolean getVisibility() {
        return visibility;
    }
}
