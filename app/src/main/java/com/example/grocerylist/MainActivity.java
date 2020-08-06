package com.example.grocerylist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import static android.R.layout.simple_list_item_multiple_choice;
import static android.R.layout.simple_selectable_list_item;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    Button addItem;
    ListView myList;
    ArrayList<String> groceryList;
    ArrayAdapter<String> arrayAdapter;
    CheckBox checkBox;
    CheckedTextView v;
    Button delItem;
    TextView textView;
    ImageView micView;
    ArrayList<String> stringArrayListExtra;

    private static final String FILE_NAME="example.text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myList = (ListView) findViewById(R.id.myList);
        addItem = (Button) findViewById(R.id.addItem);
       // delItem = (Button) findViewById(R.id.delItem);
        editText = (EditText) findViewById(R.id.editText);
        groceryList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), simple_list_item_multiple_choice, groceryList);
        textView=findViewById(R.id.textView);
        micView=findViewById(R.id.micView);


        textView.setText("Long Press on Items to Delete :)");
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = editText.getText().toString();
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No item entered!", Toast.LENGTH_LONG).show();
                } else {
                    groceryList.add(names);
                    myList.setAdapter(arrayAdapter);

                    editText.setText("");

                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                v = (CheckedTextView) view;
                v.setChecked(!v.isChecked());


            }
        });

      myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              arrayAdapter.remove(groceryList.get(position));
              arrayAdapter.notifyDataSetChanged();
              return false;




          }
      });







    }
    
    public  void getSpeechInput(View view)
    {
        Intent intent=new Intent((RecognizerIntent.ACTION_RECOGNIZE_SPEECH));
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
       
        
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(intent,10);
        }
        else
        {
            Toast.makeText(this, "Your Device doesn't support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        switch(requestCode)
        {
            case 10:
                if(resultCode==RESULT_OK && data != null)
                {
               stringArrayListExtra = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
               editText.setText( stringArrayListExtra.get(0));
                }
                break;
        }
    }


}
