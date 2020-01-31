/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aiya
 */
@Entity
@Table(name = "marcapc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Marcapc.findAll", query = "SELECT m FROM Marcapc m")
    , @NamedQuery(name = "Marcapc.findById", query = "SELECT m FROM Marcapc m WHERE m.id = :id")
    , @NamedQuery(name = "Marcapc.findByDescripci\u00f3n", query = "SELECT m FROM Marcapc m WHERE m.descripci\u00f3n = :descripci\u00f3n")})
public class Marcapc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "descripci\u00f3n")
    private String descripción;
    @OneToMany(mappedBy = "marcaPc")
    private List<Formulario> formularioList;

    public Marcapc() {
    }

    public Marcapc(Short id) {
        this.id = id;
    }

    public Marcapc(Short id, String descripción) {
        this.id = id;
        this.descripción = descripción;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    @XmlTransient
    public List<Formulario> getFormularioList() {
        return formularioList;
    }

    public void setFormularioList(List<Formulario> formularioList) {
        this.formularioList = formularioList;
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
        if (!(object instanceof Marcapc)) {
            return false;
        }
        Marcapc other = (Marcapc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Marcapc[ id=" + id + " ]";
    }
    
}
