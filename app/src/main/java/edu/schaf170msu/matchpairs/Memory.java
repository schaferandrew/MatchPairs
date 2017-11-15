package edu.schaf170msu.matchpairs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.security.AccessController.getContext;

/**
 * Created by Andrew on 11/14/17.
 */

public class Memory {

    /**
     * Percentage of the display width or height that
     * is occupied by the memory.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    /**
     * Paint for filling the area the puzzle is in
     */
    private Paint fillPaint;

    /**
     * Paint for outlining the area the puzzle is in
     */
    private Paint outlinePaint;

    /**
     * MemorySize
     */
    private int memorySize;

    /**
     * Margins
     */
    private int marginX;
    private int marginY;

    /**
     * Collection of puzzle pieces
     */
    public ArrayList<MemoryPiece> pieces = new ArrayList<MemoryPiece>();

    /**
     * samples piece
     */
    private Bitmap samplePiece;

    /**
     * How much we scale the puzzle pieces
     */
    private float scaleFactor;

    private int selectedPiece;

    private Context context;




    public Memory(Context context) {
        // Create paint for filling the area the memory will
        // be solved in.

        this.context = context;
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xff006400);

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xff000000);
        outlinePaint.setStrokeWidth(4f);

        samplePiece = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.chess_bdt45);

        selectedPiece = -1;

        // add the memory pieces
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_bdt45,
                1/8f,
                1/8f,
                "bishop"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_bdt45,
                3/8f,
                1/8f,
                "bishop"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_kdt45,
                5/8f,
                1/8f,
                "king"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_kdt45,
                7/8f,
                1/8f,
                "king"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_ndt45,
                1/8f,
                3/8f,
                "knight"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_ndt45,
                3/8f,
                3/8f,
                "knight"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_qdt45,
                5/8f,
                3/8f,
                "queen"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_qdt45,
                7/8f,
                3/8f,
                "queen"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_rdt45,
                1/8f,
                5/8f,
                "rook"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_rdt45,
                3/8f,
                5/8f,
                "rook"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_pdt45,
                5/8f,
                5/8f,
                "pawn"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_pdt45,
                7/8f,
                5/8f,
                "pawn"));
        pieces.add(new MemoryPiece(context,
                R.drawable.helmet,
                1/8f,
                7/8f,
                "helmet"));
        pieces.add(new MemoryPiece(context,
                R.drawable.helmet,
                3/8f,
                7/8f,
                "helmet"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_pdt45,
                5/8f,
                7/8f,
                "pawn"));
        pieces.add(new MemoryPiece(context,
                R.drawable.chess_pdt45,
                7/8f,
                7/8f,
                "pawn"));



        // to shuffle, use swap piece (random positions of arrays, run loop)
    }

    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        memorySize = (int)(minDim * SCALE_IN_VIEW);
        // Compute the margins so we center the puzzle
        marginX = (wid - memorySize) / 2;
        marginY = (hit - memorySize) / 2;
        // Load the empty board image

        scaleFactor = (float) (memorySize) / (float)samplePiece.getWidth();

        //
        // Draw the outline of the memory
        //
        canvas.drawRect(marginX, marginY,
                marginX + memorySize, marginY + memorySize, fillPaint);

        // Draw vertical lines
        canvas.drawLine(memorySize/4 + marginX, marginY, memorySize/4 + marginX, memorySize + marginY, outlinePaint);
        canvas.drawLine(memorySize/2 + marginX, marginY, memorySize/2 + marginX, memorySize + marginY, outlinePaint);
        canvas.drawLine(3*memorySize/4 + marginX, marginY, 3*memorySize/4 + marginX, memorySize + marginY, outlinePaint);
        // Draw Horizontal Lines
        canvas.drawLine(marginX, memorySize/4 + marginY, marginX + memorySize, memorySize/4 + marginY, outlinePaint);
        canvas.drawLine(marginX, memorySize/2 + marginY, marginX + memorySize, memorySize/2 + marginY, outlinePaint);
        canvas.drawLine(marginX, 3*memorySize/4 + marginY, marginX + memorySize, 3*memorySize/4 + marginY, outlinePaint);

        for(MemoryPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, memorySize, scaleFactor,piece.getX(), piece.getY());
        }


    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {

        switch(event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                //
                // Convert an x,y location to a relative location in the
                // puzzle.
                //

                float relX = (event.getX() - marginX) / memorySize;
                float relY = (event.getY() - marginY) / memorySize;


                return onTouched(relX, relY);
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //Log.i("onTouchEvent", "ACTION_UP");
                break;

            case MotionEvent.ACTION_MOVE:
                break;

        }
        return false;

    }

    public boolean onTouched(float X, float Y) {
        if (toGridPosition(X, Y) != null) {
            Point pieceCoords = toGridPosition(X, Y);
            int loc = (pieceCoords.x + (pieceCoords.y * 4));
            if (selectedPiece == -1) {
                selectedPiece = loc;
                pieces.get(selectedPiece).setVisibility(true);
            } else {
                if(pieces.get(selectedPiece).getName() == pieces.get(loc).getName()) {
                    pieces.get(loc).setVisibility(true);
                    pieces.get(loc).setSolved(true);
                    pieces.get(selectedPiece).setSolved(true);
                    selectedPiece = -1;
                    if (CheckGame()) {
                        //toast that game is over
                        int duration = Toast.LENGTH_SHORT;
                        CharSequence text = "Player Wins!";
                        Toast toast = Toast.makeText(context,text,duration);
                        toast.show();
                    }
                } else {
                    pieces.get(selectedPiece).setVisibility(false);
                    selectedPiece = -1;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private Point toGridPosition(float x, float y) {
        int gridX = (int) floor(x * 4);
        int gridY = (int) floor(y * 4);

        if(gridX >= 4 || gridY >=4 || gridX < 0 || gridY < 0){
            return null;
        }

        return new Point(gridX,gridY);
    }

    private boolean CheckGame() {
        for (MemoryPiece piece : pieces) {
            if (piece.getSolved() == false) {
                return false;
            }
        }
        return true;
    }

    public  void Reset() {
        for (MemoryPiece piece : pieces) {
            piece.setSolved(false);
            piece.setVisibility(false);
            Shuffle();
        }
    }

    private void Shuffle() {
        // shuffle pieces, make sure order is still correct
    }

    public void Peek() {
        // only change the visibility status, keep the solved visible upon returning
        for (MemoryPiece piece : pieces) {
            if (!piece.getSolved()){
                piece.setVisibility(!piece.getVisibility());
            }
        }
    }
}
