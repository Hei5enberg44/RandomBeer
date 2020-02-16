package xyz.weezle.randombeer.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import xyz.weezle.randombeer.R;
import xyz.weezle.randombeer.controler.Biere;
import xyz.weezle.randombeer.model.room.Bar;
import xyz.weezle.randombeer.model.room.BarDatabase;

public class BiereActivity extends AppCompatActivity {

    TextView tvNFrigo;
    TextView tvNEtagere;
    TextView tvNBiere;
    TextView tvIndiceFrigo;
    TextView tvIndiceEtagere;
    TextView tvIndiceBiere;
    Button btnChoisirBiere;

    Bar bar;
    Biere biere;

    int itemPosition;
    boolean isEdited = false;

    // Base de données
    BarDatabase barDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biere);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // On récupère le bar
        int id = this.getIntent().getIntExtra("id", 0);
        itemPosition = this.getIntent().getIntExtra("itemPosition", 0);

        barDb = BarDatabase.getInstance(this);
        bar = barDb.barDao().findById(id);

        this.setTitle(bar.nom);

        tvNFrigo = (TextView) findViewById(R.id.tvNFrigo);
        tvNEtagere = (TextView) findViewById(R.id.tvNEtagere);
        tvNBiere = (TextView) findViewById(R.id.tvNBiere);
        tvIndiceFrigo = (TextView) findViewById(R.id.tvIndiceFrigo);
        tvIndiceEtagere = (TextView) findViewById(R.id.tvIndiceEtagere);
        tvIndiceBiere = (TextView) findViewById(R.id.tvIndiceBiere);
        btnChoisirBiere = (Button) findViewById(R.id.btnModifier);

        biere = new Biere(bar.nbFrigos, bar.nbEtageres, bar.nbBieres);

        // Clic sur le bouton
        btnChoisirBiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biere.getBiere();

                tvIndiceFrigo.setVisibility(View.VISIBLE);
                tvIndiceEtagere.setVisibility(View.VISIBLE);
                tvIndiceBiere.setVisibility(View.VISIBLE);

                String count = "";

                // Frigo
                int nFrigo = biere.aleaFrigo;
                if(nFrigo == 1) {
                    count = getString(R.string.first1);
                } else if(nFrigo == 2) {
                    count = getString(R.string.second);
                } else if(nFrigo == 3) {
                    count = getString(R.string.third);
                } else {
                    count = getString(R.string.th);
                }
                String frigo = nFrigo + count + " " + getString(R.string.fridge);

                // Étagère
                int nEtagere = biere.aleaEtagere;
                if(nEtagere == 1) {
                    count = getString(R.string.first2);
                } else if(nEtagere == 2) {
                    count = getString(R.string.second);
                } else if(nEtagere == 3) {
                    count = getString(R.string.third);
                } else {
                    count = getString(R.string.th);
                }
                String etagere = nEtagere + count + " " + getString(R.string.shelve);

                // Bière
                int nBiere = biere.aleaBiere;
                if(nBiere == 1) {
                    count = getString(R.string.first2);
                } else if(nBiere == 2) {
                    count = getString(R.string.second);
                } else if(nBiere == 3) {
                    count = getString(R.string.third);
                } else {
                    count = getString(R.string.th);
                }
                String biere = nBiere + count + " " + getString(R.string.beer);

                tvNFrigo.setText(frigo);
                tvNEtagere.setText(etagere);
                tvNBiere.setText(biere);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if(isEdited) {
                    Intent intent = new Intent(this, BarListActivity.class);
                    startActivityForResult(intent, 2);
                }
                finish();
                return true;
            case R.id.menu_suppr_bar:
                supprimerBar(bar.id, itemPosition);
                return true;
            case R.id.menu_modif_bar:
                modifierBar(bar.id);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.biere_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(isEdited) {
            Intent intent = new Intent(this, BarListActivity.class);
            startActivityForResult(intent, 2);
        }
    }

    private void supprimerBar(int id, int itemPosition) {
        bar = barDb.barDao().findById(id);
        barDb.barDao().delete(bar);

        Intent intent = new Intent();
        intent.putExtra("itemPosition", itemPosition);
        setResult(RESULT_OK, intent);

        finish();
    }

    private void modifierBar(int id) {
        Intent intent = new Intent(this, EditBarActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 2);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Modification d'un bar
        if(requestCode == 2) {
            if(resultCode == RESULT_OK) {
                int id = data.getIntExtra("id", 0);

                bar = barDb.barDao().findById(id);
                biere = new Biere(bar.nbFrigos, bar.nbEtageres, bar.nbBieres);

                // On modifie les informations affichées
                this.setTitle(bar.nom);
                tvNFrigo = (TextView) findViewById(R.id.tvNFrigo);
                tvNEtagere = (TextView) findViewById(R.id.tvNEtagere);
                tvNBiere = (TextView) findViewById(R.id.tvNBiere);
                tvIndiceFrigo = (TextView) findViewById(R.id.tvIndiceFrigo);
                tvIndiceEtagere = (TextView) findViewById(R.id.tvIndiceEtagere);
                tvIndiceBiere = (TextView) findViewById(R.id.tvIndiceBiere);
                tvNFrigo.setText("");
                tvNEtagere.setText("");
                tvNBiere.setText("");
                tvIndiceFrigo.setText("");
                tvIndiceEtagere.setText("");
                tvIndiceBiere.setText("");

                isEdited = true;
            }
        }
    }
}
