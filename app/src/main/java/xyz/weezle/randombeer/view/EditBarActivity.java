package xyz.weezle.randombeer.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import xyz.weezle.randombeer.R;
import xyz.weezle.randombeer.model.room.Bar;
import xyz.weezle.randombeer.model.room.BarDatabase;

public class EditBarActivity extends AppCompatActivity {

    Button btnModifier;
    EditText etNomBar;
    EditText etFrigos;
    EditText etEtageres;
    EditText etBieres;

    Bar bar;

    // Base de données
    BarDatabase barDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // On récupère la bar à modifier
        final int id = this.getIntent().getIntExtra("id", 0);

        barDb = BarDatabase.getInstance(this);
        bar = barDb.barDao().findById(id);

        btnModifier = (Button) findViewById(R.id.btnModifier);
        etNomBar = (EditText) findViewById(R.id.etNomBar);
        etFrigos = (EditText) findViewById(R.id.etNbFrigos);
        etEtageres = (EditText) findViewById(R.id.etNbEtageres);
        etBieres = (EditText) findViewById(R.id.etNbBieres);

        etNomBar.setText(bar.nom);
        etFrigos.setText(String.valueOf(bar.nbFrigos));
        etEtageres.setText(String.valueOf(bar.nbEtageres));
        etBieres.setText(String.valueOf(bar.nbBieres));

        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valNomBar = etNomBar.getText().toString();
                String valFrigos = etFrigos.getText().toString();
                String valEtageres = etEtageres.getText().toString();
                String valBieres = etBieres.getText().toString();

                if(TextUtils.isEmpty(valFrigos) || TextUtils.isEmpty(valEtageres)
                        || TextUtils.isEmpty(valBieres) || TextUtils.isEmpty(valNomBar)) {
                    // Avertissement si l'utilisateur n'a pas rempli tous les champs
                    Toast.makeText(v.getContext(), R.string.error_complete_all_fields, Toast.LENGTH_SHORT).show();
                } else {
                    // Traitement des données
                    String nomBar = valNomBar;
                    int nbFrigos = Integer.parseInt(valFrigos);
                    int nbEtageres = Integer.parseInt(valEtageres);
                    int nbBieres = Integer.parseInt(valBieres);

                    // On modifie le bar dans la base de données
                    bar = new Bar();
                    bar.id = id;
                    bar.nom = nomBar;
                    bar.nbFrigos = nbFrigos;
                    bar.nbEtageres = nbEtageres;
                    bar.nbBieres = nbBieres;

                    modifierBar(bar);
                }
            }
        });
    }

    public void modifierBar(Bar bar) {
        barDb = BarDatabase.getInstance(this);
        barDb.barDao().update(bar);

        Intent intent = new Intent();
        intent.putExtra("id", bar.id);
        intent.putExtra("nomBar", bar.nom);
        intent.putExtra("nbFrigos", bar.nbFrigos);
        intent.putExtra("nbEtageres", bar.nbEtageres);
        intent.putExtra("nbBieres", bar.nbBieres);
        setResult(RESULT_OK, intent);

        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
