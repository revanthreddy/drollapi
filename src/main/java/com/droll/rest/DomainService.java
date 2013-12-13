/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droll.rest;

import com.droll.dao.DomainDao;
import com.droll.dao.DomainDaoImpl;
import com.droll.dao.IdeaDao;
import com.droll.dao.IdeaDaoImpl;
import com.droll.model.Comment;
import com.droll.model.Domain;
import com.droll.model.Idea;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 *  
 * @author rev
 */
@Path("/domains")
public class DomainService {
    
    DomainDao dao = null;

    public DomainService(){
        dao = new DomainDaoImpl();
    }
    
    @GET
    @Path("/")
    @Produces("application/json")
    public List<Domain> getAllDomains(){
        return dao.getAllDomains();
        //return null;
    }
    
    @GET
    @Path("/{domainId}/ideas")
    @Produces("application/json")
    public List<Idea> getIdeasInTheDomain(@PathParam("domainId") int domainId  , @Context HttpServletRequest request) {
        if(domainId <1 ){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }else{
            Domain domain = dao.getDomainById(domainId);
            if(domain != null){
                IdeaDao ideaDao = new IdeaDaoImpl();
                return ideaDao.getIdeasByDomainId(domainId);
            }else{
                throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).
                    entity("idea not found").build());
            }
        }
        
    }
}
