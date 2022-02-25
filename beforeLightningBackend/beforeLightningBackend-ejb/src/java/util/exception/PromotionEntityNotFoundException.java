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
public class PromotionEntityNotFoundException extends Exception {

	/**
	 * Creates a new instance of <code>PromotionEntityNotFoundException</code>
	 * without detail message.
	 */
	public PromotionEntityNotFoundException() {
	}

	/**
	 * Constructs an instance of <code>PromotionEntityNotFoundException</code>
	 * with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public PromotionEntityNotFoundException(String msg) {
		super(msg);
	}
}
