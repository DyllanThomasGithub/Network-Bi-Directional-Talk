import java.util.HashMap;
import java.io.*;
import java.net.*;

public class Talk {
    public static void main(String[] args){
        //parse command line arguments
        //call mode
        //done
    
        HashMap<String, String> clargs = new HashMap<>();
        for (int i = 0; i< args.length; i++) {
            if (args[i].equals("-help")) {
                clargs.put(args[i], args[i]);
            } else if (args[i].equals("-s")){
                clargs.put(args[i], args[i]);
            } else if (args[i].equals("-p")) {
                if (i+1 >= args.length) {
                System.err.println("ERROR: -p should have a portnumber");
                System.exit(1);
                }
                if (args[i+1].contains("-")) {
                System.err.println("ERROR: -p should have a valid portnumber");
                System.exit(1);
                }
                clargs.put(args[i], args[i+1]);
                i++;
            } else if (args[i].equals("-h")){
                if (i+1 < args.length && !args[i+1].contains("-")){
                clargs.put(args[i], args[i+1]);
                } else {
                clargs.put(args[i], args[i+1]);
                }
            } else if (args[i].equals("-a")){
                if (i+1 < args.length && !args[i+1].contains("-")){
                clargs.put(args[i], args[i+1]);
                } else {
                clargs.put(args[i], args[i]);
                }
            }
             
        }
        
        if (clargs.containsKey("-a")){ //checking for -a
            int port = 12987; //default portnumber
            if (clargs.containsKey("-p")) { //optional portnumber
                try {
                  port = Integer.parseInt(clargs.get("-p"));
                } 
                catch (Exception e) {               
                }
            }
            String hostname = "linux1.ens.utulsa.edu";
            if (clargs.containsKey("-a")){
                try{
                    hostname = clargs.get("-a");
                }
                catch(Exception e) {

                }
            }
            autoMode(hostname, port);
        }
        
        else if (clargs.containsKey("-h")){ //checking for -h
            int port = 12987; //default portnumber
            if (clargs.containsKey("-p")) { //optional portnumber
                try {
                  port = Integer.parseInt(clargs.get("-p"));
                } 
                catch (Exception e) {               
                }
            }
            String hostname = "linux1.ens.utulsa.edu"; //default given by program
            if (clargs.containsKey("-h")){
                try{
                    hostname = clargs.get("-h");
                }
                catch(Exception e) {

                }
            }
            clientMode(hostname, port);
        }

        else if (clargs.containsKey("-help")){ //checking for -help
            helpMode();
        }

        else if (clargs.containsKey("-s")){ //checking for -s
            int port = 12987; //default portnumber
            if (clargs.containsKey("-p")) { //optional portnumber
                try {
                  port = Integer.parseInt(clargs.get("-p"));
                } 
                catch (Exception e) {               
                }
              }
            serverMode(port);
        } 
        else {
            System.out.println("ERROR: No arguments submitted");
        }
    }

    private static void autoMode(String serverName, int serverPortNumber) { //-a auto mode
        //setup client connection
        //if client conenction fails, setup server
        //done
        if (hostAvailabilityCheck(serverName, serverPortNumber)){
            clientMode(serverName, serverPortNumber);
        }
        else {
            System.out.println("Client Connection Failed");
            serverMode(serverPortNumber);
        }
    }

    private static void clientMode(String serverName, int serverPortNumber) { //-h for client mode
        //setup client connection
        //done
        TalkClient.StartClientConnection(serverName, serverPortNumber);
    }

    private static void helpMode(){//-help help mode
        //print help info
        //done
        System.out.println("Dyllan Thomas" 
        +  "\n-a accepts [hostname|IPaddress] [-p portnumber] and runs autoMode" 
        +  "\n-h accepts [hostname | IPaddress] [-p portnumber] and runs clientMode"
        +  "\n-s accepts [-p portnumber] and runs serverMode"
        +  "\n-help functions as a help mode");
    }

    private static void serverMode(int serverPortNumber) {//-s server mode
        //start server
        //done
        TalkServer.StartServerConnection(serverPortNumber);

    }

    public static boolean hostAvailabilityCheck(String serverName, int serverPortNumber) { 
        try (Socket s = new Socket(serverName, serverPortNumber)) {
            return true;
        } catch (IOException ex) {
        }
        return false;
    }
}
