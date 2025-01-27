package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */

			// Second

		// hashmap for return, populated with all necessary parts
		HashMap<PartType, Integer> hashMap = new HashMap<>(5);
		hashMap.put(PartType.ENGINE, 1);
		hashMap.put(PartType.ELECTRICAL, 1);
		hashMap.put(PartType.FUEL_FILTER, 1);
		hashMap.put(PartType.OIL_FILTER, 1);
		hashMap.put(PartType.TIRE, 4);

		// remove/decrement parts as they are validated
		for (int i = 0; i < parts.size(); i++) {
			switch (parts.get(i).getType()) {
				case ENGINE:
					hashMap.remove(PartType.ENGINE);
					break;
				case ELECTRICAL:
					hashMap.remove(PartType.ELECTRICAL);
					break;
				case FUEL_FILTER:
					hashMap.remove(PartType.FUEL_FILTER);
					break;
				case OIL_FILTER:
					hashMap.remove(PartType.OIL_FILTER);
					break;
				case TIRE:
					int count = hashMap.get(PartType.TIRE);
					count--;
					if (count == 0) {
						hashMap.remove(PartType.TIRE);
					} else {
						hashMap.put(PartType.TIRE, count);
					}
					break;
			}
		}

		// return hashmap containing only missing parts and counts, or empty hashmap
		return hashMap;
	}

	// adding method for 'third' section
	// collecting damaged parts in manner similar to getMissingPartsMap()
	public List<Part> getDamagedPartsList() {
		List<Part> partsList = getParts();
		partsList.removeIf(Part::isInWorkingCondition);
		return partsList;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
