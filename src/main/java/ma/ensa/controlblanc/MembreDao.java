package ma.ensa.controlblanc;

import java.util.Set;

public interface MembreDao {
    public boolean insere (Membre m);
    public Set<Incident> chargerListIncidents(int membreId);
}
