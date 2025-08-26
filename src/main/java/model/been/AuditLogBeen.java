package model.been;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AuditLogBeen implements Serializable {
	private int id;
	private String actor;
	private String action;
	private String entity;
	private int entityId;
	private LocalDateTime loggedAt;

	public AuditLogBeen() {
	}

	public AuditLogBeen(int id, String actor, String action, String entity, int entityId) {
		this.id = id;
		this.actor = actor;
		this.action = action;
		this.entity = entity;
		this.entityId = entityId;
	}
	
	public AuditLogBeen(String actor, String action, String entity, int entityId) {
		this.actor = actor;
		this.action = action;
		this.entity = entity;
		this.entityId = entityId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public LocalDateTime getLoggedAt() {
		return loggedAt;
	}

	public void setLoggedAt(LocalDateTime loggedAt) {
		this.loggedAt = loggedAt;
	}
}
