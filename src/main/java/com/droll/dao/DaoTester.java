/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droll.dao;

import com.droll.model.Comment;
import com.droll.model.Domain;
import com.droll.model.HibernateUtil;
import com.droll.model.Idea;
import java.util.HashSet;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author rev
 */
public class DaoTester {
    
    public static void main(String[] args){
        
        saveIdea();        
    }

    private static void saveDomain(){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        
        DomainDao dao = new DomainDaoImpl();
        Domain d = dao.getDomainById(5);
        System.out.println(d.getName());
    }
    
    private static void saveIdea() throws HibernateException {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        DomainDao dao = new DomainDaoImpl();
        Domain d = dao.getDomainById(5);
        //System.out.println("hello world");
        session.beginTransaction();
        
        Idea i = new Idea();
        i.setDescription("abc");
        i.setUserName("user");
        i.setOpen(true);
        i.setDomainId(d.getId());
        Comment c1 = new Comment();
        c1.setComment("new comment");
        c1.setOpen(true);
        
        i.setComments(new HashSet<Comment>());
        i.getComments().add(c1);
        
        i=(Idea) session.merge(i);
        session.flush();
        session.getTransaction().commit();
        session.close();
        
        System.out.println(i.getId());
        
        IdeaDao idDao = new IdeaDaoImpl();
        i = idDao.getIdeaById(i.getId());
        
        
        System.out.println(i.getId());
    }
    
    public static void saveComment(){
        
    }
}
