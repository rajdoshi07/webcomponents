package org.webcomponents.membership;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemberStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8491148123034438937L;
	private static int nextOrdinal = 0;
	private final int ordinal = nextOrdinal++;
	
	public static final MemberStatus PENDING = new MemberStatus();
	public static final MemberStatus ACTIVE = new MemberStatus();
	public static final MemberStatus INACTIVE = new MemberStatus();
	public static final MemberStatus DELETE_ALL_MARK = new MemberStatus();

	private static final MemberStatus[] PRIVATE_VALUES = { PENDING, ACTIVE, INACTIVE, DELETE_ALL_MARK };
	public static final List<MemberStatus> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

	public static MemberStatus valueOf(String value) {
		int i = Integer.parseInt(value);
		if(i < 0 || i >= PRIVATE_VALUES.length) {
			return null;
		}
		return PRIVATE_VALUES[i];
	}

	public int getOrdinal() {
		return ordinal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.valueOf(getOrdinal());
	}
	
}