/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droll.rest;

import com.droll.dao.IdeaDao;
import com.droll.dao.IdeaDaoImpl;
import com.droll.model.Idea;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 *
 * @author rev
 */
@Path("/user")
public class UserService {
    IdeaDao ideaDao = null;
    public String UID = "uid";
    
    public UserService(){
        ideaDao = new IdeaDaoImpl();
    }
    @GET
    @Path("/ideas")
    @Produces("application/json")
    public List<Idea> getIdeasByUserName(@PathParam("ideaId") int ideaId  , 
        @QueryParam("public") boolean isPublic ,@Context HttpServletRequest request) {
         
        if(request.getHeader(UID) == null){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
         
        
            if(isPublic){
                return ideaDao.getIdeaByUserName(request.getHeader(UID));
            }else{
                return ideaDao.getPrivateIdeasByuserName(request.getHeader(UID));
            }
            
        
        
    }
}
