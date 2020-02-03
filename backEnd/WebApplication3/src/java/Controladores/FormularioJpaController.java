/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Formulario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Marcapc;
import Entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aiya
 */
public class FormularioJpaController implements Serializable {

    public FormularioJpaController(){
        this.emf = Persistence.createEntityManagerFactory("WebApplication3PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Formulario formulario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marcapc marcaPc = formulario.getMarcaPc();
            if (marcaPc != null) {
                marcaPc = em.getReference(marcaPc.getClass(), marcaPc.getId());
                formulario.setMarcaPc(marcaPc);
            }
            Usuario idpersona = formulario.getIdpersona();
            if (idpersona != null) {
                idpersona = em.getReference(idpersona.getClass(), idpersona.getId());
                formulario.setIdpersona(idpersona);
            }
            em.persist(formulario);
            if (marcaPc != null) {
                marcaPc.getFormularioList().add(formulario);
                marcaPc = em.merge(marcaPc);
            }
            if (idpersona != null) {
                idpersona.getFormularioList().add(formulario);
                idpersona = em.merge(idpersona);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFormulario(formulario.getId()) != null) {
                throw new PreexistingEntityException("Formulario " + formulario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Formulario formulario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formulario persistentFormulario = em.find(Formulario.class, formulario.getId());
            Marcapc marcaPcOld = persistentFormulario.getMarcaPc();
            Marcapc marcaPcNew = formulario.getMarcaPc();
            Usuario idpersonaOld = persistentFormulario.getIdpersona();
            Usuario idpersonaNew = formulario.getIdpersona();
            if (marcaPcNew != null) {
                marcaPcNew = em.getReference(marcaPcNew.getClass(), marcaPcNew.getId());
                formulario.setMarcaPc(marcaPcNew);
            }
            if (idpersonaNew != null) {
                idpersonaNew = em.getReference(idpersonaNew.getClass(), idpersonaNew.getId());
                formulario.setIdpersona(idpersonaNew);
            }
            formulario = em.merge(formulario);
            if (marcaPcOld != null && !marcaPcOld.equals(marcaPcNew)) {
                marcaPcOld.getFormularioList().remove(formulario);
                marcaPcOld = em.merge(marcaPcOld);
            }
            if (marcaPcNew != null && !marcaPcNew.equals(marcaPcOld)) {
                marcaPcNew.getFormularioList().add(formulario);
                marcaPcNew = em.merge(marcaPcNew);
            }
            if (idpersonaOld != null && !idpersonaOld.equals(idpersonaNew)) {
                idpersonaOld.getFormularioList().remove(formulario);
                idpersonaOld = em.merge(idpersonaOld);
            }
            if (idpersonaNew != null && !idpersonaNew.equals(idpersonaOld)) {
                idpersonaNew.getFormularioList().add(formulario);
                idpersonaNew = em.merge(idpersonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = formulario.getId();
                if (findFormulario(id) == null) {
                    throw new NonexistentEntityException("The formulario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formulario formulario;
            try {
                formulario = em.getReference(Formulario.class, id);
                formulario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formulario with id " + id + " no longer exists.", enfe);
            }
            Marcapc marcaPc = formulario.getMarcaPc();
            if (marcaPc != null) {
                marcaPc.getFormularioList().remove(formulario);
                marcaPc = em.merge(marcaPc);
            }
            Usuario idpersona = formulario.getIdpersona();
            if (idpersona != null) {
                idpersona.getFormularioList().remove(formulario);
                idpersona = em.merge(idpersona);
            }
            em.remove(formulario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Formulario> findFormularioEntities() {
        return findFormularioEntities(true, -1, -1);
    }

    public List<Formulario> findFormularioEntities(int maxResults, int firstResult) {
        return findFormularioEntities(false, maxResults, firstResult);
    }

    private List<Formulario> findFormularioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formulario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Formulario findFormulario(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Formulario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormularioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Formulario> rt = cq.from(Formulario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
