/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
/**
 *
 * @author a1562339
 */
public class MulticastPeer {
    public static void main(String args[]){ 
		// args give message contents and destination multicast group (e.g. "228.5.6.7")
		MulticastSocket s =null;
		try {
                        String mensagem = "Mensagem teste MPO";
			InetAddress group = InetAddress.getByName("224.42.42.42");
			s = new MulticastSocket(6789);
			s.joinGroup(group);
 			byte [] m = mensagem.getBytes();
			DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
			s.send(messageOut);	
			byte[] buffer = new byte[1000];
 			for(int i=0; i< 3;i++) {		// get messages from others in group
 				DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
 				s.receive(messageIn);
 				System.out.println("Received:" + new String(messageIn.getData()));
  			}
			s.leaveGroup(group);		
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){System.out.println("IO: " + e.getMessage());
		}finally {if(s != null) s.close();}
	}		
}
