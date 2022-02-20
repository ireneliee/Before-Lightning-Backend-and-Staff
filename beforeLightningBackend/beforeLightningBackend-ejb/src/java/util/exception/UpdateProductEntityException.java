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
public class UpdateProductEntityException extends Exception {

	/**
	 * Creates a new instance of <code>UpdateProductEntityException</code>
	 * without detail message.
	 */
	public UpdateProductEntityException() {
	}

	/**
	 * Constructs an instance of <code>UpdateProductEntityException</code> with
	 * the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public UpdateProductEntityException(String msg) {
		super(msg);
	}
}
