package ex3_2.com.Model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimulatorCommunicator {
    private Client client;
    public SimulatorCommunicator(){
        client = new Client();
        client.map = new LinkedHashMap<>();
    }
    public void AddAttribute(String name, String initialValue){
        client.map.put(name, Float.valueOf(initialValue).floatValue());
    }
    public void SetAttribute(String theAttribute, float value){
        client.map.put(theAttribute, Float.valueOf(value).floatValue());
    }
    private void StartCommunication(String address, String port)
            throws IOException, UnknownHostException {
        System.out.println(address + ", " + port + "\n");
        InetAddress adr = InetAddress.getByName(address);
        System.out.println("Address received.\n");
        client.StartFlight(adr, Integer.parseInt(port));
    }
    public String StartFlight(String address, String port) {
        try {
            System.out.println("trying to connect to the Flight Gear.\n");
            StartCommunication(address, port);
        }catch (UnknownHostException e){
            return "IP address not valid.";
        }
        catch (IOException e){
            return "Connection Error.";
        }
        return null;
    }
    public void endFlight(){
        client.shouldFly = false;
    }

    private class Client{
        public Map<String, Float> map;
        public boolean shouldFly = true;

        private void Communicate(PrintWriter writer){
            System.out.println("Communication started.\n");
            while (shouldFly){
                for (String name: map.keySet()) {
                    String s = "set /controls/flight/";
                    s += name + " " + map.get(name).toString();
                    writer.println(s);
                }
            }
        }
        public void StartFlight(InetAddress address, int port)
                throws IOException, UnknownHostException {
            System.out.println("In the inner class.\n");
            Socket s = new Socket(address, port);
            System.out.println("Socket opened.\n");
            OutputStream output = s.getOutputStream();
            System.out.println("Output stream opened.\n");
            PrintWriter writer = new PrintWriter(output, true);
            Communicate(writer);
            writer.close();
            output.close();
            s.close();
        }
    }
}
