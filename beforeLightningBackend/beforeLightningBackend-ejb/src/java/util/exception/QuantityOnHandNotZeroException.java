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
public class QuantityOnHandNotZeroException extends Exception {

	/**
	 * Creates a new instance of <code>QuantityOnHandNotZeroException</code>
	 * without detail message.
	 */
	public QuantityOnHandNotZeroException() {
	}

	/**
	 * Constructs an instance of <code>QuantityOnHandNotZeroException</code>
	 * with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public QuantityOnHandNotZeroException(String msg) {
		super(msg);
	}
}
