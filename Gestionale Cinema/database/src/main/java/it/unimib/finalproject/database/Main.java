package it.unimib.finalproject.database;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;

/**
 * Classe principale in cui parte il database.
 */
public class Main {
    /**
     * Porta di ascolto.
     */
    public static final int PORT = 3030;
    
    private static Database database = new Database();

    /**
     * Avvia il database e l'ascolto di nuove connessioni.
     *
     * @return Un server HTTP Grizzly.
     */
    public static void startServer() {
        try {
			var server = new ServerSocket(PORT);

            System.out.println("Database listening at localhost:" + PORT);
            while (true)
                new Handler(server.accept(), database).start();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Handler di una connessione del client.
     */
    private static class Handler extends Thread {
        private Socket client;
        private Database database;

        public Handler(Socket client, Database database) {
            this.client = client;
            this.database = database;
        }

        public void run() {
            try {
                var out = new PrintWriter(client.getOutputStream(), true);
                var in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String inputLine;
                String result = null;

                while ((inputLine = in.readLine()) != null) {
                    if ("Disconnect".equalsIgnoreCase(inputLine)) {
                        out.println("[true, \"Bye\"]");
                        break;
                    }
                    
                    try {
                    	result = executeCommand(inputLine);
                    } catch (WrongInputException e) {
                    	out.println("[false, \"" + e.getMessage() + "\"]");
                    	break;
                    }
                    
                    out.println(result);
                }

                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
            	System.err.println(e.getMessage());
                System.err.println(e);
            }
        }
        
        /**
         * Method for executing the requested command.
         *
         * @param input input of the command.
         *
         * @return answer message.
         */
        private String executeCommand(String input) {
        	Pattern pattern = Pattern.compile("\\A[a-z]+", Pattern.CASE_INSENSITIVE);
        	input = input.strip();
        	Matcher matcher = pattern.matcher(input);
        	matcher.find();
        	
        	String command = matcher.group().toLowerCase();
        	String args = input.substring(command.length()).strip();
        	String message = "[true]";
        	
        	switch(command) {
	        	/**
	        	 * Methods for Strings
	        	 */
        	
        		case "set": {
        			var result = parse(args, 2);
        			String key = result[0];
        			String value = result[1];
        			
        			database.set(key, value);
        			
        			break;
        		}
        		
        		case "mset": {
        			var list = parseMultiple(args);
        			
        			if (list.size() % 2 == 0)
        				database.mSet(list);
        			else
        				message = "[false, \"Wrong Number of Input\"]";
        			
        			break;
        		}
        		
        		case "setif": {        			
        			var result = parse(args, 3);
        			String key = result[0];
        			String value = result[1];
        			String oldValue = result[2];
        			
        			if (!database.setIf(key, value, oldValue))
        				message = "[false, \"Different Value\"]";
        			break;
        		}
        		
        		case "msetif": {        			
        			var list = parseMultiple(args);
        			
        			if (list.size() % 3 == 0)
            			if (!database.mSetIf(list))
            				message = "[false, \"Different Value\"]";
        			else
        				message = "[false, \"Wrong Number of Input\"]";
        			
        			break;
        		}
        		
        		case "get": {
        			String key = parse(args, 1)[0];

        			String result = database.get(key);
    				message = "[true,{" + result + "}]";
        			break;
        		}
        		
        		case "mget": {
        			var keys = parseMultiple(args);

        			message = "[true," + database.mGet(keys) + "]";
        			break;
        		}
        		
        		case "delete": {       			
        			String key = parse(args, 1)[0];
        			
        			if (!database.delete(key))
        				message = "[false, \"Key Not Found\"";
        			
        			break;
        		}
        		
        		/**
        		 * Methods for Lists
        		 */
        		case "add": {       			
        			var result = parse(args, 2);
        			String key = result[0];
        			String value = result[1];
        			
        			database.add(key, value);
        			break;
        		}
        		
        		case "madd": {       			
        			var list = parseMultiple(args);
        			
        			if (list.size() > 1)
        				database.mAdd(list);
        			else
        				message = "[false, \"Wrong Input Number\"]";
        			break;
        		}
        		
        		case "remove": {        			
        			String key = parse(args, 1)[0];
        			
        			if (!database.remove(key))
        				message = "[false, \"Key not Found\"";
        			break;
        		}
        		
        		case "setl": {
        			try {
	        			var result = parse(args, 3);
	        			String key = result[0];
	        			String index = result[1];
	        			String value = result[2];
	        			
	        			database.setL(key, index, value);
        			} catch (IndexOutOfBoundsException e) {
        				message = "[false, \"Index Out of Bounds\"";
        			} catch (NumberFormatException e) {
        				message = "[false, \"Wrong Input Index\"";
        			}
        			break;
        		}
        		
        		case "msetl": {
        			try {
            			var list = parseMultiple(args);
            			
            			if (list.size() % 3 == 0)
            				database.mSetL(list);
            			else
            				message = "[false, \"Wrong Number of Input\"]";
        			} catch (IndexOutOfBoundsException e) {
        				message = "[false, \"Index Out of Bounds\"";
        			} catch (NumberFormatException e) {
        				message = "[false, \"Wrong Input Index\"";
        			}
        			break;
        		}
        		
        		case "setifl": {
        			try {
	        			var result = parse(args, 4);
	        			String key = result[0];
	        			String index = result[1];
	        			String value = result[2];
	        			String Oldvalue = result[3];
	        			
            			if(!database.setIfL(key, index, value, Oldvalue))
            				message = "[false, \"Different Value\"";
        			} catch (IndexOutOfBoundsException e) {
        				message = "[false, \"Index Out Of Bounds\"]";
        			} catch (NumberFormatException e) {
        				message = "[false, \"Wrong Input Index\"";
        			}
        			break;
        		}
        		
        		case "msetifl": {
        			try {
            			var list = parseMultiple(args);
            			
            			if (list.size() % 4 == 0)
                			if(!database.mSetIfL(list))
                				message = "[false, \"Different Value\"";
            			else
            				message = "[false, \"Wrong Number of Input\"]";
        			} catch (IndexOutOfBoundsException e) {
        				message = "[false, \"Index Out of Bounds\"";
        			} catch (NumberFormatException e) {
        				message = "[false, \"Wrong Input Index\"";
        			}
        			break;
        		}
        		
        		case "getl": {
        			try {
	        			var result = parse(args, 2);
	        			String key = result[0];
	        			int index = Integer.parseInt(result[1]);

	        			String getL = database.getL(key, index);
	    				message = "[true," + getL + "]";
        			} catch (IndexOutOfBoundsException e) {
        				message = "[false, \"Index Out Of Bounds\"]";
        			} catch (NumberFormatException e) {
        				message = "[false, \"Wrong Input Index\"";
        			}
        			
        			break;
        		}
        		
        		case "getall": {
        			String key = parse(args, 1)[0];

        			message = "[true,{" + database.getAll(key) + "}]";
        			break;
        		}
        		
        		case "mgetall": {
        			var keys = parseMultiple(args);

        			message = "[true," + database.mGetAll(keys) + "]";
        			break;
        		}
        		
        		case "deletel": {
        			var key = parse(args, 1)[0];

        			if (!database.deleteL(key))
        				message = "[false, \"Key Not Found\"";
        			break;
        		}
        		
        		case "ping": {
        			message = "[true, \"PONG\"]";
        			break;
        		}
        		
        		default: {
        			throw new WrongInputException("Wrong Input Command");
        		}
        	}
        	
			return message;
        }
		
        /**
         * Method for parsing a number of values.
         *
         * @param input input of the command.
         * @param number number of the values to parse.
         *
         * @return Array of number values.
         */
		private String[] parse(String input, int number) {
			var result = new String[number];
			String key = parseValue(input);

			String parsed = key;
			
			for (int i = 1; i < number; i++) {
	        	if (parsed == null)
	        		throw new WrongInputException("Wrong Input Key");
	        	
	        	
	        	String value = parseValue(input.substring(parsed.length() + input.indexOf(parsed)).strip());
	        	
	        	parsed = parsed.replaceAll("^\"|\"$", "");
	        	result[i - 1] = parsed;
	    		
	    		parsed = value;
			}
			
			if (number == 1) {
	        	if (key == null)
	        		throw new WrongInputException("Wrong Input Key");
				
	        	key = key.replaceAll("^\"|\"$", "");
	        	result[0] = key;
			} else 
				result[number - 1] = parsed;
			
			return result;
		}
        
        /**
         * Method for parsing a value.
         *
         * @param input input to parse.
         *
         * @return parsed value.
         */
        private String parseValue(String input) {
        	Pattern pattern = Pattern.compile("\\A\".+\"", Pattern.CASE_INSENSITIVE);
        	Matcher matcher = pattern.matcher(input);
        	
    		Pattern pattern2 = Pattern.compile("\\A\\H+", Pattern.CASE_INSENSITIVE);
    		Matcher matcher2 = pattern2.matcher(input);
    		
    		String s1 = matcher.find() ? matcher.group() : null;
    		String s2 = matcher2.find() ? matcher2.group() : null;
    		
    		if (s1 != null)
    			return s1;
    		if (s2 != null)
    			return s2;
    		return null;
        }
        
        /**
         * Method for parsing multiple values.
         *
         * @param input input of the command.
         *
         * @return list of parsed values.
         */
        private ArrayList<String> parseMultiple(String input) {
        	ArrayList<String> list = new ArrayList<String>();
        	String key;
        	int offset = 0;
        	
        	do {
        		key = parseValue(input.substring(offset).strip());
        		if (key != null) {
					offset += key.length() + 1;
					list.add(key.replaceAll("^\"|\"$", ""));
        		}
        	} while (key != null && offset < input.length());
        	
			return list;
        }
    }

    /**
     * Metodo principale di avvio del database.
     *
     * @param args argomenti passati a riga di comando.
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
		startServer();
    }
}