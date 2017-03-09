package com.shevtsod;

/**
* @author Daniel Shevtsov (SID: 200351253)
*/
public class Commission {
	
	public static double COMMISSION_LEVEL_1 = 		1000;
	public static double COMMISSION_MAX_LEVEL = 	1800;
	public static double COMMISSION_SCALAR_1 = 		0.10;
	public static double COMMISSION_SCALAR_2 = 		0.15;
	public static double COMMISSION_SCALAR_3 = 		0.20;
	
	private double lockPrice, 
					stockPrice, 
					barrelPrice, 
					sales = 0, 
					commission = 0;
	
	private int locks = 0, 
				stocks = 0, 
				barrels = 0;
	
	/**
	 * Constructor, creates a new Commission and sets the prices of items
	 * @param lockPrice double price of a lock
	 * @param stockPrice double price of a stock
	 * @param barrelPrice double price of a barrel
	 */
	public Commission(double lockPrice, double stockPrice, double barrelPrice) {
		this.lockPrice = lockPrice;
		this.stockPrice = stockPrice;
		this.barrelPrice = barrelPrice;
	}
	
	/**
	 * Calculates the commission owed based on current sales
	 * @return double commission owed
	 */
	public double calculateCommission() {
		double tempSales = sales;		
		
		//10% on sales up to and including COMMISSION_LEVEL_1
		if(tempSales > 0 && tempSales < COMMISSION_LEVEL_1) {
			commission += tempSales * COMMISSION_SCALAR_1;
			tempSales = 0;
		} else {
			commission += COMMISSION_LEVEL_1 * COMMISSION_SCALAR_1;
			tempSales -= COMMISSION_LEVEL_1;
		}
		
		//15% between COMMISSION_LEVEL_1 AND COMMISSION_MAX_LEVEL
		if (tempSales > 0) {
			if (tempSales < (COMMISSION_MAX_LEVEL - COMMISSION_LEVEL_1)) {
				commission += tempSales * COMMISSION_SCALAR_2;
				tempSales = 0;
			} else {
				commission += (COMMISSION_MAX_LEVEL - COMMISSION_LEVEL_1) * COMMISSION_SCALAR_2;
				tempSales -= (COMMISSION_MAX_LEVEL - COMMISSION_LEVEL_1);
			}
		}
		
		//20% on any sales in excess of COMMISSION_MAX_LEVEL
		if(tempSales > 0) {
			commission += tempSales * COMMISSION_SCALAR_3;
		}
		
		return commission;
		
	}
	
	/**
	 * Calculates the sales based on the number of locks, stocks, and barrels sold
	 * @return double sales value
	 */
	public double calculateSales() {
		sales += stockPrice * stocks + lockPrice * locks + barrelPrice * barrels;
		return sales;
	}
	
	/**
	 * Keep track of the salesman's progress by adding the locks, stocks, and barrels sold
	 * @param locks integer number of locks sold
	 * @param stocks integers number of stocks sold
	 * @param barrels integer number of barrels sold
	 */
	public void processNewSale(int locks, int stocks, int barrels) {
		this.locks += locks;
		this.stocks += stocks;
		this.barrels += barrels;
	}
	
	/**
	 * Indicates that the month is done, resets sales and commissions
	 */
	public void sendTelegram() {
		sales = 0;
		commission = 0;
	}
	
}
