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
import logica.Mascota;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import logica.Cliente;
import logica.Turno;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Ayrto
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ClienteJpaController(){
        emf = Persistence.createEntityManagerFactory("PeluCanPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getMascotas() == null) {
            cliente.setMascotas(new LinkedList<Mascota>());
        }
        if (cliente.getTurnos() == null) {
            cliente.setTurnos(new LinkedList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LinkedList<Mascota> attachedMascotas = new LinkedList<Mascota>();
            for (Mascota mascotasMascotaToAttach : cliente.getMascotas()) {
                mascotasMascotaToAttach = em.getReference(mascotasMascotaToAttach.getClass(), mascotasMascotaToAttach.getId());
                attachedMascotas.add(mascotasMascotaToAttach);
            }
            cliente.setMascotas(attachedMascotas);
            LinkedList<Turno> attachedTurnos = new LinkedList<Turno>();
            for (Turno turnosTurnoToAttach : cliente.getTurnos()) {
                turnosTurnoToAttach = em.getReference(turnosTurnoToAttach.getClass(), turnosTurnoToAttach.getId());
                attachedTurnos.add(turnosTurnoToAttach);
            }
            cliente.setTurnos(attachedTurnos);
            em.persist(cliente);
            for (Mascota mascotasMascota : cliente.getMascotas()) {
                Cliente oldClienteOfMascotasMascota = mascotasMascota.getCliente();
                mascotasMascota.setCliente(cliente);
                mascotasMascota = em.merge(mascotasMascota);
                if (oldClienteOfMascotasMascota != null) {
                    oldClienteOfMascotasMascota.getMascotas().remove(mascotasMascota);
                    oldClienteOfMascotasMascota = em.merge(oldClienteOfMascotasMascota);
                }
            }
            for (Turno turnosTurno : cliente.getTurnos()) {
                Cliente oldClienteOfTurnosTurno = turnosTurno.getCliente();
                turnosTurno.setCliente(cliente);
                turnosTurno = em.merge(turnosTurno);
                if (oldClienteOfTurnosTurno != null) {
                    oldClienteOfTurnosTurno.getTurnos().remove(turnosTurno);
                    oldClienteOfTurnosTurno = em.merge(oldClienteOfTurnosTurno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            LinkedList<Mascota> mascotasOld = persistentCliente.getMascotas();
            LinkedList<Mascota> mascotasNew = cliente.getMascotas();
            LinkedList<Turno> turnosOld = persistentCliente.getTurnos();
            LinkedList<Turno> turnosNew = cliente.getTurnos();
            LinkedList<Mascota> attachedMascotasNew = new LinkedList<Mascota>();
            for (Mascota mascotasNewMascotaToAttach : mascotasNew) {
                mascotasNewMascotaToAttach = em.getReference(mascotasNewMascotaToAttach.getClass(), mascotasNewMascotaToAttach.getId());
                attachedMascotasNew.add(mascotasNewMascotaToAttach);
            }
            mascotasNew = attachedMascotasNew;
            cliente.setMascotas(mascotasNew);
            LinkedList<Turno> attachedTurnosNew = new LinkedList<Turno>();
            for (Turno turnosNewTurnoToAttach : turnosNew) {
                turnosNewTurnoToAttach = em.getReference(turnosNewTurnoToAttach.getClass(), turnosNewTurnoToAttach.getId());
                attachedTurnosNew.add(turnosNewTurnoToAttach);
            }
            turnosNew = attachedTurnosNew;
            cliente.setTurnos(turnosNew);
            cliente = em.merge(cliente);
            for (Mascota mascotasOldMascota : mascotasOld) {
                if (!mascotasNew.contains(mascotasOldMascota)) {
                    mascotasOldMascota.setCliente(null);
                    mascotasOldMascota = em.merge(mascotasOldMascota);
                }
            }
            for (Mascota mascotasNewMascota : mascotasNew) {
                if (!mascotasOld.contains(mascotasNewMascota)) {
                    Cliente oldClienteOfMascotasNewMascota = mascotasNewMascota.getCliente();
                    mascotasNewMascota.setCliente(cliente);
                    mascotasNewMascota = em.merge(mascotasNewMascota);
                    if (oldClienteOfMascotasNewMascota != null && !oldClienteOfMascotasNewMascota.equals(cliente)) {
                        oldClienteOfMascotasNewMascota.getMascotas().remove(mascotasNewMascota);
                        oldClienteOfMascotasNewMascota = em.merge(oldClienteOfMascotasNewMascota);
                    }
                }
            }
            for (Turno turnosOldTurno : turnosOld) {
                if (!turnosNew.contains(turnosOldTurno)) {
                    turnosOldTurno.setCliente(null);
                    turnosOldTurno = em.merge(turnosOldTurno);
                }
            }
            for (Turno turnosNewTurno : turnosNew) {
                if (!turnosOld.contains(turnosNewTurno)) {
                    Cliente oldClienteOfTurnosNewTurno = turnosNewTurno.getCliente();
                    turnosNewTurno.setCliente(cliente);
                    turnosNewTurno = em.merge(turnosNewTurno);
                    if (oldClienteOfTurnosNewTurno != null && !oldClienteOfTurnosNewTurno.equals(cliente)) {
                        oldClienteOfTurnosNewTurno.getTurnos().remove(turnosNewTurno);
                        oldClienteOfTurnosNewTurno = em.merge(oldClienteOfTurnosNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            LinkedList<Mascota> mascotas = cliente.getMascotas();
            for (Mascota mascotasMascota : mascotas) {
                em.remove(mascotasMascota);
            }
            LinkedList<Turno> turnos = cliente.getTurnos();
            for (Turno turnosTurno : turnos) {
                em.remove(turnosTurno);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Cliente> findClientesByBuscado(String buscado) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
            Root<Cliente> cliente = cq.from(Cliente.class);

            if (buscado != null && !buscado.trim().isEmpty()) {
                String searchPattern = "%" + buscado.toLowerCase() + "%";
                Predicate nombrePredicate = cb.like(cb.lower(cliente.get("nombre")), searchPattern);
                Predicate celularPredicate = cb.like(cb.lower(cliente.get("celular")), searchPattern);

                cq.select(cliente).where(cb.or(nombrePredicate, celularPredicate));
            } else {
                cq.select(cliente);
            }

            Query query = em.createQuery(cq);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findClienteByNombre(String clienteTmp) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
            Root<Cliente> cliente = cq.from(Cliente.class);

            cq.select(cliente).where(cb.equal(cliente.get("nombre"), clienteTmp));

            Query query = em.createQuery(cq);
            return (Cliente) query.getSingleResult();
        } catch (Exception e) {
            // Manejar el caso en que no se encuentre ningún resultado
            return null;
        } finally {
            em.close();
        }
    }



    
}
