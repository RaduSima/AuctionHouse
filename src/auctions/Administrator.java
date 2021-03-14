package auctions;

import commands.*;
import exceptions.CommandNotFoundException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represents the administrator of the website.
 * Handles the commands.
 */
public class Administrator implements Runnable {

    /**
     * Input scanner for commands.
     */
    private final Scanner inputScanner;

    /**
     * Map for all commands.
     */
    private HashMap<String, Command> commands;

    /**
     * One argument constructor.
     *
     * @param inputStream The input source.
     */
    public Administrator(InputStream inputStream) {
        this.inputScanner = new Scanner(inputStream);
        initCommands();
    }

    /**
     * Parses command strings and executes them.
     */
    public void executeCommands() {
        boolean runningFlag = true;
        while (runningFlag) {

            //Parses the input command string, in parameters and actual command
            String[] commandString = inputScanner.nextLine().split(" ", 2);
            String command = commandString[0];
            String parameters = null;

            //If the command has parameters, they get parsed
            if (commandString.length >= 2) {
                parameters = commandString[1];
            }

            //Special case, for the "done" command
            if (command.equals("done")) {
                runningFlag = false;
            }

            //For all the other commands
            else if (commands.containsKey(command)) {
                commands.get(command)
                        .execute(AuctionHouse.getInstance(), parameters);
            }

            //No valid command found case
            else {
                try {
                    throw new CommandNotFoundException(
                            "Command " + command + " " + "is not valid.");
                } catch (CommandNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        inputScanner.close();
    }

    /**
     * Initialises the commands map.
     */
    private void initCommands() {
        commands = new HashMap<>();
        commands.put("loadproducts", new LoadProducts());
        commands.put("logclients", new LogClients());
        commands.put("addproduct", new AddProduct());
        commands.put("clientrequest", new ClientRequest());
        commands.put("showsold", new ShowSold());
        commands.put("showearnings", new ShowEarnings());
        commands.put("showproducts", new ShowProducts());
        commands.put("showclients", new ShowClients());
    }

    /**
     * Method override from the Runnable interface.
     */
    @Override
    public void run() {
        executeCommands();
    }
}
