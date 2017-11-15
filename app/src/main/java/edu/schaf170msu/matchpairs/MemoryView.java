package edu.schaf170msu.matchpairs;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


/**
 * Custom view class for our Puzzle.
 */
public class MemoryView extends View {

    /**
     * The actual memory
     */
    private Memory memory;

    public MemoryView(Context context) {
        super(context);
        init(null, 0);
    }

    public MemoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MemoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        memory = new Memory(getContext());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (memory.onTouchEvent(this,event)){
            invalidate();
        }
        return false;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        memory.draw(canvas);

    }

    public void onReset(View view) {
        memory.Reset();
        view.invalidate();
    }


    public Memory getMemory() {
        return memory;
    }
}