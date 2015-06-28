package brimzi.app.filetransferpc;

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
		onboard.setDaemon(true);
		onboard.start();
		
		Server server=new Server(this);
		server.setDaemon(true);
		server.start();
		
		InteractionThread interaction=new InteractionThread(this);
		interaction.start();
		
		
		// wait
		synchronized (this) {
			while (appState != AppState.APP_STATE_SHUTDOWN) {
				wait();
			}
		}
		System.out.println("Shutting down.........");
		server.interrupt();
		onboard.interrupt();
		server.join(5000);
		
		
		// cleanup
		System.out.println("Goodbye!");

	}

	@Override
	public synchronized void shudDown() {
		appState = AppState.APP_STATE_SHUTDOWN;
		notify();
	}

	@Override
	public synchronized AppState getApplicationState() {
		return appState;
	}

}
