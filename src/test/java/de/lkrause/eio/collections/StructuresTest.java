package de.lkrause.eio.collections;

import static org.junit.Assert.*;

import org.junit.Test;

import de.lkrause.eio.model.Structure;

public class StructuresTest {

	@Test
	public void athanorReprocessingNullsec() {
		
		SecStatus.setSecstatus(SecStatus.NULLSEC);
		Structure lAthanor = Structures.getInstance().getStructure("Athanor");
		lAthanor.setRefineryRig(Structure.TECH2);
		
		assertEquals(0.605472, lAthanor.getReprocesing(), 0.000001);
		lAthanor.setRefineryRig(Structure.TECH1);
		assertEquals(0.582624, lAthanor.getReprocesing(), 0.000001);

	}

}
