package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.SituacaoCadastroEmpresa;
import br.com.tlmacedo.cafeperfeito.model.vo.EmailHomePage;
import br.com.tlmacedo.cafeperfeito.model.vo.EmpresaCondicoes;
import br.com.tlmacedo.cafeperfeito.model.vo.Endereco;
import br.com.tlmacedo.cafeperfeito.model.vo.Telefone;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2020-04-01T13:02:59")
@StaticMetamodel(Empresa.class)
public class Empresa_ { 

    public static volatile ListAttribute<Empresa, Telefone> telefoneList;
    public static volatile SingularAttribute<Empresa, SituacaoCadastroEmpresa> situacao;
    public static volatile SingularAttribute<Empresa, Boolean> loja;
    public static volatile SingularAttribute<Empresa, Integer> prazo;
    public static volatile SingularAttribute<Empresa, String> cnpj;
    public static volatile SingularAttribute<Empresa, BigDecimal> limite;
    public static volatile ListAttribute<Empresa, EmpresaCondicoes> empresaCondicoes;
    public static volatile SingularAttribute<Empresa, String> iMunicpipal;
    public static volatile SingularAttribute<Empresa, LocalDateTime> dtAtualizacao;
    public static volatile ListAttribute<Empresa, Endereco> enderecoList;
    public static volatile SingularAttribute<Empresa, Long> id;
    public static volatile SingularAttribute<Empresa, String> ie;
    public static volatile SingularAttribute<Empresa, Boolean> prazoDiaUtil;
    public static volatile SingularAttribute<Empresa, Usuario> usuarioCadastro;
    public static volatile SingularAttribute<Empresa, String> iSuframa;
    public static volatile SingularAttribute<Empresa, LocalDateTime> dtCadastro;
    public static volatile SingularAttribute<Empresa, String> razao;
    public static volatile SingularAttribute<Empresa, Boolean> transportadora;
    public static volatile SingularAttribute<Empresa, Boolean> cliente;
    public static volatile SingularAttribute<Empresa, String> observacoes;
    public static volatile SingularAttribute<Empresa, String> fantasia;
    public static volatile SingularAttribute<Empresa, Usuario> usuarioAtualizacao;
    public static volatile SingularAttribute<Empresa, Boolean> pessoaJuridica;
    public static volatile SingularAttribute<Empresa, Boolean> fornecedor;
    public static volatile ListAttribute<Empresa, EmailHomePage> emailHomePageList;

}