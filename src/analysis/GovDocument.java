package analysis;

public class GovDocument {

    String numberPage;
    String pubName;
    String name;
    String pubDate;
    String artType;
    String artCategory;
    String identifica;
    String ementa;
    String texto;
    String arquivo;

    public void setNumberPage(String numberPage) {
        this.numberPage = numberPage;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setArtType(String artType) {
        this.artType = artType;
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
}
