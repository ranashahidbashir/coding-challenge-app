package com.name.list;

import android.app.ListActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.name.list.dialog.AddNameDialog;
import com.name.list.dialog.AddNameDialog.onSubmitListener;
import com.name.list.util.Name;
import com.name.list.util.StringsUtil;

import java.util.ArrayList;

public class MainActivity extends ListActivity implements onSubmitListener {
    ArrayAdapter adapter;
    ArrayList<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniComponent();

    }

    private void iniComponent() {

        // For the cursor adapter, specify which columns go into which views

        nameList = StringsUtil.getNameList();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);

        setListAdapter(adapter);
    }

    public void addName(View v) {
        Log.e("MainActivity", "Add Name Click");

        AddNameDialog addNameDialog = new AddNameDialog();
        addNameDialog.mListener = MainActivity.this;

        addNameDialog.show(getFragmentManager(), "");
    }

    @Override
    public void setOnSubmitListener(Name arg) {

        if (adapter != null && arg != null) {

            String name = ((!arg.getLastName().equals("") && !arg.getFirstName().equals("")) ?
                    arg.getLastName() + ", " + arg.getFirstName() : "");
            name = (name.equals("") && !arg.getLastName().equals("")) ? arg.getLastName() : name;
            name = (name.equals("") && !arg.getFirstName().equals("")) ? arg.getFirstName() : name;

            if (!name.equalsIgnoreCase("")) {


                name = StringsUtil.toTitleCase(name);

                StringsUtil.nameList.add(name);

                adapter.notifyDataSetChanged();
            } else
                Toast.makeText(getApplicationContext(), "Invalid Name", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
