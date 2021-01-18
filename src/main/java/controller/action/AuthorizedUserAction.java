package controller.action;

import domain.enumeration.Role;

import java.util.Arrays;

public abstract class AuthorizedUserAction extends Action {
	public AuthorizedUserAction() {
		getAllowRoles().addAll(Arrays.asList(Role.values()));
	}
}
