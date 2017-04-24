import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Validator {
	
	public static boolean validateBoolean(){
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		boolean valid = false;
		while (!valid) {
			if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
				valid = true;
			} else {
				System.out.println("Invalid Input.");
				input = in.nextLine();
			}
		}
		return input.equalsIgnoreCase("Y") ? true:false;
	}
	/**
	 * This method will force the user to enter a valid phone number in the format xxx-xxx-xxxx
	 * @return the correctly formatted phone number entered by the user
	 */
	public static String validatePhoneNumber() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter a valid phone number in the format xxx-xxx-xxxx: ");
		String input = in.nextLine();
		boolean valid = false;
		while (!valid) {
			if (input.matches("\\d{3}-\\d{3}-\\d{4}")) {
				valid = true;
			} else {
				System.out.println("Invalid Input.");
				input = in.nextLine();
			}
		}
		return input;
	}
	/**
	 * This method will force the user to enter a valid purpose
	 * @return a valid option for purpose entered by the user
	 */
	public static String validatePurpose(){
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		String purpose = in.nextLine();
		boolean valid = false;
		while (!valid){
			if (purpose.equalsIgnoreCase("Demo") || purpose.equalsIgnoreCase("Other") || purpose.equalsIgnoreCase("Planning") || purpose.equalsIgnoreCase("Team Working Session")){
				valid = true;
			} else{
				System.out.println("Invalid input. Your options are Demo, Planning, Team Working Session, or Other.");
				purpose = in.nextLine();
			}
		}
		return purpose;
	}
	/**
	 * This method forces the user to enter a date in the format yyyy-MM-dd
	 * @return a valid date in the correct format
	 */
	public static String validateDate(){
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		Date date = null;
		String inputDate = in.nextLine();
		boolean valid = false;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		while (!valid){
			try {
				date = formatter.parse(inputDate);
				valid = true;
			} catch(ParseException e){
				System.out.println("Invalid date. Please enter a valid date.");
				inputDate = in.nextLine();
			}
		}
		return inputDate;
	}
	/**
	 * This method forces the user to enter in an integer
	 * @return the integer the user entered
	 */
	public static int checkInt() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int input = 0;
		boolean valid = false;
		while (!valid) {
			if (in.hasNextInt()) {
				input = in.nextInt();
				valid = true;
			} else {
				in.next(); // clear invalid string
				System.out.println("Invalid Input.");
			}
		}
		return input;
	}
	/**
	 * This method forces the user to enter an integer in a specific range
	 * @param low the lowest number the user can enter
	 * @param high the highest number the user can enter
	 * @return a valid numberin the range entered by the user
	 */
	public static int checkIntRange(int low, int high) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int input = 0;
		boolean valid = false;
		while (!valid) {
			if (in.hasNextInt()) {
				input = in.nextInt();
				if (input <= high && input >= low) {
					valid = true;
				} else {
					System.out.println("Invalid Range.");
				}
			} else {
				in.next(); // clear invalid string
				System.out.println("Invalid Input.");
			}
		}
		return input;
	}

}
