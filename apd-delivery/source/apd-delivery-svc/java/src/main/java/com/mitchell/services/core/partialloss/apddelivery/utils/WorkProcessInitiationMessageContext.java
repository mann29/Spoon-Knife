package com.mitchell.services.core.partialloss.apddelivery.utils; 

import com.mitchell.schemas.ActivityStatusType;

/**
 * This context holds all fields required for creating a WorkProcessUpdateMessage.
 * 
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 */
public class WorkProcessInitiationMessageContext { 
    /**
     * Activity operation.
     */
    private long workProcessCollaborator = 0;
    /**
     * Collaborator.
     */
    private String collaborator = null;
    /**
     * Private index.
     */
    private String privateIndex = null;
    /**
     * Public index.
     */
    private String publicIndex = null;
    /**
     * Activity status type.
     */
    private ActivityStatusType.Enum activityStatusType = null;
    /**
     * Get activity operation.
     * @return String
     */
    public long getWorkProcessCollaborator() {
        return workProcessCollaborator;
    }
    /**
     * Set activity operation.
     * @param activityOperationArg
     * activity operation
     */
    public void setWorkProcessCollaborator(long activityOperationArg) {
        this.workProcessCollaborator = workProcessCollaborator;
    }
    /**
     * Get Collaborator.
     * @return String
     */
    public String getCollaborator() {
        return collaborator;
    }
    /**
     * Set Collaborator.
     * @param collaboratorArg
     * Collaborator
     */
    public void setCollaborator(String collaboratorArg) {
        this.collaborator = collaboratorArg;
    }
    /**
     * Get private index.
     * @return String
     */
    public String getPrivateIndex() {
        return privateIndex;
    }
    /**
     * Set private index.
     * @param privateIndexArg
     * private index
     */
    public void setPrivateIndex(String privateIndexArg) {
        this.privateIndex = privateIndexArg;
    }
    /**
     * Get public index.
     * @return String
     */
    public String getPublicIndex() {
        return publicIndex;
    }
    /**
     * Set public index.
     * @param publicIndexArg
     * public index
     */
    public void setPublicIndex(String publicIndexArg) {
        this.publicIndex = publicIndexArg;
    }
    /**
     * Get activity status type.
     * @return ActivityStatusType.Enum
     */
    public ActivityStatusType.Enum getActivityStatusType() {
        return activityStatusType;
    }
    /**
     * Set activity status type.
     * @param activityStatusTypeArg
     * activity status type
     */
    public void setActivityStatusType(ActivityStatusType.Enum activityStatusTypeArg) {
        this.activityStatusType = activityStatusTypeArg;
    }
} 
