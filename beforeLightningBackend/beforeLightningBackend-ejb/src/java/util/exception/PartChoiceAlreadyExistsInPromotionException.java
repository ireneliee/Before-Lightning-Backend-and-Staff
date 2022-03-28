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
public class PartChoiceAlreadyExistsInPromotionException extends Exception {

	/**
	 * Creates a new instance of
	 * <code>PartChoiceAlreadyExistsInPromotionException</code> without detail
	 * message.
	 */
	public PartChoiceAlreadyExistsInPromotionException() {
	}

	/**
	 * Constructs an instance of
	 * <code>PartChoiceAlreadyExistsInPromotionException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public PartChoiceAlreadyExistsInPromotionException(String msg) {
		super(msg);
	}
}
