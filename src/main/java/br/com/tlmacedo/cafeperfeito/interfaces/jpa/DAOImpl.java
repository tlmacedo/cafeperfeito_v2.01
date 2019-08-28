package br.com.tlmacedo.cafeperfeito.interfaces.jpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public class DAOImpl<T, I extends Serializable> implements DAO<T, I> {
    private ConnectionFactory conexao;

    public DAOImpl() {
        if (getConexao() == null)
            setConexao(new ConnectionFactory());
    }

//    @Override
//    public T merger(T entity) {
//        transactionBegin();
//        T saved = getConexao().getEntityManager().merge(entity);
//        transactionCommit();
//        return saved;
//    }

    @Override
    public void transactionBegin() {
        getTransaction().begin();
    }

    @Override
    public T setTransactionPersist(T entity) {
        try {
            T saved = getConexao().getEntityManager().merge(entity);
            return saved;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void transactionCommit() {
        try {
            getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void transactionRollback() {
        try {
            if (getTransaction() == null)
                getTransaction().rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(T entity) {
        try {
            getConexao().getEntityManager().getTransaction().begin();
            getConexao().getEntityManager().remove(entity);
            getConexao().getEntityManager().getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public T getById(Class<T> classe, I pk) {
        try {
            return getConexao().getEntityManager().find(classe, pk);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<T> getAll(Class<T> classe, String campo, String operador, String busca, String orderBy) {
        try {
            Query select;
            String sql = String.format("from %s%s%s",
                    classe.getSimpleName(),
                    (campo != null && operador != null && busca != null)
                            ? String.format(" where %s %s %s", campo, operador, busca) : "",
                    orderBy != null
                            ? String.format(" order by %s", orderBy) : ""
            );
            select = getConexao().getEntityManager().createQuery(sql);
//        List<T> list = select.getResultList();
//        return list;
            return select.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public EntityManager getEntityManager() {
        if (getConexao() == null)
            setConexao(new ConnectionFactory());
        return getConexao().getEntityManager();
    }

    @Override
    public EntityTransaction getTransaction() {
        return getConexao().getEntityManager().getTransaction();
    }


    public ConnectionFactory getConexao() {
        return conexao;
    }

    public void setConexao(ConnectionFactory conexao) {
        this.conexao = conexao;
    }
}
