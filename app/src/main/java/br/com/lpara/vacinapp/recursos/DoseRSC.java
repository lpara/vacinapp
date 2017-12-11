package br.com.lpara.vacinapp.recursos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LPAra on 03/12/2017.
 */

public class DoseRSC extends Object implements Serializable{

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("numeracao")
    @Expose
    private Integer numeracao;

    @SerializedName("dataVacinacao")
    @Expose
    private Date dataVacinacao;

    @SerializedName("vacinacao")
    @Expose
    private VacinacaoRSC vacinacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeracao() {
        return numeracao;
    }

    public void setNumeracao(Integer numeracao) {
        this.numeracao = numeracao;
    }

    public Date getDataVacinacao() {
        return dataVacinacao;
    }

    public void setDataVacinacao(Date dataVacinacao) {
        this.dataVacinacao = dataVacinacao;
    }

    public VacinacaoRSC getVacinacao() {
        return vacinacao;
    }

    public void setVacinacao(VacinacaoRSC vacinacao) {
        this.vacinacao = vacinacao;
    }
}
