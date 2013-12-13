/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droll.dao;

import com.droll.model.HibernateUtil;
import com.droll.model.Idea;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author rev
 */
public class IdeaDaoImpl implements IdeaDao{

    SessionFactory sf = null;
    Session session = null;
    
    private static final Logger log = Logger.getLogger(IdeaDaoImpl.class);
    
    public static final String FIND_IDEAS_BY_NAME = "select i from Idea i where i.userName = :userName";
    
    public static final String FIND_PRIVATE_IDEAS_BY_NAME = "select i from Idea i where i.userName = :userName and i.pub = :pub";
    
    public static final String FIND_IDEAS_BY_DOMAIN = "select i from Idea i where i.domainId = :domainId";
    
    public static final String FIND_ALL_PUBLIC_IDEAS = "select i from Idea i where i.pub = :pub";
    
    public IdeaDaoImpl(){
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
    }
    
    @Override
    public Idea getIdeaById(Integer id) {
     checkSessionConnection();
     return (Idea)session.get(Idea.class,id);
        
        
    }

    @Override
    public List<Idea> getIdeaByUserName(String name) {
        if(name != null){
            checkSessionConnection();
            Query query = session.createQuery(FIND_IDEAS_BY_NAME);
            query.setParameter("userName", name);
            List<Idea> ideas = query.list();
            return ideas;
        }
        else{
            log.error("Username is null");
            return null;
        }
        
    }

    @Override
    public boolean deleteIdea(Integer id) {
        try{
        Idea idea = getIdeaById(id);
        if(idea != null){
                checkSessionConnection();
            session.beginTransaction();
            session.delete(idea);
            session.getTransaction().commit();
            session.close();
            return true;
        }else{
            log.error("deleteIdea : could not find idea "+id);
            return false;
        }
        }catch(Exception e){
            session.getTransaction().rollback();
            session.close();
            return false;
        }
    }

    @Override
    public boolean updateIdea(Idea idea) {
        if(idea != null){
            try{
            checkSessionConnection();
            session.beginTransaction();
            session.update(idea);
            session.flush();
            session.getTransaction().commit();
            session.close();
            return true;
            }catch(Exception e){
                log.error("updateIdea : failed with "+e.getMessage());
                if(session.getTransaction().isActive()){
                    session.getTransaction().rollback();
                }
                session.close();
                return false;
            }
            
        }else{
            log.error("updateIdea : idea is null");
            return false;
        }
    }

    @Override
    public Idea createIdea(Idea idea) {
        if(idea.getUserName()== null || idea.getDescription() == null ){
            log.info("username or description is empty");
            return null;
        }
        try{
            if(idea.getCreated() == null){
                Calendar cal = Calendar.getInstance();
                idea.setCreated(cal);
            }
        checkSessionConnection();
        session.beginTransaction();
        idea = (Idea) session.merge(idea);
        session.flush();
        session.getTransaction().commit();
        session.close();
        return idea;
        }catch(Exception e){
            log.error("createIdea failed with : "+e.getMessage());
            session.getTransaction().rollback();
            session.close();
            return null;
        }
    }

    private void checkSessionConnection() {
         if(!session.isOpen()){
            session = sf.openSession();
        }
    }

    @Override
    public List<Idea> getIdeasByDomainId(int domainId) {
        
            checkSessionConnection();
            Query query = session.createQuery(FIND_IDEAS_BY_DOMAIN);
            query.setParameter("domainId", domainId);
            List<Idea> ideas = query.list();
            return ideas;
        
    }

    @Override
    public List<Idea> getPrivateIdeasByuserName(String name) {
        if(name != null){
            checkSessionConnection();
            Query query = session.createQuery(FIND_PRIVATE_IDEAS_BY_NAME);
            query.setParameter("userName", name);
            query.setParameter("pub", true);
            List<Idea> ideas = query.list();
            return ideas;
        }
        else{
            log.error("Username is null");
            return null;
        }
    }

    @Override
    public List<Idea> getAllPublicIdeas() {
       
            checkSessionConnection();
            Query query = session.createQuery(FIND_ALL_PUBLIC_IDEAS);
            query.setParameter("pub", true);
            List<Idea> ideas = query.list();
            return ideas;
       
    }
    
}
