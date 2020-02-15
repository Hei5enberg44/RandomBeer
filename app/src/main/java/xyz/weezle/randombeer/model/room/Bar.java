package xyz.weezle.randombeer.model.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bar {
    @PrimaryKey(autoGenerate = true)
    public int id; // Id du bar

    @ColumnInfo(name = "nom")
    public String nom; // Nom du bar

    @ColumnInfo(name = "nbFrigos")
    public int nbFrigos; // Nombre de réfrigérateurs

    @ColumnInfo(name = "nbEtageres")
    public int nbEtageres; // Nombre d'étagères

    @ColumnInfo(name = "nbBieres")
    public int nbBieres; // Nombre bières par étagère
}
