package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class client
{
	public static void main(String[] args) throws IOException
	{
		Socket echoSocket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		String outMessage = "\nHello from client";
		String inMessage ="";

		// Create socket connection with host address as localhost and port number with 38300
		try
		{
			System.out.println("creating sockets" );
			echoSocket = new Socket("localhost", 38300);
			System.out.println("new socket" );
			System.out.println("creating streams" );
			out = new ObjectOutputStream(echoSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(echoSocket.getInputStream());
			System.out.println("created streams" );
			
			try {
				System.out.println("sending message..." );
				for(int i=0; i < 10; i++){
					out.writeObject(outMessage+":"+i);	
				}
				
				System.out.println("getting messages..." );
				while((inMessage = (String) in.readObject()) != null){
					System.out.println("server>" + inMessage);	
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (UnknownHostException e)
		{
			System.err.println("Don't know about host: LocalHost.");
			System.exit(1);
		}
		catch (IOException e)
		{
			System.err.println("Couldn't get I/O for " + "the connection to: LocalHost:");
			System.exit(1);
		}
		finally
		{
			// Closing connection
			try
			{
				in.close();
				out.close();
				if (echoSocket != null)
				{
					echoSocket.close();
				}
			}
			catch (IOException ioException)
			{
				ioException.printStackTrace();
			}
		}
	}
}
