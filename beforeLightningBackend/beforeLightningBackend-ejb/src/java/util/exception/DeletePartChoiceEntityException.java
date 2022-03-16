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
public class DeletePartChoiceEntityException extends Exception {

	/**
	 * Creates a new instance of <code>AccessoryNameExistsException</code>
	 * without detail message.
	 */
	public DeletePartChoiceEntityException() {
	}

	/**
	 * Constructs an instance of <code>AccessoryNameExistsException</code> with
	 * the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public DeletePartChoiceEntityException(String msg) {
		super(msg);
	}
}
