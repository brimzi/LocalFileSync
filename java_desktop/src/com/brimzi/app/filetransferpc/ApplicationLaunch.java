package com.brimzi.app.filetransferpc;

public class ApplicationLaunch implements AppStateProvider {

	public AppState appState;

	public ApplicationLaunch() {
		// initialise
		appState = AppState.APP_STATE_RUNNING;
	}

	public static void main(String args[]) {

		ApplicationLaunch app = new ApplicationLaunch();
		try {
			app.run();

		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private void run() throws InterruptedException {
		// start threads
		OnBoardingThread onboard=new OnBoardingThread(this);
		onboard.start();
		
		Server server=new Server(this);
		server.start();
		
		
		// wait
		synchronized (this) {
			while (appState != AppState.APP_STATE_SHUTDOWN) {
				wait();
			}
		}
		
		
		// cleanup

	}

	synchronized void shudDown() {
		appState = AppState.APP_STATE_SHUTDOWN;
	}

	@Override
	public synchronized AppState getApplicationState() {
		return appState;
	}

}
