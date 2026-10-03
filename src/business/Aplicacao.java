package business;

public class Aplicacao implements IAplicacao {
    private float montante;

    @Override
    public void calcularRendimento(float valorAplicado, int prazo, float taxa) {
        float taxaDecimal = taxa / 100;
        montante = (float) (valorAplicado * Math.pow(1 + taxaDecimal, prazo));
    }

    public float getMontante() {
        return montante;
    }
}
