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
public class AccessoryAlreadyExistsInPromotionException extends Exception {

	/**
	 * Creates a new instance of
	 * <code>AccessoryAlreadyExistsInPromotionException</code> without detail
	 * message.
	 */
	public AccessoryAlreadyExistsInPromotionException() {
	}

	/**
	 * Constructs an instance of
	 * <code>AccessoryAlreadyExistsInPromotionException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public AccessoryAlreadyExistsInPromotionException(String msg) {
		super(msg);
	}
}
