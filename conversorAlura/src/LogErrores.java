import java.sql.Time;
import java.time.LocalDateTime;

public class LogErrores {
    LocalDateTime hoy;
    String moneda;
    Double Monto;
    Double Saldo;
    String descripcion;
    public LogErrores(){
        hoy=LocalDateTime.now();
        moneda="";
        Monto=0.0;
        Saldo=0.0;
        descripcion="";
    }

    public LocalDateTime getHoy() {
        return hoy;
    }
    public String getMoneda() {
        return moneda;
    }
    public Double getMonto() {
        return Monto;
    }
    public Double getSaldo() {
        return Saldo;
    }
    public void setHoy(LocalDateTime hoy) {
        this.hoy=hoy;
    }
    public void setMoneda(String moneda) {
        this.moneda=moneda;
    }
    public void setMonto(Double monto) {
        this.Monto=monto;
    }
    public void setSaldo(Double saldo) {
        this.Saldo=saldo;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion=descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
}
