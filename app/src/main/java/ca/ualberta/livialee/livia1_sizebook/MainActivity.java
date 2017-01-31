package ca.ualberta.livialee.livia1_sizebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.livia1-SizeBook.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToNew(View view) {
        Intent newEntriesMenu = new Intent(this, NewEntries.class);
        startActivity(newEntriesMenu);
    }

    public void goToOld(View view){
        Intent oldEntriesMenu = new Intent(this, OldEntries.class);
        startActivity(oldEntriesMenu);
    }
}

