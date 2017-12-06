package br.com.lpara.vacinapp.recursos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LPAra on 03/12/2017.
 */

public class UsuarioRSC implements Serializable{

    @SerializedName("id")
    private Long id;

    @SerializedName("login")
    private String login;

    @SerializedName("senha")
    private String senha;

    private PessoaRSC pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PessoaRSC getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaRSC pessoa) {
        this.pessoa = pessoa;
    }
}
