package xyz.weezle.randombeer.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.weezle.randombeer.R;
import xyz.weezle.randombeer.model.room.BarDatabase;
import xyz.weezle.randombeer.model.room.Bar;

public class BarListActivity extends AppCompatActivity {

    RecyclerView rv;
    BarAdapter barAdapter;

    List<Bar> bars = new ArrayList<Bar>();
    Bar bar;

    // Base de données
    BarDatabase barDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.title_activity_bar_list);

        FloatingActionButton fab = findViewById(R.id.ajouterBar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouterBar();
            }
        });

        listeBars();
    }

    public void listeBars() {
        // Récupération de la liste des bars depuis la base de données
        barDb = BarDatabase.getInstance(this);
        bars = barDb.barDao().getAll();

        // Affichage de la liste des bars
        rv = (RecyclerView) findViewById(R.id.rvBarList);

        barAdapter = new BarAdapter(bars);
        rv.setAdapter(barAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
    }

    public void ajouterBar() {
        Intent intent = new Intent(this, AddBarActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Ajout d'un bar
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                int id = data.getIntExtra("id", 0);
                String nomBar = data.getStringExtra("nomBar");
                int nbFrigos = data.getIntExtra("nbFrigos", 0);
                int nbEtageres = data.getIntExtra("nbEtageres", 0);
                int nbBieres = data.getIntExtra("nbBieres", 0);

                bar = new Bar();
                bar.id = id;
                bar.nom = nomBar;
                bar.nbFrigos = nbFrigos;
                bar.nbEtageres = nbEtageres;
                bar.nbBieres = nbBieres;

                bars.add(bar);
                barAdapter.notifyDataSetChanged();

                Snackbar.make(findViewById(R.id.coordinationLayout), R.string.bar_added, BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }

        // Modification / Suppression d'un bar
        if(requestCode == 2) {
            if(resultCode == RESULT_OK) {
                if(data.hasExtra("deleteItem")) {
                    int itemPosition = data.getIntExtra("deleteItem", 0);
                    bars.remove(itemPosition);
                    Snackbar.make(findViewById(R.id.coordinationLayout), R.string.bar_deleted, BaseTransientBottomBar.LENGTH_SHORT).show();
                }

                if(data.hasExtra("editItem")) {
                    int id = data.getIntExtra("id", 0);
                    int itemPosition = data.getIntExtra("editItem", 0);
                    Bar editedBar = barDb.barDao().findById(id);
                    bars.get(itemPosition).nom = editedBar.nom;
                    bars.get(itemPosition).nbFrigos = editedBar.nbFrigos;
                    bars.get(itemPosition).nbEtageres = editedBar.nbEtageres;
                    bars.get(itemPosition).nbBieres = editedBar.nbBieres;
                }

                barAdapter.notifyDataSetChanged();
            }
        }
    }
}
