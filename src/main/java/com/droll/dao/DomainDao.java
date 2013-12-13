/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droll.dao;

import com.droll.model.Domain;
import java.util.List;

/**
 *
 * @author rev
 */
public interface DomainDao {
    
    public Domain createDomain(Domain domain);
    
    public Domain getDomainById(Integer id);
    
    public boolean deleteDomain(Integer id);
    
    public List<Domain> getAllDomains();
    
}
