package br.com.lpara.vacinapp.recursos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LPAra on 18/11/2017.
 */

public class VacinaRSC implements Serializable{

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("renovavel")
    @Expose
    private Boolean renovavel;

    @SerializedName("numeroDoses")
    @Expose
    private Integer numeroDoses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getRenovavel() {
        return renovavel;
    }

    public void setRenovavel(Boolean renovavel) {
        this.renovavel = renovavel;
    }

    public Integer getNumeroDoses() {
        return numeroDoses;
    }

    public void setNumeroDoses(Integer numeroDoses) {
        this.numeroDoses = numeroDoses;
    }
}
