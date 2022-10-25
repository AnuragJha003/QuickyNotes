package com.example.easynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        EditText titleinput=findViewById(R.id.titleinput);
        EditText descriptionInput=findViewById(R.id.descriptioninput);
        MaterialButton saveBtn=findViewById(R.id.savebtn);

        Realm.init(getApplicationContext());
        Realm realm=Realm.getDefaultInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=titleinput.getText().toString();
                String description=descriptionInput.getText().toString();
                long createdTime=System.currentTimeMillis();

                realm.beginTransaction();
                Note note=realm.createObject(Note.class);
                note.setTitle(title);
                note.setDescription(description);
                note.setCreatedtime(createdTime);
                realm.commitTransaction();

                Toast.makeText(getApplicationContext(), "NOTE SAVED", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}