/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author kaiyu
 */
public class PromotionDiscountTypeExclusiveOrException extends Exception {

	/**
	 * Creates a new instance of
	 * <code>PromotionDiscountTypeExclusiveOrException</code> without detail
	 * message.
	 */
	public PromotionDiscountTypeExclusiveOrException() {
	}

	/**
	 * Constructs an instance of
	 * <code>PromotionDiscountTypeExclusiveOrException</code> with the specified
	 * detail message.
	 *
	 * @param msg the detail message.
	 */
	public PromotionDiscountTypeExclusiveOrException(String msg) {
		super(msg);
	}
}
