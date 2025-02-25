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
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import logica.Cliente;
import logica.Turno;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import logica.Mascota;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Ayrto
 */
public class MascotaJpaController implements Serializable {

    public MascotaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public MascotaJpaController(){
        emf = Persistence.createEntityManagerFactory("PeluCanPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mascota mascota) {
        if (mascota.getTurnos() == null) {
            mascota.setTurnos(new LinkedList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = mascota.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                mascota.setCliente(cliente);
            }
            LinkedList<Turno> attachedTurnos = new LinkedList<Turno>();
            for (Turno turnosTurnoToAttach : mascota.getTurnos()) {
                turnosTurnoToAttach = em.getReference(turnosTurnoToAttach.getClass(), turnosTurnoToAttach.getId());
                attachedTurnos.add(turnosTurnoToAttach);
            }
            mascota.setTurnos(attachedTurnos);
            em.persist(mascota);
            if (cliente != null) {
                cliente.getMascotas().add(mascota);
                cliente = em.merge(cliente);
            }
            for (Turno turnosTurno : mascota.getTurnos()) {
                Mascota oldMascotaOfTurnosTurno = turnosTurno.getMascota();
                turnosTurno.setMascota(mascota);
                turnosTurno = em.merge(turnosTurno);
                if (oldMascotaOfTurnosTurno != null) {
                    oldMascotaOfTurnosTurno.getTurnos().remove(turnosTurno);
                    oldMascotaOfTurnosTurno = em.merge(oldMascotaOfTurnosTurno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mascota mascota) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mascota persistentMascota = em.find(Mascota.class, mascota.getId());
            Cliente clienteOld = persistentMascota.getCliente();
            Cliente clienteNew = mascota.getCliente();
            LinkedList<Turno> turnosOld = persistentMascota.getTurnos();
            LinkedList<Turno> turnosNew = mascota.getTurnos();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                mascota.setCliente(clienteNew);
            }
            LinkedList<Turno> attachedTurnosNew = new LinkedList<Turno>();
            for (Turno turnosNewTurnoToAttach : turnosNew) {
                turnosNewTurnoToAttach = em.getReference(turnosNewTurnoToAttach.getClass(), turnosNewTurnoToAttach.getId());
                attachedTurnosNew.add(turnosNewTurnoToAttach);
            }
            turnosNew = attachedTurnosNew;
            mascota.setTurnos(turnosNew);
            mascota = em.merge(mascota);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getMascotas().remove(mascota);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getMascotas().add(mascota);
                clienteNew = em.merge(clienteNew);
            }
            for (Turno turnosOldTurno : turnosOld) {
                if (!turnosNew.contains(turnosOldTurno)) {
                    turnosOldTurno.setMascota(null);
                    turnosOldTurno = em.merge(turnosOldTurno);
                }
            }
            for (Turno turnosNewTurno : turnosNew) {
                if (!turnosOld.contains(turnosNewTurno)) {
                    Mascota oldMascotaOfTurnosNewTurno = turnosNewTurno.getMascota();
                    turnosNewTurno.setMascota(mascota);
                    turnosNewTurno = em.merge(turnosNewTurno);
                    if (oldMascotaOfTurnosNewTurno != null && !oldMascotaOfTurnosNewTurno.equals(mascota)) {
                        oldMascotaOfTurnosNewTurno.getTurnos().remove(turnosNewTurno);
                        oldMascotaOfTurnosNewTurno = em.merge(oldMascotaOfTurnosNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = mascota.getId();
                if (findMascota(id) == null) {
                    throw new NonexistentEntityException("The mascota with id " + id + " no longer exists.");
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
            Mascota mascota;
            try {
                mascota = em.getReference(Mascota.class, id);
                mascota.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mascota with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = mascota.getCliente();
            if (cliente != null) {
                cliente.getMascotas().remove(mascota);
                cliente = em.merge(cliente);
            }
            LinkedList<Turno> turnos = mascota.getTurnos();
            for (Turno turnosTurno : turnos) {
                turnosTurno.setMascota(null);
                turnosTurno = em.merge(turnosTurno);
            }
            em.remove(mascota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mascota> findMascotaEntities() {
        return findMascotaEntities(true, -1, -1);
    }

    public List<Mascota> findMascotaEntities(int maxResults, int firstResult) {
        return findMascotaEntities(false, maxResults, firstResult);
    }

    private List<Mascota> findMascotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mascota.class));
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

    public Mascota findMascota(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mascota.class, id);
        } finally {
            em.close();
        }
    }

    public int getMascotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mascota> rt = cq.from(Mascota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Mascota> findMascotasByClienteId(int clienteId) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Mascota> cq = cb.createQuery(Mascota.class);
            Root<Mascota> mascotaRoot = cq.from(Mascota.class);
            cq.select(mascotaRoot).where(cb.equal(mascotaRoot.get("cliente").get("id"), clienteId));
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }
    
    public Mascota findMascotaByNameAndClienteId(String nombreMascota, int clienteId) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Mascota> cq = cb.createQuery(Mascota.class);
            Root<Mascota> mascotaRoot = cq.from(Mascota.class);
            Predicate nombrePredicate = cb.equal(mascotaRoot.get("nombre"), nombreMascota);
            Predicate clientePredicate = cb.equal(mascotaRoot.get("cliente").get("id"), clienteId);
            cq.select(mascotaRoot).where(cb.and(nombrePredicate, clientePredicate));

            // Usar getSingleResult() para devolver una sola mascota
            return em.createQuery(cq).getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; // No se encontró ninguna mascota con ese nombre para el cliente especificado
        } finally {
            em.close();
        }
    }

    public List<Mascota> getMascotasByBuscado(String buscado) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Mascota> cq = cb.createQuery(Mascota.class);
            Root<Mascota> mascota = cq.from(Mascota.class);

            if (buscado != null && !buscado.trim().isEmpty()) {
                String searchPattern = "%" + buscado.toLowerCase() + "%";
                Predicate nombrePredicate = cb.like(cb.lower(mascota.get("nombre")), searchPattern);
                Predicate colorPredicate = cb.like(cb.lower(mascota.get("color")), searchPattern);
                Predicate razaPredicate = cb.like(cb.lower(mascota.get("raza")), searchPattern);
                Predicate sexoPredicate = cb.like(cb.lower(mascota.get("sexo")), searchPattern);
                Predicate fechaNacimientoPredicate = cb.like(cb.lower(mascota.get("fechaNacimiento").as(String.class)), searchPattern);
                Predicate clienteNombrePredicate = cb.like(cb.lower(mascota.get("cliente").get("nombre")), searchPattern);

                cq.select(mascota).where(cb.or(nombrePredicate, colorPredicate, razaPredicate, sexoPredicate, fechaNacimientoPredicate, clienteNombrePredicate));
            } else {
                cq.select(mascota);
            }

            Query query = em.createQuery(cq);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
}
