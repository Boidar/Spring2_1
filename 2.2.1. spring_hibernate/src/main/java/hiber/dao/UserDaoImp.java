package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;
   @Autowired
   private HibernateTransactionManager transactionManager;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public void add(Car car) {
      sessionFactory.openSession().save(car);
   }

   @Override
   public User getUserByModAndSer(String model, int series) {
      Transaction transaction = null;
      try(Session session = transactionManager.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();
         String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
         Query query = session.createQuery(hql);
         query.setParameter("model", model);
         query.setParameter("series", series);
         User user = (User) query.getSingleResult();
         transaction.commit();
         return user;
      } catch (Exception e) {
         if (transaction != null) {
            transaction.rollback();
         }
         throw e;
      }
   }


}
