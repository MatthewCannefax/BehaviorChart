package com.matthewcannefax.behaviorchart;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.matthewcannefax.behaviorchart.ArrayAdapters.KidArrayAdapter;
import com.matthewcannefax.behaviorchart.model.Enum.BehaviorEnum;
import com.matthewcannefax.behaviorchart.model.Kid;
import com.matthewcannefax.behaviorchart.utils.JSONHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Kid> importKidList;

        if(JSONHelper.importKidsFromJSON(MainActivity.this) != null){
            importKidList = JSONHelper.importKidsFromJSON(MainActivity.this);
        }else{
            importKidList = new ArrayList<>();
        }

        final List<Kid> kids = importKidList;

        final ListView listView = findViewById(R.id.kidListView);
        Button addChildBTN = findViewById(R.id.addChildBTN);

        setLVAdapter(listView, kids);

        addChildBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add Child");

                View newChildView = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_a_child_dialog,
                        (ViewGroup)view.findViewById(android.R.id.content), false);

                final EditText etName = newChildView.findViewById(R.id.etName);

                builder.setView(newChildView);

                builder.setMessage("Enter the name of the new child.");
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = kids.size();
                        kids.add(new Kid(etName.getText().toString(), id));

                        JSONHelper.exportKidsToJSON(MainActivity.this, kids);

                        Toast.makeText(MainActivity.this, etName.getText().toString() + " has been added", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int selectedIndex = i;
                final Kid selectedKid = (Kid) adapterView.getItemAtPosition(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(String.format("Is %s misbehaving?", selectedKid.getpName()));
                builder.setMessage("Select the behavior that best fits.");

                View spinnerView = LayoutInflater.from(MainActivity.this).inflate(R.layout.infraction_dropdown_box,
                        (ViewGroup)view.findViewById(android.R.id.content), false);

                final ListView lvInfractions = spinnerView.findViewById(R.id.infractionLV);

                if (selectedKid.getInfractions() != null && selectedKid.getInfractions().size() != 0){
                    ArrayAdapter<BehaviorEnum> infractionAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, selectedKid.getInfractions());
                    lvInfractions.setAdapter(infractionAdapter);
                }

                final Spinner behaviorSpinner = spinnerView.findViewById(R.id.behaviorSpinner);
                ArrayAdapter<BehaviorEnum> behaviorAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, BehaviorEnum.values());

                behaviorSpinner.setAdapter(behaviorAdapter);

                builder.setView(spinnerView);

                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        kids.get(selectedIndex).getInfractions().add((BehaviorEnum)behaviorSpinner.getSelectedItem());
                        setLVAdapter(listView, kids);
                        JSONHelper.exportKidsToJSON(MainActivity.this, kids);
                    }
                });
                builder.setNeutralButton("Remove One", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (kids.get(selectedIndex).getInfractions().size() != 0 && kids.get(selectedIndex).getInfractions() != null) {
                            boolean removed = false;
                            for(BehaviorEnum bE: kids.get(selectedIndex).getInfractions()){
                                if(bE.getValue() == 3){
                                    kids.get(selectedIndex).getInfractions().remove(bE);
                                    removed = true;

                                    break;
                                }
                            }
                            if (!removed){
                                kids.get(selectedIndex).getInfractions().remove(0);
                            }
                            setLVAdapter(listView, kids);
                            JSONHelper.exportKidsToJSON(MainActivity.this, kids);
                        } else {
                            Toast.makeText(MainActivity.this, "No Infractions", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.show();
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int selectedIndex = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Remove Child?");
                builder.setMessage(String.format("Would you really like to remove %s?", kids.get(selectedIndex).getpName()));
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        kids.remove(selectedIndex);
                        setLVAdapter(listView, kids);
                        JSONHelper.exportKidsToJSON(MainActivity.this, kids);
                    }
                });

                builder.show();
                return true;
            }
        });


    }

    private void setLVAdapter(ListView listView, List<Kid> kids){
        KidArrayAdapter kidArrayAdapter = new KidArrayAdapter(this, kids);
        listView.setAdapter(kidArrayAdapter);
    }
}
