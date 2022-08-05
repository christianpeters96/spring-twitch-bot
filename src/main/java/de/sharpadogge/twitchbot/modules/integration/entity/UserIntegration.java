package de.sharpadogge.twitchbot.modules.integration.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_integration")
public class UserIntegration {

    @Id
    @Column(name = "user_id")
    public Long userId;

    @Column(name = "integration_id")
    public Long integrationId;

    @Transient
    public String provider;

    public UserIntegration() {
    }

    public UserIntegration(Long userId, Long integrationId) {
        this.userId = userId;
        this.integrationId = integrationId;
    }

    public UserIntegration(Long userId, Long integrationId, String provider) {
        this.userId = userId;
        this.integrationId = integrationId;
        this.provider = provider;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(Long integrationId) {
        this.integrationId = integrationId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
