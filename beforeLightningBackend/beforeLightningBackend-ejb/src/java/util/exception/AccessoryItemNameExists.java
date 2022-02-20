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
public class AccessoryItemNameExists extends Exception {

	/**
	 * Creates a new instance of <code>AccessoryItemNameExists</code> without
	 * detail message.
	 */
	public AccessoryItemNameExists() {
	}

	/**
	 * Constructs an instance of <code>AccessoryItemNameExists</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public AccessoryItemNameExists(String msg) {
		super(msg);
	}
}
