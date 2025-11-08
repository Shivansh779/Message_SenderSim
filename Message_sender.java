import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.*;

public class Message_sender {
    // it is just a simulation
    public static final Logger logger = Logger.getLogger(Message_sender.class.getName());
    public static final Scanner sc = new Scanner(System.in);

    // Send messages
    public static void sendMessage() {
        System.out.print("Enter Name of the person you want to send message to: ");
        String name = sc.nextLine();

        boolean contactFound = false;
        List<String> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Contact_List.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contacts.add(line);
                // Split to get just the name (before " - ")
                String[] parts = line.split("\\s-\\s");
                if (parts.length > 1 && parts[0].trim().equalsIgnoreCase(name)) {
                    contactFound = true;
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error reading contacts", e);
        }

        if (!contactFound) {
            System.out.println("Contact does not exist!");
            return;
        }

        List<String> messages = new ArrayList<>();
        boolean messagesSent = false;

        while (!messagesSent) {
            while (true) {
                System.out.print("Enter Message (type 'done' to finish): ");
                String message = sc.nextLine();
                if (message.equalsIgnoreCase("done")) break;
                messages.add(message);
            }

            if (messages.isEmpty()) {
                System.out.println("No messages entered. Please enter at least one message.");
                continue;
            }

            System.out.println("\nConfirm Messages to be sent:");
            for (String msg : messages) System.out.println("- " + msg);

            System.out.print("Confirm sending? (y/n): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("y")) {
                System.out.println("Sending messages...");
                try {Thread.sleep(2000);} catch  (InterruptedException e) {logger.log(Level.FINE, "Interrupted");}
                System.out.println("Messages sent successfully!");
                logger.log(Level.INFO, "Messages sent successfully.");
                messagesSent = true;
            } else {
                System.out.println("Messages cleared. Enter messages again.");
                messages.clear();
            }
        }
    }

    // Add contacts
    public static void addContacts() {
        List<String> contactsToAdd = new ArrayList<>();
        System.out.println("Enter contacts in format: Name - Phone Number (type 'done' when finished)");
        while (true) {
            System.out.print("Enter: ");
            String contact = sc.nextLine();
            if (contact.equalsIgnoreCase("done")) break;
            if (!contact.isBlank()) contactsToAdd.add(contact);
        }

        if (contactsToAdd.isEmpty()) {
            System.out.println("No contacts to add.");
            return;
        }

        int nextIndex = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("Contact_List.txt"))) {
            while (reader.readLine() != null) nextIndex++;
        } catch (IOException e) {
            nextIndex = 0; // file may not exist yet
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Contact_List.txt", true))) {
            for (String contact : contactsToAdd) {
                writer.write(contact + "\n");
                nextIndex++;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing contacts", e);
        }

        System.out.println("Contacts added successfully!");
    }

    // Remove contacts
    public static void removeContacts() {
        List<String> contactList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Contact_List.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) contactList.add(line);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading contacts", e);
            return;
        }

        if (contactList.isEmpty()) {
            System.out.println("No contacts to remove.");
            return;
        }

        System.out.println("Current Contacts:");

        int j = 1;
        for (String c : contactList) {
            System.out.println(j + "- " + c);
            j++;
        }
        System.out.print("Enter index of contact to remove: ");
        int index;
        try {
            index = sc.nextInt() - 1;
            sc.nextLine();
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println("Invalid input!");
            return;
        }

        if (index < 0 || index >= contactList.size()) {
            System.out.println("Invalid index!");
            return;
        }

        contactList.remove(index);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Contact_List.txt"))) {
            for (String c : contactList) {
                writer.write(c + "\n");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing contacts", e);
        }

        System.out.println("Contact removed successfully!");
    }

    public static void viewContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Contact_List.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s-\\s", 2);
                System.out.println(parts[0]);
                Thread.sleep(420);
            }
        } catch (IOException | InterruptedException e) {logger.log(Level.SEVERE, "An error Occurred", e);}
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Message Sender!");

        while (true) {
            File file = new File("Contact_List.txt");
            if (!file.exists() || file.length() == 0) {
                System.out.println("No contacts found. Please add some contacts first.");
                addContacts();
            }

            System.out.println("\nMenu:");
            System.out.println("1. Send Message");
            System.out.println("2. Add New Contact");
            System.out.println("3. Remove a Contact");
            System.out.println("4. View Contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input! Please enter a number 1-4.");
                continue;
            }

            switch (choice) {
                case 1 -> sendMessage();
                case 2 -> addContacts();
                case 3 -> removeContacts();
                case 4 -> viewContacts();
                case 5 -> {
                    System.out.println("Thank you! Goodbye.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Enter 1-4.");
            }
        }
    }
}
