/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aiya
 */
@Entity
@Table(name = "formulario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formulario.findAll", query = "SELECT f FROM Formulario f")
    , @NamedQuery(name = "Formulario.findById", query = "SELECT f FROM Formulario f WHERE f.id = :id")
    , @NamedQuery(name = "Formulario.findByCorreo", query = "SELECT f FROM Formulario f WHERE f.correo = :correo")
    , @NamedQuery(name = "Formulario.findByComentarios", query = "SELECT f FROM Formulario f WHERE f.comentarios = :comentarios")
    , @NamedQuery(name = "Formulario.findByFechaRespuesta", query = "SELECT f FROM Formulario f WHERE f.fechaRespuesta = :fechaRespuesta")})
public class Formulario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Column(name = "comentarios")
    private String comentarios;
    @Column(name = "fechaRespuesta")
    @Temporal(TemporalType.DATE)
    private Date fechaRespuesta;
    @JoinColumn(name = "MarcaPc", referencedColumnName = "id")
    @ManyToOne
    private Marcapc marcaPc;
    @JoinColumn(name = "idpersona", referencedColumnName = "id")
    @ManyToOne
    private Usuario idpersona;

    public Formulario() {
    }

    public Formulario(Short id) {
        this.id = id;
    }

    public Formulario(Short id, String correo) {
        this.id = id;
        this.correo = correo;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public Marcapc getMarcaPc() {
        return marcaPc;
    }

    public void setMarcaPc(Marcapc marcaPc) {
        this.marcaPc = marcaPc;
    }

    public Usuario getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Usuario idpersona) {
        this.idpersona = idpersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formulario)) {
            return false;
        }
        Formulario other = (Formulario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Formulario[ id=" + id + " ]";
    }
    
}
