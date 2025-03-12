package com.guudint.clickargo.validator;

import javax.validation.groups.Default;

/**
 * All javax validation constraints have an attribute named groups. When we add
 * a constraint to an element, we can declare the name of the group to which the
 * constraint belongs. This is done by specifying the class name of the group
 * interface in the groups attributes of the constraint.
 *
 * @author
 *
 */
public class ValidationGroup {

	/**
	 * There is a Default group in javax.validation.groups.Default, which represents
	 * the default Bean Validation group. Unless a list of groups is explicitly
	 * defined:
	 *
	 */
	public interface DefaultValid extends Default { };

	public interface CreateValid { };

	public interface UpdateValid { };

	public interface GetValid { };

	public interface ListValid { };

	public interface DeleteValid { };

	public interface SubmitValid { };

	public interface VerifyValid { };

	public interface ApproveValid { };
	
	public interface PayValid { };

}
