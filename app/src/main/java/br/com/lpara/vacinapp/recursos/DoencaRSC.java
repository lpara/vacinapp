package br.com.lpara.vacinapp.recursos;

/**
 * Created by LPAra on 18/11/2017.
 */

public class DoencaRSC {

    private String id;

    private String nome;

    private VacinaRSC vacina;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public VacinaRSC getVacina() {
        return vacina;
    }

    public void setVacina(VacinaRSC vacina) {
        this.vacina = vacina;
    }
}
