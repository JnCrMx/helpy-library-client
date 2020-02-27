package de.jcm.helpy.hardware;

public interface HelpyHardware
{
	void startHighlightElement(String elementId);
	void stopHighlightElement(String elementId);

	byte[] readSensorData(String sensorId);
}
