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
public class PromotionAlreadyExistsInPartChoiceException extends Exception {

	/**
	 * Creates a new instance of <code>PromotionAlreadyExistsInPartChoice</code>
	 * without detail message.
	 */
	public PromotionAlreadyExistsInPartChoiceException() {
	}

	/**
	 * Constructs an instance of <code>PromotionAlreadyExistsInPartChoice</code>
	 * with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public PromotionAlreadyExistsInPartChoiceException(String msg) {
		super(msg);
	}
}
