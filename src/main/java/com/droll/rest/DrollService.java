/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droll.rest;

//import javax.servlet.http.HttpServletRequest;
import com.droll.dao.IdeaDaoImpl;
import com.droll.dao.IdeaDao;
import com.droll.model.Comment;
import com.droll.model.Idea;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("/ideas")
public class DrollService {
    
    
    IdeaDao ideaDao = null;
    public String UID = "uid";
    public DrollService(){
        ideaDao = new IdeaDaoImpl();
    }
    
    @GET
    @Path("/{ideaId}")
    @Produces("application/json")
    public Idea getIdea(@PathParam("ideaId") int ideaId  , @Context HttpServletRequest request) {
        if(ideaId <1 ){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }else{
            Idea ideaFromDb = ideaDao.getIdeaById(ideaId);
            if(ideaFromDb != null){
                return ideaFromDb;
            }else{
                throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).
                    entity("idea not found").build());
            }
        }
        
    }
    
    @POST
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Idea createIdea(Idea idea , @Context HttpServletRequest request){
        if(idea != null){
            if(request.getHeader(UID) == null){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            idea.setUserName(request.getHeader(UID) );
            idea = ideaDao.createIdea(idea);
            if(idea != null){
                return idea;
            }else{
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
                
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
    }
    
    @GET
    @Path("/")
    @Produces("application/json")
    public List<Idea> getPublicIdea(@Context HttpServletRequest request) {
        
        return ideaDao.getAllPublicIdeas();
        
    }
    
    @DELETE
    @Path("/{ideaId}")
    @Produces("application/json")
    public Response deleteIdea(@PathParam("ideaId") int ideaId , 
                                        @Context HttpServletRequest request){
        Idea ideaFromDb = ideaDao.getIdeaById(ideaId);
        if(ideaFromDb == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).
                    entity("idea not found").build());
        else{
            if(ideaDao.deleteIdea(ideaId)){
                return Response.status(Response.Status.OK).entity("Idea deleted succesfully").build();
            }else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete").build();
            }
        }
        
    }
    
    @POST
    @Path("/{ideaId}/comments/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response postAComment(Comment comment ,@PathParam("ideaId") int ideaId , 
                                        @Context HttpServletRequest request){
        if(comment !=null){
            Idea ideaFromDb = ideaDao.getIdeaById(ideaId);
            if(ideaFromDb == null){
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            if(comment.getComment() == null ){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if(request.getHeader(UID) == null){
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            if(comment.getCreated() == null){
                Calendar cal = Calendar.getInstance();
                comment.setCreated(cal);
            }
            comment.setUserName(request.getHeader(UID));
            ideaFromDb.getComments().add(comment);
            boolean success  = ideaDao.updateIdea(ideaFromDb);
            if(success){
                return Response.status(Response.Status.OK).entity("comment created succesfully").build();
            }else
                return Response.status(Response.Status.NOT_MODIFIED).entity("Comment not created").build();
                
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
    }
    
    @GET
    @Path("/{ideaId}/comments")
    @Produces("application/json")
    public Set<Comment> getCommentsForTheIdea(@PathParam("ideaId") int ideaId  , @Context HttpServletRequest request) {
        if(ideaId <1 ){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }else{
            Idea ideaFromDb = ideaDao.getIdeaById(ideaId);
            if(ideaFromDb != null){
                return ideaFromDb.getComments();
            }else{
                throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).
                    entity("idea not found").build());
            }
        }
        
    }
    
    @PUT
    @Path("/{ideaId}/like")
    @Produces("application/json")
    public String likeAnIdea(@PathParam("ideaId") int ideaId  , @Context HttpServletRequest request) {
        if(ideaId <1 ){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }else{
            Idea ideaFromDb = ideaDao.getIdeaById(ideaId);
            if(ideaFromDb != null){
                int count = ideaFromDb.getLikeCounter();
                
                ideaFromDb.setLikeCounter(count+1);
                boolean success = ideaDao.updateIdea(ideaFromDb);
                if(success){
                    return ideaDao.getIdeaById(ideaId).getLikeCounter()+"";
                }else{
                    throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).
                    entity("Internal server error").build());
                }
                    
                
            }else{
                throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).
                    entity("idea not found").build());
            }
        }
        
        
    }
    
    
    
    
    
    
    
    
    
}
