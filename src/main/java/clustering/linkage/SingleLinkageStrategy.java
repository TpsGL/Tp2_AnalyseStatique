package clustering.linkage;

import clustering.models.Proximity;

import java.util.Collection;

public class SingleLinkageStrategy implements LinkageStrategy{


    @Override
    public Proximity calculateProximity(Collection<Proximity> proximities) {
        double min = Double.NaN;

        for (Proximity dist: proximities) {
            if (Double.isNaN(min) || dist.getProximity() < min) {
                min = dist.getProximity();
            }
        }

        return new Proximity(min);
    }
}
