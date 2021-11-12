package metier.graphs.coupling.distanceTemplate;

public abstract class TemplateDistance {


    public double calculateDistance(String source, String destination) {

        double expectedDistance = 0.0 ;
        try {
            double distance1 = getFirstValueOfCoupling(source, destination);
            double distance2 = getSecondValueOfCoupling(destination, source);

            expectedDistance = 1 - calculateDistanceVariant(distance1, distance2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return expectedDistance;
    }

    protected abstract double getFirstValueOfCoupling(String source, String destination);

    protected abstract double getSecondValueOfCoupling(String destination, String source);

    protected abstract double calculateDistanceVariant(double distance1, double distance2);

}
