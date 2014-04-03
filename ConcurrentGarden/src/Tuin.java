// S.Verbeek 23-2-2001

// De Tuin klasse bevat alle elementen die rechtstreeks met de tuin
// hebben te maken (lokaliteitseis). Een aantal methoden is gesynchroniseerd
// omdat ze vanuit diverse threads aangeroepen worden.
// Voor het reserveren is een aparte BusyFlag gebruikt. Dit is nodig om
// een grotere lock scope te verkrijgen zonder dat andere threads geblokkeerd
// raken. Zie soortgelijk voorbeeld in Oaks (p62).

public class Tuin
{
    static int MAX_AANTAL_BEZOEKERS = 100;
    static Integer aantalBezoekers = 0;
    
  // Attributen
  // ...
    
    

  public Tuin()
  {
    // ...
  }
  
  // Methoden (zie klassediagram in lesmateriaal)
  // ...
}