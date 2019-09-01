package de.lkrause.eio.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class SecStatusTest {
	
	@Test
	public void getSecStatusSetTest() {
		assertEquals(1, SecStatus.getSecStatusMultiplier(), 0.1);
		assertEquals(1, SecStatus.getSecStatusMultiplierReprocessing(), 0.01);
		SecStatus.setSecstatus(SecStatus.NULLSEC);
		assertEquals(2.1, SecStatus.getSecStatusMultiplier(), 0.1);
		assertEquals(1.12, SecStatus.getSecStatusMultiplierReprocessing(), 0.01);
		SecStatus.setSecstatus(SecStatus.LOWSEC);
		assertEquals(1.9, SecStatus.getSecStatusMultiplier(), 0.1);
		assertEquals(1.06, SecStatus.getSecStatusMultiplierReprocessing(), 0.01);
		SecStatus.setSecstatus(SecStatus.HIGHSEC);
		assertEquals(1, SecStatus.getSecStatusMultiplier(), 0.1);
		assertEquals(1, SecStatus.getSecStatusMultiplierReprocessing(), 0.01);
		SecStatus.setSecstatus(SecStatus.WORMHOLE);
		assertEquals(2.1, SecStatus.getSecStatusMultiplier(), 0.1);
		assertEquals(1.12, SecStatus.getSecStatusMultiplierReprocessing(), 0.01);
		
	}
	
	

}
