package br.com.tlmacedo.cafeperfeito.interfaces.jpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

public interface DAO<T, I extends Serializable> {

//    public T merger(T entity);

    public void remove(T entity);

    public void transactionBegin();

    public T setTransactionPersist(T entity);

    public void transactionCommit();

    public void transactionRollback();

    public T getById(Class<T> classe, I pk);

    public List<T> getAll(Class<T> classe, String orderBy, String campo, String operador, String busca);

    public EntityManager getEntityManager();

    public EntityTransaction getTransaction();
}