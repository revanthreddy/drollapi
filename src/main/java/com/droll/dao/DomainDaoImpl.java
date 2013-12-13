/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droll.dao;

import com.droll.model.Domain;
import com.droll.model.HibernateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author rev
 */
public class DomainDaoImpl implements DomainDao{

    SessionFactory sf = null;
    Session session = null;
    
    private static final Logger log = Logger.getLogger(DomainDaoImpl.class);
    
    public static final String FIND_ALL_DOMAINS = "select d from Domain d ";
    
    public DomainDaoImpl(){
         sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
    }
    
    @Override
    public Domain createDomain(Domain domain) {
        
        
        try{
        checkSessionConnection();
        session.beginTransaction();
        domain = (Domain) session.merge(domain);
        session.getTransaction  ().commit();
        session.close();
        return domain;
        }catch(Exception e){
            log.error("createIdea failed with : "+e.getMessage());
            session.getTransaction().rollback();
            session.close();
            return null;
        }
    }

    @Override
    public Domain getDomainById(Integer id) {
          if(id != null){
            checkSessionConnection();
            Domain domain = (Domain) session.get(Domain.class , id);
            if(domain != null){
                return domain;
            }else{
                log.error("getDomainById : domain not found with Id "+id);
                return null;
            }
        }else{
            log.error("getDomainById : domain cannot be null");
            return null;
        }      
        
    }

    @Override
    public boolean deleteDomain(Integer id) {
        try{
        Domain domain = getDomainById(id);
        if(domain != null){
                checkSessionConnection();
            session.beginTransaction();
            session.delete(domain);
            session.getTransaction().commit();
            session.close();
            return true;
        }else{
            log.error("deletedomain : could not find domain "+id);
            return false;
        }
        }catch(Exception e){
            session.getTransaction().rollback();
            session.close();
            return false;
        }
    }

    @Override
    public List<Domain> getAllDomains() {
        Query query = session.createQuery(FIND_ALL_DOMAINS);
        return query.list();
        
    }
    
    private void checkSessionConnection() {
         if(!session.isOpen()){
            session = sf.openSession();
        }
    }
}
