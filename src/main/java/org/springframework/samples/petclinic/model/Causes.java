package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Causes {
	
	private List<Cause> listCauses;
	
	@XmlElement
	public List<Cause> getCauseList(){
		if(listCauses == null) {
			listCauses = new ArrayList<>();
		}
		return listCauses;
	}
}
