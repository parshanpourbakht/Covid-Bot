import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;

import CovidParser.CovidParser;

public class CovidBot extends ListenerAdapter {
    private final String HELP_STRING = "'!name {countryName} {stat}'\n" +
            "To get avaliable countries do '!name countries' " +
            "\n for the  avaliable stats do '!name getStats ' \n";
    private final String covCommand = "cov";
    private String prefix = "!";
    private CovidParser covidParser = new CovidParser();

    public static void main(String[] args) throws LoginException, IOException {
//    CovidParser.start();
        String token = "NzIwODU5MDg2MTY1Mzc3MTE1.XuMHEw.SWZ_Q4BSIGsrqSRs8_NKq-L7H5A";
        JDA jda = new JDABuilder(token).addEventListeners(new CovidBot()).setActivity(Activity.watching("out for Covid-19"))
                .build();
    }

    private String mapToString(HashMap<String, String> map) {
        String output = "";
        for (String key : map.keySet()) {
            output += key + " : " + map.get(key) + "\n";
        }
        return output;
    }

    private String getCountryToString(String countryName) {
        HashMap<String, String> data = covidParser.getCountry(countryName);
        return mapToString(data);
    }

    private String cStatsCommand(String[] args) {
        String output = null;
        boolean getWorldStats = args.length == 1;
        boolean singleArgument = args.length == 2;
        boolean twoArguments = args.length == 3;

        if (getWorldStats) {
            output = mapToString(covidParser.worldStats());
        } else if (twoArguments) {
            System.out.println("country = " + args[1] + " stat = " + args[2]);
        } else if (singleArgument && args[1].equalsIgnoreCase("help")) {
            output = HELP_STRING;
        } else if (singleArgument) {
            String countryName = args[1];
            output = getCountryToString(countryName);
        }
        return output;
    }

    private String processCommand(String command) {
        String[] args = command.split(" ");
        command = args[0];
        System.out.println("Command " + command);
        String output = "";
        command = (command.substring(0, 0) + command.substring(1));
        System.out.println("This is the command" + command);
        switch (command) {
            case covCommand:
                output = cStatsCommand(args);
                break;

            default:
                output = "Not a command!";
        }
        return output;

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String msgString = msg.getContentRaw();
        String messageContent = null;

        if (msgString.startsWith(prefix)) {
            messageContent = processCommand(msgString);
            event.getChannel().sendMessage(messageContent).queue();

        }


    }
}
