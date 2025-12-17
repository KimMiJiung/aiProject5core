package com.core.aiProject5core.entity;

public enum ApplyStatus {
	COUNSELING_HODDING("상담대기"),
	COUNSELING_PROGRESS("상담진행중"),
	COUNSELING_CENCEL("상담취소"),
	COUNSELING_COMPLETE("상담완료"),
	OREDR_COMPLETE("구매완료");

	private final String statusName;

	ApplyStatus(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}
}
