package ca.ualberta.livialee.livia1_sizebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewEntries extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entries);
    }
    public void SaveEnter(View view) {
        //Save data here before finsishing
        finish();
    }
}
