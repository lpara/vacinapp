package br.com.lpara.vacinapp.recursos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LPAra on 03/12/2017.
 */

public class CarteiraRSC implements Serializable{

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("gerenciadorCarteira")
    @Expose
    private UsuarioRSC gerenciadorCarteira;

    @SerializedName("donoCarteira")
    @Expose
    private PessoaRSC donoCarteira;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioRSC getGerenciadorCarteira() {
        return gerenciadorCarteira;
    }

    public void setGerenciadorCarteira(UsuarioRSC gerenciadorCarteira) {
        this.gerenciadorCarteira = gerenciadorCarteira;
    }

    public PessoaRSC getDonoCarteira() {
        return donoCarteira;
    }

    public void setDonoCarteira(PessoaRSC donoCarteira) {
        this.donoCarteira = donoCarteira;
    }
}
