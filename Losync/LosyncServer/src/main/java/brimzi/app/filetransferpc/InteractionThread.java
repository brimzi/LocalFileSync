package brimzi.app.filetransferpc;

import java.util.Scanner;

public class InteractionThread extends Thread {
	private AppStateProvider appStateProvider;

	public InteractionThread(AppStateProvider stateProvider) {
		appStateProvider = stateProvider;
		this.setName(OnBoardingThread.class.getName());
	}

	public void run() {

		consoleInteraction();

	}

	private void consoleInteraction() {
		System.out.println("Hello Welcome to the LocalFileSync!");
		Scanner input = new Scanner(System.in);
		while (appStateProvider.getApplicationState() == AppState.APP_STATE_RUNNING) {
			// interact with user
			System.out
					.println("Please choose what operations to perform or type quit to exit");
			System.out.println(" 1-Display usage \n 2-Used space");

			String userInput = input.nextLine();

			switch (userInput.toLowerCase()) {
			case "quit":
				appStateProvider.shudDown();
				return;
			case "1":
				
				break;
			}

		}
		input.close();
	}
}
