package BLC.Entities.Alimentos;

public abstract class IngestaNativa extends Alimento implements IIngestaNativa {
    private GenoAlimento inyectadoCon;

    public IngestaNativa(String tipo) {
        super(tipo);
    }

    @Override
    public boolean inyectar(GenoAlimento genoAlimento) {
        this.inyectadoCon = genoAlimento;
        return true;
    }

    public GenoAlimento getInyectadoCon() {
        return inyectadoCon;
    }
}