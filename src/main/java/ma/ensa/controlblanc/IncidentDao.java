package ma.ensa.controlblanc;

import java.util.Set;

public interface IncidentDao {
    public int inserer(Incident i);
    public void inser(Set<Incident> incidents);
}
