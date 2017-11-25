package br.com.lpara.vacinapp.recursos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LPAra on 18/11/2017.
 */

public class VacinaRSC {

    @SerializedName("id")
    private Long id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("renovavel")
    private Boolean renovavel;

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
}
