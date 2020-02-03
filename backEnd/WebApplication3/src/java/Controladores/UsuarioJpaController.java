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
import Entidades.Rol;
import Entidades.Formulario;
import Entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author aiya
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("WebApplication3PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getFormularioList() == null) {
            usuario.setFormularioList(new ArrayList<Formulario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol idrol = usuario.getIdrol();
            if (idrol != null) {
                idrol = em.getReference(idrol.getClass(), idrol.getId());
                usuario.setIdrol(idrol);
            }
            List<Formulario> attachedFormularioList = new ArrayList<Formulario>();
            for (Formulario formularioListFormularioToAttach : usuario.getFormularioList()) {
                formularioListFormularioToAttach = em.getReference(formularioListFormularioToAttach.getClass(), formularioListFormularioToAttach.getId());
                attachedFormularioList.add(formularioListFormularioToAttach);
            }
            usuario.setFormularioList(attachedFormularioList);
            em.persist(usuario);
            if (idrol != null) {
                idrol.getUsuarioList().add(usuario);
                idrol = em.merge(idrol);
            }
            for (Formulario formularioListFormulario : usuario.getFormularioList()) {
                Usuario oldIdpersonaOfFormularioListFormulario = formularioListFormulario.getIdpersona();
                formularioListFormulario.setIdpersona(usuario);
                formularioListFormulario = em.merge(formularioListFormulario);
                if (oldIdpersonaOfFormularioListFormulario != null) {
                    oldIdpersonaOfFormularioListFormulario.getFormularioList().remove(formularioListFormulario);
                    oldIdpersonaOfFormularioListFormulario = em.merge(oldIdpersonaOfFormularioListFormulario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getId()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Rol idrolOld = persistentUsuario.getIdrol();
            Rol idrolNew = usuario.getIdrol();
            List<Formulario> formularioListOld = persistentUsuario.getFormularioList();
            List<Formulario> formularioListNew = usuario.getFormularioList();
            if (idrolNew != null) {
                idrolNew = em.getReference(idrolNew.getClass(), idrolNew.getId());
                usuario.setIdrol(idrolNew);
            }
            List<Formulario> attachedFormularioListNew = new ArrayList<Formulario>();
            for (Formulario formularioListNewFormularioToAttach : formularioListNew) {
                formularioListNewFormularioToAttach = em.getReference(formularioListNewFormularioToAttach.getClass(), formularioListNewFormularioToAttach.getId());
                attachedFormularioListNew.add(formularioListNewFormularioToAttach);
            }
            formularioListNew = attachedFormularioListNew;
            usuario.setFormularioList(formularioListNew);
            usuario = em.merge(usuario);
            if (idrolOld != null && !idrolOld.equals(idrolNew)) {
                idrolOld.getUsuarioList().remove(usuario);
                idrolOld = em.merge(idrolOld);
            }
            if (idrolNew != null && !idrolNew.equals(idrolOld)) {
                idrolNew.getUsuarioList().add(usuario);
                idrolNew = em.merge(idrolNew);
            }
            for (Formulario formularioListOldFormulario : formularioListOld) {
                if (!formularioListNew.contains(formularioListOldFormulario)) {
                    formularioListOldFormulario.setIdpersona(null);
                    formularioListOldFormulario = em.merge(formularioListOldFormulario);
                }
            }
            for (Formulario formularioListNewFormulario : formularioListNew) {
                if (!formularioListOld.contains(formularioListNewFormulario)) {
                    Usuario oldIdpersonaOfFormularioListNewFormulario = formularioListNewFormulario.getIdpersona();
                    formularioListNewFormulario.setIdpersona(usuario);
                    formularioListNewFormulario = em.merge(formularioListNewFormulario);
                    if (oldIdpersonaOfFormularioListNewFormulario != null && !oldIdpersonaOfFormularioListNewFormulario.equals(usuario)) {
                        oldIdpersonaOfFormularioListNewFormulario.getFormularioList().remove(formularioListNewFormulario);
                        oldIdpersonaOfFormularioListNewFormulario = em.merge(oldIdpersonaOfFormularioListNewFormulario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Rol idrol = usuario.getIdrol();
            if (idrol != null) {
                idrol.getUsuarioList().remove(usuario);
                idrol = em.merge(idrol);
            }
            List<Formulario> formularioList = usuario.getFormularioList();
            for (Formulario formularioListFormulario : formularioList) {
                formularioListFormulario.setIdpersona(null);
                formularioListFormulario = em.merge(formularioListFormulario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
