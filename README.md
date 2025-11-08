# Message Sender

A simple console-based messaging system that allows users to manage contacts and send messages.  
It uses text file storage, `ArrayList` for dynamic data handling, and Java's built-in `Logger` for meaningful logging.

---

## Features

- Add new contacts to a persistent contact list
- View existing contacts in a clean formatted output
- Remove contacts by selecting from displayed list
- Send one or multiple messages to stored contacts
- Contact list stored in `Contact_List.txt`

---

## How It Works

### Contact Storage Format
Each contact is stored as a line in the following format:
Name - Phone Number

### Program Flow Overview

| Operation        | Description                                                       |
|------------------|-------------------------------------------------------------------|
| Send Message     | Select a contact, compose message(s), and send                     |
| Add Contact      | Input name + number, appended directly into the contact file       |
| View Contacts    | Display all contacts in readable format                            |
| Remove Contact   | Choose an index from the list to remove a specific contact         |
| Exit             | Ends the program                                                   |

The interface loops until the user chooses to exit.

---

## What I Learned

### 1. Proper Use of Logging Levels

Instead of using `SEVERE` for everything, logging is now used based on situation:

| Level    | Use Case Example                                   |
|---------|----------------------------------------------------|
| `INFO`   | Normal operations (e.g., "Message sent successfully") |
| `WARNING`| Recoverable issues (e.g., missing file on first run)  |
| `SEVERE` | Serious errors that prevent continuation             |
| `FINE`   | Debugging small internal events (optional)           |

This makes debugging easier and logs more meaningful.

---

### 2. Using `ArrayList` Instead of Fixed Arrays

Replaced static arrays with:

```java
List<String> contacts = new ArrayList<>();

Benefits:
	•	Automatically grows as contacts are added
	•	Easier add/remove operations
	•	Works smoothly with file input
	•	Cleaner and more maintainable code

This was a major improvement over previous projects.
```
### Conclusion

This project marks progress in:
	•	Writing cleaner and more maintainable code
	•	Understanding proper logging practices
	•	Using dynamic data structures effectively

It builds a strong foundation for larger future Java programs
