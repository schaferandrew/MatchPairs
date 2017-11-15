package edu.schaf170msu.matchpairs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        final MemoryView view = (MemoryView)this.findViewById(R.id.memoryView);
        if(bundle != null) {
            // We have saved state
            view.loadInstanceState(bundle);
            view.invalidate();
        }
        final Memory memory = view.getMemory();

        final Button reset = (Button) findViewById(R.id.button_reset);
        final Button peek = (Button) findViewById(R.id.button_peek);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                memory.Reset();
                view.invalidate();
            }
        });
        peek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                memory.Peek();
                view.invalidate();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        MemoryView view = (MemoryView) this.findViewById(R.id.memoryView);
        view.saveInstanceState(bundle);
    }
}
