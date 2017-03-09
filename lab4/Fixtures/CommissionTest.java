import fit.ColumnFixture;

import com.shevtsod.Commission;

/**
* @author Daniel Shevtsov (SID: 200351253)
*/
public class CommissionTest extends ColumnFixture {
	
	// These values are constant in the specification
	private static final double lockPrice = 	45;
	private static final double stockPrice = 	30;
	private static final double barrelPrice = 	25;
	
	public int locks, stocks, barrels;
	
	/**
	 * FIT test for Commission method calculateSales()
	 */
	public double calculateSalesTest() {
		Commission commission = new Commission(lockPrice, stockPrice, barrelPrice);
		commission.processNewSale(locks, stocks, barrels);
		return commission.calculateSales();
	}
	
	/**
	 * FIT test for Commission method calculateCommission()
	 */
	public double calculateCommissionTest() {
		Commission commission = new Commission(lockPrice, stockPrice, barrelPrice);
		commission.processNewSale(locks, stocks, barrels);
		commission.calculateSales();
		return commission.calculateCommission();
	}

}
