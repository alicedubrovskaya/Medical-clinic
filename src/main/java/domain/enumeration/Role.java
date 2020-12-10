package domain.enumeration;

public enum Role {
	ADMINISTRATOR("администратор"),
	DOCTOR("врач"),
	PATIENT("пациент");

	private String name;

	private Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Integer getId() {
		return ordinal();
	}

	public static Role getById(Integer id) {
		return Role.values()[id];
	}
}
