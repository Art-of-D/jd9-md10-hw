package crudservice;

import hibernate.HibernateUtil;
import objectid.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ClientCrudService {
    private Session connection;
    private Transaction transaction;
    private List<Client> resultList;
    private HibernateUtil util = new HibernateUtil().getInstance();

    public void readAll(){
        try {
            connection = util.getSessionFactory().openSession();
            transaction = connection.beginTransaction();

            resultList = connection.createQuery("FROM Client", Client.class).list();

            connection.getTransaction().commit();
        } catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
        } finally {
            connection.close();
        }

        for (Client client: resultList) {
            System.out.println(client.getName());
        }
    }

    public void getById(int number){
        connection = util.getSessionFactory().openSession();
        connection.beginTransaction();

        Query<Client> query = connection.createQuery("FROM Client WHERE id=:id", Client.class);
        query.setParameter("id", number);
        resultList = query.list();

        connection.getTransaction().commit();
        connection.close();

        for (Client client: resultList) {
            System.out.println(client.getName());
        }
    }

    public void create(String name){
        Long lastIdentifier = null;
        try {
            connection = util.getSessionFactory().openSession();
            transaction = connection.beginTransaction();
            Query<Long> query = connection.createQuery("SELECT MAX(id) FROM Client", Long.class);
            lastIdentifier = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            connection.close();
        }

        if (lastIdentifier != null) {
            Long newIdentifier = (lastIdentifier != null) ? lastIdentifier + 1 : 1;
            Client client = Client.builder().id(newIdentifier).name(name).build();

            Session connection2 = util.getSessionFactory().openSession();
            Transaction transaction2 = null;

            try {
                transaction2 = connection2.beginTransaction();
                connection2.save(client);
                transaction2.commit();
            } catch (Exception e) {
                if (transaction2 != null) {
                    transaction2.rollback();
                }
            } finally {
                connection2.close();
            }
        }
    }

    public void delete(String name){
        try {
            connection = util.getSessionFactory().openSession();
            transaction = connection.beginTransaction();
            Query<?> query = connection.createNativeQuery("DELETE FROM Client WHERE name=:name", Client.class);
            query.setParameter("name", name);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            connection.close();
        }
    }
}
