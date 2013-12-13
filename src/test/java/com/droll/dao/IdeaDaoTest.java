/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droll.dao;

import com.droll.model.Comment;
import com.droll.model.Domain;
import com.droll.model.Idea;
import java.util.HashSet;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author rev
 */
public class IdeaDaoTest {
    IdeaDao dao = null;
    
    public IdeaDaoTest() {
        dao = new IdeaDaoImpl();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getIdeaById method, of class IdeaDaoImpl.
     */
    @Test
    public void testGetIdeaById() {
        
    }

    /**
     * Test of getIdeaByUserName method, of class IdeaDaoImpl.
     */
    @Test
    public void testGetIdeaByUserName() {
        String userName = "droll1@ni.com";
        
        int intialsize  = dao.getIdeaByUserName(userName).size();
        Idea i = new  Idea();
        i.setDescription("This is a wonderful idea.");
        i.setOpen(true);
        i.setUserName(userName);
        i.setDomainId(getTechDomain().getId());
        
        i.setComments(new HashSet<Comment>());
        
        Comment comment1 = new Comment();
        comment1.setComment("I already did this idea.");
        
        i.getComments().add(comment1);
        
        Idea ideaFromDb = dao.createIdea(i);
        
        Assert.assertNotNull("should not be NULL",ideaFromDb);
        
        
        
        Idea newIdea = new Idea();
        newIdea.setUserName(userName);
        newIdea.setDescription("oho oho oho");
        newIdea.setOpen(true);
        
        dao.createIdea(newIdea);
        
        List<Idea> ideas = dao.getIdeaByUserName(userName);
        Assert.assertNotNull(ideas);
        //Assert.assertEquals(intialsize+2,ideas.size());
        
        boolean deleteSuccess = dao.deleteIdea(ideaFromDb.getId());
        
        Assert.assertTrue(deleteSuccess);
    }

    /**
     * Test of deleteIdea method, of class IdeaDaoImpl.
     */
    @Test
    public void testDeleteIdea() {
       //Tested in creation , updation
    }

    /**
     * Test of updateIdea method, of class IdeaDaoImpl.
     */
    @Test
    public void testUpdateIdea() {
        Idea i = new  Idea();
        i.setDescription("This is a wonderful idea.");
        i.setOpen(true);
        i.setUserName("droll@ni.com");
        i.setDomainId(getTechDomain().getId());
        
        i.setComments(new HashSet<Comment>());
        
        Comment comment1 = new Comment();
        comment1.setComment("I already did this idea.");
        
        i.getComments().add(comment1);
        
        Idea ideaFromDb = dao.createIdea(i);
        
        Assert.assertNotNull("should not be NULL",ideaFromDb);
        Assert.assertTrue(ideaFromDb.isOpen());
        
        
        ideaFromDb.setOpen(false);
        Comment comment2 = new Comment();
        comment2.setComment("This is a bad idea");
        ideaFromDb.getComments().add(comment2);
        
        boolean updateSuccess = dao.updateIdea(ideaFromDb);
        Assert.assertTrue(updateSuccess);
        
        ideaFromDb = dao.getIdeaById(ideaFromDb.getId());
        Assert.assertFalse(ideaFromDb.isOpen());
        Assert.assertEquals(2,ideaFromDb.getComments().size());
        
        boolean deleteSuccess = dao.deleteIdea(ideaFromDb.getId());
        
        Assert.assertTrue(deleteSuccess);
    
    }

    /**
     * Test of createIdea method, of class IdeaDaoImpl.
     */
    @Test
    public void testCreateIdea() {
        Idea i = new  Idea();
        i.setDescription("This is a wonderful idea.");
        i.setOpen(true);
        i.setUserName("droll@ni.com");
        i.setDomainId(5);
        
        i.setComments(new HashSet<Comment>());
        
        Comment comment1 = new Comment();
        comment1.setComment("I already did this idea.");
        
        i.getComments().add(comment1);
        
        Idea ideaFromDb = dao.createIdea(i);
        
        Assert.assertNotNull("should not be NULL",ideaFromDb);
        
        boolean deleteSuccess = dao.deleteIdea(ideaFromDb.getId());
        
        Assert.assertTrue(deleteSuccess);
    }
    
    
    public Domain getTechDomain(){
        DomainDao dao = new DomainDaoImpl();
        Domain d = dao.getDomainById(5);
        return d;
        
    }
    
    
    
    
}