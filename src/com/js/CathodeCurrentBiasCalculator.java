package com.js;

public class CathodeCurrentBiasCalculator extends BiasCalculator {

    public CathodeCurrentBiasCalculator() {
        super(R.layout.main);
    }

    @Override
    protected void calculateResultsAndStartResultsActivity(Float maxPowerDissipation, int numberOfTubes) {
        Float plateVoltage = getEditBoxFloat(R.id.plateVoltage);
        Float screenResistor = getEditBoxFloat(R.id.screenResistor);
        Float screenDrop = getEditBoxFloat(R.id.screenDrop);
        Float cathodeResistor = getEditBoxFloat(R.id.cathodeResistor);
        Float cathodeDrop = getEditBoxFloat(R.id.cathodeDrop);

        Float cathodeCurrent = cathodeDrop / cathodeResistor;
        Float screenCurrent = screenDrop / screenResistor;
        Float plateCurrent = cathodeCurrent - screenCurrent;
        Float plateDrop = plateVoltage - cathodeDrop;

        Float idlePlateDissipation = plateDrop*plateCurrent/numberOfTubes;
        Float percentageMaxDissipation =  100.0f*idlePlateDissipation/maxPowerDissipation;

        startResultsActivity(idlePlateDissipation, percentageMaxDissipation);
    }

}
