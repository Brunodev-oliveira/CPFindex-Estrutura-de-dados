package model;

import java.lang.String;

public class Contribuinte {
    private String cpf;
    private String nome;
    private String situation;

    public Contribuinte(String cpf, String nome, String situation){
        this.cpf = cpf.replaceAll("[^0-9]","");
        this.nome = nome;
        this.situation = situation;

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfFormatted(){
        return String.format(
                "%s.%s.%s-%s",
                getCpf().substring(0,3),
                getCpf().substring(3, 6),
                getCpf().substring(6,9),
                getCpf().substring(9,11));
    }


}

