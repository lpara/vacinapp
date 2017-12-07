package br.com.lpara.vacinapp.recursos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by LPAra on 03/12/2017.
 */

public class VacinacaoRSC implements Serializable{

    @SerializedName("id")
    private Long id;

    private VacinaRSC vacina;

    private CarteiraRSC carteira;

    @SerializedName("lote")
    private String lote;

    @SerializedName("dataRenovacao")
    private Date dataRenovacao;

    private List<DoseRSC> doses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VacinaRSC getVacina() {
        return vacina;
    }

    public void setVacina(VacinaRSC vacina) {
        this.vacina = vacina;
    }

    public CarteiraRSC getCarteira() {
        return carteira;
    }

    public void setCarteira(CarteiraRSC carteira) {
        this.carteira = carteira;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getDataRenovacao() {
        return dataRenovacao;
    }

    public void setDataRenovacao(Date dataRenovacao) {
        this.dataRenovacao = dataRenovacao;
    }

    public List<DoseRSC> getDoses() {
        return doses;
    }

    public void setDoses(List<DoseRSC> doses) {
        this.doses = doses;
    }
}
