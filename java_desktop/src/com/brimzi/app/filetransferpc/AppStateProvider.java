package com.brimzi.app.filetransferpc;

enum AppState{
	APP_STATE_RUNNING,
	APP_STATE_SHUTDOWN
}
public interface AppStateProvider {
	 AppState getApplicationState();
}
