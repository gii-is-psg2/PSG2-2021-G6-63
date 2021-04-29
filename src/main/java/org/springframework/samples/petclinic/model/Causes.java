package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Causes {
	
	private List<Cause> causes;
	
	@XmlElement
	public List<Cause> getCauseList(){
		if(causes == null) {
			causes = new ArrayList<>();
		}
		return causes;
	}
}
