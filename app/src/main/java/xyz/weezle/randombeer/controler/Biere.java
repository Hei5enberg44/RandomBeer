package xyz.weezle.randombeer.controler;

import java.util.Random;

public class Biere {

    private Integer nbFrigos;
    private Integer nbEtageres;
    private Integer nbBieres;

    public Integer aleaFrigo;
    public Integer aleaEtagere;
    public Integer aleaBiere;

    public Biere(Integer nbFrigos, Integer nbEtageres, Integer nbBieres) {
        this.nbFrigos = nbFrigos;
        this.nbEtageres = nbEtageres;
        this.nbBieres = nbBieres;
    }

    public void getBiere() {
        Random r = new Random();
        this.aleaFrigo = r.nextInt(this.nbFrigos) + 1;
        this.aleaEtagere = r.nextInt(this.nbEtageres) + 1;
        this.aleaBiere = r.nextInt(this.nbBieres) + 1;
    }
}
