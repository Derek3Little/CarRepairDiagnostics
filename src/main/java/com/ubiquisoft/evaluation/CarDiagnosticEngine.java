package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {
		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 *
		 *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 *
		 *      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 *
		 *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 *
		 * Important:
		 *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */

			// First

		// validating car data fields present
		if (car.getYear() == null || car.getMake() == null || car.getModel() == null) {

			if (car.getYear() == null) {
				System.out.println("Missing Vehicle Data Field Detected: YEAR");
			}
			if (car.getMake() == null) {
				System.out.println("Missing Vehicle Data Field Detected: MAKE");
			}
			if (car.getModel() == null) {
				System.out.println("Missing Vehicle Data Field Detected: MODEL");
			}

			// ending diagnostics early due to missing data field(s)
			return;
		}

			// Second

		// missing parts check
		Map<PartType, Integer> missingPartsMap = car.getMissingPartsMap();

		// if empty hashmap with no missing parts, bypass
		if (missingPartsMap.size() > 0) {
			PartType[] partsArray = missingPartsMap.keySet().toArray(new PartType[0]);

			for (int i = 0; i < partsArray.length; i++) {
				printMissingPart(partsArray[i], missingPartsMap.get(partsArray[i]));
			}

			System.exit(0); // ending diag early
		}

			// Third

		// working parts check
		Part[] partsArray = car.getParts().toArray(new Part[0]);
		for (int i = 0; i < partsArray.length; i++) {

			if (!partsArray[i].isInWorkingCondition()) {
				printDamagedPart(partsArray[i].getType(), partsArray[i].getCondition());
				exitFlag = true;
			}
		}

		if (exitFlag) {
			System.exit(0); // ending diag early
		}

			// Fourth

		System.out.println("Your mint condition " + car.getYear() + " " + car.getMake() + " " + car.getModel() +
				" is in perfect working order!");
	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
