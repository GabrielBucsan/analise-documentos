package com.bucsan.analysis;

public class GovDocument {

    String numberPage;
    String name;
    String pubDate;
    String artCategory;
    String identifica;
    String ementa;
    String texto;
    String arquivo;

    public void setNumberPage(String numberPage) {
        this.numberPage = numberPage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setArtCategory(String artCategory) {
        this.artCategory = artCategory;
    }

    public void setIdentifica(String identifica) {
        this.identifica = identifica;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getTexto() {
        return this.texto;
    }

    public String getNumberPage() {
        return numberPage;
    }

    public String getName() {
        return name;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getArtCategory() {
        return artCategory;
    }

    public String getIdentifica() {
        return identifica;
    }

    public String getEmenta() {
        return ementa;
    }

    public String getArquivo() {
        return arquivo;
    }
}
