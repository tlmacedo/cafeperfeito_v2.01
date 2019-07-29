package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.AccessGuest;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Optional;

@Entity(name = "Usuario")
@Table(name = "usuario")
public class Usuario extends Colaborador implements Serializable {
    private static final long serialVersionUID = 1L;

    private StringProperty email = new SimpleStringProperty();
    private StringProperty senha = new SimpleStringProperty();
    private IntegerProperty accessGuest = new SimpleIntegerProperty();

    public Usuario() {
    }

    @Transient
    public String getDetalhe() {
        StringBuilder detalhe = new StringBuilder();
        Optional.of(getNome()).ifPresent(n -> {
//                    if (detalhe != null)
//                        detalhe.append("\n");
                    detalhe.append(String.format("Usuário: %s", getNome()));
                }
        );
        Optional.of(getApelido()).ifPresent(n -> {
                    if (detalhe != null)
                        detalhe.append("\n");
                    detalhe.append(String.format("Apelido: %s", getApelido()));
                }
        );
        Optional.of(getNome()).ifPresent(n -> {
                    if (detalhe != null)
                        detalhe.append("\n");
                    detalhe.append(String.format("Cargo: %s", getAccessGuest().getDescricao()));
                }
        );
        return detalhe.toString();
    }

    @Column(length = 150, nullable = false)
    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    @Column(length = 128, nullable = false)
    public String getSenha() {
        return senha.get();
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    @Column(length = 2, nullable = false)
    public AccessGuest getAccessGuest() {
        return AccessGuest.toEnum(accessGuest.get());
    }

    public IntegerProperty accessGuestProperty() {
        return accessGuest;
    }

    public void setAccessGuest(AccessGuest accessGuest) {
        this.accessGuest.set(accessGuest.getCod());
    }

    @Override
    public String toString() {
        return nomeProperty().get();
//        return "Usuario{" +
//                "email=" + email +
//                ", senha=" + senha +
//                ", accessGuest=" + accessGuest +
//                '}';
    }
}
