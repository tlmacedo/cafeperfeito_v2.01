package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProdutoProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-09-02T15:09:00")
@StaticMetamodel(SaidaProduto.class)
public class SaidaProduto_ { 

    public static volatile SingularAttribute<SaidaProduto, Empresa> cliente;
    public static volatile SingularAttribute<SaidaProduto, Usuario> vendedor;
    public static volatile ListAttribute<SaidaProduto, SaidaProdutoProduto> saidaProdutoProdutoList;
    public static volatile SingularAttribute<SaidaProduto, LocalDateTime> dtCadastro;
    public static volatile SingularAttribute<SaidaProduto, LocalDate> dtSaida;
    public static volatile SingularAttribute<SaidaProduto, Long> id;

}