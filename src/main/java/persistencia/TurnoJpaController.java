/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import logica.Cliente;
import logica.Mascota;
import logica.Turno;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Ayrto
 */
public class TurnoJpaController implements Serializable {

    public TurnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public TurnoJpaController(){
        emf = Persistence.createEntityManagerFactory("PeluCanPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turno turno) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = turno.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                turno.setCliente(cliente);
            }
            Mascota mascota = turno.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getId());
                turno.setMascota(mascota);
            }
            em.persist(turno);
            if (cliente != null) {
                cliente.getTurnos().add(turno);
                cliente = em.merge(cliente);
            }
            if (mascota != null) {
                mascota.getTurnos().add(turno);
                mascota = em.merge(mascota);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turno turno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno persistentTurno = em.find(Turno.class, turno.getId());
            Cliente clienteOld = persistentTurno.getCliente();
            Cliente clienteNew = turno.getCliente();
            Mascota mascotaOld = persistentTurno.getMascota();
            Mascota mascotaNew = turno.getMascota();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                turno.setCliente(clienteNew);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getId());
                turno.setMascota(mascotaNew);
            }
            turno = em.merge(turno);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getTurnos().remove(turno);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getTurnos().add(turno);
                clienteNew = em.merge(clienteNew);
            }
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getTurnos().remove(turno);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getTurnos().add(turno);
                mascotaNew = em.merge(mascotaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = turno.getId();
                if (findTurno(id) == null) {
                    throw new NonexistentEntityException("The turno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno turno;
            try {
                turno = em.getReference(Turno.class, id);
                turno.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turno with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = turno.getCliente();
            if (cliente != null) {
                cliente.getTurnos().remove(turno);
                cliente = em.merge(cliente);
            }
            Mascota mascota = turno.getMascota();
            if (mascota != null) {
                mascota.getTurnos().remove(turno);
                mascota = em.merge(mascota);
            }
            em.remove(turno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turno> findTurnoEntities() {
        return findTurnoEntities(true, -1, -1);
    }

    public List<Turno> findTurnoEntities(int maxResults, int firstResult) {
        return findTurnoEntities(false, maxResults, firstResult);
    }

    private List<Turno> findTurnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turno.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Turno> turnos = q.getResultList();
            for (Turno turno : turnos) {
                if (turno.getMascota() == null) {
                    turno.setMascota(obtenerMascotaEliminada());
                }
            }
            return turnos;
        } finally {
            em.close();
        }
    }

    public Turno findTurno(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turno.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turno> rt = cq.from(Turno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Turno> findTurnoByFecha(LocalDate fecha) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Turno> cq = em.getCriteriaBuilder().createQuery(Turno.class);
            Root<Turno> root = cq.from(Turno.class);

            LocalDateTime startOfDay = fecha.atStartOfDay();
            LocalDateTime endOfDay = fecha.atTime(LocalTime.MAX);

            cq.select(root).where(
                em.getCriteriaBuilder().between(root.get("fechaHora"), startOfDay, endOfDay)
            );

            Query query = em.createQuery(cq);
            List<Turno> turnos = query.getResultList();
            for (Turno turno : turnos) {
                if (turno.getMascota() == null) {
                    turno.setMascota(obtenerMascotaEliminada());
                }
            }
            return turnos;
        } finally {
            em.close();
        }
    }
    
    public Turno findTurnoByFechaHora(LocalDateTime fechaHora) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Turno> cq = em.getCriteriaBuilder().createQuery(Turno.class);
            Root<Turno> root = cq.from(Turno.class);
            cq.select(root).where(em.getCriteriaBuilder().equal(root.get("fechaHora"), fechaHora));
            Query query = em.createQuery(cq);
            Turno turno = (Turno) query.getSingleResult();
            if (turno.getMascota() == null) {
                turno.setMascota(obtenerMascotaEliminada());
            }
            return turno;
        } catch (Exception e) {
            // Manejar el caso en que no se encuentre ningún resultado
            return null;
        } finally {
            em.close();
        }
    }

    private Mascota obtenerMascotaEliminada() {
        Mascota mascotaEliminada = new Mascota();
        mascotaEliminada.setNombre("Mascota eliminada");
        return mascotaEliminada;
    }


}


