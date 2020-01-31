/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Formulario;
import Entidades.Marcapc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aiya
 */
public class MarcapcJpaController implements Serializable {

    public MarcapcJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Conexion-JAVA-MySQL-JPAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marcapc marcapc) throws PreexistingEntityException, Exception {
        if (marcapc.getFormularioList() == null) {
            marcapc.setFormularioList(new ArrayList<Formulario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Formulario> attachedFormularioList = new ArrayList<Formulario>();
            for (Formulario formularioListFormularioToAttach : marcapc.getFormularioList()) {
                formularioListFormularioToAttach = em.getReference(formularioListFormularioToAttach.getClass(), formularioListFormularioToAttach.getId());
                attachedFormularioList.add(formularioListFormularioToAttach);
            }
            marcapc.setFormularioList(attachedFormularioList);
            em.persist(marcapc);
            for (Formulario formularioListFormulario : marcapc.getFormularioList()) {
                Marcapc oldMarcaPcOfFormularioListFormulario = formularioListFormulario.getMarcaPc();
                formularioListFormulario.setMarcaPc(marcapc);
                formularioListFormulario = em.merge(formularioListFormulario);
                if (oldMarcaPcOfFormularioListFormulario != null) {
                    oldMarcaPcOfFormularioListFormulario.getFormularioList().remove(formularioListFormulario);
                    oldMarcaPcOfFormularioListFormulario = em.merge(oldMarcaPcOfFormularioListFormulario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMarcapc(marcapc.getId()) != null) {
                throw new PreexistingEntityException("Marcapc " + marcapc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marcapc marcapc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marcapc persistentMarcapc = em.find(Marcapc.class, marcapc.getId());
            List<Formulario> formularioListOld = persistentMarcapc.getFormularioList();
            List<Formulario> formularioListNew = marcapc.getFormularioList();
            List<Formulario> attachedFormularioListNew = new ArrayList<Formulario>();
            for (Formulario formularioListNewFormularioToAttach : formularioListNew) {
                formularioListNewFormularioToAttach = em.getReference(formularioListNewFormularioToAttach.getClass(), formularioListNewFormularioToAttach.getId());
                attachedFormularioListNew.add(formularioListNewFormularioToAttach);
            }
            formularioListNew = attachedFormularioListNew;
            marcapc.setFormularioList(formularioListNew);
            marcapc = em.merge(marcapc);
            for (Formulario formularioListOldFormulario : formularioListOld) {
                if (!formularioListNew.contains(formularioListOldFormulario)) {
                    formularioListOldFormulario.setMarcaPc(null);
                    formularioListOldFormulario = em.merge(formularioListOldFormulario);
                }
            }
            for (Formulario formularioListNewFormulario : formularioListNew) {
                if (!formularioListOld.contains(formularioListNewFormulario)) {
                    Marcapc oldMarcaPcOfFormularioListNewFormulario = formularioListNewFormulario.getMarcaPc();
                    formularioListNewFormulario.setMarcaPc(marcapc);
                    formularioListNewFormulario = em.merge(formularioListNewFormulario);
                    if (oldMarcaPcOfFormularioListNewFormulario != null && !oldMarcaPcOfFormularioListNewFormulario.equals(marcapc)) {
                        oldMarcaPcOfFormularioListNewFormulario.getFormularioList().remove(formularioListNewFormulario);
                        oldMarcaPcOfFormularioListNewFormulario = em.merge(oldMarcaPcOfFormularioListNewFormulario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = marcapc.getId();
                if (findMarcapc(id) == null) {
                    throw new NonexistentEntityException("The marcapc with id " + id + " no longer exists.");
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
            Marcapc marcapc;
            try {
                marcapc = em.getReference(Marcapc.class, id);
                marcapc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marcapc with id " + id + " no longer exists.", enfe);
            }
            List<Formulario> formularioList = marcapc.getFormularioList();
            for (Formulario formularioListFormulario : formularioList) {
                formularioListFormulario.setMarcaPc(null);
                formularioListFormulario = em.merge(formularioListFormulario);
            }
            em.remove(marcapc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marcapc> findMarcapcEntities() {
        return findMarcapcEntities(true, -1, -1);
    }

    public List<Marcapc> findMarcapcEntities(int maxResults, int firstResult) {
        return findMarcapcEntities(false, maxResults, firstResult);
    }

    private List<Marcapc> findMarcapcEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marcapc.class));
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

    public Marcapc findMarcapc(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marcapc.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcapcCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marcapc> rt = cq.from(Marcapc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
