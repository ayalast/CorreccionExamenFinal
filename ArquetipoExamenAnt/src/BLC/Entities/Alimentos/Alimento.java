package BLC.Entities.Alimentos;

public abstract class Alimento {
    private String tipo;

    public Alimento(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}