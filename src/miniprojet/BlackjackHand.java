package miniprojet;

import java.util.Random;
import java.util.Scanner;

public class BlackjackHand {
	// attributs
	private Deck deck;
	private int balance;

	// constructeur sans parametres
	public BlackjackHand() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructeur avec parametres
	public BlackjackHand(Deck deck, int balance) {
		super();
		this.deck = deck;
		this.balance = balance;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public static void main(String[] args) {
		BlackjackHand game = new BlackjackHand();
		Hand userHand = new Hand();
		Hand dealerHand = new Hand();
		System.out.println("=====================================");
		System.out.println("Bienvenue dans le jeu");
		System.out.println("Your initial balance is : $" + game.getBalance());
		System.out.println("\nReady to play? Let's go!!!");

		int betValue = 0;
		String userInput = "";
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Combien veux tu parier?");
			System.out.println("entrer une valeur entre 1 et  " + game.getBalance());
			System.out.println("*** tape 'Quit' pour sortir du jeu.");
			userInput = sc.nextLine();
			userInput = userInput.trim().toLowerCase();
			if (!userInput.equals("quit")) {
				try {
					betValue = Integer.valueOf(userInput);
					if (betValue > game.getBalance())
						throw new Exception("Bet value can not be greater than balance.");
					else if (betValue < 1)
						throw new Exception("Bet value can not be less than 1$.");
					boolean userWins = game.play(userHand, dealerHand);
					if (userWins) {
						
						System.out.println("\t Felicitation,tu as gagné !");
					} else {
						
						System.out.println("\t Game Over, tu as perdu");
					}
					System.out.println("\t You: " + userHand.getValue());
					userHand.cards.forEach(c -> System.out
							.println("[+] " + c.getTitle() + " of " + c.getType() + " = " + c.getValue()));
					System.out.println("\t Dealer: " + dealerHand.getValue());
					dealerHand.cards.forEach(c -> System.out
							.println("[+] " + c.getTitle() + " of " + c.getType() + " = " + c.getValue()));
					game.setBalance(userWins ? game.getBalance() + betValue : game.getBalance() - betValue);
					System.out.println();
					System.out.println(" Current balance: $" + game.getBalance());
				} catch (Exception e) {
					System.out.println("Wrong input. " + e.getMessage());
					System.out.println("Please enter an integer between 1 an " + game.getBalance());
				}
			} else
				break;
		} while (game.getBalance() >= 1);
		sc.close();

		System.out.println(" Fin du jeu ");
		System.out.println("Your final balance is : $" + game.getBalance());
	}

	public boolean play(Hand userHand, Hand dealerHand) {
		// Clear all existing cards in the hand - in case it's not a fresh game
		userHand.empty();
		dealerHand.empty();
		this.deck.setCards();

		// Initial hands of 2 cards chosen randomly
		userHand.addCard(deck.hit());
		userHand.addCard(deck.hit());

		dealerHand.addCard(deck.hit());
		dealerHand.addCard(deck.hit());

		return this.verifyHands(userHand, dealerHand);
	}

	public boolean verifyHands(Hand userHand, Hand dealerHand) {
		if (dealerHand.isBlackjack() || userHand.getValue() > 21) {
			return false;
		} else if (userHand.isBlackjack() || dealerHand.getValue() > 21) {
			return true;
		} else {
			Random rand = new Random();
			System.out.println("vous avez une valeur totale de : " + userHand.getValue());
			System.out.println("Your cards are :");
			userHand.cards.forEach(c -> System.out.println(c.getTitle() + " of " + c.getType() + " = " + c.getValue()));
			Card oneCardFromDealerHand = dealerHand.cards.get(rand.nextInt(0, dealerHand.cards.size()));
			System.out.println("The dealer has one " + oneCardFromDealerHand.getTitle() + " of "
					+ oneCardFromDealerHand.getType() + " = " + oneCardFromDealerHand.getValue());
			System.out.println("What next?");
			String userChoice = "";
			Scanner scanner = new Scanner(System.in);
			do {
				System.out.println("Type 'Hit' or 'Stand':");
				userChoice = scanner.nextLine();
				userChoice = userChoice.trim().toLowerCase();
				if (!(userChoice.equals("hit") || userChoice.equals("stand")))
					System.out.println("Wrong input.");
			} while (!(userChoice.equals("hit") || userChoice.equals("stand")));
			
			if (userChoice.equals("hit")) {
				Card newCard = this.deck.hit();
				System.out.println(
						"New card: " + newCard.getTitle() + " of " + newCard.getType() + " = " + newCard.getValue());
				userHand.addCard(newCard);
				return verifyHands(userHand, dealerHand);
			} else { // Stand
				if (dealerHand.getValue() <= 16) {
					do {
						System.out.println("It's up to the dealer to hit!");
						System.out.println("...");
						dealerHand.addCard(this.deck.hit());
						System.out.println("The dealer has now a total value of " + dealerHand.getValue());
						System.out.println("His cards are :");
						dealerHand.cards.forEach(
								c -> System.out.println(c.getTitle() + " of " + c.getType() + " = " + c.getValue()));
					} while (dealerHand.getValue() <= 16);
				}
				return dealerHand.getValue() > 21 || userHand.getValue() > dealerHand.getValue();
			}
		}
	}

}
