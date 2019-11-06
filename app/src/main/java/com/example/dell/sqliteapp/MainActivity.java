package com.example.dell.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity  {

    DatabaseHelper myDb;
    EditText editName,editSurname,editBatch,editTextId;
    Button btnAddData,btnviewAll,btnviewUpdate,btnDelete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = (EditText)findViewById(R.id.etName);
        editSurname = (EditText)findViewById(R.id.etSurname);
        editBatch = (EditText)findViewById(R.id.etBatch);
        editTextId = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button)findViewById(R.id.add_button);
        btnviewAll = (Button)findViewById(R.id.button_viewAll);
        btnviewUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);


        addData();
        viewAll();
        updateData();
        deleteData();
    }


    public void deleteData(){
      btnDelete.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                      if (deletedRows > 0)
                          Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_SHORT).show();
                      else
                          Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_SHORT).show();
                  }
              }
      );
    }

    public void updateData(){
    btnviewUpdate.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isUpdate = myDb.updataData(editTextId.getText().toString(),
                            editName.getText().toString(),editSurname.getText().toString()
                    ,editBatch.getText().toString());

                    if (isUpdate == true)
                        Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_SHORT).show();
                }
            }
    );
    }

    public void addData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            boolean isInserted =     myDb.insertData(editName.getText().toString(),
                        editSurname.getText().toString(),editBatch.getText().toString());
            if (isInserted == true)
                Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewAll(){
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Cursor res = myDb.getAllData();
                      if (res.getCount()==0) {
                          //show msg
                          showMessage("Empty Table","nothing to show");
                          return;
                      }
                      else {
                          StringBuffer buffer = new StringBuffer();
                          while (res.moveToNext()){
                              buffer.append("Id "+res.getString(0)+"\n");
                              buffer.append("Name "+res.getString(1)+"\n");
                              buffer.append("Surname "+res.getString(2)+"\n");
                              buffer.append("Batch "+res.getString(3)+"\n");
                          }
                          showMessage("data",buffer.toString());
                      }
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
