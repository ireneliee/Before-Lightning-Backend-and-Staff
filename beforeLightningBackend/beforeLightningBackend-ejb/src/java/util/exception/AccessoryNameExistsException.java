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
public class AccessoryNameExistsException extends Exception {

	/**
	 * Creates a new instance of <code>AccessoryNameExistsException</code>
	 * without detail message.
	 */
	public AccessoryNameExistsException() {
	}

	/**
	 * Constructs an instance of <code>AccessoryNameExistsException</code> with
	 * the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public AccessoryNameExistsException(String msg) {
		super(msg);
	}
}
