package ca.ualberta.livialee.livia1_sizebook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends Activity {

    private static final String FILENAME = "file.sav";
    private EditText editName;
    private EditText editDate;
    private EditText editNeck;
    private EditText editBust;
    private EditText editChest;
    private EditText editWaist;
    private EditText editHip;
    private EditText editInseam;
    private EditText editComment;
    private ListView previousEntries;
    private TextView counter;

    private ArrayList<Person> personList;
    private ArrayAdapter<Person> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.edit_name);
        editDate = (EditText) findViewById(R.id.edit_date);
        editNeck = (EditText) findViewById(R.id.edit_neck);
        editBust = (EditText) findViewById(R.id.edit_bust);
        editChest = (EditText) findViewById(R.id.edit_chest);
        editWaist = (EditText) findViewById(R.id.edit_waist);
        editHip = (EditText) findViewById(R.id.edit_hip);
        editInseam = (EditText) findViewById(R.id.edit_inseam);
        editComment = (EditText) findViewById(R.id.edit_comment);
        Button saveButton = (Button) findViewById(R.id.save);

        previousEntries = (ListView) findViewById(R.id.previousEntries);
        previousEntries.setClickable(true);

        counter = (TextView) findViewById(R.id.count);

        // Click on item - retrieves the data and places it in the textViews
        previousEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Person humanEdit = personList.get(position);
                editName.setText(humanEdit.getName());
                editDate.setText(humanEdit.getDate());
                editNeck.setText(humanEdit.getNeck());
                editBust.setText(humanEdit.getBust());
                editChest.setText(humanEdit.getChest());
                editWaist.setText(humanEdit.getWaist());
                editHip.setText(humanEdit.getHip());
                editInseam.setText(humanEdit.getInseam());
                editComment.setText(humanEdit.getComment());
            }});

        // Use a long click to delete items
        previousEntries.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id)
            {
                personList.remove(pos);					//removed
                adapter.notifyDataSetChanged();			//update the view
                counter.setText(Integer.toString(personList.size()));      //update the counter
                saveInFile();
                return true;
            }
        });

        // Save button to save entries
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                String Name = editName.getText().toString();
                String Date = editDate.getText().toString();
                String Neck = editNeck.getText().toString();
                String Bust = editBust.getText().toString();
                String Chest = editChest.getText().toString();
                String Waist = editWaist.getText().toString();
                String Hip = editHip.getText().toString();
                String Inseam = editInseam.getText().toString();
                String Comment = editComment.getText().toString();

                // Has to have at least a name to save an entry
                if (!Name.equals("")) {
                    Person one = new Person(Name, Date, Neck, Bust, Chest, Waist, Hip, Inseam, Comment);

                    // Use a for loop if Name is equal to a previous Name entered, update it
                    personList.add(one);

                    int size = personList.size();
                    //int last = size - 1;
                    String sizeString = Integer.toString(size);
                    //counter.setText(sizeString);
                    //System.out.println(last);

                    for(int i=0; i < size; i++){
                        System.out.println(String.format("%d, %d",i, size));
                        if (personList.get(i).getName().equals(Name)){
                            personList.get(i).setDate(Date);
                            personList.get(i).setNeck(Neck);
                            personList.get(i).setBust(Bust);
                            personList.get(i).setChest(Chest);
                            personList.get(i).setWaist(Waist);
                            personList.get(i).setHip(Hip);
                            personList.get(i).setInseam(Inseam);
                            personList.get(i).setComment(Comment);
                            adapter.notifyDataSetChanged();

                            //personList.remove(0);
                            saveInFile();
                        }
                    }

                    counter.setText(sizeString);
                    //System.out.println(last);

                    adapter.notifyDataSetChanged();
                    saveInFile();

                    editName.setText("");
                    editDate.setText("");
                    editNeck.setText("");
                    editBust.setText("");
                    editChest.setText("");
                    editWaist.setText("");
                    editHip.setText("");
                    editInseam.setText("");
                    editComment.setText("");
                }

            };
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();


        loadFromFile();
        counter.setText(Integer.toString(personList.size()));

        adapter = new ArrayAdapter<Person>(this, R.layout.list_item, personList);
        previousEntries.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a
            // Jan 24 2017
            Type listType = new TypeToken<ArrayList<Person>>() {}.getType();
            personList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            personList = new ArrayList<Person>();
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    /**
     * Saves tweets to a specified file in JSON format.
     * @throws FileNotFoundException if file folder doesn't exist
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(personList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the Exception properly later.
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}