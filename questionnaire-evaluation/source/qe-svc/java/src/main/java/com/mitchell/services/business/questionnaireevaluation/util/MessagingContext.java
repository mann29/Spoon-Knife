package com.mitchell.services.business.questionnaireevaluation.util;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;

public class MessagingContext { 
    /**
     * Event ID the message is published for.
     */
    private int eventId = 0;
    /**
     * Mitchell envelope.
     */
    private MitchellEnvelopeDocument meDoc = null;
    /**
     * User info for tracking.
     */
    private UserInfoDocument userInfoDoc = null;
    /**
     * Work item id.
     */
    private String workItemId = null;
    /**
     * Comment text.
     */
    private String comment = null;
    
    /**
     * Constructor.
     * @param eventIdArg
     * Event ID the message is published for
     * @param meDocArg
     * Mitchell Envelope
     * @param userInfoDocArg
     * user info for tracking
     * @param workItemIdArg
     * work item id
     * @param commentArg
     * comment text
     */
    public MessagingContext(int eventIdArg,
                            MitchellEnvelopeDocument meDocArg,
                            UserInfoDocument userInfoDocArg,
                            String workItemIdArg,
                            String commentArg) {
        this.eventId = eventIdArg;
        this.meDoc = meDocArg;
        this.userInfoDoc = userInfoDocArg;
        this.workItemId = workItemIdArg;
        this.comment = commentArg;
    }
    
    /**
     * Get Event ID the message is published for.
     * @return int
     */
    public int getEventId() {
        return eventId;
    }
    /**
     * Set Event ID the message is published for.
     * @param eventIdArg
     * Event ID
     */
    public void setEventId(int eventIdArg) {
        this.eventId = eventIdArg;
    }
    /**
     * Get Mitchell envelope.
     * @return MitchellEnvelopeDocument
     */
    public MitchellEnvelopeDocument getMitchellEnvelopeDocument() {
        return meDoc;
    }
    /**
     * Set Mitchell envelope.
     * @param meDocArg
     * Mitchell envelope
     */
    public void setMitchellEnvelopeDocument(MitchellEnvelopeDocument meDocArg) {
        this.meDoc = meDocArg;
    }
    /**
     * Get User info for tracking.
     * @return UserInfoDocument
     */
    public UserInfoDocument getUserInfoDoc() {
        return userInfoDoc;
    }
    /**
     * Set User info for tracking.
     * @param userInfoDocArg
     * User info
     */
    public void setUserInfoDoc(UserInfoDocument userInfoDocArg) {
        this.userInfoDoc = userInfoDocArg;
    }
    /**
     * Get work item id.
     * @return String
     */
    public String getWorkItemId() {
        return workItemId;
    }
    /**
     * Set work item id.
     * @param workItemIdArg
     * Work item id
     */
    public void setWorkItemId(String workItemIdArg) {
        this.workItemId = workItemIdArg;
    }
    /**
     * Get comment text.
     * @return String
     */
    public String getComment() {
        return comment;
    }
    /**
     * Set comment text.
     * @param commentArg
     * Comment text
     */
    public void setComment(String commentArg) {
        this.comment = commentArg;
    }
} 
