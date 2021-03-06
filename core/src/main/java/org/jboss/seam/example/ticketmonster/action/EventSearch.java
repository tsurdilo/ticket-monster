package org.jboss.seam.example.ticketmonster.action;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.example.ticketmonster.model.Event;
import org.jboss.seam.example.ticketmonster.model.EventCategory;
import org.jboss.seam.servlet.http.HttpParam;

/**
 * Event search actions handled here
 * 
 * @author Shane Bryzak
 *
 */
public @Model class EventSearch
{
   @Inject EntityManager entityManager;   
   @Inject @HttpParam("category") String category;
      
   private List<Event> events;
   
   @SuppressWarnings("unchecked")
   private void loadEvents()
   {                         
      events = (category != null) ? 
            (List<Event>) entityManager.createQuery(
               "select e from Event e where e.category = :category")
               .setParameter("category", lookupCategory(Long.valueOf(category)))
               .getResultList() :
            (List<Event>) entityManager.createQuery(
               "select e from Event e where e.major = true")
               .getResultList();           
   }
   
   @SuppressWarnings("unchecked")
   private void loadAllEvents()
   {
      events = (List<Event>) entityManager.createQuery("select e from Event e").getResultList();
   }
   
   public EventCategory lookupCategory(Long categoryId)
   {
      return entityManager.find(EventCategory.class, categoryId);
   }
   
   public List<Event> getEvents()
   {
      if (events == null) loadEvents();
      return events;
   }
   
   public List<Event> getAllEvents()
   {
      if (events == null) loadAllEvents();
      return events;
   }
}
