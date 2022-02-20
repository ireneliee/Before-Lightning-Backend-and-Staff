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
public class UpdateAccessoryItemEntityException extends Exception {

	/**
	 * Creates a new instance of <code>UpdateAccessoryItemEntityException</code>
	 * without detail message.
	 */
	public UpdateAccessoryItemEntityException() {
	}

	/**
	 * Constructs an instance of <code>UpdateAccessoryItemEntityException</code>
	 * with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public UpdateAccessoryItemEntityException(String msg) {
		super(msg);
	}
}
