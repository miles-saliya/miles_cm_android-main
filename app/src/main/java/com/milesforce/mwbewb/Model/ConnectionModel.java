package com.milesforce.mwbewb.Model;

public class ConnectionModel {
    public String connectionId;
    public String ConnectionType;

    public ConnectionModel(String connectionId, String connectionType) {
        this.connectionId = connectionId;
        ConnectionType = connectionType;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionType() {
        return ConnectionType;
    }

    public void setConnectionType(String connectionType) {
        ConnectionType = connectionType;
    }

    @Override
    public String toString() {
        return "ConnectionModel{" +
                "connectionId='" + connectionId + '\'' +
                ", ConnectionType='" + ConnectionType + '\'' +
                '}';
    }
}
