/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droll.dao;

import com.droll.model.Idea;
import java.util.List;

/**
 *
 * @author rev
 */
public interface IdeaDao {
    public Idea getIdeaById(Integer id);
    
    public List<Idea> getIdeaByUserName(String name);
    
    public List<Idea> getPrivateIdeasByuserName(String name);
    
    public boolean deleteIdea(Integer id);
    
    public boolean updateIdea(Idea idea);
    
    public Idea createIdea(Idea idea);
    
    public List<Idea> getIdeasByDomainId(int domainId);
    
    public List<Idea> getAllPublicIdeas();
    
    
}
